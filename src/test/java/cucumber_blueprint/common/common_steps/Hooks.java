package cucumber_blueprint.common.common_steps;

import cucumber_blueprint.common.helpers.environment.Env;
import cucumber_blueprint.common.helpers.environment.YamlReader;
import cucumber_blueprint.core.driver.BaseDriver;
import cucumber_blueprint.core.driver.PagesContainer;
import cucumber_blueprint.core.driver.UiScenarioContext;
import cucumber_blueprint.core.driver.DriverUtils;
import cucumber_blueprint.common.properties.CommonProperties;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.Duration;
import java.util.Optional;

@Slf4j
public class Hooks {
    UiScenarioContext uiScenarioContext;

    public Hooks(UiScenarioContext uiScenarioContext) {
        this.uiScenarioContext = uiScenarioContext;
    }

    @BeforeAll
    public static void beforeAllScenarios() throws IOException {
        RestAssured.requestSpecification = new RequestSpecBuilder().build().noFilters().filter(new AllureRestAssured());

        //Set environment
        CommonProperties.envName = Optional.ofNullable(System.getProperty("env")).orElse("dev");
        CommonProperties.driverType = Optional.ofNullable(System.getProperty("driver")).orElse("chrome");

        //Set base urls
        Env testEnvironment = YamlReader.getEnvironmentFromYaml(CommonProperties.envName);
        CommonProperties.restUri = Optional.ofNullable(System.getProperty("REST_URI")).orElse(testEnvironment.restUri);
        CommonProperties.webUri = Optional.ofNullable(System.getProperty("WEB_APP_URI")).orElse(testEnvironment.webUri);
    }

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        log.info("Scenario: " + scenario.getName() + " started");
    }

    @Before(value = "@ui", order = 1)
    public void beforeUiScenario() throws MalformedURLException {
        uiScenarioContext.driver = BaseDriver.initializeDriver(CommonProperties.driverType);
        uiScenarioContext.wait = new WebDriverWait(uiScenarioContext.driver, Duration.ofSeconds(5));
        uiScenarioContext.driverUtils = new DriverUtils(uiScenarioContext.driver, uiScenarioContext.wait);
        uiScenarioContext.pagesContainer = new PagesContainer(uiScenarioContext.driver, uiScenarioContext.wait);
    }

    @After(value = "@ui", order = 1)
    public void afterUiScenario(Scenario scenario) throws Exception {
        //Take screenshot if test fail
        if (scenario.isFailed() && uiScenarioContext.driver != null) {
            uiScenarioContext.driverUtils.takeScreenshot(scenario.getName());
            uiScenarioContext.driverUtils.addScreenshotAllure(scenario.getName());
        }
        if (uiScenarioContext.driver != null) {
            uiScenarioContext.driver.manage().deleteAllCookies();
            uiScenarioContext.driver.quit();
        }
    }

    @After(order = 0)
    public void afterScenario(Scenario scenario) {
        log.info("Scenario: " + scenario.getName() + " finished");
    }
  }
