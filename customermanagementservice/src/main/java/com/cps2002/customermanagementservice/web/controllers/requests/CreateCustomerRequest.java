package com.cps2002.customermanagementservice.web.controllers.requests;

public class CreateCustomerRequest {
    private String name;
    private String specialityPreference;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialityPreference() {
        return specialityPreference;
    }

    public void setSpecialityPreference(String specialityPreference) {
        this.specialityPreference = specialityPreference;
    }
}
