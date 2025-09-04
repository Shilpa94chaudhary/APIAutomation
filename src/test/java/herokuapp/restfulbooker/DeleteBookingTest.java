package herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DeleteBookingTest extends BaseTest{
    @Test
    public void deleteBookingTest(){
        // Create new booking for testing partial update booking
        Response response = createBooking();
        int bookingID = response.jsonPath().getInt("bookingid");

        // Delete the booking detail
        Response deteledBooking = RestAssured.
                given().auth().preemptive().basic("admin","password123").
                delete("https://restful-booker.herokuapp.com/booking/" + bookingID);

        deteledBooking.prettyPrint();
        System.out.println(deteledBooking.then().extract().body().asString());

        // Assert status code and message
        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(deteledBooking.statusCode(),201,
                "Status code expected to be 201 but it is " + deteledBooking.statusCode());

        softAssert.assertEquals(deteledBooking.then().extract().body().asString(),"Created",
                "Response should be Created but it is " + deteledBooking.then().extract().body().asString());

        // Try to get booking detail, response should be 404 record not found
        Response bookingDetail = RestAssured.get("https://restful-booker.herokuapp.com/booking/" + bookingID);
        bookingDetail.prettyPrint();

        // Asset that record is not found
        softAssert.assertEquals(bookingDetail.statusCode(),404,
                "Status code is expected to be 404 but it is " + bookingDetail.statusCode());

        softAssert.assertEquals(bookingDetail.then().extract().body().asString(), "Not Found",
                "Response for deteled booking should be Not Found but it is " + bookingDetail.then().extract().body().asString());

        softAssert.assertAll();
    }
}
