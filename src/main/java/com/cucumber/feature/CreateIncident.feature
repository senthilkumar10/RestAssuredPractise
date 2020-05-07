Feature: Create Incident in Service Now

  Scenario: Create Incident only with short description

    Given Request URL is Initiated
    And Authorization is performed
    When Body is posted with JSON file ServiceNow_Data1.json
    Then Status code should be 201
    And Response time within 5 seconds