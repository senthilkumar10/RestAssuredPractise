package com.gfk.api_test;

import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class VerifyGetUserSchema {


	@Test
	public void validateUserSchema() {
		
		//Generated Schema from the URL - https://www.jsonschema.net/ and saved it as 'EmployeeSchema.json'
		
		
		//to identify Root Classpath & Save the JSON Schema
		System.out.println(this.getClass().getResource("/").getPath()); 
				
		baseURI = "http://dummy.restapiexample.com/api/v1";
		
		//Get all Employees and print it in Console
		String res = given().when().get("/employees").then().extract().asString();
		System.out.println("Response :"+res);
		
		//JSON Schema validation
		given().when().get("/employees")
				.then()
				.assertThat().body(matchesJsonSchemaInClasspath("EmployeeSchema.json"));
		
	
		
	}

}
