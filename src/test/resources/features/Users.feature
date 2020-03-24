@Users
Feature: Users Function
	In order to avoid silly mistakes
	As a math idiot
	I want to be told the sum of two numbers

@SearchUserPage
Scenario Outline: The user searches name
	Given I open the users page
	When  I enter the name "<name>"
	Then   I Click the Search Button
	And  The user should be returned

	Examples:
	| testing     |    name    |
	|  valid      |   John Doe |

@CreateNewUser
Scenario: The user creates new user
		Given I open the users page
		When  I click Add User Button
		Then  I add new user
		And   I verify the user added


