package com.cps2002.consultancyservice.web.controllers.requests;

import com.cps2002.consultancyservice.services.models.Consultant;

public class CreateConsultantRequest {

    private Consultant consultant;
     

    public Consultant getValue() {
        return consultant;
    }

    public void setValue(Consultant  consultant) {
        this.consultant = consultant;
    }

}
