package cucumber_blueprint.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.picocontainer.annotations.Inject;

public class LoginPage extends PageObjectBase {

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @FindBy(css="div[data-testid='loginInputUsername']")
    public WebElement username;

    @FindBy(css="div[data-testid='loginInputPassword']")
    public WebElement password;

    public boolean checkUserNameExist() {
        return isElementDisplayed(username);
    }

    public boolean checkPasswordExist() {
        return isElementDisplayed(password);
    }


}
