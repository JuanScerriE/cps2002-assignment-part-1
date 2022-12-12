package com.cps2002.timetablingservice.web.controllers.responses;

public class GetBookingResponse {
    private String uuid;
    private String customerUuid;
    private String consultantUuid;
    private String start;
    private String end;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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
}
