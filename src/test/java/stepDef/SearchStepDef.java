package stepDef;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pageObjects.web.ADPageObjects;

public class SearchStepDef {

    final ADPageObjects adPageObjects = new ADPageObjects();

    @When("I click on search button")
    public void iClickOnSearchButton() {
        adPageObjects.clickSearchButton();
    }

    @And("I enter {string} into the search bar")
    public void iEnterIntoTheSearchBar(String word) {
        adPageObjects.writeSearchForm(word);
    }

    @And("I click on Zoek button")
    public void iClickOnZoekButton() {
        adPageObjects.clickZoekButton();
    }

    @Then("I should see the articles about {string} on the page")
    public void iShouldSeeTheArticlesAboutOnThePage(String word) {
        adPageObjects.checkResultsTitle(word);
    }

    @Then("I should see no articles found message on the page for {string}")
    public void iShouldSeeNoArticlesFoundMessageOnThePageFor(String word) {
        adPageObjects.checkNoResultsFoundTitle(word);
    }

}