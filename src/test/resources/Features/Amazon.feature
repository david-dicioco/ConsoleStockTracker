@AllFeatures @AmazonFeature
Feature: Feature to test product stock in Amazon

  Scenario Outline: Verify stock in Amazon for - <product>
    Given Browser is open - Amazon
    And User enters Amazon homepage
    When User inputs a <product> in Amazon search box
    And User clicks Amazon search button
    And User is on Amazon <product> results page
    And User clicks Amazon <product> link
    Then User verifies <product> stock in Amazon

    Examples: 
      | product                       |
      | PlayStation 4                 |
      | PlayStation 5                 |
      | PlayStation 5 Digital Edition |
      | Xbox One Console              |
      | Xbox Series X                 |
      | Xbox Series S                 |
