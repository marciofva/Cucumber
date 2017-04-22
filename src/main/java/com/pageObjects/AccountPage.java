package com.pageObjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.navBar.Menu.NavBarMenu;

public class AccountPage extends AbstractPageDriver{

	By myTasks_btn = By.cssSelector(".btn.btn-lg.btn-success");
	
	By message_menu = By.cssSelector(".nav.navbar-nav.navbar-right>li>a[href='#']");
	
	
	public AccountPage(WebDriver driver) {
		super(driver);
	}
	
	public boolean isVisible(String elementName){
		
		NavBarMenu navBar = new NavBarMenu();
		boolean found = false;
		
		
		if(elementName.equals("My Tasks link")){
			found = isElementVisible(navBar.getMytasks_menu());
		} else{
			if (elementName.equals("My Tasks button")){
				found = isElementVisible(myTasks_btn);
			}
			else{
				Assert.assertTrue("Element: '" + elementName + " not found", false);
			}
		}
		
		return found;
	}
	
	public String getUserName(){
		
		String full_message = driver.findElement(message_menu).getText();
		int size_message = full_message.length();
		String userName = "";
		int space_position = 0;
		
		for(int i = 0; i < size_message; i++ ){
			
			if (full_message.substring(i, i+1).equals(",")){
				space_position = i;
				break;
			}
		}
		
		userName = full_message.substring(space_position+2, size_message-1);
		
		return userName;
	}
	
	public TaskPage toTaskpage(){
		NavBarMenu navBar = new NavBarMenu();
		getUniqueElement(navBar.getMytasks_menu()).click();;
		return new TaskPage(driver);
	}
}
