package cucumber_blueprint.rest.steps;

import cucumber_blueprint.core.api.RestScenarioContext;
import cucumber_blueprint.rest.helpers.RestGetEmployeeHelpers;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;

public class RestExampleSteps {

    RestScenarioContext restScenarioContext;
    SoftAssertions assertions;
    RestGetEmployeeHelpers restGetEmployeeHelpers;

    public RestExampleSteps(RestScenarioContext restScenarioContext) {
        this.restScenarioContext = restScenarioContext;
        assertions = new SoftAssertions();
        restGetEmployeeHelpers = new RestGetEmployeeHelpers(restScenarioContext);
    }

    @When("GetEmployee request is sent with user id {string}")
    public void get_employee_request_is_sent(String userId) {
       restScenarioContext.response = restGetEmployeeHelpers.getEmployee(userId);
    }

    @Then("Response contains desired employee data")
    public void response_contains_desired_employee_data() {
        //Deserialization with gson is shown in gql example so here we will use fetch via jsonPath
        assertions.assertThat(restScenarioContext.response.jsonPath().get("status").toString()).isEqualTo("success");
        assertions.assertAll();
    }


}
