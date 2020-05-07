package com.jira.get;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetCookies {


    @Test
    public void getAllIssues() throws ParseException {

        RestAssured.baseURI = "https://api-mar2020.atlassian.net/rest/api/2";

        RestAssured.authentication = RestAssured.preemptive().basic("rajalakshmi.govindarajan@testleaf.com", "kEJxzmhkQzvdeP8iysWN2D1B");


        Response response = RestAssured.given()
                .param("jql", "project=MAR")
                .get("search");

       // response.prettyPrint();

        Map<String, String> cookies = response.getCookies();
        for (Map.Entry<String,String> eachCookie:cookies.entrySet()){
            System.out.println(eachCookie.getKey());
            System.out.println(eachCookie.getValue());

        }

    }

}
