import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GeolocationAPITests {

    private static String API_KEY;
    private static Logger logger = Logger.getLogger(GeolocationAPITests.class);

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://www.googleapis.com";
        RestAssured.basePath = "/geolocation/v1";
        API_KEY = System.getenv("GOOGLE_API_KEY");
        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new RuntimeException("API key is not set. Please set the GOOGLE_API_KEY environment variable.");
        }
    }

    private Response sendGeolocationRequest(String requestBody) {
        logger.info("Sending Geolocation request with body: " + requestBody);
        Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", API_KEY)
                .body(requestBody)
                .when()
                .post("/geolocate");

        logger.info("Received response with status code: " + response.getStatusCode());
        logger.debug("Response body: " + response.getBody().asString());

        return response;
    }

    @DataProvider(name = "coordinates")
    public Object[][] coordinatesProvider() {
        return GeolocationTestUtils.generateRandomCoordinates();
    }

    @DataProvider(name = "accuracies")
    public Object[][] accuraciesProvider() {
        return new Object[][] {
                {GeolocationTestUtils.generateRandomAccuracy()},
                {200000},
                {500000},
        };
    }




    @Test(description = "Test Positive Geolocation API")
    public void testPositiveGeolocation() {
        String requestBody = "{ " +
                "\"homeMobileCountryCode\":310, " +
                "\"homeMobileNetworkCode\":410, " +
                "\"radioType\":\"gsm\", " +
                "\"carrier\":\"Vodafone\", " +
                "\"considerIp\":true " +
                "}";

        Response response = sendGeolocationRequest(requestBody);
        logger.info("Response: " + response.asString());
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
    }




    @Test(dataProvider = "coordinates", description = "Verify Geolocation API response with provided dynamic coordinates")
    public void testDynamicCoordinates(double latitude, double longitude) {
        String requestBody = GeolocationTestUtils.generateRequestBody(latitude, longitude);

        Response response = sendGeolocationRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 status code");
    }




    @Test(dataProvider = "accuracies", description = "Verify Geolocation API response with dynamic accuracy")
    public void testDynamicAccuracy(double accuracy) {
        String requestBody = GeolocationTestUtils.generateRequestBodyWithAccuracy(accuracy);

        Response response = sendGeolocationRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 status code");
    }




    @Test(description = "Verify Geolocation API response for invalid request format")
    public void testInvalidRequestFormat() {
        String invalidRequest = GeolocationTestUtils.generateInvalidRequestBody();

        Response response = sendGeolocationRequest(invalidRequest);
        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 status code for invalid request format");
    }




    @Test(description = "Verify Geolocation API response for Invalid API key")
    public void testInvaliAPIkey() {

        Response response = sendGeolocationRequestWithApiKey("INVALID_API_KEY");
        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 status code for unauthorized access");
    }




    @Test(description = "Verify Geolocation API response for unauthorized access")
    public void testUnauthorizedAccess() {

        String requestBody = GeolocationTestUtils.generateRequestBody(34.052235, -118.243683);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/geolocate");
        Assert.assertEquals(response.getStatusCode(), 403, "Expected 403 status code for unauthorized access");

    }




//    @Test(description = "Verify Geolocation API response for invalid coordinates")
//    public void testInvalidCoordinates() {
//        double latitude = 200; // Invalid latitude
//        double longitude = -118.243683;
//
//        String requestBody = GeolocationTestUtils.generateRequestBody(latitude, longitude);
//
//        Response response = sendGeolocationRequest(requestBody);
//        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 status code for invalid coordinates");
//
//    }




//    @Test(description = "Verify Geolocation API response with empty request body")
//    public void testEmptyRequestBody() {
//        String requestBody = "";
//
//        Response response = sendGeolocationRequest(requestBody);
//        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 status code for empty request body");
//
//    }




    @Test(description = "Verify Geolocation API response with invalid JSON format")
    public void testInvalidJsonFormat() {
        String requestBody = "{ \"invalid\": }"; // Invalid JSON format

        Response response = sendGeolocationRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 status code for invalid JSON format");
    }




    @Test(description = "Verify Geolocation API response with boundary coordinates")
    public void testBoundaryCoordinates() {
        double maxLatitude = 90.0;
        double maxLongitude = 180.0;
        String requestBody = GeolocationTestUtils.generateRequestBody(maxLatitude, maxLongitude);

        Response response = sendGeolocationRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 status code for boundary coordinates");
    }




    @Test(description = "Verify Geolocation API response time")
    public void testResponseTime() {
        double latitude = 34.052235;
        double longitude = -118.243683;
        String requestBody = GeolocationTestUtils.generateRequestBody(latitude, longitude);

        Response response = sendGeolocationRequest(requestBody);

        long responseTime = response.time();
        System.out.println("Response time in milliseconds: " + responseTime);

        Assert.assertTrue(responseTime < 2000, "Response time should be less than 2000 milliseconds");
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 status code for response time test");
    }




    @Test(description = "Verify Geolocation API response with minimum parameters")
    public void testMinimumParameters() {
        String requestBody = "{ " +
                "\"homeMobileCountryCode\":310, " +
                "\"homeMobileNetworkCode\":410, " +
                "\"radioType\":\"gsm\", " +
                "\"carrier\":\"Vodafone\", " +
                "\"considerIp\":true " +
                "}";

        Response response = sendGeolocationRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 status code for minimum parameters request");
    }




    @Test(description = "Verify Geolocation API response with maximum parameters")
    public void testMaximumParameters() {
        String requestBody = "{ " +
                "\"homeMobileCountryCode\":310, " +
                "\"homeMobileNetworkCode\":410, " +
                "\"radioType\":\"gsm\", " +
                "\"carrier\":\"Vodafone\", " +
                "\"considerIp\":true, " +
                "\"cellTowers\": [{\"cellId\": 1234, \"locationAreaCode\": 5678, \"mobileCountryCode\": 310, \"mobileNetworkCode\": 410}], " +
                "\"wifiAccessPoints\": [{\"macAddress\": \"01:23:45:67:89:AB\", \"signalStrength\": -65, \"signalToNoiseRatio\": 40}] " +
                "}";

        Response response = sendGeolocationRequest(requestBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 status code for maximum parameters request");
    }





//    ----------------------------------------------------------private methods----------------------------------------------------------------------

    private Response sendGeolocationRequestWithApiKey(String apiKey) {
        String requestBody = GeolocationTestUtils.generateRequestBody(0, 0); // Dummy request body
        return given()
                .contentType(ContentType.JSON)
                .queryParam("key", apiKey)
                .body(requestBody)
                .when()
                .post("/geolocate");
    }

}
