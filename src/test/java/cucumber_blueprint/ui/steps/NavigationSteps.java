package cucumber_blueprint.ui.steps;

import cucumber_blueprint.common.common_steps.BaseUiSteps;
import cucumber_blueprint.data_containers.UiScenarioContext;
import cucumber_blueprint.common.helpers.ui.Urls;
import cucumber_blueprint.data_containers.CommonProperties;
import io.cucumber.java.en.When;

public class NavigationSteps extends BaseUiSteps {

    public NavigationSteps(UiScenarioContext uiScenarioContext) {
        super(uiScenarioContext);
    }

    @When("User navigates to login page")
    public void userNavigatesToLoginPage() {
        driver.get(Urls.login());
    }

    @When("User navigates to base page")
    public void userNavigatesToBasePage() {
        driver.get(CommonProperties.webUri);
    }
}
