Feature: If user make mouse or keyboard events, event count save in database

  Background: User use trac instance
    Given I'm working with "http://127.0.0.1:8000/trac-env" and my username is "admin" and password "123"
    And I'm on tasks form of application
    And I've chosen one ticket
    And Start following
    And Emulate mouse click "13" and keyboard press "5"
    And stay for a while "11" seconds

  Scenario: Open day report
    When I open day report
    And Find frame by user "admin" with "13" clicks and "5" presses


