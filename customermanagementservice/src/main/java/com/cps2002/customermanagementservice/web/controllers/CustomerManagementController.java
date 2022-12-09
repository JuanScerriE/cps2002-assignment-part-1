package com.cps2002.customermanagementservice.web.controllers;

import com.cps2002.customermanagementservice.services.CustomerManagementService;
import com.cps2002.customermanagementservice.web.controllers.requests.CreateCustomerRequest;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> create(@RequestBody CreateCustomerRequest request) {
        return null;
    }

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@RequestBody CreateCustomerRequest request) {
        return null;
    }

    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody CreateCustomerRequest request) {
        return null;
    }

    @PutMapping(value = "add-booked-consultant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBookedConsultant(@RequestBody CreateCustomerRequest request) {
        return null;
    }

    @DeleteMapping (value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestBody CreateCustomerRequest request) {
        return null;
    }


}
