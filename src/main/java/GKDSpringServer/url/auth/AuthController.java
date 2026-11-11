package GKDSpringServer.url.auth;

import GKDSpringServer.common.httpTypes.BaseType;
import GKDSpringServer.common.httpTypes.auth.AuthRequestTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/refreshToken")
    public BaseType refreshToken(
            @RequestHeader(value = "Authorization", required = false) String authorization) {
        return authService.refreshToken(authorization);
    }

    @PostMapping("/signUp")
    public BaseType signUp(@RequestBody AuthRequestTypes.SignUpRequestType request) {
        return authService.signUp(request);
    }

}
