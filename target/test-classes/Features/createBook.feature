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
    
    Scenario: Try to create a book with missing author
    Given I am authenticated as an admin
    When I send a POST request to "/api/books" with the following details:
      | title | author        |
      |  Pride and Prejudice |				|
    Then I should receive a response with status code 400
    And the response body should contain "Invalid | Empty Input Parameters in the Request"

  Scenario: Successfully create a new book
    Given I am authenticated as A User
    When I send a POST request to "/api/books" with the following details:
      | title                 | author               |
      | 1948      | George Orwell  |
    Then I should receive a response with status code 201
    And the response body should contain the book title "1948"
    And the response body should contain the author "George Orwell"

  Scenario: Try to create a book with an already existing title
    Given I am authenticated as A User
    When I send a POST request to "/api/books" with the following details:
      | title  | author  |
      | The Great Gatsby  | F. Scott Fitzgerald |
    Then I should receive a response with status code 208
    And the response body should contain "Book Already Exists"

  Scenario: Submitting invalid data with integers for author and title
    Given I am authenticated as A User
    When I send a POST request to "/api/books" with the following details:
      | title | author |
      | 1234  | 6789   |
    Then I should receive a response with status code 400
    And the response body should contain "Invalid | Empty Input Parameters in the Request"
