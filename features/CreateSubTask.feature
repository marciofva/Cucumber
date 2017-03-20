#Author: Marcio Almeida

Feature: Create a SubTask
	As a ToDo App user
	I should be able to create a subtask
	So I can break down my tasks in smaller pieces

  Background: Login into the system
    Given I access the website "http://qa-test.avenuecode.com/"
    And click on SignIn link on the NavBar
    When I submit the form with valid credentials
      | fields   | value            |
      | email    | student@test.com |
      | password |         12345678 |
    And click on SignIn button
    Then I should see the "My Tasks link"
    And I should see the "My Tasks button"
    When click on My Tasks link
    When enter "SAMPLE TASKS" in Task Name field
    And click on add button
    Then The system should add the task

  @CreateSubTask
  Scenario: Validate the button labeled as (Manage Subtasks)
    Then I should see a button labeled as "Manage Subtasks"
    
  @CreateSubTask
  Scenario: Enter a new subtask with less than 250 characters
  	When click on Manage Subtasks button
  	And I enter "SUBTASK 01" in SubTask Description field
  	And I enter Due Date with "06/08/2013"
  	And click on Add subtask button
    Then The system should create a new substask for that task
    
  @CreateSubTask
  Scenario: Enter a new subtask with an empty SubTask description
  	When click on Manage Subtasks button
  	And I enter Due Date with "11/13/2000"
  	And click on Add subtask button
    Then The system should not allow to create a SubTask without entering a description
    
  @CreateSubTask
  Scenario: Enter a new subtask with 250 characters
  	When click on Manage Subtasks button
  	And I enter "HDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFArAZ" in SubTask Description field
  	And I enter Due Date with "10/12/2015"
  	And click on Add subtask button
    Then The system should create a new substask for that task
    
  @CreateSubTask
  Scenario: Enter a new subtask with more than 250 characters
  	When click on Manage Subtasks button
  	And I enter "KHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFAHDFGSFYJQ3RFASW3SFArAZ" in SubTask Description field
  	And I enter Due Date with "08/28/1999"
  	And click on Add subtask button
    Then The system should not create a new substask for that task
    
  @CreateSubTask
  Scenario: Enter a new subtask with an invalid Due Date format
  	When click on Manage Subtasks button
  	And I enter "SUBTASK 02" in SubTask Description field
  	And I enter Due Date with "02-08-2013"
  	And click on Add subtask button
    Then The system should invalidate the Due Date
    
  @CreateSubTask
  Scenario: Enter a new subtask with an invalid month
  	When click on Manage Subtasks button
  	And I enter "SUBTASK 02" in SubTask Description field
  	And I enter Due Date with "45/13/2011"
  	And click on Add subtask button
    Then The system should invalidate the Due Date
    
  @CreateSubTask
  Scenario: Enter a new subtask with an invalid day
  	When click on Manage Subtasks button
  	And I enter "SUBTASK 03" in SubTask Description field
  	And I enter Due Date with "09/33/2011"
  	And click on Add subtask button
    Then The system should invalidate the Due Date
    
  @CreateSubTask
  Scenario: Enter a new subtask with an invalid year
  	When click on Manage Subtasks button
  	And I enter "SUBTASK 04" in SubTask Description field
  	And I enter Due Date with "09/33/20DF"
  	And click on Add subtask button
    Then The system should invalidate the Due Date
    
  @CreateSubTask
  Scenario: Enter a new subtask with an invalid value in Due Date
  	When click on Manage Subtasks button
  	And I enter "SUBTASK 02" in SubTask Description field
  	And I enter Due Date with "H7/81/201G"
  	And click on Add subtask button
    Then The system should invalidate the Due Date
    
  @CreateSubTask
  Scenario: Validate the number of subtasks created in the button
  	When click on Manage Subtasks button
    Then I should see the same number of tasks which is shown in the button
    
  @CreateSubTask
  Scenario: Check whether Task ID is displayed
  	When click on Manage Subtasks button
  	Then I should see the task ID on the top
  	
  @CreateSubTask
  Scenario: Check whether all subtasks were appended on the bottom
  	When click on Manage Subtasks button
  	And I enter "ERROR 01" in SubTask Description field
  	And I enter Due Date with "11/22/2012"
  	And click on Add subtask button
  	And I enter "ERROR 02" in SubTask Description field
  	And I enter Due Date with "05/10/2008"
  	And click on Add subtask button
  	And I enter "ERROR 03" in SubTask Description field
  	And I enter Due Date with "09/29/2011"
  	And click on Add subtask button
    Then The system should append all subtasks on the bottom
    