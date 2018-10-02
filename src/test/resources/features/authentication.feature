Feature: Authentication

  As a registered user
  I want to authenticate
  So that I can access secure resources

  Scenario: Successful login
    Given user already exists
      | name      | John Doe  |
      | username  | john.doe  |
      | password  | j04n!@    |
    When I call the registration api
    Then the status code of response should be 201
    And content type should be in JSON format
    And response body attribute id should not be null
    And response body attribute name should be equals "John Doe"
    And response body attribute username should be equals "john.doe"
    And response body attribute password should be null

  Scenario: Failed login using wrong credentials
    Given user already exists
      | name      | John Doe  |
      | username  | john.doe  |
      | password  | j04n!@    |
    And I use the following credentials
      | username  | john  |
      | password  | 123   |
    When I call the registration api
    Then the status code of response should be 404
    And content type should be in JSON format
    And response body attribute message should be equals "Invalid credentials"