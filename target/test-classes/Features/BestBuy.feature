@AllFeatures @BestBuyFeature
Feature: Feature to test product stock in BestBuy

  Scenario Outline: Verify stock in BestBuy for - <product>
    Given Browser is open - BestBuy
    And User enters BestBuy homepage
    When User inputs a <product> in BestBuy search box
    And User clicks BestBuy search button
    #And User is on BestBuy <product> results page
    And User clicks BestBuy <product> link
    Then User verifies <product> stock in BestBuy

    Examples: 
      | product                       |
      #| PlayStation 4                 |
      #| PlayStation 5                 |
      #| PlayStation 5 Digital Edition |
      #| Xbox One Console              |
      #| Xbox Series X                 |
      | Xbox Series S                 |
