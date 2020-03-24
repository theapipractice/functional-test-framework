@Model
Feature: Model Function
	As a
	I want to be ensure that the model feature works well

    @MD_Create_Duplicate_New_Model
    Scenario Outline: MD_Create_Duplicate_New_Model
	Given I login successfully with userName "<username>" and passWord "<password>" that login "<result>"
	Then  I Click on Model Link 
	Then  Add New Model With Existing Name "<modelName>" and Type Of Model "<Type>"
	And   Verify the error message "<errorMessage>" will be displayed
	Examples: 
	| testing     | username                    | password       | modelName     | Type        | errorMessage     | result |
	| duplicated  | dinhquyenonline1@gmail.com  | 0906879564@Qt  | ANewModeal    | Object      |  asas            | passed |

	