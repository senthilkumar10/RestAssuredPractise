package com.gfk.api_test;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static io.restassured.RestAssured.*;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;


public class APIE2EValidation {

	JsonInputData jsonInputData;
	JsonOutputData jsonOutputData;
	Gson gson;
	public static String empName;
	public static String empAge;


	@Test
	public void E2ETest() {
		this.verifyPostEmployeeAndValidateResponse();
		this.validateSingleResponse();
		this.validateUpdateAPIResponse();
		this.validateDeleteAPIResponse();
	}
	
	
	public void initialize() {
		//HTML response to be interpreted as JSON
		registerParser("text/html", Parser.JSON);
		baseURI = "http://dummy.restapiexample.com/api/v1";	
		//Gson will convert the JSon into object so that it will be compared. 
		 gson = new GsonBuilder().create();
	}
	
	
	public void verifyPostEmployeeAndValidateResponse() {

		Fairy fairy = Fairy.create();
		Person person = fairy.person();
		empName = person.fullName();
		empAge = Integer.toString(person.age());

		this.initialize();

		jsonInputData = new JsonInputData(empName,"5000",empAge);

		Response response = given()
				.header("Content_Type", "application/json")
				.and()
				.body(jsonInputData)
				.when()
				.post("/create")
				.then()
				.statusCode(200)
				.and()
				.extract().response();

		//Created a POJO for Jason output
		jsonOutputData = new JsonOutputData();
		
		System.out.println("The following Employee Record is created :- ");

		jsonOutputData = gson.fromJson(response.prettyPrint(), JsonOutputData.class);

		//*****************Assert to verify the response is as correct or not**************

		System.out.println("The Employee ID is : " +jsonOutputData.getId());

		//*****************************************

		if(jsonOutputData.getName().equals(empName)) {
			System.out.println("The Employee Name is as expected : "+jsonOutputData.getName());
		} else {
			System.out.println("The Employee Name is not as expected and the actual is : "+jsonOutputData.getName());
		}

		Assert.assertEquals(jsonOutputData.getName(), empName);

		//*****************************************

		if(jsonOutputData.getSalary().equals(jsonInputData.getSalary())) {
			System.out.println("The Employee Salary is as expected : "+jsonOutputData.getSalary());
		} else {
			System.out.println("The Employee Salary is not as expected and the actual is : "+jsonOutputData.getSalary());
		}

		Assert.assertEquals(jsonOutputData.getSalary(), jsonInputData.getSalary());

		//*****************************************

		if(jsonOutputData.getAge().equals(empAge)) {
			System.out.println("The Employee age is as expected : "+jsonOutputData.getAge());
		} else {
			System.out.println("The Employee age is not as expected and the actual is : "+jsonOutputData.getAge());
		}

		Assert.assertEquals(jsonOutputData.getAge(), empAge);
		
		System.out.println();

		//*****************************************

	}

	
	public void validateSingleResponse() {

		this.initialize();
		
		System.out.println("The Employee ID - "+jsonOutputData.getId()+ " output as below");

		Response singleIDResponse = given()
		.when().get("/employee/"+jsonOutputData.getId())
		.then()
		.statusCode(200).extract().response();
		
		System.out.println(singleIDResponse.prettyPrint());

		Assert.assertEquals(jsonOutputData.getName(), jsonInputData.getName());
		Assert.assertEquals(jsonOutputData.getSalary(), jsonInputData.getSalary());
		Assert.assertEquals(jsonOutputData.getAge(), jsonInputData.getAge());
	}

	
	public void validateUpdateAPIResponse() {

		this.initialize();
		
		jsonInputData = new JsonInputData(empName,"8500",empAge);

		Response updateResponse = given()
		.contentType(ContentType.JSON)
		.body(jsonInputData)
		.when().put("/update/"+jsonOutputData.getId())
		.then()
		.statusCode(200)
		.and()
		.extract()
		.response();
		
		System.out.println("The response after the Salary of the Emp is updated");
		
		jsonOutputData = gson.fromJson(updateResponse.prettyPrint(), JsonOutputData.class);
		
		if(jsonOutputData.getSalary().equals("8500")) {
			System.out.println("The Employee Salary is updated as : "+jsonOutputData.getSalary());
		} else {
			System.out.println("The Employee Salary is not updated and the actual is : "+jsonOutputData.getSalary());
		}

		Assert.assertEquals(jsonOutputData.getSalary(), "8500");
	}

	
	public void validateDeleteAPIResponse() {
		
		this.initialize();
		
		Response verifyDeletion = given()
		.when().delete("/delete/"+jsonOutputData.getId())
		.then()
		.statusCode(200).extract().response();	
		
		System.out.println();
		System.out.println(verifyDeletion.asString());
		
		if(verifyDeletion.asString().contains("deleted Records")){
			System.out.println("\nThe record is deleted");
		}else {
			System.out.println("\nThe record is not deleted");
		}
		
		Assert.assertTrue(verifyDeletion.asString().contains("deleted Records"));
		
	}
	
}
