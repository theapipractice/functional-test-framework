Feature: Login Function
	In order to avoid silly mistakes
	As a math idiot
	I want to be told the sum of two numbers

	@Login
Scenario Outline: LG_Login
	Given I have entered userName "<username>" and passWord "<password>"
	When  I login
	Then  I should be informed that login "<result>"

	Examples: 
	| testing               | username                  | password             | result   |

