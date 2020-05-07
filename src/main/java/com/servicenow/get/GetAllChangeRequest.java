package com.servicenow.get;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class GetAllChangeRequest {

    @Test
    public void getAllCR(){

        RestAssured.baseURI ="https://dev96924.service-now.com/api/now/table/change_request";

        RestAssured.authentication = RestAssured.basic("admin","Senthil123$");

        Response response = RestAssured.get();

        System.out.println(response.getStatusCode());

        System.out.println(response.getContentType());

        System.out.println(response.getTime());

        response.prettyPrint();

    }


}
