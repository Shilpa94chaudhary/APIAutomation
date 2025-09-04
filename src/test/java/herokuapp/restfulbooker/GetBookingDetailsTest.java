package herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingDetailsTest extends BaseTest{
    @Test
    public void getBookingDetailsTest(){
        // Create new booking for testing get booking API
        Response response = createBooking();
        response.prettyPrint();
        int bookingID = response.jsonPath().getInt("bookingid");
        // Get booking detail using booking ID
        Response bookingResponse = RestAssured.get("https://restful-booker.herokuapp.com/booking/"+bookingID);
        bookingResponse.prettyPrint();

        // Write assertions to validate booking detail
        Assert.assertEquals(bookingResponse.statusCode(),200,
                "Status code is expected to be 200, but it is not.");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(bookingResponse.jsonPath().getString("firstname"), response.jsonPath().getString("booking.firstname"),
                "Firs tname expected to be "+ response.jsonPath().getString("booking.firstname") + " but it is "+ bookingResponse.jsonPath().getString("firstname") );
        softAssert.assertEquals(bookingResponse.jsonPath().getString("lastname"), response.jsonPath().getString("booking.lastname"),
                "Last name expected to be "+ response.jsonPath().getString("booking.lastname") + " but it is "+ bookingResponse.jsonPath().getString("lastname") );
        softAssert.assertEquals(bookingResponse.jsonPath().getString("totalprice"), response.jsonPath().getString("booking.totalprice"),
                "Total price expected to be "+ response.jsonPath().getString("booking.totalprice") + " but it is "+ bookingResponse.jsonPath().getString("totalprice") );
        softAssert.assertEquals(bookingResponse.jsonPath().getString("depositpaid"), response.jsonPath().getString("booking.depositpaid"),
                "Deposit paid expected to be "+ response.jsonPath().getString("booking.depositpaid") + " but it is "+ bookingResponse.jsonPath().getString("depositpaid") );
        softAssert.assertEquals(bookingResponse.jsonPath().getString("bookingdates.checkin"), response.jsonPath().getString("booking.bookingdates.checkin"),
                "Check in date expected to be "+ response.jsonPath().getString("booking.bookingdates.checkin") + " but it is "+ bookingResponse.jsonPath().getString("bookingdates.checkin") );
        softAssert.assertEquals(bookingResponse.jsonPath().getString("bookingdates.checkout"), response.jsonPath().getString("booking.bookingdates.checkout"),
                "Check out date expected to be "+ response.jsonPath().getString("booking.bookingdates.checkout") + " but it is "+ bookingResponse.jsonPath().getString("bookingdates.checkout") );
        softAssert.assertEquals(bookingResponse.jsonPath().getString("additionalneeds"), response.jsonPath().getString("booking.additionalneeds"),
                "Additional needs expected to be "+ response.jsonPath().getString("booking.additionalneeds") + " but it is "+ bookingResponse.jsonPath().getString("additionalneeds") );

        softAssert.assertAll();

    }
}
