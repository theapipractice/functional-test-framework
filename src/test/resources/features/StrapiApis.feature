@Strapi
Feature: Hospital Apis
  As a user
  I want to ensure that Hospital Apis are working well

  Background: Submit Authorization
    Given user set authorization endpoint
    Then  user send post auth request

  @Get
  Scenario: Verify the user can get Hospital by Id - Api
    Given user access to the hospital endpoint
    Then  user sends get hospital request with id
      |abd92922-9f8e-4a48-8f49-ce568558a2f7|
    And   user checks the get response body and code
      |200|

  @GetList
  Scenario Outline: Verify the user can get list Hospital - Api
    Given user access to the hospital endpoint
    Then  user sends get hospital request with limit : "<limit>" and Organization : "<organization>"
    And   user checks the get response body and code "<statusCode>"
    Examples:
      |limit	|organization				| statusCode  |
      |  2      |3M TEMBILAHAN, RIAU - RS   | 	200		  |

  @Post
  Scenario: Verify the user can create new Hospital - Api
    Given user access to the hospital endpoint
    Then  user sends post hospital request with json body
    And   user checks the response code
      |200|

  @GetInvalid
  Scenario: Verify the user can not get hospital without Authorization - Api
    Given user access to the hospital endpoint without authorization
    Then  user sends get hospital request with id
      |abd92922-9f8e-4a48-8f49-ce568558a2f7|
    And   user checks status invalid response code
      |403     |Forbidden     |Forbidden   |