#Author: Hannah Morrison (hmorris3)

Feature: Personal Representatives
	As a patient
	I want to be another patient's representative and have a representative
	so that someone can make decisions for me if I become incapacitated.
	As a HCP
	I want to declare personal representatives and view the representatives of patients
	so that I can help patients prepare for and act properly if patients become incapacitated.
	
Scenario Outline: Test HCP Adding A Personal Representative for A Patient
Given I am able to log in to iTrust as <user> with password <password>
When I go to the Personal Representatives page
When I select patient with the name <patient>
When I select the representative <patientrep>
When I click the 'Add Representative' button
Then <patientrep> is successfully added as a representative.
Examples:
	| user | password | patient | patientrep |
	| hcp | 123456 | patient  | cscThreeTwentySix |
	
Scenario Outline: Test Patient Adding A Personal Representative for Themselves
Given I am able to log in to iTrust as <user> with password <password>
When I go to the Personal Representatives page
When I add the patient <patientrep> by selecting their name and clicking the 'Add Representative' button
Then <patientrep> is succesfully added as a representative.
Examples:
	| user | password | patientrep |
	| cscThreeTwentySix | redball | patient |
	
Scenario Outline: Test Patient Removing Their Patient Representative
Given I am able to log in to iTrust as <user> with password <password>
When I go to the Personal Representatives page
When I add the patient <patientrep> by selecting their name and clicking the 'Add Representative' button
When I remove the representative <patientrep> by clicking the 'Delete' button for that representative.
Then that representative <patientrep> does not appear.
Examples:
	| user | password | patientrep |
	| cscThreeTwentySix | redball | patient |