package cucumber_blueprint.ui.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

public abstract class PageObjectBase {
    public WebDriver driver;
    public WebDriverWait wait;

    public PageObjectBase(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    /**
     * Wait for element to be clickable
     *
     * @param element
     * @return WebElement after wait
     * @author dino.rac
     */
    public WebElement waitToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait to finish page loanding after JavaScript executor is finished
     *
     * @param waitTime
     * @author dino.rac
     */
    public void waitForLoad(long waitTime) {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            new WebDriverWait(driver, Duration.ofSeconds(waitTime)).until(expectation);
        } catch (Throwable error) {
        }
    }


    /**
     * Method will perform Click on Element via JavaScript executor
     *
     * @param element
     * @author dino.rac
     */
    public void performClick(WebElement element) {
        WebElement ele = element;
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", ele);
    }


    /**
     * Method perform scroll down via JavaScript executor
     *
     * @param scrollableElement, waitBeforeNextScroll, scrollTimes
     * @author Vladimir Papuga
     */
    public void performScrollDown(WebElement scrollableElement, int waitBeforeNextScroll, int scrollTimes) {
        WebElement div = scrollableElement;
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        int i = 1;
        while (i <= scrollTimes) {
            jse.executeScript("arguments[0].scrollBy(0,document.body.scrollHeight)", div, waitBeforeNextScroll, scrollTimes);
            waitForLoad(waitBeforeNextScroll);
            i++;
        }
    }


    /**
     * Wait for 5 seconds that condition become true
     * @param condition desired condition
     * @author dino.rac
     */
    public void waitToBeTrue(Boolean condition) {
        waitSpecificTimeToBeTrue(5, condition);
    }

    /**
     * Wait for 5 seconds that condition become true
     * @param condition desired condition
     * @param time waitng period
     * @author dino.rac
     */
    public void waitSpecificTimeToBeTrue(Integer time, Boolean condition) {
        await("Wait condition to be true")
                .atMost(time, TimeUnit.SECONDS).until(() -> condition);
    }

    /**
     * Method is Element Present
     *
     * @author Vladimir Papuga
     */
    public boolean isElementDisplayed(WebElement element) {
        try {
            if(element.isDisplayed()){
                return true;
            }else{
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    /**
     * Method will perform Clear on input field
     *
     * @param element
     * @author Vladimir Papuga
     */
    public void performClear(WebElement element) {
        WebElement ele = element;
        ele.sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);

    }

    /**
     * Method will perform mouse hover on element
     *
     * @param element
     * @author Vladimir Papuga
     */
    public void performMouseHover(WebElement element) {
        Actions actions = new Actions(driver);
        WebElement ele = element;
        actions.moveToElement(ele);

    }

    /**
     * Method will handle OS side for upload file
     *
     * @param
     * @author Vladimir Papuga
     */
    public void uploadFileOSSide(String filePath) throws AWTException {
        //put path to your image in a clipboard
        StringSelection ss = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        //imitate mouse events like ENTER, CTRL+C, CTRL+V
        Robot robot = new Robot();
        robot.delay(500);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(90);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }


    /**
     * Method will perform Double click
     *
     * @param element
     * @author Vladimir Papuga
     */
    public void performDoubleClick(WebElement element) {
        int i = 0;
        for(i=0;i<2;i++){
            waitToBeClickable(element).click();
        }
    }

    /**
     * Method will convert RGBA color format to Hexadecimal color format
     *
     * @param element
     * @author Vladimir Papuga
     */
    public String rgbaToHexColor(WebElement element) {
        String color = element.getCssValue("color");
        String[] hexValue = color.replace("rgba(", "").replace(")", "").split(",");
        hexValue[0] = hexValue[0].trim();
        int hexValue1 = Integer.parseInt(hexValue[0]);
        hexValue[1] = hexValue[1].trim();
        int hexValue2 = Integer.parseInt(hexValue[1]);
        hexValue[2] = hexValue[2].trim();
        int hexValue3 = Integer.parseInt(hexValue[2]);
        String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
        return actualColor.toUpperCase();
    }

    public void waitUntilPageIsLoaded(String pagePath) {
        wait.until(ExpectedConditions.urlContains(pagePath));
    }

}
