package GKDSpringServer.common.httpTypes;

public interface HttpRequestTypes {

    record SignUpRequestType(
            String userId,
            String userName,
            String userEmail,
            String userPassword) {}
}
