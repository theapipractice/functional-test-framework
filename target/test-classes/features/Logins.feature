Feature: Login Function Feature
  As a user
  I want to ensure that Login feature is working well

  @smoke @test
  Scenario: user verify field of login page - Web
    Given user opens the login page
    And   user checks that all fields are displayed correctly

  @smoke @single
  Scenario: user verify that login with invalid information - Web
    Given user opens the login page
    Then  user login with invalid information
      |everzet@knplabs.com      |1234        |
      |         			    |12345678@A  |
      |usera                    |            |
      |                         |            |
      |userb                    |12345678@B  |
      |usere         			|12345678@A  |

  @smoke
  Scenario: user verify that login with valid information - Web
    Given user opens the login page
#    Then  user actives scanning attempts to find potential vulnerabilities before login
    Then  user login with valid information
      |claim     		|usera       |12345678@A    |
      |UnderWriting     |userb       |12345678@A    |
#    Then  user actives scanning attempts to find potential vulnerabilities after login
    Then  user Clicks Sign Out Link
    And   user checks that all fields are displayed correctly