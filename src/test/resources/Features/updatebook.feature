Feature: Update Book API

  Scenario: Successfully update an existing book as an Admin
    Given I am authenticated as an admin
    When I send a PUT request to "/api/books/1" with the following details:
      | id | title                 | author          |
      | 1  | "Updated"       | "Doyle"|
    Then I should receive an update response with status code 200
    And the updated response body should contain the book title "Updated"
    And the updated response body should contain the author "Doyle"
#
  Scenario: Try to update a book with missing author as an Admin
    Given I am authenticated as an admin
    When I send a PUT request to "/api/books/1" with the following details:
      | id | title                 | author |
      | 1  | "Updated Title" | ""     |
    Then I should receive an update response with status code 400

  Scenario: Try to update a book author without changing title
    Given I am authenticated as an admin
    When I send a PUT request to "/api/books/1" with the following details:
      | id | title                 | author |
      | 1  | "Updated" | "shelly"     |
    Then I should receive an update response with status code 200


  Scenario: user should not be allowed to successfully update a book
    Given I am authenticated as A User
    When I send a PUT request to "/api/books/1" with the following details:
      | id | title                 | author          |
      | 1  | "Updated"       | "Updated_b"|
    Then I should receive an update response with status code 403


