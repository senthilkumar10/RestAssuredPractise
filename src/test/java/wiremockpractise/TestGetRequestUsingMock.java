package wiremockpractise;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * 1. Create the wiremock server and start
 * 2. configure the server to intercept the request
 * 3. stub for the request [get request]
 * - create the mock response
 * 4. shutdown the mock server
 */

public class TestGetRequestUsingMock {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    private static WireMockServer server = new WireMockServer(PORT);

    @BeforeClass
    private void setup() {

        server.start();
        WireMock.configureFor(HOST, PORT);
        WireMock.stubFor(
                WireMock.get("/rest/api/2")
                        .willReturn(new ResponseDefinitionBuilder().withStatus(200))

        );

    }


    @Test
    public void verifyGETRequestStatusCode() {

        RestAssured.baseURI = "http://localhost:8080/rest/api/2";

        RestAssured.given()
                .get()
                .then()
                .assertThat()
                .statusCode(200);


    }

    @AfterClass
    public void tearDown() {
        if (null != server && server.isRunning()) {
            server.shutdownServer();
        }
    }


}
