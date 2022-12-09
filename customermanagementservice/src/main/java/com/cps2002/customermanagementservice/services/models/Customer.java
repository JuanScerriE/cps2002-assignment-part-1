package com.cps2002.customermanagementservice.services.models;

public class Customer {
    private String uuid;
    private String name;
    private String specialityPreference;

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

    public String getSpecialityPreference() {
        return specialityPreference;
    }

    public void setSpecialityPreference(String specialityPreference) {
        this.specialityPreference = specialityPreference;
    }
}
