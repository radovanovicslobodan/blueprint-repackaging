package cucumber_blueprint.common.common_steps;

import cucumber_blueprint.core.driver.PagesContainer;
import cucumber_blueprint.core.driver.UiScenarioContext;
import cucumber_blueprint.core.driver.DriverUtils;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseUiSteps {
    protected UiScenarioContext uiScenarioContext;
    protected SoftAssertions assertions;

    // these properties set as properties here only because this way in step definition classes we don't always
    // have to write sharedDriver.utils or sharedDriver.pagesContainer
    protected DriverUtils driverUtils;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected PagesContainer pagesContainer;

    public BaseUiSteps(UiScenarioContext uiScenarioContext) {
        this.uiScenarioContext = uiScenarioContext;
        this.assertions = new SoftAssertions();

        this.driver = uiScenarioContext.driver;
        this.wait = uiScenarioContext.wait;
        this.driverUtils = uiScenarioContext.driverUtils;
        this.pagesContainer = uiScenarioContext.pagesContainer;
    }
}
