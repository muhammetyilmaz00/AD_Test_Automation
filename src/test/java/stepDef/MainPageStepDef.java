package stepDef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.web.ADPageObjects;

public class MainPageStepDef {

    final ADPageObjects adPageObjects = new ADPageObjects();

    @Given("I navigate to main page")
    public void iNavigateToMainPage() {
        adPageObjects.navigateToMainPage();
    }

    @When("I click on first news")
    public void iClickOnFirstNews() {
        adPageObjects.clickFirstNews();
    }

    @Then("I should see the details on the opened page")
    public void iShouldSeeTheDetailsOnTheOpenedPage() {
        adPageObjects.checkDetailsOnOpenedPage();
    }
}
