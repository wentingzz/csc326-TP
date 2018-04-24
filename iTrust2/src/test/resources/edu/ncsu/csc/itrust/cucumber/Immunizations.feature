#Author Hannah and Abeer

Feature: Manage Immunizations
	As an admin
	I want to manage the list of available vaccines
	So that all records are up to date and HCPs can prescribe the latest vaccines
	As an HCP
	I want to record immunizations for patients
	So that I have record of past prescriptions and they can be fulfilled
	As a patient
	I want to view my past immunizations
	So that I can make sure they match my expectations

Scenario Outline: Add New Vaccine
	Given I login as admin
	When I choose to add a new vaccine
	And submit the values for CPT <cpt>, name <name>, and description <description>
	Then the vaccine <name> is successfully added to the system

Examples:
	| cpt          | name         | description       |
	| 12345 | IG | Immunoglobin |
	
Scenario Outline: Add Immunization to an Office Visit
	Given required patient exists
    Given I login with username: <user>
    When I started documenting an office visit for the patient with name: <first> <last> and date of birth: <dob>
    And filled in the office visit with date: <date>, hospital: <hospital>, notes: <notes>, weight: <weight>, height: <height>, blood pressure: <pressure>, household smoking status: <hss>, patient smoking status: <pss>, hdl: <hdl>, ldl: <ldl>, and triglycerides: <triglycerides>
    And add an immunization for <immunization>
    And submitted the office visit 
   	Then A message indicated the visit was submitted successfully

Examples:
	| user  | first | last | dob        | date       | hospital         | notes                                                              | weight | height | pressure | hss     | pss   | hdl | ldl | triglycerides | immunization      | 
	| svang | Jim   | Bean | 04/12/1995 | 12/15/2017 | General Hospital | Jim appears to be depressed. He also needs to eat more vegetables. | 130.4  | 73.1   | 160/80   | OUTDOOR | NEVER | 50  | 123 | 148           | IG |



Scenario Outline: View Immunizations
	Given I login with username: <user>
	When I choose to view my immunizations
	Then I see an immunization for <immunization> 

Examples:
	| user  | immunization      | 
	| jbean | IG |