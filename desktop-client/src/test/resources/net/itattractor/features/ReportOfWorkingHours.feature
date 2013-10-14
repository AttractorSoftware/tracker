Feature: If user wants to see working hours of tickets,user should choose date and see the report

  Background: User use trac instance
  Given I'm working with "http://127.0.0.1:8000/trac-env" and my username is "admin" and password "123"

  Scenario: Check if link Report is working
    Given I'm on Tracker page
    When I click on link Report of user "admin"
    Then should see choose date

  Scenario: User see working hours of new ticket
    Given I'm working on new ticket for 10 sec
    When I go to report table of user "admin"
    Then I should see 10 min of new ticket

  Scenario: User see working hours of today's date
    Given I'm on page Report of user "admin"
    When I check working hours
    And choose the ticket
    And working for 10 sec
    When I'm on page Report of user "admin"
    And choose todays date in calendar
    And  choose end date "10-09-2015" in calendar
    And push submit button
    Then I should see working hours plus 10 min
