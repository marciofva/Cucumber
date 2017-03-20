package com.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.navBar.Menu.NavBarMenu;

public class HomePage extends AbstractPageDriver {

	protected String title = "ToDo Rails and Angular";

	// ************************************************************//
	// * 					B U T T O N S
	// ************************************************************//
	By signUp_btn = By.cssSelector("a.btn.btn-lg.btn-success");

	public HomePage(WebDriver driver) {
		super(driver);
	}

	public SignIn toSignInPage() {
		NavBarMenu navBar = new NavBarMenu();
		getUniqueElement(navBar.getSignIn_Menu()).click();
		return new SignIn(driver);
	}

	public String getTitle() {
		return title;
	}

}
