package com.cps2002.timetablingservice.web.controllers.customermanagement;

import com.cps2002.timetablingservice.services.customermanagement.CustomerManagementService;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cps2002.timetablingservice.web.controllers.requests.EchoRequest;

@RestController
@RequestMapping(path = "customer-management")
public class CustomerManagementController {
    private final ModelMapper mapper;
    private final CustomerManagementService customerManagementService;

    public CustomerManagementController(ModelMapper mapper, CustomerManagementService customerManagementService) {
        this.mapper = mapper;
        this.customerManagementService = customerManagementService;
    }

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody EchoRequest request) {
        return null;
    }

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@RequestBody EchoRequest request) {
        return null;
    }

    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody EchoRequest request) {
        return null;
    }

    @PutMapping(value = "add-booked-consultant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBookedConsultant(@RequestBody EchoRequest request) {
        return null;
    }

    @DeleteMapping (value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestBody EchoRequest request) {
        return null;
    }


}
