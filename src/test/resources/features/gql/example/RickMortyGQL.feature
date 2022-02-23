@gql @regression
Feature:Rick and Morty dummy test

  Scenario: GQL happy path example - scenario
    When GetUser query is called
    Then Response contains desired user data