@ui @regression
Feature: Login

  Background:
    Given User is not logged in

  Scenario: Verify that all fields are displayed on login page
    When User navigates to "login" page
    Then Login page fields are displayed
#    Then On "login" page "username" element is displayed
#    And "password" element is displayed

  Scenario: Verify that login page is displayed on base url
    When User navigates to "base" page
    Then Login page fields are displayed