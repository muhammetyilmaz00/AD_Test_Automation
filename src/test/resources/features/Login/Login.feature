@regression @login
Feature: Login Function

  Scenario: Successful Login Feature
    Given I have a valid account
    And I navigate to login page
    When I enter the correct email
    And I click to "Ga verder" button
    And I click code button
    And I acquire & save email with SUBJECT -> Code om in te loggen:
    And Get email at CONTEXT-emailPath
    And I save login code
    And I enter login code
    Then I should be logged in successfully

  Scenario Outline: Unsuccessful Login cases for email
    Given I navigate to login page
    When I enter the wrong email "<email>"
    And I click to "Ga verder" button
    Then It should show a warning message
    Examples:
      | email     |
      | test_adnl |
      | 123123123 |

  Scenario: Username cannot be empty
    Given I navigate to login page
    When I click to "Ga verder" button
    Then It should show a required field message

  Scenario: Forgot Password Case
    Given I have a valid account
    And I navigate to login page
    When I click to forgot password link
    And I enter the correct email
    And I click to "Stuur mij een code" button
    And I acquire & save email with SUBJECT -> Code om jouw wachtwoord te wijzigen:
    And Get email at CONTEXT-emailPath
    And I save login code
    And I enter login code
    And I enter new password
    And I click to "Stel mijn wachtwoord in" button
    Then I should be logged in successfully