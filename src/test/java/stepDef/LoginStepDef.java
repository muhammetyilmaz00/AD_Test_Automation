package stepDef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.configuration.ConfigurationException;
import pageObjects.web.ADPageObjects;
import utils.*;
import java.util.concurrent.TimeoutException;


public class LoginStepDef {

    final ADPageObjects adPageObjects = new ADPageObjects();
    String username, password;


    @Given("I have a valid account")
    public void iHaveAValidAccount() {
        username = ConfigurationReader.getCredentialsProperty("username");
        password = ConfigurationReader.getCredentialsProperty("password");
    }

    @And("I navigate to login page")
    public void iNavigateToLoginPage() {
        adPageObjects.navigateToMainPage();
        adPageObjects.clickInLoggenButton();
    }

    @When("I enter the correct email")
    public void iEnterTheCorrectEmail() {
        adPageObjects.enterEmail(username);
    }

    @And("I click to {string} button")
    public void iClickToButton(String button) {
        adPageObjects.clickOnButton(button);
    }

    @And("I enter the correct password")
    public void iEnterTheCorrectPassword() {
        adPageObjects.enterPassword(password);
    }

    @Then("I should be logged in successfully")
    public void iShouldBeLoggedInSuccessfully() {
        adPageObjects.checkLoggedIn();
    }

    @When("I enter the wrong email {string}")
    public void iEnterTheWrongEmail(String email) {
        adPageObjects.enterEmail(email);
    }

    @And("I enter the wrong password {string}")
    public void iEnterTheWrongPassword(String password) {
        adPageObjects.enterPassword(password);
    }

    @Then("It should show a warning message")
    public void itShouldShowAWarningMessage() {
        adPageObjects.checkWarning();
    }

    @Then("It should show a required field message")
    public void itShouldShowARequiredFieldMessage() {
        adPageObjects.checkRequiredFieldWarning();
    }

    @Given("I acquire & save email with {} -> {}")
    public void acquireEmail(EmailUtilities.Inbox.EmailField filterType, String filterKey) throws TimeoutException {
        adPageObjects.acquireSaveEmail(filterType, filterKey);
    }


    @And("Get email at {}")
    public void getHTML(String url) {
        adPageObjects.getHTML(url);
    }

    @And("I click code button")
    public void iClickCodeButton() {
        adPageObjects.clickCodeButton();
    }

    @And("I save login code")
    public void iSaveLoginCode() {
        adPageObjects.saveLoginCode();
    }

    @And("I enter login code")
    public void iEnterLoginCode() {
        adPageObjects.enterLoginCode();
    }

    @When("I click to forgot password link")
    public void iClickToForgotPasswordLink() {
        adPageObjects.clickForgotPassword();
    }

    @And("I enter new password")
    public void iEnterNewPassword() throws ConfigurationException {
        adPageObjects.enterNewPassword();
    }
}