Feature: In order to see only active tickets on tasks form, user should have option to update active tickets list

  Background: User uses trac instance
    Given I'm working with "http://tracker-trac.demo.esdp.it-attractor.net" and my username is "demo" and password "123"


  Scenario: Created ticket, but not accepted it
    Given I'm on tasks form of application
    And I'm on main page of trac
    When I create new ticket
    And press "Refresh" button
    Then I shouldn't see created ticket in tasks list

  Scenario: Accepted ticket and see it on refresh of task list
    Given I'm on tasks form of application
    And I've accepted ticket on trac
    When press "Refresh" button
    Then I should see created ticket in tasks list

  Scenario: Deleted ticket and see it on refresh of task list
    Given I'm on tasks form of application
    And I've closed ticket on trac
    When press "Refresh" button
    Then I shouldn't see created ticket in tasks list

