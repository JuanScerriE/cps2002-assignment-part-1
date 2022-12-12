package com.cps2002.timetablingservice.web.controllers.responses;

public class DeleteBookingResponse {
    private boolean deleted;

    public DeleteBookingResponse(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
