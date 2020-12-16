@EBGamesFeature
Feature: feature to test EB Games search functionality

  Scenario: Validate EB Games search is working
    Given browser is open - eb games
    And user is on EB Games page
    #When user hovers over product button
    And click on product
    When user enters product page
    Then user check product stock
    