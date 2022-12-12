package com.cps2002.timetablingservice.web.controllers.requests;

public class CreateBookingRequest {
    private String customerUuid;
    private String consultantUuid;
    private String start;

    public String getCustomerUuid() {
        return customerUuid;
    }

    public void setCustomerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
    }

    public String getConsultantUuid() {
        return consultantUuid;
    }

    public void setConsultantUuid(String consultantUuid) {
        this.consultantUuid = consultantUuid;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    private String end;
}
