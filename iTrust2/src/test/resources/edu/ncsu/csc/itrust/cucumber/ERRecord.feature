#Author: Wenting Zheng(wzheng8)
Feature: Emergency Record
	As an HCP
	I want to view a patient's emergency record
	So that I can view relevant information for a patient who needs immediate treatment
	As an ER
	I want to view a patient's emergency record
	So that I can view relevant information for a patient who needs immediate treatment	

Scenario Outline: HCP views valid emergency record
	Given all the required users exist
	And Dr Shelly Vang has logged in and chosen to view a patient's emergency record
	When she selects a patient with first name: <first> and last name: <last>
	Then The correct <fullname>, <age>, <birthday>, <gender>, and <bloodtype> are displayed
	
Examples:
	| first | last | fullname | age | birthday | gender | bloodtype |
	| Nellie  | Sanderson | Nellie Sanderson | 26 | 12/27/1992 | Female | ABPos |