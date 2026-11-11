package GKDSpringServer.common.httpTypes;

public record BaseType(
        boolean ok,
        Object body,
        int statusCode,
        String gkdErrMsg,
        String message) {}
