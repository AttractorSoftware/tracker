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
    And should see screenshot work flow by date "08/19/13"

