package GKDSpringServer.base.utils;

import GKDSpringServer.common.httpTypes.HTTPResponseTypes;

public class HttpResponseUtils {

    public static HTTPResponseTypes.BaseType fail(int statusCode, String gkdErrMsg) {
        return new HTTPResponseTypes.BaseType(false, null, statusCode, gkdErrMsg, "");
    }

    public static HTTPResponseTypes.BaseType ok(Object body) {
        return new HTTPResponseTypes.BaseType(true, body, 200, "", "");
    }
}
