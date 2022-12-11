package com.cps2002.customermanagementservice.web.controllers;

import com.cps2002.customermanagementservice.services.CustomerManagementService;
import com.cps2002.customermanagementservice.services.models.Customer;
import com.cps2002.customermanagementservice.web.controllers.requests.CreateCustomerRequest;
import com.cps2002.customermanagementservice.web.controllers.requests.UpdateCustomerRequest;
import com.cps2002.customermanagementservice.web.controllers.responses.CreateCustomerResponse;
import com.cps2002.customermanagementservice.web.controllers.responses.GetCustomerResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerManagementController {
    private final ModelMapper mapper;
    private final CustomerManagementService customerManagementService;

    public CustomerManagementController(ModelMapper mapper, CustomerManagementService customerManagementService) {
        this.mapper = mapper;
        this.customerManagementService = customerManagementService;
    }

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CreateCustomerRequest request) {
        Optional<String> uuid = customerManagementService.createCustomer(mapper.map(request, Customer.class));

        if (!uuid.isPresent()) {
            return Error.message("could not create customer");
        }

        CreateCustomerResponse response = new CreateCustomerResponse();

        response.setUuid(uuid.get());

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@RequestParam String uuid) {
        Optional<Customer> customer = customerManagementService.getCustomer(uuid);

        if (!customer.isPresent()) {
            return Error.message("user with specified uuid does not exist");
        }

        GetCustomerResponse response = new GetCustomerResponse();

        return ResponseEntity.ok(mapper.map(customer.get(), GetCustomerResponse.class));
    }


    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        Optional<List<Customer>> customers = customerManagementService.getAllCustomers();

        if (!customers.isPresent()) {
            return Error.message("user with specified uuid does not exist");
        }

        List<GetCustomerResponse> customersResponse = new LinkedList<>();

        for (Customer customer : customers.get()) {
            customersResponse.add(mapper.map(customer, GetCustomerResponse.class));
        }

        return ResponseEntity.ok(customersResponse);
    }

    @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestParam String uuid) {
        boolean deleted = customerManagementService.deleteCustomer(uuid);

        if (!deleted) {
            return Error.message("user with specified uuid could not be deleted or does not exist");
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody UpdateCustomerRequest request) {
        boolean updated = customerManagementService.updateCustomer(mapper.map(request, Customer.class));

        if (!updated) {
            return Error.message("user with specified uuid could not be update or does not exist");
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "add-booked-consultant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addBookedConsultant(@RequestBody CreateCustomerRequest request) {
        return null;
    }



}
