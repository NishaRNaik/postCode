Feature: Lookup postcode information by postcode

  Scenario: User calls web service to find all key info by postcode
	Given a postcode of NR11TS
	When  a user retrieves the key info by postcode
	Then the status code is 200
    And response includes the following
	| result.postcode			    | NR1 1TS	|
	| result.country				| England	|
	| result.region				    | East of England |
	| result.admin_district         | Norwich   |

  Scenario: validate a valid  postcode
    Given a postcode of NR11TS
    When  a user performs a get request to validate the postcode "/validate"
    Then the status code is 200
    And  the postcode validation returns true


  Scenario: validate a invalid  postcode
    Given a postcode of NR1
    When  a user performs a get request to validate the postcode "/validate"
    Then the status code is 200
    And  the postcode validation returns false

  Scenario:  query a postcode
    Given the query parameter is NR11TS
    When  the user retrieves the postcodes
    Then the status code is 200
    And response includes the following
      | result.postcode			    | NR1 1TS	|
      | result.country				| England	|
      | result.region				| East of England |
      | result.admin_district         | Norwich   |
