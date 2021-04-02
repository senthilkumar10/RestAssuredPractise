package wiremockpractise;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

public class WireMockRequestResponse {

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
        mockResponse.withStatus(200)
                .withStatusMessage("Welcome to Wiremock Server")
                .withHeader("Content-Type","text/json")
                .withHeader("token","12345")
                .withHeader("Set-Cookie","session-id=58687858")
                .withHeader("Set-Cookie","Testing_another_session=W")
                .withBody("Text to be displayed in the body");


        //Mock request
        WireMock.stubFor(
                WireMock.get("/rest/api/1")
                        .willReturn(mockResponse));
    }

    @Test
    public void verifyGETRequest() {

        RestAssured.baseURI = "http://localhost:8080/rest/api/1";

        Response respose = RestAssured.given()
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        Assert.assertEquals(respose.getHeader("Content-Type"),"text/json");
        Assert.assertEquals(respose.getStatusLine(),"HTTP/1.1 200 Welcome to Wiremock Server");
        Assert.assertEquals(respose.getCookie("session-id"),"58687858");
        Assert.assertEquals(respose.getCookie("Testing_another_session"),"W");
        Assert.assertEquals(respose.getBody().asString(),"Text to be displayed in the body");


    }




    @AfterTest
    public void tearDown() {
        if (null != server && server.isRunning()) {
            server.shutdownServer();
        }
    }


}
