$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("src/test/resources/features/Logins.feature");
formatter.feature({
  "name": "Login Function Feature",
  "description": "  As a user\n  I want to ensure that Login feature is working well",
  "keyword": "Feature"
});
formatter.scenario({
  "name": "user verify field of login page - Web",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@smoke"
    },
    {
      "name": "@test"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "user opens the login page",
  "keyword": "Given "
});
formatter.match({
  "location": "LoginSteps.iOpenTheLoginPage()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "user checks that all fields are displayed correctly",
  "keyword": "And "
});
formatter.match({
  "location": "LoginSteps.iEnsureThatAllFieldsAreDisplayedCorrectly()"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
});