package wiremockpractise;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class ReadResponseFromFile {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";
    private static WireMockServer server = new WireMockServer(PORT);

    @BeforeTest
    private void setup() {

        //Start the wiremock server via code & configure it w/ required host and port
        server.start();
        WireMock.configureFor(HOST, PORT);

        //Response mapping

        ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();
        mockResponse.withStatus(201)
                .withBodyFile("json/index.json");

        //Mock request
        WireMock.stubFor(
                WireMock.get("/readfromfile/index")
                        .willReturn(mockResponse));
    }

    @Test
    public void verifyGETRequest() {

        RestAssured.baseURI = "http://localhost:8080/readfromfile/index";

        Response respose = RestAssured.given()
                .get()
                .then()
                .statusCode(201)
                .extract().response();

        Assert.assertEquals(respose.jsonPath().get("data[0].type"),"articles");
        Assert.assertEquals(respose.jsonPath().get("data[0].relationships.author.data.id"),"42");
    }


    @AfterTest
    public void tearDown() {
        if (null != server && server.isRunning()) {
            server.shutdownServer();
        }
    }


}
