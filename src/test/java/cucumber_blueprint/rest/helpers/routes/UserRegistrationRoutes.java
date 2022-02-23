package cucumber_blueprint.rest.helpers.routes;

public class UserRegistrationRoutes {
    static final String USER_REGISTRATION  = "/user-registration";
    static final String API = "/api";
    static final String VERSION = "/v1";
    static final String REGISTRATION = "/registration";
    static final String PATIENT = "/patient";

    public static String registration(){
        return USER_REGISTRATION + API + VERSION + REGISTRATION + PATIENT;
    }
}
