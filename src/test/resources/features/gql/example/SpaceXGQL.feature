@examples @api @gql @regression
Feature: I can write tests for GraphQL

  Scenario: I can fetch data from query
    Given I create GraphQl query request
    When I send GraphQl request
    Then I receive response with data

  Scenario: I can fetch filtered data from query
    Given I create GraphQl query request with filter
    When I send GraphQl request
    Then I receive response with data


  Scenario: I can fetch data from Rocket query
    Given I create GraphQl query Rocket request
    When I send GraphQl request
    Then I receive response with rocket data