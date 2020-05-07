Feature: Create Issue in Jira

  Scenario: Create Issue in Jira with minimal details

    Given Request URL is Initiated for Jira
    And Authorization is performed for Jira
    When In Jira body is posted with JSON file Jira_Issue_Creation.json
    Then Status code should be 201 for Jira
    And Response time within 5 seconds for Jira
    And Delete the bugID
    Then Delete Status code should be 204