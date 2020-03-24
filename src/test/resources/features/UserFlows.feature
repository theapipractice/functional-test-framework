Feature: UserFlows Function
	In order to avoid silly mistakes
	As a math idiot
	I want to be told the sum of two numbers

Scenario Outline: UF_Create_UserFlows 
	Given I have entered username '<username>' and password '<password>'
	When  I login
	Then  I should be informed that login '<result>'
	Then  I go to User Flows page
	Then  I click create button
	Then  I will create new User Flow 
	And   Verify that the User Flows is created sucessfully

	Examples: 
	| testing               | username                  | password             | result   |
	| valid combination     | dinhquyenonline@gmail.com | 0906879564@Qt        | passed   |
	
	Scenario Outline: UF_Search_UserFlows
	Given I have entered username '<username>' and password '<password>'
	When  I login
	Then  I should be informed that login '<result>'
	Then  I go to User Flows page 
	Then  Enter search value in text field '<searchvalue>'
	And   Verify result of userFlows '<expected>'
	Examples: 
	| testing | username                  | password      | searchvalue    | expected       |
	| valid   | dinhquyenonline@gmail.com | 0906879564@Qt | HUB Department | HUB Department | 
	| invalid | dinhquyenonline@gmail.com | 0906879564@Qt | invalidProperty|                | 
	| valid   | dinhquyenonline@gmail.com | 0906879564@Qt | Department     | HUB Department |
	| valid   | dinhquyenonline@gmail.com | 0906879564@Qt | HUB            | HUB Department |

	Scenario Outline: UF_Clear_Search_UserFlows 
	Given I have entered username '<username>' and password '<password>'
	When  I login
	Then  I should be informed that login '<result>'
	Then  I go to User Flows page 
	Then  Enter search value in text field '<searchvalue>'
	And   Verify result of userFlows '<expected>'
	Then  Click X button to clear the value
	And   Verify amout of first page of userFlows
	Examples: 
	| testing | username                   | password      | searchvalue         | expected       | 
	| valid   | dinhquyenonline@gmail.com  | 0906879564@Qt | HUB Department      | HUB Department | 

	Scenario Outline: UF_Quick_Add_New_Multi_UserFlows 
	Given I have entered username '<username>' and password '<password>'
	When  I login
	Then  I should be informed that login '<result>'
	Then  I go to User Flows page 
	Then  Create userFlows by using quickadd with create another mode
	Then  Enter search value in text field '<searchvalue>'
	And   Verify result of userFlows '<expected>' 
	
	Examples: 
	| testing | username                   | password      | searchvalue         | expected       | 
	| valid   | dinhquyenonline@gmail.com  | 0906879564@Qt | Autotest            | Autotest       | 

	Scenario Outline: UF_Quick_Add_New_Single_UserFlows 
	Given I have entered username '<username>' and password '<password>'
	When  I login
	Then  I should be informed that login '<result>'
	Then  I go to User Flows page 
	Then  Create userFlows by using quickadd without create another mode
	Then  Enter search value in text field '<searchvalue>'
	And   Verify result of userFlows '<expected>' 
	
	Examples: 
	| testing | username                   | password      | searchvalue         | expected       | 
	| valid   | dinhquyenonline@gmail.com  | 0906879564@Qt | Autotest            | Autotest       | 

	Scenario Outline: UF_Verify_Gird_And_Non_Gird 
	Given I have entered username '<username>' and password '<password>'
	When  I login
	Then  I should be informed that login '<result>'
	Then  I go to User Flows page 
	Then  Click Gird View
	And   Verify Gird view is displayed sucseefully
	Then  Click List View
	Then  Verify Non Gird view is displayed sucseefully
	
	Examples: 
	| testing | username                   | password      | 
	| valid   | dinhquyenonline@gmail.com  | 0906879564@Qt | 

	Scenario Outline: UF_The_UserFlow_Link_Will_Be_Hidden_If_You_Do_Not_Go_Project
	Given I have entered username '<username>' and password '<password>'
	When  I login
	Then  I should be informed that login '<result>'
	And   Verify Link is display
	
	Examples: 
	| testing | username                   | password      | 
	| valid   | dinhquyenonline@gmail.com  | 0906879564@Qt | 