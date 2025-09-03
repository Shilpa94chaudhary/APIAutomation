package herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTest {
    @Test
    public void createNewBookingTest(){

        /*
         *  "firstname" : "Jim", "lastname" : "Brown",
            "totalprice" : 111, "depositpaid" : true,
            "bookingdates" : {
                "checkin" : "2018-01-01",
                "checkout" : "2019-01-01"
            },
            "additionalneeds" : "Breakfast"
         */
        // Create JSON request body
        JSONObject body = new JSONObject();
        JSONObject bookingDates = new JSONObject();
        body.put("firstname","Robert");
        body.put("lastname" , "Downey");
        body.put("totalprice",1000);
        body.put("depositpaid",true);
        bookingDates.put("checkin","2025-09-05");
        bookingDates.put("checkout","2025-09-08");
        body.put("bookingdates",bookingDates);
        body.put("additionalneeds","Breakfast");

        System.out.println("Request body: " + body);

        // Get response
        Response response = RestAssured.given().contentType(ContentType.JSON).
                body(body.toString()).post("https://restful-booker.herokuapp.com/booking");

        response.prettyPrint();

        // Save response values in variables
        int bookingID = response.jsonPath().getInt("bookingid");
        String firstName = response.jsonPath().getString("booking.firstname");
        String lastName = response.jsonPath().getString("booking.lastname");
        int totalPrice = response.jsonPath().getInt("booking.totalprice");
        Boolean depositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        String checkIn = response.jsonPath().getString("booking.bookingdates.checkin");
        String checkOut = response.jsonPath().getString("booking.bookingdates.checkout");
        String additionalNeeds = response.jsonPath().getString("booking.additionalneeds");

        // Verify response
        Assert.assertEquals(response.statusCode(),200, "Status code should be 200, but it is not.");

        SoftAssert softAssert = new SoftAssert();
        // Verify that booking ID is valid number
        softAssert.assertTrue(bookingID > 0,"Booking ID is missing or invalid.");
        // Verify first name
        softAssert.assertEquals(firstName, body.get("firstname"),
                "First name mismatch: expected " + body.get("firstname") + " but found " + firstName);
        // Verify last name
        softAssert.assertEquals(lastName, body.get("lastname"),
                "Last name mismatch: expected " + body.get("lastname") + " but found " + lastName);
        // Verify total price
        softAssert.assertEquals(totalPrice, body.get("totalprice"),
                "Total price mismatch: expected " + body.get("totalprice") + " but found " + totalPrice);
        // Verify deposit paid status
        softAssert.assertEquals(depositPaid, body.get("depositpaid"),
                "Deposit paid should be " + body.get("depositpaid") + " but found " + depositPaid);
        // Verify check in date
        softAssert.assertEquals(checkIn, bookingDates.get("checkin") ,
                "Checkin date should be " + bookingDates.get("checkin") + " but found " + checkIn);
        // Verify check out date
        softAssert.assertEquals(checkOut, bookingDates.get("checkout"),
                "Checkout date should be " + bookingDates.get("checkout") + " but found " + checkOut);
        // Verify additional need details
        softAssert.assertEquals(additionalNeeds, body.get("additionalneeds"),
                "Additional needs should be " + body.get("additionalneeds") + " but found " + additionalNeeds);

        softAssert.assertAll();

        // Verify booking details
        String getBookingDetailURL = "https://restful-booker.herokuapp.com/booking/" +  bookingID;
        System.out.println(getBookingDetailURL);
        Response checkBooking = RestAssured.get(getBookingDetailURL);
        checkBooking.prettyPrint();

    }
}
