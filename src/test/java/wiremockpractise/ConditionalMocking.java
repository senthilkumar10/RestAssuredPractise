package wiremockpractise;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author senthil
 */
public class ConditionalMocking {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    private static WireMockServer server = new WireMockServer(PORT);

    @BeforeTest
    private void setup() {

        //Start the wiremock server via code & configure it w/ required host and port
        server.start();
        WireMock.configureFor(HOST, PORT);

        //Response mapping

        ResponseDefinitionBuilder mockResponse1 = new ResponseDefinitionBuilder();
        mockResponse1.withStatus(503)
                .withHeader("Content-Type","text/plain")
                .withBodyFile("Service Not Available");

        ResponseDefinitionBuilder mockResponse2 = new ResponseDefinitionBuilder();
        mockResponse2.withStatus(200)
                .withHeader("Content-Type","application/json")
                .withBody("{\"Current-Status\":\"running\"}")
                .withFixedDelay(2500);

        //Condition based on the matching header
        WireMock.stubFor(
                WireMock.get("/movies/1")
                        .withHeader("Accept",WireMock.matching("text/plain"))
                        .willReturn(mockResponse1));

        WireMock.stubFor(
                WireMock.get("/movies/1")
                        .withHeader("Accept",WireMock.matching("application/json"))
                        .willReturn(mockResponse2));
    }

    @Test
    public void verifyGETRequestWithMockingResponse1() {

        RestAssured.baseURI = "http://localhost:8080/movies/1";

        Response respose = RestAssured.given()
                .header(new Header("Accept","text/plain"))
                .when()
                .get();

        Assert.assertEquals(respose.statusCode(), HttpStatus.SC_SERVICE_UNAVAILABLE);

    }

    @Test
    public void verifyGETRequestWithMockingResponse2() {

        RestAssured.baseURI = "http://localhost:8080/movies/1";

        Response respose = RestAssured.given()
                .header(new Header("Accept","application/json"))
                .when()
                .get();

        Assert.assertEquals(respose.statusCode(), HttpStatus.SC_OK);
    }

    @Test
    public void verifyGETRequestWithNoResponse() {

        RestAssured.baseURI = "http://localhost:8080/movies/2";

        Response respose = RestAssured.given()
                .header(new Header("Accept","application/json"))
                .when()
                .get();

        Assert.assertEquals(respose.statusCode(), HttpStatus.SC_NOT_FOUND);
    }


    @AfterTest
    public void tearDown() {
        if (null != server && server.isRunning()) {
            server.shutdownServer();
        }
    }

}
