Feature: Registration

  As an user
  I want to create a account
  So that I can authenticate to access secure resources

  Scenario: User successfully creates an account
    Given I have the following registration data
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

  Scenario: Return error when creating a user with an existing username
    Given already exists a user with username "alice"
    And I have the following registration data
      | name      | Alice  |
      | username  | alice  |
      | password  | aa123  |
    When I call the registration api
    Then the status code of response should be 400
    And content type should be in JSON format
    And response body attribute message should be equals "Username already in use."