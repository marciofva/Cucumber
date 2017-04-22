package com.steps;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.pageObjects.AccountPage;
import com.pageObjects.HomePage;
import com.pageObjects.SignIn;
import com.pageObjects.SubTaskPage;
import com.pageObjects.TaskPage;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps{

	//***********************************************************************// 
	//				D R I V E R   C O N T R O L 
	//***********************************************************************// 
	WebDriver driver;

	//***********************************************************************// 
	//				L O C A L   V A R I A B L E S	
	//***********************************************************************// 
	String chromeDriverLocation;
	String currentDir;
	Properties properties;
	InputStream inputStream;
	String browser;
	String UserName = "";
	int size_TaskList_Actual = 0;
	int txt_length = 0;
	int size_list_manageSub = 0;
	String subTask_Name = "";
	int taskSubList_old = 0;
	int taskSubList_new = 0;
	String dueDate = "";
	
	ArrayList<Integer> list_subtasks_button = new ArrayList<Integer>();
	ArrayList<Integer> list_subtasks_inside = new ArrayList<Integer>();
	
	List<WebElement> elements;

	//***********************************************************************// 
	//				P A G E    O B J E C T S 	
	//***********************************************************************// 
	HomePage homePageObj;
	SignIn signInObj;
	AccountPage AccountPageObj;
	TaskPage taskPageObj;
	SubTaskPage subTaskObj;

	@Before
	public void setUp() {

		currentDir = System.getProperty("user.dir");

		PropertyFileReader();	
	
		switch (browser.toLowerCase()) {
		case "chrome":
			chromeDriverLocation = currentDir + "/src/main/resources/drivers/chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
			driver = new ChromeDriver();
			break;
		case "ie":
			chromeDriverLocation = currentDir + "/src/main/resources/drivers/IEDriverServer.exe";
			System.setProperty("webdriver.ie.driver", chromeDriverLocation);
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			InternetExplorerDriver driverIE = new InternetExplorerDriver(capabilities);
			driver = driverIE;
			break;
		case "firefox":
			chromeDriverLocation = currentDir + "/src/main/resources/drivers/geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", chromeDriverLocation);
			DesiredCapabilities capabilitiesf = DesiredCapabilities.firefox();
			capabilitiesf.setBrowserName("firefox");
			capabilitiesf.setPlatform(org.openqa.selenium.Platform.WINDOWS);
			capabilitiesf.setCapability("marionette", true);
			WebDriver driverFirefox = new FirefoxDriver(capabilitiesf);
			driver = driverFirefox;
			break;
		default:
			Assert.assertTrue("Browser '" + browser + "' is INVALID.", false);
		}

		driver.manage().window().maximize();
	}


	public void PropertyFileReader() {

		chromeDriverLocation = currentDir + "\\config.properties";

		try {
			inputStream = new FileInputStream(chromeDriverLocation);
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException e) {
			Assert.assertTrue("Property File not loaded - Exception: " + e, false);
		}
		browser = properties.getProperty("browser");
	}
	
	@After
	public void shotDown() {
		driver.quit();
	}

	@Given("^I access the website \"([^\"]*)\"$")
	public void i_access_the_website(String URL) throws Throwable {

		driver.navigate().to(URL);
		homePageObj = new HomePage(driver);
		
		Assert.assertEquals(homePageObj.getTitle(), driver.getTitle());
	}

	@Given("^click on SignIn link on the NavBar$")
	public void click_on_SignIn_link_on_the_NavBar() throws Throwable {
		signInObj = homePageObj.toSignInPage();
	}

	@When("^I submit the form with valid credentials$")
	public void i_submit_the_form_with_valid_credentials(DataTable table) throws Throwable {

	    /*  
		  | fields 	 (0,0)  	| value             (0,1)  	|
	      | email  	 (1,0)		| student@test.com 	(1,1)	|
	      | password (2,0)		|         12345678 	(2,1)	|
		*/

		List<List<String>> data = table.raw();

		signInObj.enterUserEmail(data.get(1).get(1));
		signInObj.enterUserPassword(data.get(2).get(1));
	}

	@When("^click on SignIn button$")
	public void click_on_SignIn_button() throws Throwable {
		AccountPageObj = signInObj.clickSignInButton();
	}

	@Then("^I should see the \"([^\"]*)\"$")
	public void i_should_see_the_link_on_the_NavBar(String element) throws Throwable {
		UserName = AccountPageObj.getUserName();
		Assert.assertTrue("Element \'" + element + "\' not found for "
				+ AccountPageObj.getClass().getSimpleName(), AccountPageObj.isVisible(element));
	}
	
	@When("^click on My Tasks link$")
	public void click_on_My_Tasks_link() throws Throwable {
		taskPageObj = AccountPageObj.toTaskpage();	
	}

	@Then("^I should see the message \"([^\"]*)\"$")
	public void i_should_see_the_message(String message) throws Throwable {
		
		//replace the word "user" by the real user's name which was registered in the system
		String newMessage = message.replaceFirst("user", UserName);
		
		Assert.assertEquals(newMessage, taskPageObj.getTextMessage());

	}
	
	@When("^enter \"([^\"]*)\" in Task Name field$")
	public void enter_in_Task_Name_field(String taskName) throws Throwable {
		
		size_TaskList_Actual = 0;
		
		//check whether task list is empty before entering a new task
		if (taskPageObj.getSizeTaskList() > 0){
			
			//get the current size list
			size_TaskList_Actual = taskPageObj.getTaskList().size();
		}
		
		txt_length = taskName.length();
		taskPageObj.enterTaskName(taskName);
	}

	@When("^click on add button$")
	public void click_on_add_button() throws Throwable {
		taskPageObj.clickOnButton_AddTask();

	}

	@Then("^The system should not add the task$")
	public void the_system_should_not_add_the_task() throws Throwable {
	
		int taskList_new = 0;
		
		//check whether task list is empty after clicking on add button to insert a new task
		if (taskPageObj.getSizeTaskList() > 0){
			taskList_new = taskPageObj.getTaskList().size();
		}
		
		//comparing the size of task list before entering a new task and after clicking on add button
		Assert.assertTrue("Task Name should contain between 3 and 250 long characters, but was entered " + txt_length + " character(s).", size_TaskList_Actual == taskList_new );
	}
	

	@When("^hit \"([^\"]*)\" key$")
	public void hit_key(String key) throws Throwable {
		taskPageObj.keyboardEventToAddTask(key);
		Thread.sleep(5000);
	}

	@Then("^The system should add the task$")
	public void the_system_should_add_the_task() throws Throwable {
			
		//check whether was created a new task
		Assert.assertEquals(size_TaskList_Actual+1, taskPageObj.getTaskList().size());
		
		//check the length of Task Name as per the requirements
		Assert.assertTrue("Task Name should contain between 3 and 250 long characters, but was entered " + txt_length + " character(s).", (txt_length >=3 || txt_length <= 250) );
	
	}
	
	@Then("^I should see a button labeled as \"([^\"]*)\"$")
	public void i_should_see_a_button_labeled_as(String labeledBtn) throws Throwable {
		
		Assert.assertEquals("Subtasks button is labeled wrongly", labeledBtn, taskPageObj.getManageSubtasksButton().get(0).getText().substring(4));
	}
	
	@When("^click on Manage Subtasks button$")
	public void click_on_Manage_Subtasks_button() throws Throwable {
	
	
		elements = taskPageObj.getManageSubtasksButton();
		
		subTaskObj = taskPageObj.toSubTaskPage(elements.get(0));
		
	}
	
	@When("^I enter \"([^\"]*)\" in SubTask Description field$")
	public void i_enter_in_SubTask_Description_field(String subTaskDesc) throws Throwable {
		
		subTaskObj.enterSubTaskDescription(subTaskDesc);
		
		subTask_Name = subTaskDesc;
	}
	
	
	@When("^I enter Due Date with \"([^\"]*)\"$")
	public void i_enter_Due_Date_with(String date_subTask) throws Throwable {
		
		dueDate = date_subTask;
		
		subTaskObj.enterSubTaskDueDate(dueDate);
	}


	@When("^click on Add subtask button$")
	public void click_on_Add_subtask_button() throws Throwable {
		
		taskSubList_old = 0;
		
		//Check whether SubTask list is empty after clicking on add button to add a new SubTask
		if (subTaskObj.getSizeTaskSubList() > 0){
			// Get the size of the SubTask List before clicking on add button - Get the size of old subtask list
			taskSubList_old = subTaskObj.getAllSubstasks().size();
		}
		
		//Click on button to add a new SubTask
		subTaskObj.clickOnButton_AddSubTask();
		
		taskSubList_new = 0;
		
		//Check whether SubTask list is empty after clicking on add button to add a new SubTask
		if (subTaskObj.getSizeTaskSubList() > 0){
			// Get the size of the SubTask List after clicking on add button - Get the size of new subtask list
			taskSubList_new = subTaskObj.getAllSubstasks().size();
		}
	}

	@Then("^The system should create a new substask for that task$")
	public void the_system_should_create_a_new_substask_for_that_task() throws Throwable {

		//check whether was added a new subtask 
		Assert.assertTrue("Problems to create a new SubTask", (taskSubList_old + 1) == taskSubList_new);
		
		//check whether the length of Task Description
		Assert.assertTrue("SubTask Description should have 250 characters at most", (subTask_Name.length() <= 250));
	}
	
	
	public void getNumberOfSubTasksForEachTask() throws InterruptedException{
		
		//Number of "Manage Subtask" buttons - Return the size
		size_list_manageSub = taskPageObj.getManageSubtasksButton().size();
		
		//Get all elements regarding "Manage SubTask" button - Return a List
		elements = taskPageObj.getManageSubtasksButton();
		
		String number_subtask_str = "";
		int number_subtask_int = 0;
		
		//For each "Manage SubTask" button, will be checked if all subTask number is right
		for (int i = 0; i < size_list_manageSub; i++){
					
			//Initialize the variables
			number_subtask_str = "";
			number_subtask_int = 0;
			
			//Get the number of subtask for the element i - Labeled button
			number_subtask_str = taskPageObj.getManageSubtasksButton().get(i).getText().substring(1, 2);
			
			//Convert into Integer and store in the array
			number_subtask_int =  Integer.parseInt(number_subtask_str);
			list_subtasks_button.add(number_subtask_int);
			
			//check whether SubTask list is empty after clicking on add button to add a new SubTask
			if (subTaskObj.getSizeTaskSubList() > 0){
				// Get the size of the SubTask List before clicking on add button - Get the size of old subtask list
				list_subtasks_inside.add(subTaskObj.getAllSubstasks().size());
			}else{
				list_subtasks_inside.add(0);
			}
		}
		
		Assert.assertEquals("Number of SubTasks does not match", list_subtasks_button.size(), list_subtasks_inside.size());
	}
	
	
	@Then("^The system should invalidate the Due Date$")
	public void the_system_should_invalidate_the_Due_Date() throws Throwable {

		Assert.assertTrue("Month '" + dueDate.substring(0, 2) + "' is invalid. It is expected a Numeric value and MM/DD/YYYY format", 
				StringUtils.isNumeric(dueDate.substring(0, 2)));
		
		Assert.assertTrue("Day '" 	+ dueDate.substring(3, 5) 	+ "' is invalid. It is expected a Numeric value and MM/DD/YYYY format", 
				StringUtils.isNumeric(dueDate.substring(3, 5)));
		
		Assert.assertTrue("Year '" 	+ dueDate.substring(6) 	+ "' is invalid. It is expected a Numeric value and MM/DD/YYYY format", 
				StringUtils.isNumeric(dueDate.substring(6)));
		
		Assert.assertTrue("Splitter Date is invalid. Should be '/' and MM/DD/YYYY format", 
				(dueDate.substring(2, 3).equals("/") && dueDate.substring(5, 6).equals("/")));
		

		//Convert Due Date values into Integer
		int month_int = Integer.parseInt(dueDate.substring(0, 2));
		int day_int = Integer.parseInt(dueDate.substring(3, 5));
		

		Assert.assertTrue("Month '" + month_int + "' is invalid. It is expected a Numeric value between 1 and 31", 
				(month_int >= 0 && month_int <= 12));
		
		Assert.assertTrue("Day '" + day_int + "' is invalid. It is expected a Numeric value between 1 and 12", 
				(day_int >= 0 && day_int <= 31));
		
		//check whether was added a new subtask 
		Assert.assertTrue("The system has creatde a new SubTask with a invalid Due Date: '" + dueDate +"'", taskSubList_old  == taskSubList_new);
	}
	
	@Then("^The system should not create a new substask for that task$")
	public void the_system_should_not_create_a_new_substask_for_that_task() throws Throwable {

		//check whether was added a new subtask 
		Assert.assertTrue("Problems to create a new SubTask", (taskSubList_old + 1) == taskSubList_new);
		Assert.assertTrue("SubTask Description should NOT have more than 250 characters", (subTask_Name.length() <= 250));	
	}

	@Then("^I should see the same number of tasks which is shown in the button$")
	public void i_should_see_the_same_number_of_tasks_which_is_shown_in_the_button() throws Throwable {
		getNumberOfSubTasksForEachTask();
	}
	
	@Then("^The system should not allow to create a SubTask without entering a description$")
	public void the_system_should_not_allow_to_create_a_SubTask_without_entering_a_description() throws Throwable {
		
		//check whether was added a new subtask 
		if (subTask_Name.length() == 0){
			Assert.assertTrue("SubTask Description should not be Empty", taskSubList_old == taskSubList_new);
		}
	}
	
	@Then("^I should see the task ID on the top$")
	public void i_should_see_the_task_ID_on_the_top() throws Throwable {	
		
		try {
			Integer.parseInt(subTaskObj.gettaskID().substring(13));
		} catch (RuntimeException e) {
			Assert.assertTrue("Task ID '" + subTaskObj.gettaskID() + "' Not Numeric.", false);
		}
		
		Assert.assertTrue("Task ID '" 	+ subTaskObj.gettaskID() + "' invalid.", 
				StringUtils.isNumeric(subTaskObj.gettaskID().substring(13)));
	}
	
	@Then("^The system should append all subtasks on the bottom$")
	public void the_system_should_add_all_subtasks_on_the_bottom() throws Throwable {
		
		//check whether was added a new subtask 
		Assert.assertTrue("Problems to create a new SubTask", (taskSubList_old + 1) == taskSubList_new);
	}
}
