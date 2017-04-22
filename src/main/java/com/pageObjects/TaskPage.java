package com.pageObjects;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TaskPage extends AbstractPageDriver{
	
	// User's Message saying that list belongs to the logged user
	By userMessage_lbl = By.cssSelector(".container>h1");
	
	// TextBox: Task Name
	By taskName_txt = By.id("new_task");
	
	// Button: Add a new task
	By addTask_btn = By.cssSelector("span[ng-click='addTask()']");
	
	// List all added tasks
	By taskList_link = By.cssSelector(".ng-scope.ng-binding.editable.editable-click");
	
	// Button: Manage Subtasks(n)
	By manageSubTask_btn = By.cssSelector(".btn.btn-xs.btn-primary.ng-binding");
	

	public TaskPage(WebDriver driver) {
		super(driver);
	}
	
	public String getTextMessage(){
		return getUniqueElement(userMessage_lbl).getText();
	}
	
	public void enterTaskName(String taskName){
		getUniqueElement(taskName_txt).clear();
		getUniqueElement(taskName_txt).sendKeys(taskName);
	}
	
	public void clickOnButton_AddTask(){
		getUniqueElement(addTask_btn).click();
	}
	
	public void keyboardEventToAddTask(String key){
		
		if (key.equals("ENTER")){
			getUniqueElement(taskName_txt).sendKeys(Keys.ENTER);
		}
		else{
			Assert.assertTrue("Key '" + key + "' is not a valid KEY", false);
		}
	}
	
	public List<WebElement> getTaskList(){	
		return getManyElements(taskList_link);
	}
	
	public int getSizeTaskList(){
		return getSizeElements(taskList_link);
	}
	
	public List<WebElement> getManageSubtasksButton(){
		return getManyElements(manageSubTask_btn);
	}
	
	public SubTaskPage toSubTaskPage(WebElement locator){
		locator.click();
		return new SubTaskPage(driver);
	}

}
