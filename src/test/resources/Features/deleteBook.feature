Feature: Delete Book API

  Scenario: Successfully delete an existing book
    Given User is authenticated as an admin
    And there is a book with the following details:
      | id   | title                 | author               |
      | 101  | The Great Gatsby      | F. Scott Fitzgerald  |
    When I send a DELETE request to "/api/books/1"
    Then I should receive a response with status code for deletion 200
    And the response body should contain the message "Book deleted successfully"
    And the book with ID "200" should no longer exist in the system

  Scenario: Try to delete a non-existent book
    Given User is authenticated as an admin
    When I send a DELETE request to "/api/books/999"
    Then I should receive a response with status code for deletion 404
    And the response body should contain the message "Book not found"

  Scenario: Try to delete a book without providing an ID
    Given User is authenticated as an admin
    When I send a DELETE request to "/api/books/"
    Then I should receive a response with status code for deletion 400
    And the response body should contain the message "Invalid book ID"

  Scenario: User tries to delete a book
    Given User is authenticated as a user
    When I send a DELETE request to "/api/books/1"
    Then I should receive a response with status code for deletion 403
    And the response body should contain the message "Permission denied: You are not authorized to delete books"