package GKDSpringServer.base.utils;

import GKDSpringServer.common.httpTypes.BaseType;

public class HttpResponseUtils {

    public static BaseType fail(int statusCode, String gkdErrMsg) {
        return new BaseType(false, null, statusCode, gkdErrMsg, "");
    }

    public static BaseType ok(Object body) {
        return new BaseType(true, body, 200, "", "");
    }
}
