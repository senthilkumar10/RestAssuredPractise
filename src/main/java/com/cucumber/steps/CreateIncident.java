package com.cucumber.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;

public class CreateIncident {

   public static RequestSpecification request;
   public static String sys_id;
   public static Response response;

    @Given("Request URL is Initiated")
    public void initiateRequestEndPoint(){
        RestAssured.baseURI = "https://dev96924.service-now.com/api/now/table/change_request";
    }

    @And("Authorization is performed")
    public void authorize(){
        RestAssured.authentication = RestAssured.basic("admin","Senthil123$");
    }

    @When("^Body is posted with JSON file (.*)$")
    public void postRequest(String fileName){
        response = RestAssured.given().log().all().contentType(ContentType.JSON)
                .body(new File("./TestData/"+fileName))
                .post();
    }

    @Then("Status code should be {int}")
    public void verifyStatusCode(int statusCode){
        int respStatusCode = response.getStatusCode();
        System.out.println(respStatusCode);
        if(respStatusCode==statusCode){
            System.out.println("Matching");
        }else{
            System.out.println("Does not match");
        }
    }

    @Then("Response time within {int} seconds")
    public void verifyResponseTime(long responseTime){
        long resptime = response.getTime();
        if(resptime<=(responseTime*1000)){
            System.out.println("Response time is within 5 secs");
        }else{
            System.out.println("Response time is greater than 5 secs");
        }
    }


}
