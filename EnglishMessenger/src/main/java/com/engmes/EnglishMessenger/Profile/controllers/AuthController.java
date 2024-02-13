package com.engmes.EnglishMessenger.Profile.controllers;

import com.engmes.EnglishMessenger.Profile.jwt.JwtRequest;
import com.engmes.EnglishMessenger.Profile.jwt.JwtToken;

import com.engmes.EnglishMessenger.Profile.models.User;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import io.jsonwebtoken.security.SignatureException;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtToken jwtToken;

    @PostMapping
    @PermitAll
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest, HttpServletResponse response) {
        logger.info("POST-метод работает");
        Optional<User> userOptional = userService.findByEmail(authRequest.getEmail());

        if (userOptional.isEmpty()) {
            logger.info("Такой пользователь не зарегистрирован");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Такой пользователь не зарегистрирован");
        }

        User user = userOptional.get();
        String saltedPassword = user.getSalt() + authRequest.getPassword();
        if (BCrypt.checkpw(saltedPassword, user.getPassword())) {
            UserDetails userDetails = userService.loadUserByEmail(authRequest.getEmail());

            String accessToken = jwtToken.generateAccessToken(userDetails);
            String refreshToken = jwtToken.generateRefreshToken(userDetails);

            Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

            accessTokenCookie.setMaxAge(86400);
            refreshTokenCookie.setMaxAge(86400);

            accessTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setPath("/auth/refresh");

            response.addCookie(accessTokenCookie);
            response.addCookie(refreshTokenCookie);

            logger.info("Токены созданы.\n Access token: " + accessToken + "\n Refresh token: " + refreshToken);
            return ResponseEntity.ok("Авторизация успешна");
        } else {
            logger.info("Неверный пароль или почта для пользователя: " + authRequest.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный пароль или почта");
        }
    }

    @PostMapping("/refresh")
    @PermitAll
    public ResponseEntity<?> refreshTokens(@CookieValue(value = "refreshToken", required = false) String refreshTokenArg) {
        try {
            Claims claims = jwtToken.getAllClaimsFromRefreshToken(refreshTokenArg);
            String username = claims.getSubject();
            long expirationTime = claims.getExpiration().getTime();
            long currentTime = System.currentTimeMillis();

            if (expirationTime > currentTime) {

                try {

                    UserDetails userDetails = userService.loadUserByUsername(username);

                    String accessToken = jwtToken.generateAccessToken(userDetails);
                    String refreshToken = jwtToken.generateRefreshToken(userDetails);

                    Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
                    Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);

                    accessTokenCookie.setMaxAge(86400); // token expiration date
                    refreshTokenCookie.setMaxAge(86400);

                    accessTokenCookie.setHttpOnly(true); // httpOnly flag
                    refreshTokenCookie.setHttpOnly(true);
                    refreshTokenCookie.setPath("/auth/refresh");

                    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

                    assert response != null;
                    response.addCookie(accessTokenCookie);
                    response.addCookie(refreshTokenCookie);

                    logger.info("Refresh token is valid");
                    return ResponseEntity.ok("Refresh token is valid");

                } catch (UsernameNotFoundException e) {
                    logger.info("there is no user with that login");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("there is no user with that login");
                }

            } else {
                logger.info("Refresh token has been expired");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh token has been expired");
            }
        } catch (ExpiredJwtException e) {
            logger.info("Refresh token has been expired");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Refresh token has been expired");
        } catch (SignatureException e) {
            logger.info("Token signature validation error");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token signature validation error");
        }

    }
}
