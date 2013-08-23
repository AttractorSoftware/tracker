Feature: In order to check work flow user should

  Background:
    Given I'm working with "tracker-trac.demo.esdp.it-attractor.net" and my username is "vteremasov" and password "kr230393a"

  Scenario: User navigates to tracker tab on trac instance
    Given I open browser
    When navigate to trac instance
    Then should see tracker tab in main menu

  Scenario: User chooses one of the users on tracker tab
    Given I'm on main page of trac
    When click on Tracker menu link
    Then should see list of users and choose first
#    And should see screenshot work flow by date "08/19/13"

    Scenario: User check screenshots work flow
      Given First user from user list on tracker main page is chosen
      When click on that user
      And choose date in "08/19/13" calendar
      And push update button
      Then should see screensots

