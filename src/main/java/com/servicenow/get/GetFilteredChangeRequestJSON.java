package com.servicenow.get;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

public class GetFilteredChangeRequestJSON {

    @Test
    public void getFilteredCR(){

        RestAssured.baseURI = "https://dev96924.service-now.com/api/now/table/change_request";

        RestAssured.authentication = RestAssured.basic("admin","Senthil123$");

        Response response =  RestAssured.given()
                .param("type","normal")
                .param("state","-5")
                .param("sysparm_fields","number,sys_id,type,state")
                .accept(ContentType.JSON)
                .get();

        response.prettyPrint();

       JsonPath jsonPath = response.jsonPath();

       List<String> list = jsonPath.getList("result.number");

       System.out.println(list.size());




    }


}
