package GKDSpringServer.common.httpTypes.auth;

public interface AuthResponseTypes {

    record Auth_LogInBodyType(
            String jwtFromServer,
            int userAuth,
            String userId,
            String userEmail,
            String userName) {}

    record Auth_RefreshTokenBodyType(
            String jwtFromServer,
            int userAuth,
            String userEmail,
            String userId,
            String userName) {}

    record Auth_SignUpBodyType(
            String jwtFromServer,
            int userAuth) {}
}
