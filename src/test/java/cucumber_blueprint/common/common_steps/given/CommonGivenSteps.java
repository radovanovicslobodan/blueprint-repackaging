package cucumber_blueprint.common.common_steps.given;

import cucumber_blueprint.common.common_steps.BaseUiSteps;
import cucumber_blueprint.core.driver.UiScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CommonGivenSteps extends BaseUiSteps {

    public CommonGivenSteps(UiScenarioContext uiScenarioContext) {
        super(uiScenarioContext);
    }

    @Given("User is not logged in")
    public void userIsNotLoggedIn() {
        driver.manage().deleteAllCookies();
    }

    @When("{string} element is displayed")
    public void isElementDisplayed(String field) throws Exception {
        WebElement element = driver.findElement(By.id(field));
        Assert.assertTrue(element.isDisplayed());
    }
}
