package com.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

public class CreateIssueInJira {

   public static String bugID;
   public static Response response;
   public static Response response2;

    @Given("Request URL is Initiated for Jira")
    public void initiateRequestEndPoint(){
        RestAssured.baseURI = "https://api-mar2020.atlassian.net/rest/api/2";
    }

    @And("Authorization is performed for Jira")
    public void authorize(){
        RestAssured.authentication = RestAssured.preemptive().basic("rajalakshmi.govindarajan@testleaf.com","kEJxzmhkQzvdeP8iysWN2D1B");
    }

    @When("^In Jira body is posted with JSON file (.*)$")
    public void postRequest(String fileName){
        response = RestAssured.given().log().all().contentType(ContentType.JSON)
                .body(new File("./TestData/"+fileName))
                .post("issue");



    }

    @Then("Status code should be {int} for Jira")
    public void verifyStatusCode(int statusCode){
        response.prettyPrint();
        int respStatusCode = response.getStatusCode();
        System.out.println(respStatusCode);
        if(respStatusCode==statusCode){
            System.out.println("Matching");
        }else{
            System.out.println("Does not match");
        }
    }

    @Then("Response time within {int} seconds for Jira")
    public void verifyResponseTime(long responseTime){
        long resptime = response.getTime();
        if(resptime<=(responseTime*1000)){
            System.out.println("Response time is within 5 secs");
        }else{
            System.out.println("Response time is greater than 5 secs");
        }
    }

    @And("Delete the bugID")
    public void deleteBugId(){
        JsonPath jsonPath = response.jsonPath();
        bugID = jsonPath.get("key");

        response2 = RestAssured.given()
                .delete("issue/"+bugID);
    }

    @Then("Delete Status code should be {int}")
    public void verifyStatusCodeAfterDelete(int statusCode){
        response2.prettyPrint();
        int respStatusCode = response2.getStatusCode();
        System.out.println(respStatusCode);
        if(respStatusCode==statusCode){
            System.out.println("Matching");
        }else{
            System.out.println("Does not match");
        }
    }


}
