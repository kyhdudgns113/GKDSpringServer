package GKDSpringServer.common.jwt;

import GKDSpringServer.common.commonObjTypes.CAuthObjTypes;
import GKDSpringServer.common.commonUtils.Secret;
import GKDSpringServer.db.user.UserEntity;
import GKDSpringServer.db.user.UserRepository;
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
    private final UserRepository userRepository;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expire-days:3}") long expireDays,
            UserRepository userRepository) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expireMs = expireDays * 24 * 60 * 60 * 1000;
        this.userRepository = userRepository;
    }

    public String create(UserEntity user) {
        Date now = new Date();
        String jwt = Jwts.builder()
                .subject(user.getUserId())
                .claim("userId", user.getUserId())
                .claim("userEmail", user.getUserEmail())
                .claim("userName", user.getUserName())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expireMs))
                .signWith(key)
                .compact();
        return Secret.encodeJwtFromServer(jwt);
    }

    public CAuthObjTypes.AuthObjectType verify(String jwt) {
        String rawJwt = Secret.decodeJwtFromClient(jwt);
        if (rawJwt == null || rawJwt.isBlank()) {
            rawJwt = jwt;
        }

        var claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(rawJwt)
                .getPayload();

        String userId = claims.get("userId", String.class);
        if (userId == null || userId.isBlank()) {
            userId = claims.getSubject();
        }

        UserEntity user = userRepository
                .findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다"));

        return new CAuthObjTypes.AuthObjectType(
                Secret.encodeJwtFromServer(rawJwt),
                user.getUserAuth(),
                user.getUserEmail(),
                user.getUserId(),
                user.getUserName());
    }
}
