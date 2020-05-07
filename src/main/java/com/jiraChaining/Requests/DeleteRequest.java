package com.jiraChaining.Requests;

import com.jiraChaining.Base.BaseRequest;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.text.ParseException;


public class DeleteRequest extends BaseRequest {


    @Test(dependsOnMethods = "com.jiraChaining.Requests.CreateIssue.createIssue")
    public void deleteRequest() throws ParseException {


        Response response = request
                .delete("issue/"+bugid);

        System.out.println(response.statusCode());

    }

}
