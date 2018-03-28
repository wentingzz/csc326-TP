#Author: Hannah Morrison (hmorris3)
Feature: ER User
	As an admin
	I want to create ER Users
	So that they can access iTrust
	As an ER User
	I want to log in
	So that I can access iTrust
	
Scenario Outline: Admin creates an ER User who signs in successfully
Given the user with username <user> does not exist
When I login as admin
When I navigate to the Add User page
When I fill in the values in the Add User form with username <user> and password <password>
And select the type of user as ER User
Then the ER User is created successfully
Examples:
	| user | password |
	| testeruser	| greenball |