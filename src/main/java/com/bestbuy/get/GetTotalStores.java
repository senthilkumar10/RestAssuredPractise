package com.bestbuy.get;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class GetTotalStores {


    public Map<String,String> storeHeaders(){

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("format","json");
        paramMap.put("show","city,longName");
        paramMap.put("pageSize","2");
        paramMap.put("apiKey","qUh3qMK14GdwAs9bO59QRSCJ");
        return paramMap;

    }




    @Test
    public void getTotalNoOfStores(){

        RestAssured.baseURI = "https://api.bestbuy.com/v1/stores";

        Map<String,String> paramMap = storeHeaders();

        Response response = RestAssured.given()
                .params(paramMap)
                .get();

        response.prettyPrint();


    }



}
