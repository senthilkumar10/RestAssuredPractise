package com.servicenow.get;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

public class GetFilteredChangeRequestXML {

    @Test
    public void getFilteredCR(){

        RestAssured.baseURI = "https://dev96924.service-now.com/api/now/table/change_request";

        RestAssured.authentication = RestAssured.basic("admin","Senthil123$");

        Response response =  RestAssured.given()
                .param("type","normal")
                .param("state","-5")
                .param("sysparm_fields","number,sys_id,type,state")
                .accept(ContentType.XML)
                .get();

        response.prettyPrint();

        XmlPath xmlPath = response.xmlPath();

        List<String> xmlPathList = xmlPath.getList("response.result.number");

        System.out.println(xmlPathList.size());


    }


}
