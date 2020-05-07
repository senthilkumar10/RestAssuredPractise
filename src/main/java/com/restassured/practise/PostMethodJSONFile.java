package com.restassured.practise;

import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class PostMethodJSONFile {

	@Test
	public void postUsingJavaClassObjects() {

		RestAssured.baseURI = "https://reqres.in/";

		RestAssured.given().header("Contest_Type", "application/json").and().body("./TestData/Users.json").when()
				.post("/api/users").then().statusCode(201).and().log().all();

	}

}
