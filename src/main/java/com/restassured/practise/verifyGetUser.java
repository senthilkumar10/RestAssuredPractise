package com.restassured.practise;


import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

public class verifyGetUser {
	
	
	@Test
	public void getMethod() {
	
		RestAssured.baseURI = "https://reqres.in/";	
		
		//Extract the body as a string
		String res = RestAssured.given().when().get("/api/users?page=2").then().extract().asString();
		System.out.println("Response :"+res);
		
		
		ValidatableResponse response = RestAssured.given().when().get("/api/users?page=2").then();
		response.statusCode(200);
		response.contentType(ContentType.JSON);
		
		//Exract a particular data based on the xml path and store it in a string.
		String email = response.extract().response().path("data[1].email");
		System.out.println("Email Address "+email);
		
		//Another way to get the path via JsonPath
		JsonPath jsonPath = new JsonPath(res);
		String email1 = jsonPath.get("data[1].email");
		System.out.println(email1);
		
		//Another way to validate from hamcrest Matcher. 
		
		response.body("data[1].email", Matchers.equalToIgnoringCase("charles.morris@reqres.in"));
		
		
	}

}
