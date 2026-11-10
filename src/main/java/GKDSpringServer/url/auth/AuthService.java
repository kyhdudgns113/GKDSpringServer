package GKDSpringServer.url.auth;

import static GKDSpringServer.base.utils.HttpResponseUtils.fail;
import static GKDSpringServer.base.utils.HttpResponseUtils.ok;

import GKDSpringServer.common.httpTypes.HTTPResponseTypes;
import GKDSpringServer.common.httpTypes.HttpRequestTypes;
import GKDSpringServer.common.jwt.JwtService;
import GKDSpringServer.db.user.UserEntity;
import GKDSpringServer.db.user.UserRepository;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+$");

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public HTTPResponseTypes.BaseType refreshToken(String authorization) {
        return fail(501, "미구현");
    }

    @Transactional
    public HTTPResponseTypes.BaseType signUp(HttpRequestTypes.SignUpRequestType request) {
        int userAuth = 0;
        String userEmail = request.userEmail();
        String userId = request.userId();
        String userName = request.userName();
        String userPassword = request.userPassword();

        if (userEmail == null || !EMAIL_PATTERN.matcher(userEmail).matches()) {
            return fail(400, "이메일 형식이어야 합니다.");
        }

        if (userRepository.existsByUserId(request.userId()) || userRepository.existsByUserEmail(userEmail)) {
            return fail(400, "중복된 값입니다");
        }

        UserEntity user = new UserEntity();
        user.setUserAuth(userAuth);
        user.setUserEmail(userEmail);
        user.setUserId(userId);
        user.setUserName(userName);
        user.setHashedPassword(passwordEncoder.encode(userPassword));

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            return fail(400, "중복된 값입니다");
        }

        HTTPResponseTypes.Auth_SignUpBodyType body = new HTTPResponseTypes.Auth_SignUpBodyType(jwtService.create(user),
                userAuth);
        return ok(body);
    }
}
