package com.cps2002.resourcemanagementservice.web.controllers;

import com.cps2002.resourcemanagementservice.services.ConsultantsService;
import com.cps2002.resourcemanagementservice.services.models.Consultant;
import com.cps2002.resourcemanagementservice.web.controllers.requests.CreateConsultantRequest;
import com.cps2002.resourcemanagementservice.web.controllers.requests.UpdateConsultantRequest;
import com.cps2002.resourcemanagementservice.web.controllers.responses.CreateConsultantResponse;
import com.cps2002.resourcemanagementservice.web.controllers.responses.DeleteConsultantResponse;
import com.cps2002.resourcemanagementservice.web.controllers.responses.GetConsultantResponse;
import com.cps2002.resourcemanagementservice.web.controllers.responses.UpdateConsultantResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
public class ConsultantServiceController {

    @Autowired
    ConsultantsService consultantsService;


    @Autowired
    private final ModelMapper mapper;

    public ConsultantServiceController(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping(value = "new_consultant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateConsultantResponse> submit( @RequestBody CreateConsultantRequest request) {

        Consultant consultantCreation = mapper.map(request.getValue(), Consultant.class);
        
        

        String consultantId = consultantsService.CreateConsultant(consultantCreation);

        consultantCreation.setUuid(consultantId);
        


       
        return ResponseEntity.ok(new CreateConsultantResponse(consultantId));
    }

    @GetMapping(value = "consultant/{consultantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetConsultantResponse> get(@PathVariable String consultantId) {
        Consultant consultant = consultantsService.GetConsultant(consultantId);

        if (consultant == null) {
            return ResponseEntity.notFound().build();
        } else {

            GetConsultantResponse getConsultantResponse = mapper.map(consultant, GetConsultantResponse.class);
            return ResponseEntity.ok(getConsultantResponse);
        }
    }

    @GetMapping(value = "consultants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GetConsultantResponse>> getAll() {
        ArrayList<Consultant> consultants = consultantsService.GetConsultants();

        if (consultants == null) {
            return ResponseEntity.notFound().build();
        }

        LinkedList<GetConsultantResponse> response = new LinkedList<>();

        for (Consultant consultant : consultants) {
            response.add(mapper.map(consultant, GetConsultantResponse.class));
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete/{consultantId}")
    public ResponseEntity<?> deletePost(@PathVariable String consultantId) {
        boolean deleted = consultantsService.DeleteConsultant(consultantId);

        if (!deleted) {
            ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }


    @PostMapping(value = "/update_consultant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateConsultantResponse> update( @RequestBody UpdateConsultantRequest request) {


        Consultant consultantCreation = mapper.map(request.getValue(), Consultant.class);
        System.out.println("test");
        System.out.println(consultantCreation.toString());

        String consultantId = consultantsService.UpdateConsultant(consultantCreation.getUuid(), consultantCreation);
        System.out.println(consultantId);
        return ResponseEntity.ok(new UpdateConsultantResponse(consultantId));
    }

    //get all consultants with a specific speciality
    @GetMapping(value = "getallconsultants/{speciality}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GetConsultantResponse>> getAllC(@PathVariable String speciality) {

        //ConsultantService.java includes all functions/operations
        //this gets specific consultant, you can format to any type of query you'd like

        ArrayList<Consultant> consultants = consultantsService.GetConsultantsBySpeciality(speciality);
        

        if (consultants == null) {
            return ResponseEntity.notFound().build();
        }


        LinkedList<GetConsultantResponse> response = new LinkedList<>();

        for (Consultant consultant : consultants) {
            response.add(mapper.map(consultant, GetConsultantResponse.class));
        }

        return ResponseEntity.ok(response);
    }



}

