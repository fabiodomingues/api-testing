Feature: Registration

  As a new user
  I want to register
  So that I can authenticate to access secure resources

  Scenario: Register a new user
    Given the following registration data
      | name      | John Doe  |
      | username  | john.doe  |
      | password  | j04n!@    |
    When call registration api
    Then the status code of response should be 201
    And content type should be in JSON format
    And response body attribute id should not be null
    And response body attribute name should be equals John Doe
    And response body attribute username should be equals john.doe
    And response body attribute password should be null