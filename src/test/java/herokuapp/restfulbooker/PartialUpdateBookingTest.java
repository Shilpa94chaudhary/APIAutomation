package herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTest extends BaseTest {
    @Test
    public void partialBookingTest(){
        // Create new booking for testing partial update booking
        Response response = createBooking();
//        response.prettyPrint();
        int bookingID = response.jsonPath().getInt("bookingid");

        // Create body to partial update booking detail
        JSONObject body = new JSONObject();
        body.put("totalprice",800);
        body.put("depositpaid",false);
        body.put("additionalneeds","Pick up and drop");

        Response partiallyUpdatedBooking = RestAssured.given().auth().preemptive().basic("admin","password123").
                contentType(ContentType.JSON).body(body.toString()).
                patch("https://restful-booker.herokuapp.com/booking/"+bookingID);
        partiallyUpdatedBooking.prettyPrint();

        Assert.assertEquals(partiallyUpdatedBooking.statusCode(),200,
                "Status code is expected to be 200 but it is "+ partiallyUpdatedBooking.statusCode());

        SoftAssert softAssert = new SoftAssert();

        // Assert updated values
        softAssert.assertEquals(partiallyUpdatedBooking.jsonPath().getInt("totalprice"),body.get("totalprice"),
                "Totalprice is expected to be " + body.get("totalprice") + " but it is " + partiallyUpdatedBooking.jsonPath().getInt("totalprice"));

        softAssert.assertEquals(partiallyUpdatedBooking.jsonPath().getBoolean("depositpaid"),body.get("depositpaid"),
                "Depositpaid is expected to be " + body.get("depositpaid") + " but it is " + partiallyUpdatedBooking.jsonPath().getBoolean("depositpaid"));

        softAssert.assertEquals(partiallyUpdatedBooking.jsonPath().getString("additionalneeds"),body.get("additionalneeds"),
                "Additionalneeds is expected to be " + body.get("additionalneeds") + " but it is " + partiallyUpdatedBooking.jsonPath().getString("additionalneeds"));

        // Assert values not updated
        softAssert.assertEquals(partiallyUpdatedBooking.jsonPath().getString("firstname"),response.jsonPath().getString("booking.firstname"),
                "First name is expected to be" + response.jsonPath().getString("booking.firstname") + " but it is " + partiallyUpdatedBooking.jsonPath().getString("firstname"));

        softAssert.assertEquals(partiallyUpdatedBooking.jsonPath().getString("lastname"),response.jsonPath().getString("booking.lastname"),
                "Last name is expected to be" + response.jsonPath().getString("booking.lastname") + " but it is " + partiallyUpdatedBooking.jsonPath().getString("lastname"));

        softAssert.assertEquals(partiallyUpdatedBooking.jsonPath().getString("depositpaid.checkin"),response.jsonPath().getString("booking.depositpaid.checkin"),
                "Check in date is expected to be" + response.jsonPath().getString("booking.depositpaid.checkin") + " but it is " + partiallyUpdatedBooking.jsonPath().getString("depositpaid.checkin"));

        softAssert.assertEquals(partiallyUpdatedBooking.jsonPath().getString("depositpaid.checkout"),response.jsonPath().getString("booking.depositpaid.checkout"),
                "Check out date is expected to be" + response.jsonPath().getString("booking.depositpaid.checkout") + " but it is " + partiallyUpdatedBooking.jsonPath().getString("depositpaid.checkout"));

        softAssert.assertAll();
    }
}
