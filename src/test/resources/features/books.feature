Feature: Simple Books API Testing

  Scenario Outline: Full API workflow
    Given I create a new client and get access token
    When I send a GET request to fetch "<Type>" books
    And I get the book details
    And I create a new order
    Then I get all my orders
    Then I get the order details
    And I update the order name
    Then I get the order details
    And I delete the order
    Then I get all my orders


    Examples:
      | Type        |
      | fiction     |
      | non-fiction |