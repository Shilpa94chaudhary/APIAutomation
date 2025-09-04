package herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTest extends BaseTest{
    @Test
    public void updateBookingTest(){
        // Step1: Create booking
        Response response = createBooking();
        response.prettyPrint();

        // Step 2: Get booking ID to update booking details
        int bookingID = response.jsonPath().getInt("bookingid");

        // Step 3: Update booking
        // Create JSON body to update booking detail
        JSONObject body = new JSONObject();
        JSONObject bookingDates = new JSONObject();
        body.put("firstname","Robert");
        body.put("lastname" , "Downey II");  // Updated
        body.put("totalprice",800); // Updated
        body.put("depositpaid",false); // Updated
        bookingDates.put("checkin","2025-09-05");
        bookingDates.put("checkout","2025-09-08");
        body.put("bookingdates",bookingDates);
        body.put("additionalneeds","Pickup and Drop");

        Response updatedBooking = RestAssured.given().
                auth().preemptive().basic("admin","password123").
                contentType(ContentType.JSON).body(body.toString()).
                put("https://restful-booker.herokuapp.com/booking/"+bookingID);
        updatedBooking.prettyPrint();

        // Step 4: Validate updated response
        Assert.assertEquals(updatedBooking.statusCode(),200,
                "Status code is expected to be 200, but it is not.");

        SoftAssert softAssert = new SoftAssert();

        // Validate updated values
        String lastName = updatedBooking.jsonPath().getString("lastname");
        softAssert.assertEquals(lastName,"Downey II",
                "Last name should be Downey II, but it is not.");

        int totalPrice = updatedBooking.jsonPath().getInt("totalprice");
        softAssert.assertEquals(totalPrice,800,
                "Total price should be 800, but it is not.");

        Boolean depositPaid = updatedBooking.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositPaid,
                "Deposit paid should be true, but it is not");

        String additionalNeeds = updatedBooking.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(additionalNeeds,"Pickup and Drop",
                "Additional needs should be Pickup and Drop, but it is not.");

        // Validate values not updated
        String firstName = updatedBooking.jsonPath().getString("firstname");
        softAssert.assertEquals(firstName,"Robert",
                "First name should be Robert, but it is not.");

        String checkIn = updatedBooking.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(checkIn,"2025-09-05",
                "Check in date should be 2025-09-05, but it is not.");

        String checkOut = updatedBooking.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(checkOut,"2025-09-08",
                "Check out date should be 2025-09-08, but it is not.");

        softAssert.assertAll();
    }
}
