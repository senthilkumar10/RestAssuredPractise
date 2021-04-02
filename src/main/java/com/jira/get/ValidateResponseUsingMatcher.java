package com.jira.get;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class ValidateResponseUsingMatcher {


    @Test
    public void getAllIssues() throws ParseException {

        RestAssured.baseURI = "https://api-mar2020.atlassian.net/rest/api/2";

        RestAssured.authentication = RestAssured.preemptive().basic("rajalakshmi.govindarajan@testleaf.com", "kEJxzmhkQzvdeP8iysWN2D1B");

        List<Header> headerList = new ArrayList<>();
        headerList.add(new Header("content-type","application/json"));
        headerList.add(new Header("accept","*/*"));

        Headers headersMap = new Headers(headerList);

        RestAssured.given()
                .param("jql", "project=MAR")
                .headers(headersMap)
                .get("search")
                .then()
                .statusCode(200)
                .assertThat()
                .contentType(ContentType.JSON)
                .assertThat()
                .body("total",equalTo(1097));


       // response.prettyPrint();



    }

}
