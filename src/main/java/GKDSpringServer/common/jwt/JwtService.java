package GKDSpringServer.common.jwt;

import GKDSpringServer.db.user.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expireMs;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expire-ms}") long expireMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expireMs = expireMs;
    }

    public String create(UserEntity user) {
        Date now = new Date();
        return Jwts.builder()
                .subject(user.getUserId())
                .claim("userAuth", user.getUserAuth())
                .claim("userEmail", user.getUserEmail())
                .claim("userName", user.getUserName())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expireMs))
                .signWith(key)
                .compact();
    }
}
