package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

public class BookApiSteps {

    private Response response;

    @Given("I am authenticated as an admin")
    public void iAmAuthenticatedAsAnAdmin() {
        // Set up RestAssured base URI and admin authentication
        RestAssured.baseURI = "http://localhost:7081";
        RestAssured.authentication = RestAssured.basic("admin", "password");
    }

    @Given("I am authenticated as A User")
    public void iAmAuthenticatedAsAUser() {
        // Set up RestAssured base URI and admin authentication
        RestAssured.baseURI = "http://localhost:7081";
        RestAssured.authentication = RestAssured.basic("user", "password");
    }

    @When("I send a POST request to {string} with the following details:")
    public void iSendAPOSTRequestToWithTheFollowingDetails(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        // Extract book details from DataTable with null checks
        String title = dataTable.cell(1, 0) != null ? dataTable.cell(1, 0).trim() : "";
        String author = dataTable.cell(1, 1) != null ? dataTable.cell(1, 1).trim() : "";

        // Create JSON payload
        String jsonBody = String.format("{\"title\": \"%s\", \"author\": \"%s\"}", title, author);

        // Send POST request
        response = given()
                .header("Content-Type", "application/json")
                .body(jsonBody)
            .when()
                .post(endpoint);

        System.out.println("Response Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.prettyPrint());
    }


    @Then("I should receive a response with status code {int}")
    public void iShouldReceiveAResponseWithStatusCode(int statusCode) {
        // Assert the response status code
        Assert.assertEquals(response.getStatusCode(), statusCode, "Unexpected status code");
    }

    @Then("the response body should contain the book title {string}")
    public void theResponseBodyShouldContainTheBookTitle(String expectedTitle) {
        // Assert the book title in response body
        String actualTitle = response.jsonPath().getString("title");
        Assert.assertEquals(actualTitle, expectedTitle, "Book title mismatch");
    }

    @Then("the response body should contain the author {string}")
    public void theResponseBodyShouldContainTheAuthor(String expectedAuthor) {
        // Assert the author in response body
        String actualAuthor = response.jsonPath().getString("author");
        Assert.assertEquals(actualAuthor, expectedAuthor, "Author mismatch");
    }

    @Then("the response body should contain {string}")
    public void theResponseBodyShouldContain(String expectedMessage) {
        // Assert that the response body contains the expected message
        String actualResponse = response.asString();
        Assert.assertTrue(actualResponse.contains(expectedMessage), "Expected message not found in response body");
    }




    // Nawangi



}