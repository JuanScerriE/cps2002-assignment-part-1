package com.cps2002.consultancyservice.web.controllers.responses;
import com.cps2002.consultancyservice.services.models.Booking;
import java.util.ArrayList;

public class GetBookingsResponse {
    private ArrayList<Booking> bookings;



    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

}

// Path: consultancyservice/src/main/java/com/cps2002/consultancyservice/web/controllers/responses/GetConsultantResponse.java
// Compare this snippet from consultancyservice/src/main/java/com/cps2002/consultancyservice/web/controllers/responses/GetConsultantResponse.java:
// package com.cps2002.consultancyservice.web.controllers.responses;
//
// public class GetConsultantResponse {
//     private String uuid;
//     private String name;
//     private String type;
//     private String speciality;
//     private int rate;
//
