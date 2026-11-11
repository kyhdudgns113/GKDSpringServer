package GKDSpringServer.url.auth;

import static GKDSpringServer.base.utils.HttpResponseUtils.fail;
import static GKDSpringServer.base.utils.HttpResponseUtils.ok;

import GKDSpringServer.common.commonObjTypes.CAuthObjTypes;
import GKDSpringServer.common.commonUtils.Secret;
import GKDSpringServer.common.httpTypes.BaseType;
import GKDSpringServer.common.httpTypes.auth.AuthRequestTypes;
import GKDSpringServer.common.httpTypes.auth.AuthResponseTypes;
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

    public BaseType refreshToken(String authorization) {
        String jwtFromClient = authorization;

        if (jwtFromClient == null
                || jwtFromClient.length() <= Secret.BAREER_PREFIX_LENGTH + Secret.BAREER_POSTFIX_LENGTH) {
            return fail(400, "유효하지 않은 토큰입니다");
        }

        try {
            CAuthObjTypes.AuthObjectType authObj = jwtService.verify(jwtFromClient);
            UserEntity user = userRepository.findByUserId(authObj.userId()).orElse(null);
            if (user == null) {
                return fail(400, "유효하지 않은 토큰입니다");
            }

            return ok(new AuthResponseTypes.Auth_RefreshTokenBodyType(
                    jwtService.create(user),
                    user.getUserAuth(),
                    user.getUserEmail(),
                    user.getUserId(),
                    user.getUserName()));
        } catch (Exception e) {
            return fail(400, "유효하지 않은 토큰입니다");
        }
    }

    @Transactional
    public BaseType signUp(AuthRequestTypes.SignUpRequestType request) {
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

        return ok(new AuthResponseTypes.Auth_SignUpBodyType(jwtService.create(user), userAuth));
    }
}
