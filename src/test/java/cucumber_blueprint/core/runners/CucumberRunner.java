package cucumber_blueprint.core.runners;

import cucumber_blueprint.common.helpers.cukedoctor.GenerateDoc;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "classpath:features"
        },
        glue = {
                "cucumber_blueprint"
        },
        tags = "@regression",
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "json:target/cucumber.json",
                "junit:target/test-results/Cucumber.xml"
        }
)
@Slf4j
public class CucumberRunner {
    @AfterClass
    public static void afterAllScenarios() {
        //Generate report as file
        new GenerateDoc().generate();
    }
}
