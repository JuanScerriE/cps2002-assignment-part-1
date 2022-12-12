package com.cps2002.timetablingservice.web.controllers.requests;

public class CanBookRequest {
    private String consultantUuid;
    private String start;
    private String end;

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
