Feature: In order to check workflow user

  Background:
    Given I have host "tracker-trac.demo.esdp.it-attractor.net" username "demo" and password "123"

  Scenario: Did the tracker plugin install
    Given The browser is run
    When I open default host
    Then I should see tab "Tracker"

  Scenario: Did the authorization module install
    Given The browser is run
    When I open host "tracker-trac.demo.esdp.it-attractor.net/authenticate"
    Then I should see status "Success"

  Scenario: Opens a user page
    Given I have been sent several screenshots
    When I open host "tracker-trac.demo.esdp.it-attractor.net/users"
    And find default user
    And click on the user
    Then I should see text "Username: demo"

  Scenario: Are there any screenshots of the user
    Given I have been sent several screenshots
    When I open host "tracker-trac.demo.esdp.it-attractor.net/users"
    And find default user
    And click on the user
    And choose today date in calendar
    And click "Update" button
    Then I should see screenshots

  Scenario: Screenshot modal window
    Given I have been sent several screenshots
    When I open host "tracker-trac.demo.esdp.it-attractor.net/users"
    And find default user
    And click on the user
    And choose today date in calendar
    And click "Update" button
    And choose one image
    And click on image
    Then I should see modal window

