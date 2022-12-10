package com.cps2002.resourcemanagementservice.web.controllers.responses;

public class CreateConsultantResponse {

    private String consultantId;

    public CreateConsultantResponse(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }


}