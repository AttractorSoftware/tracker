Feature: Cucumber should test Java Application
  Scenario: User should login
    Given I run application
    When I input url "http://tracker-trac.demo.esdp.it-attractor.net/" and username "zakir.lamiev" and password "11235813ewq"
    And I click "submit" button
    Then I should see "tasks form"