package cucumber_blueprint.rest.helpers;

import cucumber_blueprint.common.helpers.back_end.RestHelpers;
import cucumber_blueprint.data_containers.RestScenarioContext;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestGetEmployeeHelpers {
    RestScenarioContext restScenarioContext;

    public RestGetEmployeeHelpers(RestScenarioContext restScenarioContext) {
        this.restScenarioContext = restScenarioContext;
    }

    public Response getEmployee(String employeeId) {

        RequestSpecBuilder builder = new RequestSpecBuilder();

        //Prepare request
        builder.setBaseUri("restUriExample");
        builder.setBasePath("/api/v1/employee/" + employeeId);
        builder.setContentType("application/json");
        RequestSpecification rSpec = builder.build();

        return RestHelpers.sendGetRequest(rSpec);
    }


}
