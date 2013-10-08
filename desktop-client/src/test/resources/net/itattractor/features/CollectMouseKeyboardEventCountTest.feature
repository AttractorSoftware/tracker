Feature: If user make mouse or keyboard events, event count save in database

  Background: User use trac instance
    Given I'm working with "http://127.0.0.1:8000/trac-env" and my username is "admin" and password "secret"
  Scenario: Make events
    Given I'm on tasks form of application
    And I've chosen one ticket
    And Start following