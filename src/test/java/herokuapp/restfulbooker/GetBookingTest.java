package herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTest {
    @Test
    public void getBookingDetailTest(){
        // Get booking detail for given booking ID
        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking/9");
        // Print response
        response.prettyPrint();
        // Verify the status code
        Assert.assertEquals(response.statusCode(),200,"Status code should be 200, but it is not");

        // Verify all fields
        // {"firstname":"Jim","lastname":"Ericsson","totalprice":341,
        // "depositpaid":false,
        // "bookingdates":{"checkin":"2019-04-24","checkout":"2023-09-26"},
        // "additionalneeds":"Breakfast"}
        // Get all values from response and save in variable
        String firstName = response.jsonPath().getString("firstname");
        String lastName = response.jsonPath().getString("lastname");
        int totalPrice = response.jsonPath().getInt("totalprice");
        Boolean depositPaid = response.jsonPath().getBoolean("depositpaid");
        String checkIn = response.jsonPath().getString("bookingdates.checkin");
        String checkOut = response.jsonPath().getString("bookingdates.checkout");
        String additionalNeeds = response.jsonPath().getString("additionalneeds");

        // Soft Assert
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(firstName,"Jim","First name should be Jim, but it is not.");
        softAssert.assertEquals(lastName,"Ericsson","Last name should be Ericsson, but it is not.");
        softAssert.assertEquals(totalPrice,341,"Total price should be 341, but it is not.");
        softAssert.assertFalse(depositPaid,"Deposit paid should be true, but it is not.");
        softAssert.assertEquals(checkIn,"2019-04-24");
        softAssert.assertEquals(checkOut,"2023-09-26");
        softAssert.assertEquals(additionalNeeds,"Breakfast");

        softAssert.assertAll();

    }
}
