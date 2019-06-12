package com.restassured.practise;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class PostMethodJavaClass {
	
	@Test	
	public void postUsingJavaClassObjects() {
	
		JSONInputData jsonInputData = new JSONInputData("SampleName1", "UXDeveloper1");
		
		RestAssured.baseURI = "https://reqres.in/";
		
		RestAssured.given()
				   		.header("Contest_Type", "application/json")
				   		.and()
				   		.body(jsonInputData)
				   	.when()
				   		.post("/api/users")
				   	.then()
				   		.statusCode(201)
				   		.and()
				   		.log().all();
		
	}

}
