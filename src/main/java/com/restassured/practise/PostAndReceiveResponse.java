package com.restassured.practise;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PostAndReceiveResponse {

	@Test
	public void postAndReceiveResponse() {

		JSONInputData jsonInputData = new JSONInputData("SampleName3", "UXDeveloper3");

		RestAssured.baseURI = "https://reqres.in/";

		Response response = RestAssured.given()
						.header("Contest_Type", "application/json")
						.and()
						.body(jsonInputData)
					.when()
						.post("/api/users")
					.then()
						.statusCode(201)
						.and()
						.log().all().extract().response();
		
		JSONReceiveData jsonReceiveData = new JSONReceiveData();
		
		//Gson will convert the JSon into object so that it will be compared. 
		Gson gson = new GsonBuilder().create();
		
		jsonReceiveData = gson.fromJson(response.prettyPrint(), JSONReceiveData.class);
		
		//Assert.assertEquals(jsonReceiveData.getName(), jsonInputData.getName());
		
		

	}


}
