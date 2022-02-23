@regression
Feature:Get Employee dummy test

  Scenario: Rest happy path example - scenario
    When GetEmployee request is sent with user id "1"
    Then Response contains desired employee data