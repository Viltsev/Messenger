package example.demo.controllers;

import example.demo.JWT.JwtRequest;
import example.demo.JWT.JwtToken;
import example.demo.UserService;
import example.demo.data.User;
import example.demo.error.AppError;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final UserService userService;
    private final JwtToken jwtToken;
    private final AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @PermitAll
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        logger.info("POST-метод работает");
        Optional<User> user = userService.findByEmail(authRequest.getEmail());

        if (user.isEmpty()) {
            logger.info("Такой пользователь не зарегистрирован");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Такой пользователь не зарегистрирован");
        }

        if (passwordEncoder.matches(authRequest.getPassword(), user.get().getPassword())) {
            String saltPassword = passwordEncoder.encode(user.get().getSalt() + authRequest.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), saltPassword));
        } else {
            logger.info("Неверный пароль или почта");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверный пароль или почта");
        }

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

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        assert response != null;
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        logger.info("токены созданы.\n access token: " + accessToken + "\nrefresh token: " + refreshToken);
        return ResponseEntity.ok("Авторизация успешна");
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

