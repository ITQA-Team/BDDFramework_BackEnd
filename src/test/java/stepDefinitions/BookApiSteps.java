package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
//import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.Assert;

import static io.restassured.RestAssured.*;

import org.apache.http.HttpStatus;

public class BookApiSteps {

    private Response response;

    @Given("I am authenticated as an admin")
    public void iAmAuthenticatedAsAnAdmin() {
        RestAssured.baseURI = "http://localhost:7081";
    }

    @When("I send a POST request to {string} with the following details:")
    public void iSendAPOSTRequestToWithTheFollowingDetails(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        String title = dataTable.cell(1, 0);
        String author = dataTable.cell(1, 1);

        String jsonBody = String.format("{\"title\": \"%s\", \"author\": \"%s\"}", title, author);

        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
                .body(jsonBody)
            .when()
                .post(endpoint);

        System.out.println("Response: " + response.asString());
    }

    @Then("I should receive a response with status code {int}")
    public void iShouldReceiveAResponseWithStatusCode(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode, "Unexpected status code");
    }

    @Then("the response body should contain the book title {string}")
    public void theResponseBodyShouldContainTheBookTitle(String expectedTitle) {
        String actualTitle = response.jsonPath().getString("title");
        Assert.assertEquals(actualTitle, expectedTitle, "Book title mismatch");
    }

    @Then("the response body should contain the author {string}")
    public void theResponseBodyShouldContainTheAuthor(String expectedAuthor) {
        String actualAuthor = response.jsonPath().getString("author");
        Assert.assertEquals(actualAuthor, expectedAuthor, "Author mismatch");
    }

    @Then("the response body should contain {string}")
    public void theResponseBodyShouldContain(String expectedMessage) {
        String actualResponse = response.asString();
        Assert.assertTrue(actualResponse.contains(expectedMessage), "Expected message not found");
    }
}