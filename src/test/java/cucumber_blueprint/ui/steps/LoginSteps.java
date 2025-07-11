package cucumber_blueprint.ui.steps;

import cucumber_blueprint.common.common_steps.BaseUiSteps;
import cucumber_blueprint.core.driver.UiScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LoginSteps extends BaseUiSteps {

    public LoginSteps(UiScenarioContext uiScenarioContext) {
        super(uiScenarioContext);
    }

    @Then("Login page fields are displayed")
    public void loginPageFieldsAreDisplayed() {
        assertions.assertThat(pagesContainer.getLoginPage().checkUserNameExist()).isEqualTo(true);
        assertions.assertThat(pagesContainer.getLoginPage().checkPasswordExist()).isEqualTo(true);
        assertions.assertAll();
    }

}
