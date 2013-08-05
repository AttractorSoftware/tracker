Feature: test
  Scenario: user login
    Given I run application
    When I input url "http://tracker-trac.demo.esdp.it-attractor.net/" and username "beknazar" and password "beknazar31"
    And I click submit button
    Then I should see tasks form
    And I click "start" button
    And I should see "record form"
    And I send comment "bbbb" and submit
    And I go to track "tracker-trac.demo.esdp.it-attractor.net/ticket/4" and username "beknazar" and password "beknazar31"

