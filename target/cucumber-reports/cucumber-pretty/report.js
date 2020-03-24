$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("Users.feature");
formatter.feature({
  "line": 2,
  "name": "Users Function",
  "description": "In order to avoid silly mistakes\r\nAs a math idiot\r\nI want to be told the sum of two numbers",
  "id": "users-function",
  "keyword": "Feature",
  "tags": [
    {
      "line": 1,
      "name": "@Users"
    }
  ]
});
formatter.scenarioOutline({
  "line": 8,
  "name": "The user searches name",
  "description": "",
  "id": "users-function;the-user-searches-name",
  "type": "scenario_outline",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 7,
      "name": "@SearchUserPage"
    }
  ]
});
formatter.step({
  "line": 9,
  "name": "I open the users page",
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "I enter the name \"\u003cname\u003e\"",
  "keyword": "When "
});
formatter.step({
  "line": 11,
  "name": "I Click the Search Button",
  "keyword": "Then "
});
formatter.step({
  "line": 12,
  "name": "The user should be returned",
  "keyword": "And "
});
formatter.examples({
  "line": 14,
  "name": "",
  "description": "",
  "id": "users-function;the-user-searches-name;",
  "rows": [
    {
      "cells": [
        "testing",
        "name"
      ],
      "line": 15,
      "id": "users-function;the-user-searches-name;;1"
    },
    {
      "cells": [
        "valid",
        "John Doe"
      ],
      "line": 16,
      "id": "users-function;the-user-searches-name;;2"
    }
  ],
  "keyword": "Examples"
});
formatter.before({
  "duration": 8055188,
  "status": "passed"
});
formatter.before({
  "duration": 6250353290,
  "status": "passed"
});
formatter.scenario({
  "line": 16,
  "name": "The user searches name",
  "description": "",
  "id": "users-function;the-user-searches-name;;2",
  "type": "scenario",
  "keyword": "Scenario Outline",
  "tags": [
    {
      "line": 1,
      "name": "@Users"
    },
    {
      "line": 7,
      "name": "@SearchUserPage"
    }
  ]
});
formatter.step({
  "line": 9,
  "name": "I open the users page",
  "keyword": "Given "
});
formatter.step({
  "line": 10,
  "name": "I enter the name \"John Doe\"",
  "matchedColumns": [
    1
  ],
  "keyword": "When "
});
formatter.step({
  "line": 11,
  "name": "I Click the Search Button",
  "keyword": "Then "
});
formatter.step({
  "line": 12,
  "name": "The user should be returned",
  "keyword": "And "
});
formatter.match({
  "location": "UsersSteps.iOpenTheUsersPage()"
});
formatter.result({
  "duration": 227978784,
  "status": "passed"
});
formatter.match({
  "arguments": [
    {
      "val": "John Doe",
      "offset": 18
    }
  ],
  "location": "UsersSteps.iEnterTheName(String)"
});
formatter.result({
  "duration": 281544299,
  "status": "passed"
});
formatter.match({
  "location": "UsersSteps.iClickTheSearchButton()"
});
formatter.result({
  "duration": 1930894,
  "status": "passed"
});
formatter.match({
  "location": "UsersSteps.theUserShouldBeReturned()"
});
formatter.result({
  "duration": 173960486,
  "status": "passed"
});
formatter.after({
  "duration": 13018129,
  "status": "passed"
});
formatter.after({
  "duration": 202388888,
  "status": "passed"
});
formatter.before({
  "duration": 1890717,
  "status": "passed"
});
formatter.before({
  "duration": 4069191576,
  "status": "passed"
});
formatter.scenario({
  "line": 19,
  "name": "The user creates new user",
  "description": "",
  "id": "users-function;the-user-creates-new-user",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 18,
      "name": "@CreateNewUser"
    }
  ]
});
formatter.step({
  "line": 20,
  "name": "I open the users page",
  "keyword": "Given "
});
formatter.step({
  "line": 21,
  "name": "I click Add User Button",
  "keyword": "When "
});
formatter.step({
  "line": 22,
  "name": "I add new user",
  "keyword": "Then "
});
formatter.step({
  "line": 23,
  "name": "I verify the user added",
  "keyword": "And "
});
formatter.match({
  "location": "UsersSteps.iOpenTheUsersPage()"
});
formatter.result({
  "duration": 132934,
  "status": "passed"
});
formatter.match({
  "location": "UsersSteps.iClickAddUserButton()"
});
formatter.result({
  "duration": 290132325,
  "status": "passed"
});
formatter.match({
  "location": "UsersSteps.iAddNewUser()"
});
formatter.result({
  "duration": 1553394287,
  "status": "passed"
});
formatter.match({
  "location": "UsersSteps.iVerifyTheUserAdded()"
});
formatter.result({
  "duration": 1589384,
  "error_message": "java.lang.AssertionError: expected [firstName] but found [John Doe]\n\tat org.testng.Assert.fail(Assert.java:94)\n\tat org.testng.Assert.failNotEquals(Assert.java:513)\n\tat org.testng.Assert.assertEqualsImpl(Assert.java:135)\n\tat org.testng.Assert.assertEquals(Assert.java:116)\n\tat org.testng.Assert.assertEquals(Assert.java:190)\n\tat org.testng.Assert.assertEquals(Assert.java:200)\n\tat suite.stepdefs.UsersSteps.iVerifyTheUserAdded(UsersSteps.java:48)\n\tat ✽.And I verify the user added(Users.feature:23)\n",
  "status": "failed"
});
formatter.after({
  "duration": 542741804,
  "status": "passed"
});
formatter.after({
  "duration": 196144970,
  "status": "passed"
});
});