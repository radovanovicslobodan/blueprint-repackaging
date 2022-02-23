package cucumber_blueprint.rest.helpers;

import cucumber_blueprint.common.helpers.backend.RestHelpers;
import cucumber_blueprint.common.properties.CommonProperties;
import cucumber_blueprint.core.api.RestScenarioContext;
import cucumber_blueprint.rest.helpers.routes.LoginRoutes;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class LoginHelpers {
    RestScenarioContext restScenarioContext;

    public LoginHelpers(RestScenarioContext restScenarioContext) {
        this.restScenarioContext = restScenarioContext;
    }

    public Response postLogin(String username, String password) {

        RequestSpecBuilder builder = new RequestSpecBuilder();
        //Prepare request
        builder.setBaseUri(CommonProperties.restUri);
        builder.setBasePath(LoginRoutes.login());
        builder.setContentType("application/json");
        builder.setBody("{\r\n    \"password\": \""+password+"\",\r\n    \"username\": \""+username+"\"\r\n}");
        RequestSpecification rSpec = builder.build();

        return RestHelpers.sendPostRequest(rSpec);
    }

}
