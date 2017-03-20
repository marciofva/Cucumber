package com.pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SubTaskPage extends AbstractPageDriver{
	
	
	// Label Task ID
	By taskID_lbl = By.cssSelector(".modal-title.ng-binding");
	
	// Task Name
	By taskName_txt = By.id("edit_task");
	
	// SubTask Description
	By subTaskDesc_txt = By.id("new_sub_task");
	
	// Due Date
	By dueDate_txt = By.id("dueDate");
	
	// Button: Add a new SubTask
	By addSubtask_btn = By.id("add-subtask");
	
	// Button: Close
	By subtaskClose_btn = By.cssSelector("button[ng-click='close()']");
	
	// List of subtasks (Dynamic List)
	By subTaskList_FullList = By.xpath("html/body/div[4]/div/div/div[2]/div[2]/table/tbody/tr/td/a");

	// Button: List of "Remove SubTasks" (Dynamic List)
	By removeSubTask_btn = By.xpath("html/body/div[4]/div/div/div[2]/div[2]/table/tbody/tr/td/button");
	
	
	public SubTaskPage(WebDriver driver) {
		super(driver);
	}
	
	public List<WebElement> getNumberOfSubtasks(){
		return getManyElements(subTaskList_FullList);
	}
	
	public void enterSubTaskDescription(String description){	
		getUniqueElement(subTaskDesc_txt).clear();
		getUniqueElement(subTaskDesc_txt).sendKeys(description);
	}
	
	public void enterSubTaskDueDate(String description){
		getUniqueElement(dueDate_txt).clear();
		getUniqueElement(dueDate_txt).sendKeys(description);
	}
	
	public void clickOnButton_AddSubTask(){
		getUniqueElement(addSubtask_btn).click();
	}
	
	public void clickOnButton_closeButton(){
		getUniqueElement(subtaskClose_btn).click();
	}
		
	public List<WebElement> getAllSubstasks(){
		return getManyElements(subTaskList_FullList);
	}
	
	public int getSizeTaskSubList(){
		return getSizeElements(subTaskList_FullList);
	}
	
	public String gettaskID(){
		return getUniqueElement(taskID_lbl).getText();
	}
	

}
