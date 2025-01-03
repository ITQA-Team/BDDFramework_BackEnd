package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

public class UpdateBookSteps {
    private Response response;

    @Given("I am authenticated as an admin for update")
    public void iAmAuthenticatedAsAnAdminForUpdate() {
        RestAssured.baseURI = "http://localhost:7081";
    }

    @Given("I am authenticated as a user for update")
    public void iAmAuthenticatedAsAUser() {
        RestAssured.baseURI = "http://localhost:7081";
    }


    @When("I send a PUT request to {string} with the following details:")
    public void iSendAPUTRequestToWithTheFollowingDetails(String endpoint, io.cucumber.datatable.DataTable dataTable) {
        int id = Integer.parseInt(dataTable.cell(1, 0));
        // Remove double quotes from title and author values
        String title = dataTable.cell(1, 1).replaceAll("\"", "");
        String author = dataTable.cell(1, 2).replaceAll("\"", "");

        String jsonBody = String.format("{\"id\":%d,\"title\":\"%s\",\"author\":\"%s\"}", id, title, author);

        response = given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
                .body(jsonBody)
                .when()
                .put(endpoint);

        System.out.println("Request Body: " + jsonBody);
        System.out.println("Response: " + response.asString());
    }

    @Then("I should receive an update response with status code {int}")
    public void iShouldReceiveAnUpdateResponseWithStatusCode(int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode, "Unexpected status code for update");
    }

    @Then("the updated response body should contain the book title {string}")
    public void theUpdatedResponseBodyShouldContainTheBookTitle(String expectedTitle) {
        String actualTitle = response.jsonPath().getString("title");
        Assert.assertEquals(actualTitle, expectedTitle.replaceAll("\"", ""), "Updated book title mismatch");
    }

    @Then("the updated response body should contain the author {string}")
    public void theUpdatedResponseBodyShouldContainTheAuthor(String expectedAuthor) {
        String actualAuthor = response.jsonPath().getString("author");
        Assert.assertEquals(actualAuthor, expectedAuthor.replaceAll("\"", ""), "Updated author mismatch");
    }
}