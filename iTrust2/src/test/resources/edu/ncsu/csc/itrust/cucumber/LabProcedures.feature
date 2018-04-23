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
When I fill in values in the Add Lab Procedure form with <name> and <code> and <components> and <property>
Then The lab procedure <code> was created successfully.
Examples:
	|name |code| components | property |
	|urine glucose test |57921-7| Glucose | MCnc  |
	
Scenario Outline: Admin edits a lab procedure
Given I can login as admin
When I go to the Lab Procedures page
When I fill in values in the Add Lab Procedure form with <name> and <code> and <components> and <property>
When I edit the components field of <code> to be <editedComponents>
Then the lab procedure, <code>, has a components field that is edited successfully, and contains <editedComponents>.
Examples:
	|name |code| components | property | editedComponents |
	|fasting glucose in serum |15581-6 | Glucose^post CFst  | 	MCnc   | Glucose post-fast |
	
Scenario Outline: Admin deletes a lab procedure
Given I can login as admin
When I go to the Lab Procedures page
When I fill in values in the Add Lab Procedure form with <name> and <code> and <components> and <property>
When I click the delete button for <code>
Then the lab procedure, <code>, is deleted successfully.
Examples:
	|name |code| components | property | 
	|Selenium [Mass/volume] in Serum or Plasma|	57241-0 | Selenium | 	MCnc   | 
	
Scenario Outline: HCP adds a lab procedure to an office visit
Given I log in with username <user> and password <password>
When I start to fill out the form to document the office visit, including adding a lab procedure with <code> and <priority> and <comments> and <labtech>
Then The ofice visit is documented successfully.
Examples:
	| user  | password |  first | last | dob        | date       | hospital         | notes                                                              | weight | height | pressure | hss     | pss   | hdl | ldl | triglycerides | prescription      | dosage | start      | end        | renewals |  code | priority | comments | labtech | 
	| svang | 123456 | Jim   | Bean | 04/12/1995 | 12/15/2017 | General Hospital | Jim appears to be depressed. He also needs to eat more vegetables. | 130.4  | 73.1   | 160/80   | OUTDOOR | NEVER | 50  | 123 | 148           | Quetiane Fumarate | 100    | 12/15/2017 | 12/14/2018 | 12       |  5792-7| 2| suspected diabetic | labtech |
	
Scenario Outline: Lab Tech views lab procedures
Given I log in with username <user> and password <password>
When I go to the View Lab Procedures page
Then I am able to access a page with the heading "Procedures"
Examples:
	| user | password |
	| labtech | 123456 |