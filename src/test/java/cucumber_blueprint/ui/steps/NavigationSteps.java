package cucumber_blueprint.ui.steps;

import cucumber_blueprint.common.common_steps.BaseUiSteps;
import cucumber_blueprint.common.properties.CommonProperties;
import cucumber_blueprint.common.properties.Urls;
import cucumber_blueprint.core.driver.UiScenarioContext;
import io.cucumber.java.en.When;

public class NavigationSteps extends BaseUiSteps {

    public NavigationSteps(UiScenarioContext uiScenarioContext) {
        super(uiScenarioContext);
    }

    @When("User navigates to {string} page")
    public void userNavigatesToPage(String page) {
        switch (page){
            case "login":
                driver.get(Urls.login());
            case "base":
                driver.get(CommonProperties.webUri);
        }
    }
}
