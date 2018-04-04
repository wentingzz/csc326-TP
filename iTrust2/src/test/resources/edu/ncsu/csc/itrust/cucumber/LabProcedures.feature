#Author: Hannah Morrison (hmorris3)
Feature: Lab Procedures
	As an admin
	I want to create lab procedures
	So that they can be used in the hospital
	As an HCP
	I want to add lab procedures to office visits
	So that they can be kept track of
	As a Lab Tech
	I want to view lab procedures
	So that I can look at what I might need to run. 

Scenario Outline: Admin creates a lab procedure
Given I can login as admin
When I go to the Lab Procedures page
When I fill in values in the Add Lab Procedure form with <name> and <code>
Then The lab procedure was created successfully.
Examples:
	|name |code|
	|urine glucose test |5792-7|
	
Scenario Outline: HCP adds a lab procedure to an office visit
Given I have logged in with username: <user> and password <password>
When I start fill out the form to document the office visit, including adding a lab procedure with name <name> and code <code>
Then The ofice visit is documented successfully.
Examples:
	| user  | password |  first | last | dob        | date       | hospital         | notes                                                              | weight | height | pressure | hss     | pss   | hdl | ldl | triglycerides | prescription      | dosage | start      | end        | renewals | name | code |
	| svang | 123456 | Jim   | Bean | 04/12/1995 | 12/15/2017 | General Hospital | Jim appears to be depressed. He also needs to eat more vegetables. | 130.4  | 73.1   | 160/80   | OUTDOOR | NEVER | 50  | 123 | 148           | Quetiane Fumarate | 100    | 12/15/2017 | 12/14/2018 | 12       | urine glucose test | 5792-7|
	
Scenario Outline: Lab Tech views lab procedures
Given I have logged in with username: <user> and password: <password>
When I go to the Lab Procedures page
Then I am able to access a page with the heading "Procedures"
Examples:
	| user | password |
	| labtech | greenball |