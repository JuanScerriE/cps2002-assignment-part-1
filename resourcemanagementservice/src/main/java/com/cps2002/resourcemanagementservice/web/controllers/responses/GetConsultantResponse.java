package com.cps2002.resourcemanagementservice.web.controllers.responses;

public class GetConsultantResponse {
    private String uuid;
    private String name;
    private String type;
    private String speciality;
    private int rate;
    private int companyCut;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getCompanyCut() {
        return companyCut;
    }

    public void setCompanyCut(int companyCut) {
        this.companyCut = companyCut;
    }
}
