#Author: Marcio Almeida
Feature: Create a New Task
  As a ToDo app user
  I should ba able to create a task
  so I can manage my tasks

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

  @CreateNewTask
  Scenario: Validation of user message
    Then I should see the message "Hey user, this is your todo list for today:"

  @CreateNewTask
  Scenario: Validate Task Name with less than 3 characters using mouse-click
    When enter "KJ" in Task Name field
    And click on add button
    Then The system should not add the task

  @CreateNewTask
  Scenario: Validate Task Name with less 3 characters using ENTER key
    When enter "TE" in Task Name field
    And hit "ENTER" key
    Then The system should not add the task

  @CreateNewTask
  Scenario: Validate Task Name with 3 characters using mouse-click
    When enter "PMZ" in Task Name field
    And click on add button
    Then The system should add the task

  @CreateNewTask
  Scenario: Validate Task Name with 3 characters using ENTER key
    When enter "MQO" in Task Name field
    And hit "ENTER" key
    Then The system should add the task

  @CreateNewTask
  Scenario: Validate Task Name with 250 characters using mouse-click
    When enter "MADFGRTHGBBDFHFDHHFRRTUVNGMADFGRTHGBBDFHFDHHFRRTUVNGMADFGRTHGBBDFHFDHHFRRTUVNGMADFGRTHGBBDFHFDHHFRRTUVNGMADFGRTHGBBDFHFDHHFRRTUVNGMADFGRTHGBBDFHFDHHFRRTUVNGMADFGRTHGBBDFHFDHHFRRTUVNGMADFGRTHGBBDFHFDHHFRRTUVNGMADFGRTHGBBDFHFDHHFRRTUVNGMADFGRTHGBBDFHFD" in Task Name field
    And click on add button
    Then The system should add the task

  @CreateNewTask
  Scenario: Validate Task Name with more than 250 characters using ENTER key
    When enter "VMVMBG98575646WHSDAWQ340394843GDVMVMBG98575646WHSDAWQ340394843GDVMVMBG98575646WHSDAWQ340394843GDVMVMBG98575646WHSDAWQ340394843GDVMVMBG98575646WHSDAWQ340394843GDVMVMBG98575646WHSDAWQ340394843GDVMVMBG98575646WHSDAWQ340394843GDVMVMBG98575646WHSDAWQ340394" in Task Name field
    And hit "ENTER" key
    Then The system should not add the task

  @CreateNewTask
  Scenario: Validate Task Name containing between 3 and 250 characters using ENTER key
    When enter "CANCEL BUTTON IS DISABLED IN THE FORM" in Task Name field
    And hit "ENTER" key
    Then The system should add the task

  @CreateNewTask
  Scenario: Validate Task Name containing between 3 and 250 characters using mouse-click
    When enter "EXCEPTION IS TRIGGERED WHEN CLICKED ON BACK ARROW" in Task Name field
    And hit "ENTER" key
    Then The system should add the task