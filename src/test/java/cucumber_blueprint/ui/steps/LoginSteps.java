package cucumber_blueprint.ui.steps;

import cucumber_blueprint.common.common_steps.BaseUiSteps;
import cucumber_blueprint.core.driver.UiScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class LoginSteps extends BaseUiSteps {

    public LoginSteps(UiScenarioContext uiScenarioContext) {
        super(uiScenarioContext);
    }

    @Given("User is not logged in")
    public void userIsNotLoggedIn() {
        driver.manage().deleteAllCookies();
    }

    @Then("Login page fields are displayed")
    public void loginPageFieldsAreDisplayed() throws InterruptedException {
        assertions.assertThat(pagesContainer.getLoginPage().checkUserNameExist()).isEqualTo(true);
        assertions.assertThat(pagesContainer.getLoginPage().checkPasswordExist()).isEqualTo(true);
        assertions.assertAll();
    }

}
