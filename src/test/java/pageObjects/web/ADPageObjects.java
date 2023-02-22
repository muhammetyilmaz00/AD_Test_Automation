package pageObjects.web;

import context.ContextStore;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import pageObjects.interfaces.ADPageObjectsInterface;
import utils.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static utils.EmailUtilities.Inbox.EmailField.CONTENT;
import static utils.StringUtilities.Color.BLUE;
import static utils.StringUtilities.Color.GRAY;

public class ADPageObjects implements ADPageObjectsInterface {

    StringUtilities stringUtilities = new StringUtilities();
    String title;
    String loginButtonXpath = "//span[contains(text(),'Inloggen')]";
    String usernameId = "username";
    String passwordId = "password";
    String repeatPasswordId = "passwordRepeat";
    String userProfileButtonXpath = "//*[@for='userMenu']";
    String userProfileDetailsXpath = "(//*[@class='menu__popover']//span)[1]";
    String errorBlockEmailId = "errorBlock-Email";
    String errorBlockNonEmptyId = "errorBlock-NotEmpty";
    String codeButtonCssSelector = "[data-selector=\"sel-login-with-challenge-link\"]";
    String searchButtonXpath = "//*[@data-gtm='hoofdnavigatie/zoeken']";
    String searchFormXpath = "//*[@class='sub-nav__search']//*[@type='text']";
    String resultsTitleXpath = "//*[@class='results__title']";
    String firstNewsXpath = "(//*[@class='tile-grid']//li)[1]";
    String firstNewsTitleXpath = "(//*[@class='tile-grid']//li)[1]//h2";
    String closeButtonXpath = "//*[@id='PURCHASE_HYBRID__OVERLAY']//button[@data-gtm='button/close']";
    String headerXpath = "//*[@class='article__wrapper']//header//h1";
    String zoekButtonXpath = "//*[@class='submit']";
    String submitButtonXpath = "//*[@type='submit']";
    String forgotPasswordCssSelector = "[data-selector=\"sel-reset-password-link\"]";

    @Override
    public void navigateToMainPage(){
        Driver.getDriver();
        Helper.navigateURL(ConfigurationReader.getProperty("mainPage"));

        // Click "Akkoord" to accept privacy setting
        Helper.waitMilliseconds(1000);
        List<WebElement> element = Driver.getDriver().findElements(By.cssSelector("[title=\"SP Consent Message\"]"));
        int i = element.size();
        Driver.getDriver().switchTo().frame(i);
        Helper.waitAndClickByXpath("//button[@title='Akkoord']");

        Helper.waitMilliseconds(3000);
        Helper.verifyCurrentPageURL(ConfigurationReader.getProperty("mainPage"));
    }

    @Override
    public void clickInLoggenButton(){
        Helper.waitAndClickByXpath(loginButtonXpath);
    }

    @Override
    public void enterEmail(String email){
        Helper.waitAndSendKeysById(usernameId, email);
    }

    @Override
    public void clickOnButton(String button){
        Helper.waitMilliseconds(2000);
        Helper.waitAndClickByXpath("//*[@type='submit' and contains(text(),'" + button + "')]");
        Helper.waitMilliseconds(2000);
    }

    @Override
    public void enterPassword(String password){
        Helper.waitAndSendKeysById(passwordId, password);
    }

    @Override
    public void checkLoggedIn(){
        Helper.waitMilliseconds(2000);
        Helper.waitAndClickByXpath(userProfileButtonXpath);
        String userDetails = Helper.getTextOfWebElementByXpath(userProfileDetailsXpath);
        String nameSurname = ConfigurationReader.getProperty("name&surname");
        Assert.assertEquals(nameSurname,userDetails);
    }

    @Override
    public void checkWarning(){
        String warningMessage = "Dit is geen geldig e-mailadres";
        String message = Helper.getTextOfWebElementByID(errorBlockEmailId);
        Assert.assertEquals(warningMessage,message);
    }

    @Override
    public void checkRequiredFieldWarning(){
        String warningMessage = "Dit veld is verplicht";
        String message = Helper.getTextOfWebElementByID(errorBlockNonEmptyId);
        Assert.assertEquals(warningMessage,message);
    }

    @Override
    public void clickCodeButton(){
        Helper.waitAndClickByCSSSelector(codeButtonCssSelector);
    }

    @Override
    public void clickFirstNews(){
        Helper.waitMilliseconds(1000);
        // get title and delete hidden hyphen in title
        title = Helper.getTextOfWebElementByXpath(firstNewsTitleXpath).replaceAll("\u00AD","");
        Helper.waitAndClickByXpath(firstNewsXpath);

        // if close button appears, click to close
        try {
            Helper.waitAndClickByXpath(closeButtonXpath);
        } catch (Exception e){
            System.out.println("No close button found");
        }
    }

    @Override
    public void checkDetailsOnOpenedPage(){
        Helper.waitMilliseconds(2000);
        // get title and delete hidden hyphen in title
        String titleOnPage = Helper.getTextOfWebElementByXpath(headerXpath).replaceAll("\u00AD","");

        Assert.assertEquals(title,titleOnPage);
    }

    @Override
    public void clickSearchButton(){
        Helper.waitAndClickByXpath(searchButtonXpath);
    }

    @Override
    public void writeSearchForm(String word){
        Helper.waitAndSendKeysByXpath(searchFormXpath,word);
    }

    @Override
    public void clickZoekButton(){
        Helper.waitAndClickByXpath(zoekButtonXpath);
    }

    @Override
    public void checkResultsTitle(String word){
        Assert.assertTrue(Helper.getTextOfWebElementByXpath(resultsTitleXpath).contains(word));
    }

    @Override
    public void checkNoResultsFoundTitle(String word){
        Assert.assertTrue(Helper.getTextOfWebElementByXpath(resultsTitleXpath).equalsIgnoreCase("0 Resultaten voor “"+word+"” gevonden"));
    }

    @Override
    public String contextCheck(@NotNull String input){
        TextParser parser = new TextParser();
        if (input.contains("CONTEXT-"))
            input = ContextStore.get(parser.parse("CONTEXT-", null, input)).toString();
        else if (input.contains("RANDOM-")){
            boolean useLetters = input.contains("LETTER");
            boolean useNumbers = input.contains("NUMBER");
            String keyword = "";
            if (input.contains("KEYWORD")) keyword = parser.parse("-K=", "-", input);
            int length = Integer.parseInt(parser.parse("-L=", null, input));
            input = stringUtilities.generateRandomString(keyword, length, useLetters, useNumbers);
        }
        else if (input.contains("UPLOAD-")){
            String relativePath = parser.parse("UPLOAD-", null, input);
            input = new FileUtilities().getAbsolutePath(relativePath);
        }
        return input;
    }

    @Override
    public String getEmail(
            EmailUtilities.Inbox.EmailField filter,
            String filterKey,
            Boolean print,
            Boolean save) throws TimeoutException {
        double initialTime = System.currentTimeMillis();
        LogUtils.logInfo("Acquiring email...");

        EmailUtilities.Inbox inbox;
        do {
            inbox = new EmailUtilities.Inbox(
                    "pop.gmail.com",
                    "995",
                    ConfigurationReader.getCredentialsProperty("username"),
                    ConfigurationReader.getCredentialsProperty("popPassword"),
                    "ssl",
                    filter,
                    filterKey,
                    print,
                    save,
                    save
            );
            try {
                TimeUnit.SECONDS.sleep(1);}
            catch (InterruptedException e) {throw new RuntimeException(e);}
            if (System.currentTimeMillis() - initialTime > 45000) throw new TimeoutException("Verification email did not arrive!");
        }
        while (inbox.messages.size() == 0);

        LogUtils.logInfo("Email acquired!");
        return inbox.messages.get(0).get(CONTENT).toString();
    }

    @Override
    public void acquireSaveEmail(EmailUtilities.Inbox.EmailField filterType, String filterKey) throws TimeoutException {
        // it's not the best way for now, retry mechanism will be replaced
        Helper.waitMilliseconds(3000);
        LogUtils.logInfo("Acquiring & saving email(s) by " +
                stringUtilities.highlighted(BLUE, filterType.name()) +
                stringUtilities.highlighted(GRAY, " -> ") +
                stringUtilities.highlighted(BLUE, filterKey)
        );
        getEmail(filterType, filterKey, false, true);

        File dir = new File("inbox");
        String absolutePath = null;
        for (File email : Objects.requireNonNull(dir.listFiles()))
            try {
                boolean nullCheck = Files.probeContentType(email.toPath())!=null;
                if (nullCheck && Files.probeContentType(email.toPath()).equals("text/html")) {
                    absolutePath = "file://" + email.getAbsolutePath().replaceAll("#","%23");
                    break;
                }
            }
            catch (IOException e) {throw new RuntimeException(e);}

        ContextStore.put("emailPath", absolutePath);
    }

    @Override
    public void getHTML(String url){
        url = contextCheck(url);

        String parentHandle = Driver.getDriver().getWindowHandle();

        // switch to new tab
        Driver.getDriver().switchTo().newWindow(WindowType.TAB);

        LogUtils.logInfo("Navigating to the email @" + url);
        Driver.getDriver().navigate().to(url);

        ContextStore.put("parentHandle",parentHandle);
    }

    @Override
    public void saveLoginCode(){
        String code = Helper.getTextOfWebElementByID("challenge_code");
        ContextStore.put("challengeCode",code);
        Driver.getDriver().close();
        // get back to main tab
        Driver.getDriver().switchTo().window(ContextStore.get("parentHandle").toString());
    }

    @Override
    public void enterLoginCode(){
        Helper.waitAndSendKeysByCss("input#challengeCode",ContextStore.get("challengeCode").toString());
        Helper.waitAndClickByXpath(submitButtonXpath);
    }

    @Override
    public void clickForgotPassword(){
        Helper.waitAndClickByCSSSelector(forgotPasswordCssSelector);
    }

    @Override
    public void enterNewPassword() throws ConfigurationException {
        String newPassword = RandomStringUtils.randomAlphanumeric(15);
        Helper.waitAndSendKeysById(passwordId, newPassword);
        Helper.waitAndSendKeysById(repeatPasswordId, newPassword);
        ConfigurationReader.updateCredentialsProperty("password", newPassword);
    }
}
