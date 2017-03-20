package com.runner.createTask;

/**
 * Author: Marcio Almeida
 */
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions (
		plugin = {"html:target/cucumberHtmlReport", "json:target/cucumber-report.json"},
		features = "features",glue={"com.steps"},
		tags = {"@CreateNewTask, @CreateSubTask"}
		)
public class runnerTask {

}
