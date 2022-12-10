package com.cps2002.consultancyservice.web.controllers.responses;
public class UpdateConsultantResponse {
    private String consultantId;

    public UpdateConsultantResponse(String consultantId) {
        this.consultantId = consultantId;
    }
    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

}
