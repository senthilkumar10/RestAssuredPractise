package com.servicenow.delete;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

public class DeleteCR {

    @Test
    public void getAllCR(){

        RestAssured.baseURI ="https://dev96924.service-now.com/api/now/table/change_request";

        RestAssured.authentication = RestAssured.basic("admin","Senthil123$");

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)

                .delete("3215528d075c10100431ff808c1ed088");

        System.out.println(response.getStatusCode());

        System.out.println(response.getContentType());

        System.out.println(response.getTime());



        response.prettyPrint();

    }


}
