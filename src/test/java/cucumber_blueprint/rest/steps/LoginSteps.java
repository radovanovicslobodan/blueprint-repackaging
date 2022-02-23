package cucumber_blueprint.rest.steps;

import cucumber_blueprint.common.common_steps.BaseRestSteps;
import cucumber_blueprint.core.api.RestScenarioContext;
import cucumber_blueprint.rest.helpers.LoginHelpers;
import io.cucumber.java.en.When;

public class LoginSteps extends BaseRestSteps {
    LoginHelpers loginHelpers;

    public LoginSteps(RestScenarioContext restScenarioContext) {
        super(restScenarioContext);
        loginHelpers = new LoginHelpers(restScenarioContext);
    }

    @When("Login request with username {string} and password {string} is sent")
    public void loginWithUserAndPassword(String username, String password) {
        restScenarioContext.response = loginHelpers.postLogin(username, password);
    }


}
