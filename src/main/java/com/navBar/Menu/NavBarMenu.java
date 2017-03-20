package com.navBar.Menu;

import org.openqa.selenium.By;

public class NavBarMenu {
	
	By home_menu = By.cssSelector(".nav.navbar-nav>li>a[href='/']");
	
	By mytasks_menu = By.cssSelector(".nav.navbar-nav>li>a[href='/tasks']");
	
	By signIn_Menu = By.cssSelector(".nav.navbar-nav.navbar-right>li>a[href='/users/sign_in']");
	
	By register_menu = By.cssSelector(".nav.navbar-nav.navbar-right>li>a[href='/users/sign_up']");
	

	public NavBarMenu(){
		
	}

	public By getHome_menu() {
		return home_menu;
	}


	public By getMytasks_menu() {
		return mytasks_menu;
	}


	public By getSignIn_Menu() {
		return signIn_Menu;
	}


	public By getRegister_menu() {
		return register_menu;
	}
	
}
