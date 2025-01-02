Feature: Create Book API

  Scenario: Successfully create a new book
    Given I am authenticated as an admin
    When I send a POST request to "/api/books" with the following details:
      | title                 | author               |
      | The Great Gatsby      | F. Scott Fitzgerald  |
    Then I should receive a response with status code 201
    And the response body should contain the book title "The Great Gatsby"
    And the response body should contain the author "F. Scott Fitzgerald"

  Scenario: Try to create a book with missing title
    Given I am authenticated as an admin
    When I send a POST request to "/api/books" with the following details:
      | title | author        |
      |       | Jane          |
    Then I should receive a response with status code 400
    And the response body should contain "Invalid | Empty Input Parameters in the Request"