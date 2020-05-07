package com.jiraChaining.Requests;

import com.jiraChaining.Base.BaseRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.text.ParseException;

public class CreateIssue extends BaseRequest {


    @Test
    public void createIssue() throws ParseException {


        File filesrc = new File("./TestData/Jira_Issue_Creation.json");

        Response response =
                request
                .body(filesrc)
                .post("issue");

        response.prettyPrint();

        //Store the response in Json format
        JsonPath jsonPath = response.jsonPath();

        bugid = jsonPath.get("key");

    }

}
