package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


import java.time.Duration;
import java.util.Locale;

public class Driver {

    /*
    Creating a private constructor, so we are closing
    access to the object of this class from outside the class
     */
    private Driver() {}

    /* We make WebDriver private, because we want to close access from outside the class.
       We make it static because we will use it in a static method.  */
    private static WebDriver driver;

    /*    Create a re-usable utility method which will return same driver instance when we call it  */
    public static WebDriver getDriver() {

        if (driver == null) {

            /*
            We read our browserType from configuration.properties.
            This way, we can control which browser is opened from outside our code, from configuration.properties.
             */
            String browserType = ConfigurationReader.getProperty("browser").toLowerCase(Locale.ROOT);


            /*
                Depending on the browserType that will be return from configuration.properties file
                switch statement will determine the case, and open the matching browser
            */
            switch (browserType) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;
                case "firefox":
                    WebDriverManager.chromedriver().setup();
                    driver = new FirefoxDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;
                case "edge":
                    WebDriverManager.chromedriver().setup();
                    driver = new EdgeDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;
            }
        }
        return driver;
    }

    // This method will make sure our driver value is always null after using quit() method
    public static void closeDriver() {
        if (driver != null) {
//            driver.close(); // this line will terminate the existing session. value will not even be null
            driver.quit();
          //  Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
            driver = null;
        }
    }

}
