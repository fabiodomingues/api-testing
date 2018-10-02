Feature: Authentication

  As a registered user
  I want to authenticate
  So that I can access secure resources

  Scenario: Authenticate user
    Given the following authentication data
      | name      | John Doe  |
      | username  | john.doe  |
      | password  | j04n!@    |
    When call authentication api
    Then the status code of response should be 201
    And content type should be in JSON format
    And response body attribute token should not be null