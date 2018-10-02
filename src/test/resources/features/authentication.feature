Feature: Authentication

  As a registered user
  I want to authenticate
  So that I can access secure resources

  Scenario: Successful login
    Given user already exists
      | name      | Fabio            |
      | username  | fabio.domingues  |
      | password  | fabio            |
    When I call the login api
    Then the status code of response should be 200
    And content type should be in JSON format
    And response body attribute id should not be null
    And response body attribute name should be equals "Fabio"
    And response body attribute username should be equals "fabio.domingues"
    And response body attribute password should be null

  Scenario: Failed login using wrong credentials
    Given user already exists
      | name      | Fernando  |
      | username  | fer.nando |
      | password  | F3rN4     |
    And I use the following credentials
      | username  | fer.nando  |
      | password  | 123        |
    When I call the login api
    Then the status code of response should be 404
    And content type should be in JSON format
    And response body attribute message should be equals "Invalid credentials."