package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignIn extends AbstractPageDriver{
	
	By email_txt = By.id("user_email");
	By password_txt = By.id("user_password");
	By signIn_btn = By.name("commit");
	
	
	public SignIn(WebDriver driver) {
		super(driver);
	}
	
	public void enterUserEmail(String email){
		getUniqueElement(email_txt).clear();
		getUniqueElement(email_txt).sendKeys(email);
	}
	
	public void enterUserPassword(String password){
		getUniqueElement(password_txt).clear();
		getUniqueElement(password_txt).sendKeys(password);
	}
	
	public AccountPage clickSignInButton(){
		getUniqueElement(signIn_btn).click();
		return new AccountPage(driver);
	}

}
