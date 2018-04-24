#Author kpresle

Feature: Delete A User
	As an Admin
	I want to delete a user
	So I can keep the system updated with only current users

Scenario Outline: Delete An Existing User
Given I, an admin, am able to log in
When I navigate to the Delete User page
When I select the radio button for <user>
When I press submit without checking the checkbox
Then I am not redirected to a blank page
Examples:
	| user |
	| pwtestuser4|