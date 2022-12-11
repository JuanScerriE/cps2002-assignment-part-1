package com.cps2002.resourcemanagementservice.web.controllers.requests;

import com.cps2002.resourcemanagementservice.services.models.Booking;

public class BookConsultantRequest {
    private Booking booking;

    public Booking getValue() {
        return booking;
    }

    public void setValue(Booking booking) {
        this.booking = booking;
    }
}
