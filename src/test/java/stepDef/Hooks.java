package stepDef;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.Driver;
import utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;

public class Hooks {

    private static Scenario scenario;
    public static Scenario getScenario(){
        return scenario;
    }
    public static String getScenarioName(){
        return scenario.getName();
    }
    public static Collection<String> getScenarioTags(){
        return scenario.getSourceTagNames();
    }

    @Before
    public void init(Scenario scenario) {
        Hooks.scenario = scenario;
    }

    @Before
    public static void initialSequence(){
        File dir = new File("inbox");
        File[] emails = dir.listFiles();
        assert emails != null;
        String mediaType;
        if(emails.length>0) for (File email : emails) {
            try {mediaType = Files.probeContentType(email.toPath());}
            catch (IOException e) {throw new RuntimeException(e);}
            if (mediaType != null && mediaType.equals("text/html")) email.delete();
        }
    }


    @Before
    public void initLog4j() {
        // the path of log4j.properties file
        String log4jConfPath = "src/test/resources/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);

        LogUtils.logInfo("Started Scenario: "+getScenarioName());
    }

    @After
    public void tearDown() {
        if (scenario.isFailed()) {
            final byte[] screenshot = (
                    (TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
        }
        Driver.closeDriver();
        LogUtils.logInfo("Finished Scenario: "+getScenarioName());
    }
}
