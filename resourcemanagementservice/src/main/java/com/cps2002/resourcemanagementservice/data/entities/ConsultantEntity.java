package com.cps2002.resourcemanagementservice.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ConsultantEntity {
    @Id
    private String uuid;
    private String value;
    private String name;
    private String type;
    private String speciality;
    private int rate;
    private double companyRate;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public double getCompanyRate() {
        return companyRate;
    }

    public void setCompanyRate(double companyRate) {
        this.companyRate = companyRate;
    }


    @Override
    public String toString() {
        //override to string method to return consultant object

        return "Consultant{" + "\n" +
                "Name" + name + "\n" +
                "Type" + type + "\n" +
                "Speciality" + speciality + "\n" +
                "Rate" + rate + "\n" +
                "Id" + uuid + "\n" +
                "Company Rate" + companyRate + "\n" +
                '}';
    }
}
