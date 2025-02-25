package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;


public class DeleteBookApiSteps {

    private Response response;

    @Given("User is authenticated as an admin")
    public void iAmAuthenticatedAsAnAdmin() {
        // Set up RestAssured base URI and admin authentication
        RestAssured.baseURI = "http://localhost:7081";
        RestAssured.authentication = RestAssured.basic("admin", "password");
    }
    @Given("User is authenticated as a user")
    public void iAmAuthenticatedAsAUser() {
        // Set up RestAssured base URI and admin authentication
        RestAssured.baseURI = "http://localhost:7081";
        RestAssured.authentication = RestAssured.basic("user", "password");
    }
    @Given("there is a book with the following details:")
    public void thereIsABookWithTheFollowingDetails(io.cucumber.datatable.DataTable dataTable) {

        String id = dataTable.cell(1, 0).trim();
        String title = dataTable.cell(1, 1).trim();
        String author = dataTable.cell(1, 2).trim();

        String jsonBody = String.format("{\"id\": \"%s\", \"title\": \"%s\", \"author\": \"%s\"}", id, title, author);
        Response createResponse = given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
                .when()
                .post("/api/books");

        if (createResponse.getStatusCode() != 201) {
            System.out.println("Book creation skipped as it already exists.");
        }
    }


    @When("I send a DELETE request to {string}")
    public void iSendADELETERequestTo(String endpoint) {
        try {
            response = given()
                    .header("Content-Type", "application/json")
                    .when()
                    .delete(endpoint);

            System.out.println("Response Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.prettyPrint());
        } catch (Exception e) {
            System.out.println("Error while sending DELETE request: " + e.getMessage());
            response = null;
        }
    }
    @Then("I should receive a response with status code for deletion {int}")
    public void iShouldReceiveAResponseWithStatusCode(int statusCode) {
        Assert.assertNotNull(response, "Response object is null. Request may have failed.");
        Assert.assertEquals(response.getStatusCode(), statusCode, "Unexpected status code");
    }

    @Then("the response body should contain the message {string}")
    public void theResponseBodyShouldContainTheMessage(String expectedMessage) {
        Assert.assertNotNull(response, "Response object is null. Request may have failed.");
        String actualResponse = response.asString();
        Assert.assertTrue(actualResponse.contains(expectedMessage), "Expected message not found in response body");
    }

    @Then("the book with ID {string} should no longer exist in the system")
    public void theBookWithIDShouldNoLongerExistInTheSystem(String bookId) {

        Response getResponse = given()
                .when()
                .get("/api/books/" + bookId);

        Assert.assertEquals(getResponse.getStatusCode(), 404, "Book still exists in the system");
    }

}
