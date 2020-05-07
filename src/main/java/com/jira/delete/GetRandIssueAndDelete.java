package com.jira.delete;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GetRandIssueAndDelete {


    public int getRandomNumber(int range){

        Random rand = new Random();
        return rand.nextInt(range)+1;

    }


    @Test
    public void getAllIssuesInLast24Hours() throws ParseException {

        RestAssured.baseURI = "https://api-mar2020.atlassian.net/rest/api/2/";

        RestAssured.authentication = RestAssured.preemptive().basic("rajalakshmi.govindarajan@testleaf.com","kEJxzmhkQzvdeP8iysWN2D1B");

        Response response = RestAssured.given()
                .param("jql","project=MAR")
                .get("search");


        //Store the response in Json format
        JsonPath jsonPath = response.jsonPath();

        //Get the size
        int count = jsonPath.getList("issues.id").size();

        //Get Random Number
        int randNumber = getRandomNumber(count);

        //Get the bugID
        String bugId = jsonPath.get("issues["+randNumber+"].key");

        System.out.println(bugId);


        Response response2 = RestAssured.given()
                .delete("issue/"+bugId);

        System.out.println(response2.statusCode());

    }

}
