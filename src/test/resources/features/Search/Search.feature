@regression @search
Feature: Search feature

  Scenario Outline: Successful Search function
    Given I navigate to main page
    When I click on search button
    And I enter "<word>" into the search bar
    And I click on Zoek button
    Then I should see the articles about "<word>" on the page
    Examples:
      | word     |
      | Football |
      | Banana   |

  Scenario: Successful Search function for no-result searches
    Given I navigate to main page
    When I click on search button
    And I enter "no-result-found-word" into the search bar
    And I click on Zoek button
    Then I should see no articles found message on the page for "no-result-found-word"