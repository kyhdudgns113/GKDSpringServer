package GKDSpringServer.common.httpTypes;

public interface HTTPResponseTypes {
    record BaseType(
            boolean ok,
            Object body,
            int statusCode,
            String gkdErrMsg,
            String message) {
    }

    record Auth_SignUpBodyType(
            String jwtFromServer,
            int userAuth) {
    }
}
