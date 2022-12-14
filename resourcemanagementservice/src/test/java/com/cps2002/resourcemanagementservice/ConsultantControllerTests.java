package com.cps2002.resourcemanagementservice;

import com.cps2002.resourcemanagementservice.services.ConsultantsService;
import com.cps2002.resourcemanagementservice.services.models.Booking;
import com.cps2002.resourcemanagementservice.services.models.Consultant;
import com.cps2002.resourcemanagementservice.web.controllers.requests.BookConsultantRequest;
import com.cps2002.resourcemanagementservice.web.controllers.requests.CreateConsultantRequest;
import com.cps2002.resourcemanagementservice.web.controllers.requests.UpdateConsultantRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultantControllerTests extends Tests {

    public ConsultantControllerTests(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    @Autowired
    ConsultantsService consultantsService;


    @Test
    public void testCreateConsultant() throws Exception {
        CreateConsultantRequest request = new CreateConsultantRequest();

//        ConsultantsService create = new ConsultantsService();

        Consultant consultant = new Consultant();
        consultant.setName("John");
        consultant.setType("Consultant");
        consultant.setSpeciality("Maths");
        consultant.setRate(10);


        request.setValue(consultant);

//        Consultant got = create.GetConsultant("2ac0cb64");
//        System.out.println(asJsonString(request));

        mockMvc.perform(post("/new_consultant")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isOk());

    }




    @Test
    public void testGetConsultant() throws Exception {


        Consultant consultant = new Consultant();
        //set values of consultant for testing
        consultant.setName("John");
        consultant.setType("Consultant");
        consultant.setSpeciality("Maths");
        consultant.setRate(10);

        String consultantId = consultantsService.CreateConsultant(consultant);
        consultant.setUuid(consultantId);


        mockMvc.perform(get("/consultant/{consultantId}", consultantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(consultant.getName()))
                .andExpect(jsonPath("$.type").value(consultant.getType()))
                .andExpect(jsonPath("$.speciality").value(consultant.getSpeciality()))
                .andExpect(jsonPath("$.rate").value(consultant.getRate()))
                .andExpect(jsonPath("$.uuid").value(consultant.getUuid()))
                .andReturn();

    }

    //test for get all consultants
    @Test
    public void testGetAllConsultants() throws Exception {

        //pass customer id and put query function before booking to find consultant id, or
        //give option for them to pass consultant id themselves || name.
         Consultant consultant = new Consultant();

         consultant.setName("John");
         consultant.setType("Consultant");
         consultant.setSpeciality("Maths");
         consultant.setRate(10);

         String consultantId = consultantsService.CreateConsultant(consultant);
         consultant.setUuid(consultantId);


        Consultant consultantX = new Consultant();

        consultantX.setName("Sam");
        consultantX.setType("Consultant");
        consultantX.setSpeciality("Fraud");
        consultantX.setRate(10);

        String consultantXId = consultantsService.CreateConsultant(consultantX);
        consultantX.setUuid(consultantXId);


        mockMvc.perform(get("/consultants"))
                .andExpect(status().isOk())
                .andReturn();

    }


    //test delete consultant

    @Test
    public void testDeleteConsultant() throws Exception {

        //pass customer id and put query function before booking to find consultant id, or
        //give option for them to pass consultant id themselves || name.
        Consultant consultant = new Consultant();
        //set values of consultant for testing
        consultant.setName("John");
        consultant.setType("Consultant");
        consultant.setSpeciality("Maths");
        consultant.setRate(10);

        String consultantId = consultantsService.CreateConsultant(consultant);
        consultant.setUuid(consultantId);

        mockMvc.perform(delete("/delete/{consultantId}", consultantId))
                .andExpect(status().isOk());


    }

    //test for update consultant

    @Test
    public void testUpdateConsultant() throws Exception {
        UpdateConsultantRequest request = new UpdateConsultantRequest();
        //pass customer id and put query function before booking to find consultant id, or
        //give option for them to pass consultant id themselves || name.
        Consultant consultant = new Consultant();
        //set values of consultant for testing
        consultant.setName("John");
        consultant.setType("Consultant");
        consultant.setSpeciality("Maths");
        consultant.setRate(10);

        String consultantId = consultantsService.CreateConsultant(consultant);
        consultant.setUuid(consultantId);
        //new consultant object
        Consultant updated_consultant = new Consultant();
        updated_consultant.setName("Sam");
        updated_consultant.setType("Consultant");
        updated_consultant.setSpeciality("English");
        updated_consultant.setRate(10);
        updated_consultant.setUuid(consultantId);

        request.setValue(updated_consultant);


        mockMvc.perform(post("/update_consultant")
                        .content(asJsonString(request))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());


    }





    //test delete booking


    //test get consultants by speciality 
    @Test
    public void testGetConsultantsBySpeciality() throws Exception {

        //pass customer id and put query function before booking to find consultant id, or
        //give option for them to pass consultant id themselves || name.
        Consultant consultant = new Consultant();
        //set values of consultant for testing
        consultant.setName("John");
        consultant.setType("Consultant");
        consultant.setSpeciality("Maths");
        consultant.setRate(10);

        String consultantId = consultantsService.CreateConsultant(consultant);
        consultant.setUuid(consultantId);

        mockMvc.perform(get("/getallconsultants/{speciality}", consultant.getSpeciality()))
                .andExpect(status().isOk())
                .andReturn();

    }

}