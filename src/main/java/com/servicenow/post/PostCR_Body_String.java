package com.servicenow.post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class PostCR_Body_String {

    @Test
    public void getAllCR(){

        RestAssured.baseURI ="https://dev96924.service-now.com/api/now/table/change_request";

        RestAssured.authentication = RestAssured.basic("admin","Senthil123$");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "\n" +
                        "\t\"short_description\": \"Body as String\",\n" +
                        "\t\"description\": \"Senthil Kumar 1\",\n" +
                        "\t\"category\": \"software\"\n" +
                        "\n" +
                        "}")
                .post();

        System.out.println(response.getStatusCode());

        System.out.println(response.getContentType());

        System.out.println(response.getTime());

        JsonPath jsonPath = response.jsonPath();

        System.out.println("The Sys_ID = "+jsonPath.get("result.sys_id"));

        System.out.println("The CR Number = "+jsonPath.get("result.number"));

        response.prettyPrint();

    }


}
