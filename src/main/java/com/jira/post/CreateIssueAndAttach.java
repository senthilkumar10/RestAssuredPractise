package com.jira.post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateIssueAndAttach {


    @Test
    public void createIssueAndAttach() throws ParseException {

        RestAssured.baseURI = "https://api-mar2020.atlassian.net/rest/api/2";

        RestAssured.authentication = RestAssured.preemptive().basic("rajalakshmi.govindarajan@testleaf.com","kEJxzmhkQzvdeP8iysWN2D1B");

        File filesrc = new File("./TestData/Jira_Issue_Creation.json");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(filesrc)
                .post("issue");

        response.prettyPrint();

        //Store the response in Json format
        JsonPath jsonPath = response.jsonPath();

        String bugId = jsonPath.get("key");

        Response response2 = RestAssured.given()
                .header("X-Atlassian-Token","no-check")
                .multiPart(new File("/home/senthil/Documents/Senzy Data/RESTAPI/Bug_Attachment_1.jpeg"))
                .multiPart(new File("/home/senthil/Documents/Senzy Data/RESTAPI/Bug_Attachment_2.jpeg"))
                .post("issue/"+bugId+"/attachments");


        System.out.println("Attached Successfully");


    }

}
