package com.pageObjects;

import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractPageDriver {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected WebElement element;
	protected List<WebElement> elements;

	public AbstractPageDriver(WebDriver driver) {
		this.driver = driver;
		this.wait = (new WebDriverWait(driver, 10));
	}

	/**
	 * Method to check if the element is present and assure that there is only one element
	 * @param selector
	 * @return single element
	 */
	public WebElement getUniqueElement(By selector) {

		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
		} catch (NoSuchElementException e) {
		}

		elements = driver.findElements(selector);

		Assert.assertTrue(
				"Unique Element {'" + selector.toString() + "}' not found for " + this.getClass().getSimpleName(),
				(elements.size() == 1));

		return elements.get(0);
	}

	/**
	 * Method to check if the element is present for a list of elements
	 * @param selector
	 * @return list of elements
	 */
	public List<WebElement> getManyElements(By selector) {

		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(selector));
		} catch (NoSuchElementException e) {
		}

		List<WebElement> elements = driver.findElements(selector);

		Assert.assertTrue(
				"Unique Element {'" + selector.toString() + "}' not found for " + this.getClass().getSimpleName(),
				(elements.size() > 0));

		return elements;
	}

	public boolean isElementClickable(By locator) {

		try {
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (NoSuchElementException e) {
		} catch (TimeoutException t) {
			Assert.assertTrue("Timeout: '" + t.getMessage() + "  - Element '" + locator.toString() + "\' not found",
					false);
		}
		return false;
	}

	public boolean isElementVisible(By locator) {

		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (NoSuchElementException e) {
		} catch (TimeoutException t) {
			Assert.assertTrue("Timeout: '" + t.getMessage() + "  - Element '" + locator.toString() + "\' not found ",
					false);
		}
		return false;
	}

	public int getSizeElements(By locator) {

		List<WebElement> elements;

		try {
			elements = driver.findElements(locator);
			return elements.size();
		} catch (NoSuchElementException e) {
		} catch (RuntimeException t) {
			Assert.assertTrue("Error: '" + t.getMessage() + "  - Element '" + locator.toString(), false);
		}
		return 0;
	}
}
