package GKDSpringServer.url.auth;

import GKDSpringServer.common.httpTypes.HttpRequestTypes;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public Map<String, Object> signUp(HttpRequestTypes.SignUpRequestType request) {
        return notImplemented();
    }

    public Map<String, Object> refreshToken(String authorization) {
        return notImplemented();
    }

    private Map<String, Object> notImplemented() {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("ok", false);
        res.put("body", null);
        res.put("statusCode", 501);
        res.put("gkdErrMsg", "미구현");
        res.put("message", "아직 구현되지 않은 API입니다");
        return res;
    }
}
