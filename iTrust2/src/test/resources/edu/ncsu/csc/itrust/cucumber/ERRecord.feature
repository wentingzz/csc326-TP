Author: Wenting Zheng(wzheng8)
Feature: Emergency Record
	As an HCP
	I want to view emergency record
	So that I can view relevant information for a patient who needs immediate treatment
	As an ER
	I want to view emergency record
	So that I can view relevant information for a patient who needs immediate treatment	

Scenario Outline: HCP views valid emergency record 
Given The required ERRecord facilities exist
When I log in to iTrust2 as an HCP
When I go to the Emergency Health Records page
When I fill in user type with <usesrtype>
Then The <name>, <age>, <birthday>, <gender>, and <blood> are correct
And I see the list of diagnoses codes for the patient
Examples:
	| usertype | name | age | birthday | gender | blood |
	| csc326  | CSC | 13 | 2005 |  |  |

Scenario Outline: HCP views invalid emergency record
Given The required ERRecord facilities exist
When I log in to iTrust2 as an HCP
When I go to the Emergency Health Records page
When I fill in user type with <usesrtype>
Then an error message is shown
Examples:
	| usertype |
	| mynonexistentpatient  |

Scenario Outline: ER user views valid emergency record
Given The required ERRecord facilities exist
When I log in to iTrust2 as an ER
When I go to the Emergency Health Records page
When I fill in user type with <usesrtype>
Then The <name>, <age>, <birthday>, <gender>, and <blood> are correct
And I see the list of diagnoses codes for the patient
Examples:
	| usertype | name | age | birthday | gender | blood |
	| csc326  | ER | 13 | 2005 | Female | A |