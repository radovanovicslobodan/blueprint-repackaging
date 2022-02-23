package cucumber_blueprint.data_containers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestScenarioContext {
    public Response response;
    public RequestSpecification rSpec;
}
