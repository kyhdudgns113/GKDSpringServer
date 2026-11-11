package GKDSpringServer.common.httpTypes.auth;

public interface AuthRequestTypes {

    record LogInRequestType(
            String userIdOrEmail,
            String password) {}

    record SignUpRequestType(
            String userId,
            String userName,
            String userEmail,
            String userPassword) {}
}
