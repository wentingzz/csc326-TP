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
Given The lab procedure <name> does not already exist
When I login as admin
When I go to the Lab Procedures page
When I fill in values in the Add Lab Procedure form with <name> and <code>
Then The lab procedure was created successfully.
Examples:
	|name |code|
	|urine glucose test |5792-7|
	
Scenario Outline: HCP adds a lab procedure to an office visit
Given I have logged in with username: <user>
When I start documenting an office visit for the patient with name: <first> <last> and date of birth: <dob>
And fill in the office visit with date: <date>, hospital: <hospital>, notes: <notes>, weight: <weight>, height: <height>, blood pressure: <pressure>, household smoking status: <hss>, patient smoking status: <pss>, hdl: <hdl>, ldl: <ldl>, and triglycerides: <triglycerides>
And add a prescription for <prescription> with a dosage of <dosage> starting on <start> and ending on <end> with <renewals> renewals
And add a lap procedure with name <name> and code <code>
And submit the office visit 
Then A message indicates the visit was submitted successfully
Examples:
	| user  | first | last | dob        | date       | hospital         | notes                                                              | weight | height | pressure | hss     | pss   | hdl | ldl | triglycerides | prescription      | dosage | start      | end        | renewals | name | code |
	| svang | Jim   | Bean | 04/12/1995 | 12/15/2017 | General Hospital | Jim appears to be depressed. He also needs to eat more vegetables. | 130.4  | 73.1   | 160/80   | OUTDOOR | NEVER | 50  | 123 | 148           | Quetiane Fumarate | 100    | 12/15/2017 | 12/14/2018 | 12       | urine glucose test | 5792-7|
	
Scenario Outline: Lab Tech views lab procedures
Given I have logged in with username: <user> and password: <password>
When I go to the Lab Procedures page
I am able to access a page with the heading "Procedures"
Examples:
	| user | password |
	| labtech | greenball |