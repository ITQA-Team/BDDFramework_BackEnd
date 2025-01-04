Feature: Retrieve Book

  Scenario: User retrieves a book by ID
    Given I am authenticated as A User
    When I send a GET request to "/api/books/1"
    Then I should receive a response with status code 200
    And the response body should contain the book title "The Great Gatsby"
    And the response body should contain the author "F. Scott Fitzgerald"

  Scenario: Admin retrieves a book by ID
    Given I am authenticated as an admin
    When I send a GET request to "/api/books/1"
    Then I should receive a response with status code 200
    And the response body should contain the book title "The Great Gatsby"
    And the response body should contain the author "F. Scott Fitzgerald"



  Scenario: Admin retrieves all books
    Given I am authenticated as an admin
    When I send a GET request to "/api/books"
    Then I should receive a response with status code 200
    And the response body should contain a list of books

  Scenario: User retrieves all books
    Given I am authenticated as A User
    When I send a GET request to "/api/books"
    Then I should receive a response with status code 200
    And the response body should contain a list of books

