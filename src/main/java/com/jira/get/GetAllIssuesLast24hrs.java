package com.jira.get;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetAllIssuesLast24hrs {


    @Test
    public void getAllIssuesInLast24Hours() throws ParseException {

        //Define the Date Format => 2020-04-19T12:38:16.964+0530
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

        //Find the 24hrs before in milliseconds
        long last24hrs = new Date().getTime()-(24*60*60*1000);

        //Set a counter
        int counter = 0;

        RestAssured.baseURI = "https://api-mar2020.atlassian.net/rest/api/2";

        RestAssured.authentication = RestAssured.preemptive().basic("rajalakshmi.govindarajan@testleaf.com","kEJxzmhkQzvdeP8iysWN2D1B");

        Response response = RestAssured.given()
                .param("jql","project=MAR")
                .get("search");

        //response.prettyPrint();

        //Store the response in Json format
        JsonPath jsonPath = response.jsonPath();

        //Get the size
        int count = jsonPath.getList("issues.id").size();

        System.out.println("Bugs Created Last 24hrs :- ");


        //Loop through each issues to find the creation date is last 24hrs.
        for(int i=0;i<count;i++){

            //Get the creation date and store it in a string
            String creationDateInJSON = jsonPath.get("issues["+i+"].fields.created");

            //Covert the creation time in milliseconds
            long creationTime = simpleDateFormat.parse(creationDateInJSON).getTime();

            if(creationTime>last24hrs){

                System.out.println("Bug id = "+ jsonPath.get("issues["+i+"].key") + " || Creation_Date => "+ jsonPath.get("issues["+i+"].fields.created"));
                counter++;
            }
        }
        System.out.println("\nTotal No of bugs created - "+counter);
    }

}
