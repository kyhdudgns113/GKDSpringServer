package GKDSpringServer.common.commonObjTypes;

public interface CAuthObjTypes {

    record AuthObjectType(
            String jwtFromServer,
            int userAuth,
            String userEmail,
            String userId,
            String userName) {}
}
