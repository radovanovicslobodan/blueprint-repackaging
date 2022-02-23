package cucumber_blueprint.common.helpers.ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
public class BaseDriver {

    /**
     * Method will initialize driver depends on desired operating system
     *
     * @param desiredDriver operating system or docker env, check switch statement
     * @return initialized driver
     * @throws MalformedURLException
     * @author dino.racd
     */
    public static WebDriver initializeDriver(String desiredDriver) throws MalformedURLException {
        WebDriver driver;
        switch (desiredDriver) {
            case "headlessChrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions(true));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                return driver;
            case "headlessFirefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions(true));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                return driver;
            case "chrome":
                //  WebDriverManager.chromedriver().config().setForceDownload(true);
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions(false));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                return driver;
            case "chromeGrid":
                //  WebDriverManager.chromedriver().config().setForceDownload(true);
                WebDriverManager.chromedriver().setup();
                driver = new RemoteWebDriver(new URL(""), chromeOptions(false));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                return driver;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions(false));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                return driver;
            default:
                throw new IllegalStateException("Unexpected value: " + desiredDriver);
        }
    }

    public static ChromeOptions chromeOptions(boolean headless){
        HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
        chromePrefs.put("profile.default_content_settings.popups", 0);
        chromePrefs.put("download.default_directory", System.getProperty("user.dir")+"\\src\\test\\resources\\downloaded_files");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(headless);
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--ignore-certificate-errors");
        chromeOptions.setExperimentalOption("prefs", chromePrefs);
        //     chromeOptions.addArguments("window-size=1920,1080");
        return  chromeOptions;
    }

    public static FirefoxOptions firefoxOptions(boolean headless){
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("start-maximized");
        firefoxOptions.addArguments("--disable-extensions");
        firefoxOptions.addArguments("--ignore-certificate-errors");
        firefoxOptions.addArguments("--disable-infobars");
        firefoxOptions.addArguments("--disable-gpu");
        firefoxOptions.setHeadless(headless);
        // FIXME: headless???
        firefoxOptions.setAcceptInsecureCerts(headless);
        return  firefoxOptions;
    }
}
