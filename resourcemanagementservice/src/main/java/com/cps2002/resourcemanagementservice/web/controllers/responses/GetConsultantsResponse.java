package com.cps2002.resourcemanagementservice.web.controllers.responses;

import com.cps2002.resourcemanagementservice.services.models.Consultant;

import java.util.ArrayList;

public class GetConsultantsResponse {
    private ArrayList<Consultant> consultants;


    public ArrayList<Consultant> getConsultants() {
        return consultants;
    }

    public void setConsultants(ArrayList<Consultant> consultants) {
        this.consultants = consultants;
    }

}

   