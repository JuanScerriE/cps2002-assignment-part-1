package com.cps2002.timetablingservice.web.controllers.responses;

public class CreateBookingResponse {
    private String uuid;

    public CreateBookingResponse(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
