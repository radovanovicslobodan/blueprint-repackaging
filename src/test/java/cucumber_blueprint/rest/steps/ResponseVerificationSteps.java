package cucumber_blueprint.rest.steps;

import cucumber_blueprint.core.api.RestScenarioContext;
import io.cucumber.java.en.Then;
import org.assertj.core.api.SoftAssertions;

public class ResponseVerificationSteps {
    RestScenarioContext restScenarioContext;
    SoftAssertions assertions;

    public ResponseVerificationSteps(RestScenarioContext restScenarioContext) {
        this.restScenarioContext = restScenarioContext;
        assertions = new SoftAssertions();
    }

    @Then("Response status is {int}")
    public void responseStatusIs(int value) {
        assertions.assertThat(restScenarioContext.response.getStatusCode()).isEqualTo(value);
        assertions.assertAll();
    }
}
