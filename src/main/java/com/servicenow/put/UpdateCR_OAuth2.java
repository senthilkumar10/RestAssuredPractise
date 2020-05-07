package com.servicenow.put;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class UpdateCR_OAuth2 {

    @Test
    public void getAllCR(){

        RestAssured.baseURI ="https://dev96924.service-now.com/api/now/table/change_request";

       // RestAssured.authentication = RestAssured.basic("admin","Senthil123$");

        RestAssured.authentication = RestAssured.oauth2("tIxgxTD8eaRqUTyztxhdAkkE4eNmRE3NOBEWfctVVTlgjIN5l_gJl8XuUGyL94RvDm75MNflJ2KWr_-lCYizig");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\"short_description\": \"Updated through RA with OAuth2\"}")

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
