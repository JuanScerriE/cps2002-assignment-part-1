package com.cps2002.timetablingservice.web.controllers.responses;

public class CanBookResponse {
    private boolean canBeBooked;

    public CanBookResponse(boolean canBeBooked) {
        this.canBeBooked = canBeBooked;
    }

    public boolean isCanBeBooked() {
        return canBeBooked;
    }

    public void setCanBeBooked(boolean canBeBooked) {
        this.canBeBooked = canBeBooked;
    }
}
