#Author kpresle

Feature: Delete A Hospital
	As an Admin
	I want to delete a hospital
	So I can keep the system updated with only current hospitals

Scenario Outline: Delete An Existing Hospital
Given I am able to log in as an admin 
When I navigate to the Delete Hospital page
When I click the radio button for <name>
When I press submit without checking the check box
Then I am not directed to a blank page
Examples:
	| name |
	| General Hospital|