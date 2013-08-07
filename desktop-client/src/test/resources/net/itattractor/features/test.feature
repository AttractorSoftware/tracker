Feature: test
  Scenario: user login
    Given I run application
    When I input url "http://localhost:8000/myproject" and username "admin" and password "123"
    And I click submit button
    Then I should see tasks form
    And I click "start" button
    And I should see "record form"
    And I send comment "test\n\rdsfgds\n\r" and submit
    And I go to track "localhost:8000/myproject/ticket/1" and username "admin" and password "123"