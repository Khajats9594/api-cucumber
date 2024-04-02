package stepDefinition;

import contants.FrameworkConstants;
import enums.ConfigProperties;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.response.ExchangeRateResponseBody;
import utiles.PropertyUtils;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ExchangeRateSteps{

    @Before
    public void setup() {
        RestAssured.baseURI = PropertyUtils.get(ConfigProperties.BASEURI);
    }
    private ExchangeRateResponseBody exchangeRateResponseBody;

    @When("I fetch the exchange rate for endpoint {string}")
    public void fetchExchangeRate(String endpoint) {
       Response response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint);
        exchangeRateResponseBody = response.as(ExchangeRateResponseBody.class);
        exchangeRateResponseBody.setStatusCode(response.getStatusCode());
    }

    @Then("I verify the status code and status returned in the API response")
    public void verifyStatusCodeAndStatus() {
        assertEquals(200, exchangeRateResponseBody.getStatusCode());
        assertEquals("success", exchangeRateResponseBody.getResult());
    }

    @Then("I verify the USD price against the AED is in the range of {double} - {double}")
    public void verifyPriceRange(double minAedRate, double maxAedRate) {
        double usdToAedRate = exchangeRateResponseBody.getRatesResponseBody().getAED();
        assertTrue(usdToAedRate >= minAedRate && usdToAedRate <= maxAedRate);
    }

    @Then("I verify the API response time is not less than {int} seconds from the current time in seconds")
    public void verifyResponseTime(int expectedResponseTime) {
        long responseTime = exchangeRateResponseBody.getTimeLastUpdateUnix();
        long currentTime = System.currentTimeMillis() / 1000;
        assertTrue(currentTime - responseTime >= expectedResponseTime);
    }

    @Then("I verify that {int} currency pairs are returned by the API")
    public void verifyCurrencyPairsCount(int expectedCurrencyPairSize) {
        assertEquals(expectedCurrencyPairSize, exchangeRateResponseBody.getRatesResponseBody().size());
    }
    @Then("I validate the API response schema")
    public void validateApiResponseSchema() {
        String schemaFilePath = FrameworkConstants.getExchangeRateSchemaFilePath();
        given().body(exchangeRateResponseBody)
                .then().body(matchesJsonSchema(new File(schemaFilePath)));
    }
}
