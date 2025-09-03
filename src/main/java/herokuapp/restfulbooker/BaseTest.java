package herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class BaseTest {
    protected Response createBooking(){
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
        return RestAssured.given().contentType(ContentType.JSON).
                body(body.toString()).post("https://restful-booker.herokuapp.com/booking");
    }
}
