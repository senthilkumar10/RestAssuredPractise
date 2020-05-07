package com.servicenow.post;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;

public class PostCR_Body_Multiple_File {

    @DataProvider(name = "DP")
    public Object[] getFileName(){
        String[] data = new String[2];
        data[0] = "./TestData/ServiceNow_Data1.json";
        data[1] = "./TestData/ServiceNow_Data2.json";

        return data;
    }



    @Test(dataProvider = "DP")
    public void getAllCR(String fileName){

        RestAssured.baseURI ="https://dev96924.service-now.com/api/now/table/change_request";

        RestAssured.authentication = RestAssured.basic("admin","Senthil123$");

        File filesrc = new File(fileName);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(filesrc)
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
