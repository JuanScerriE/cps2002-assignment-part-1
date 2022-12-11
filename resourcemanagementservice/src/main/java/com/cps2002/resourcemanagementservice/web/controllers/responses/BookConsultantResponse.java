package com.cps2002.resourcemanagementservice.web.controllers.responses;

public class BookConsultantResponse {
    private String bookingId;

    public BookConsultantResponse(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

}
