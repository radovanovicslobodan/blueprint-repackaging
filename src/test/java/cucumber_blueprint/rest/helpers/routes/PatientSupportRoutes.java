package cucumber_blueprint.rest.helpers.routes;

public class PatientSupportRoutes {
    static final String PATIENT_SUPPORT  = "/patient-support";
    static final String API = "/api";
    static final String VERSION = "/v1";
    static final String HEALTH_COACHES = "/health-coaches";
    static final String PATIENTS = "/patients";

    public static String healthcoachecontroller(){
        return PATIENT_SUPPORT + API + VERSION + HEALTH_COACHES;
    }

    public static String patientcontroller(){
        return PATIENT_SUPPORT + API + VERSION + PATIENTS;
    }
}
