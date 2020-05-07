Feature: Incident Management

  Scenario: Create Incident

    Given url 'https://dev96924.service-now.com/api/now/table/change_request'
    And header Authorization = call read('basic-auth.js') {username: 'admin', password: 'Senthil123$'}
    When method get
    Then status 200
    And match header Content-Type contains 'application/json'