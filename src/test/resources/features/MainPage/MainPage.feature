@regression @mainPage
  Feature: Main Page functions

    Scenario: News should be opened once clicked
      Given I navigate to main page
      When I click on first news
      Then I should see the details on the opened page