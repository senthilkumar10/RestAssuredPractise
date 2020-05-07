package com.cucumber.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = "src/main/java/com/cucumber/feature", glue="steps")
public class TestRun extends AbstractTestNGCucumberTests{

}
