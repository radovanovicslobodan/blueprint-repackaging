package cucumber_blueprint.data_containers;

import cucumber_blueprint.common.helpers.ui.DriverUtils;
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
