package utils;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.NoSuchElementException;

public class Helper {

    private static final Duration durationTimeout = Duration.ofSeconds(10);

    public static String getTextOfWebElementByXpath(String xpath) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        scrollIntoViewByXpath(xpath);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        return element.getText();
    }

    public static String getTextOfWebElementByID(String id) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        scrollIntoViewById(id);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
        return element.getText();
    }

    public static String getTextOfWebElement(By by) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        scrollIntoView(by);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return element.getText();
    }

    public static void scrollIntoViewByXpath(String xpath) {
        WebElement element = Driver.getDriver().findElement(By.xpath(xpath));
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollIntoViewByCssSelector(String css) {
        WebElement element = Driver.getDriver().findElement(By.cssSelector(css));
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollIntoViewById(String id) {
        WebElement element = Driver.getDriver().findElement(By.id(id));
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollIntoView(By by) {
        WebElement element = Driver.getDriver().findElement(by);
        JavascriptExecutor js = ((JavascriptExecutor) Driver.getDriver());
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static String alertMessage() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = Driver.getDriver().switchTo().alert();
        return alert.getText();
    }

    public static void acceptAlert() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = Driver.getDriver().switchTo().alert();
        alert.accept();
    }

    public static void dismissAlert() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = Driver.getDriver().switchTo().alert();
        alert.dismiss();
    }

    public static void hoverToElementByXpath(String xpath) {
        Actions actions = new Actions(Driver.getDriver());
        WebElement menuOption = Driver.getDriver().findElement(By.xpath(xpath));
        actions.moveToElement(menuOption).perform();
    }

    public static void hoverToElementById(String id) {
        Actions actions = new Actions(Driver.getDriver());
        WebElement menuOption = Driver.getDriver().findElement(By.id(id));
        actions.moveToElement(menuOption).perform();
    }

    public static void waitAndClickByXpath(String xpath) {
        scrollIntoViewByXpath(xpath);
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        element.click();
    }

    public static void waitAndClickByCSSSelector(String css) {
        scrollIntoViewByCssSelector(css);
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));
        element.click();
    }

    public static void waitAndClickByID(String id) {
        scrollIntoViewById(id);
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        element.click();
    }

    public static void waitAndSendKeysByXpath(String xpath, String text) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        element.sendKeys(text);
    }

    public static void waitAndSendKeysByCss(String css, String text) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(css)));
        element.sendKeys(text);
    }

    public static void waitAndSendKeysById(String id, String text) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), durationTimeout);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id(id)));
        element.sendKeys(text);
    }

    public static void switchToTab(int tab) {
        ArrayList<String> tabs = new ArrayList<>(Driver.getDriver().getWindowHandles());
        Driver.getDriver().switchTo().window(tabs.get(tab));
    }

    public static List<String> newWindowText(By by) {
        String mainWindow = Driver.getDriver().getWindowHandle();
        Set<String> set = Driver.getDriver().getWindowHandles();
        List<String> list = new ArrayList<>();

        for (String childWindow : set) {
            if (!mainWindow.equalsIgnoreCase(childWindow)) {
                Driver.getDriver().switchTo().window(childWindow);
                list.add(getTextOfWebElement(by));
            }
        }
        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(mainWindow);
        return list;
    }

    public static boolean isElementPresent(String xpath) {
        try {
            return Driver.getDriver().findElement(By.xpath(xpath)) != null;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean isElementDisplayed(String xpath) {
        WebElement element = Driver.getDriver().findElement(By.xpath(xpath));
        element.isDisplayed();
        return true;
    }

    public static void verifyCurrentPageURL(String URL) {
        Assert.assertEquals(Driver.getDriver().getCurrentUrl(), URL);
    }

    public static void navigateURL(String URL) {
        Driver.getDriver().navigate().to(URL);
    }

    public static void takeSnapShot() throws Exception {
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        TakesScreenshot scrShot = ((TakesScreenshot) Driver.getDriver());
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File("src/test/java/screenshot/screen" + sdf.format(d) + ".png");
        FileUtils.copyFile(SrcFile, DestFile);
    }

    public static void waitMilliseconds(int i) {
        try {
            Thread.sleep(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

