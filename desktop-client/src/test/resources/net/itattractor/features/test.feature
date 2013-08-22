Feature: test
  Scenario: user login
    Given I run application
    When I input url "http://tracker-trac.demo.esdp.it-attractor.net/" and username "vteremasov" and password "kr230393a"
    And I click submit button
    Then I should see tasks form
    And I click "start" button
    And I should see "record form"
    And I send comment "test\n\rdsfgds\n\r" and submit
    And login to trac in url "tracker-trac.demo.esdp.it-attractor.net/" and username "vteremasov" and password "kr230393a"
    And I go to track "tracker-trac.demo.esdp.it-attractor.net/ticket/1"