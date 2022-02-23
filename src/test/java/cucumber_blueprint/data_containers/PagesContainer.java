package cucumber_blueprint.data_containers;

import cucumber_blueprint.ui.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PagesContainer {
    WebDriver driver;
    WebDriverWait wait;

    private LoginPage loginPage;

    public PagesContainer(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // Each page will be instantiated when needed
    public LoginPage getLoginPage() {
        return (loginPage == null) ? loginPage = new LoginPage(driver, wait) : loginPage;
    }
}
