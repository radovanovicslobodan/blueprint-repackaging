package cucumber_blueprint.core.driver;

import cucumber_blueprint.core.driver.DriverUtils;
import cucumber_blueprint.core.driver.PagesContainer;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

// pagesContainer and utils are here because we need only one instance of each object per scenario and we want to share
// that same instance between step definition classes
public class UiScenarioContext {
    public WebDriver driver;
    public WebDriverWait wait;
    public PagesContainer pagesContainer;
    public DriverUtils driverUtils;
}
