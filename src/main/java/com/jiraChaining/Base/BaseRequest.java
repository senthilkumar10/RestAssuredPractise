package com.jiraChaining.Base;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeSuite;

public class BaseRequest {

    public static RequestSpecification request;
    public static String bugid;

    @BeforeSuite
    public void initialize(){

        RestAssured.baseURI = "https://api-mar2020.atlassian.net/rest/api/2";

        RestAssured.authentication = RestAssured.preemptive().basic("rajalakshmi.govindarajan@testleaf.com","kEJxzmhkQzvdeP8iysWN2D1B");

        request = RestAssured.given().log().all().contentType(ContentType.JSON);


    }



}
