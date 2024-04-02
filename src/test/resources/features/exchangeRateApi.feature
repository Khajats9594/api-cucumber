Feature: Exchange Rate API Testing

  Background:
    When I fetch the exchange rate for endpoint "v6/latest/USD"

  Scenario Outline: Validate API response status and currency pairs count
    Then I verify the status code and status returned in the API response
    And I verify that <EXPECTED_CURRENCY_PAIR_SIZE> currency pairs are returned by the API

    Examples:
      | EXPECTED_CURRENCY_PAIR_SIZE |
      | 162                         |

  Scenario Outline: Validate USD price against AED in specified range
    Then I verify the USD price against the AED is in the range of <MIN_AED_RATE> - <MAX_AED_RATE>

    Examples:
      | MIN_AED_RATE | MAX_AED_RATE |
      | 3.6          | 3.7          |

  Scenario Outline: Validate API response time
    Then I verify the API response time is not less than <EXPECTED_RESPONSE_TIME> seconds from the current time in seconds

    Examples:
      | EXPECTED_RESPONSE_TIME |
      | 3                      |

  Scenario: Validate API response schema
    Then I validate the API response schema
