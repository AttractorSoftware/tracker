Feature: If user wrote comment on record form, this comment must be saved in file tracker.xml in home directory

  Background: User uses trac instance
    Given I'm working with "http://tracker-trac.demo.esdp.it-attractor.net" and my username is "demo" and password "123"
    #Given I'm working with "http://127.0.0.1:8002/trac-env" and my username is "ashedrin" and password "secret"
  Scenario: Write comment and check that this comment has saved in tracker.xml
    Given I'm on tasks form of application
    And I've chosen one ticket
    When I wrote comment "blablablabla" and submit
    Then I see in file "tracker.xml" that this comment exists