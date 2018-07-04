package stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class LookUpPostCodeDefinatitons {

	private Response response;
	private ValidatableResponse json;
	private RequestSpecification request;
	private String postcode ;

	private String ENDPOINT_GET_POST_API = "http://api.postcodes.io/postcodes";


	@Given("a postcode of (\\w+)$")
		public void a_postcode_of(String postcode){
           this.postcode = postcode ;
		}

	@When("a user retrieves the key info by postcode$")
	public void a_user_retrieves_the_key_info_by_postcode(){
		response = given().when().get(ENDPOINT_GET_POST_API +"/"+this.postcode);
		System.out.println("response: " + response.prettyPrint());
	}

	@Then("the status code is (\\d+)$")
	public void verify_status_code(int statusCode){
		json = response.then().statusCode(statusCode);


	}

	@And("response includes the following$")
	public void response_equals(Map<String,String> responseFields){
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if(StringUtils.isNumeric(field.getValue())){
				json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
			}
			else{
				json.body(field.getKey(), equalTo(field.getValue()));
			}
		}
	}
	@When("^a user performs a get request to validate the postcode \"([^\"]*)\"$")
	public void a_user_performs_a_get_request_to_validate_the_postcode(String api_url) {
		response = given().when().get(ENDPOINT_GET_POST_API +"/"+this.postcode+ api_url );
		System.out.println("response: " + response.prettyPrint());
	}


	@Then("^the postcode validation returns true$")
	public void the_postcode_validation_returns_true(){
		json.body("result", is(true));
	}

	@Then("^the postcode validation returns false")
	public void the_postcode_validation_returns_false(){
		json.body("result", is(false));
	}

	@Given("^the query parameter is (.*)$")
    public void the_query_parameter_is (String queryParam){
		request = given().param("q" , queryParam);
	}

	@When("^the user retrieves the postcodes$")
		public void the_user_retrives_the_postcodes(){
		response = request.when().get(ENDPOINT_GET_POST_API);
		System.out.println("response: " + response.prettyPrint());
		}



	}




