Feature: test
  Scenario: user login
    Given I run application
    Given I have host "tracker-trac.demo.esdp.it-attractor.net" username "demo" and password "123"
    When I input url "http://tracker-trac.demo.esdp.it-attractor.net/" and username "demo" and password "123"
    And I click submit button
    Then I should see tasks form
    And I click "start" button
    And I should see "record form"
    And I send comment "test\n\rdsfgds\n\r" and submit
    And login to trac in url "tracker-trac.demo.esdp.it-attractor.net/" and username "demo" and password "123"
    And I go to track "tracker-trac.demo.esdp.it-attractor.net/ticket/1"