package example.demo.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtToken {
    @Value("${jwt.secretAccess}")
    private String secretAccess;
    @Value("${jwt.secretRefresh}")
    private String secretRefresh;
    @Value("${jwt.lifetime}")
    private Duration jwtDuration;
    @Value("${jwt.lifetimeRefresh}")
    private Duration jwtDurationRefresh;

    public String generateAccessToken(UserDetails userDetails){
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtDuration.toMillis());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(Keys.hmacShaKeyFor(secretAccess.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails){
        Date issuedDate = new Date();
        Date expiredDate = new Date(issuedDate.getTime() + jwtDurationRefresh.toMillis());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(Keys.hmacShaKeyFor(secretRefresh.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getAllClaimsFromAccessToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(secretAccess)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims getAllClaimsFromRefreshToken(String token){
        Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretRefresh.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
