package com.jira.put;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;

public class GetRecentlyCreatedAndUpdate {


    @Test
    public void getAllIssuesInLast24Hours() throws ParseException {

        RestAssured.baseURI = "https://api-mar2020.atlassian.net/rest/api/2";

        RestAssured.authentication = RestAssured.preemptive().basic("rajalakshmi.govindarajan@testleaf.com", "kEJxzmhkQzvdeP8iysWN2D1B");

        Response response = RestAssured.given()
                .param("jql", "project=MAR")
                .get("search");

        //response.prettyPrint();

        //Store the response in Json format
        JsonPath jsonPath = response.jsonPath();

        List<String> bugIDList = jsonPath.getList("issues.id");

        Integer max = bugIDList.stream().mapToInt(num -> Integer.parseInt(num)).max().getAsInt();

        System.out.println("issue/"+max.toString());

        Response response2 = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(new File("./TestData/Jira_Issue_Edition.json"))
                .put("issue/"+max.toString());

        response2.statusCode();



    }



}
