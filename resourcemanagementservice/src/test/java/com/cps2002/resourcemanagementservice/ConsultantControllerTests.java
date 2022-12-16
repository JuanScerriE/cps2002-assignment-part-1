package com.cps2002.resourcemanagementservice;

import com.cps2002.resourcemanagementservice.services.ConsultantsService;
import com.cps2002.resourcemanagementservice.services.models.Consultant;
import com.cps2002.resourcemanagementservice.web.controllers.requests.CreateConsultantRequest;
import com.cps2002.resourcemanagementservice.web.controllers.requests.UpdateConsultantRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConsultantControllerTests extends Tests {

    public ConsultantControllerTests(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    @Autowired
    ConsultantsService consultantsService;

    @MockBean
    private RestTemplate rest;

    @Test
    public void testCreateConsultant() throws Exception {
        CreateConsultantRequest request = new CreateConsultantRequest();

        Consultant consultant = new Consultant();
        consultant.setName("John");
        consultant.setType("Senior");
        consultant.setSpeciality("Maths");
        consultant.setRate(100);

        request.setValue(consultant);

        mockMvc.perform(post("/new_consultant")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());

    }

    @Test
    public void testGetConsultant() throws Exception {
        Consultant consultant = new Consultant();
      
        consultant.setName("John");
        consultant.setType("Senior");
        consultant.setSpeciality("Maths");
        consultant.setRate(100);

        String consultantId = consultantsService.CreateConsultant(consultant);
        consultant.setUuid(consultantId);

        System.out.println(consultant.getCompanyCut());

        mockMvc.perform(get("/consultant/{consultantId}", consultantId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(consultant.getName()))
                .andExpect(jsonPath("$.type").value(consultant.getType()))
                .andExpect(jsonPath("$.speciality").value(consultant.getSpeciality()))
                .andExpect(jsonPath("$.rate").value(consultant.getRate()))
                .andExpect(jsonPath("$.uuid").value(consultant.getUuid()))

                .andExpect(jsonPath("$.companyCut").value(consultant.getCompanyCut()))
                .andReturn();

    }

    
    @Test
    public void testGetAllConsultants() throws Exception {
        
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

    @Test
    public void testDeleteConsultant() throws Exception {
        Consultant consultant = new Consultant();

        consultant.setName("John");
        consultant.setType("Consultant");
        consultant.setSpeciality("Maths");
        consultant.setRate(10);

        String consultantId = consultantsService.CreateConsultant(consultant);

        consultant.setUuid(consultantId);

        mockMvc.perform(delete("/delete/{consultantId}", consultantId))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateConsultant() throws Exception {
        UpdateConsultantRequest request = new UpdateConsultantRequest();
       
        Consultant consultant = new Consultant();
      
        consultant.setName("John");
        consultant.setType("Consultant");
        consultant.setSpeciality("Maths");
        consultant.setRate(10);

        String consultantId = consultantsService.CreateConsultant(consultant);
        consultant.setUuid(consultantId);
       
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

    @Test
    public void testGetConsultantsBySpeciality() throws Exception {
       
        Consultant consultant = new Consultant();

        consultant.setName("John");
        consultant.setType("Consultant");
        consultant.setSpeciality("Maths");
        consultant.setRate(10);

        String consultantId = consultantsService.CreateConsultant(consultant);
        consultant.setUuid(consultantId);

        Consultant consultant2 = new Consultant();

        consultant2.setName("John2");
        consultant2.setType("Consultant");
        consultant2.setSpeciality("Maths");
        consultant2.setRate(102);

        String consultantId2 = consultantsService.CreateConsultant(consultant2);
        consultant2.setUuid(consultantId2);

        mockMvc.perform(get("/getallconsultants/{speciality}", "Mat"))
                .andExpect(status().isOk())
                .andReturn();
    }
}