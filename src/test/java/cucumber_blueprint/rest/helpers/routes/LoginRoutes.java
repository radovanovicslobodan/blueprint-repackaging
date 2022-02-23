package cucumber_blueprint.rest.helpers.routes;

public class LoginRoutes {

    static final String IAM = "/iam";
    static final String API = "/api";
    static final String VERSION = "/v1";
    static final String USER = "/user";

    public static String login(){
        return IAM + API + VERSION + USER + "/login";
    }
}
