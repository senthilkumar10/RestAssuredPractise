package com.servicenow.put;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class UpdateCR {

    @Test
    public void getAllCR(){

        RestAssured.baseURI ="https://dev96924.service-now.com/api/now/table/change_request";

        RestAssured.authentication = RestAssured.basic("admin","Senthil123$");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"short_description\": \"Updated through RA\"}")

                .put("7615924d075c10100431ff808c1ed096");

        System.out.println(response.getStatusCode());

        System.out.println(response.getContentType());

        System.out.println(response.getTime());

        JsonPath jsonPath = response.jsonPath();

        System.out.println("The Sys_ID = "+jsonPath.get("result.sys_id"));

        System.out.println("The CR Number = "+jsonPath.get("result.number"));

        response.prettyPrint();

    }


}
