package cucumber_blueprint.rest.helpers.routes;

public class SymptomsTrackingRoutes {
    static final String SYMPTOMS_TRACKING  = "/symptoms-tracking";
    static final String API = "/api";
    static final String VERSION = "/v1";
    static final String PATIENTS = "/patients";

    public static String patiens(){
        return SYMPTOMS_TRACKING + API + VERSION + PATIENTS;
    }

}
