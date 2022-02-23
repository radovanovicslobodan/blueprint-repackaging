@rest @regression
Feature: Login

  Scenario: Verify that user can log in with valid credentials
    When Login request with username "username" and password "password" is sent
    Then Response status is 200