package com.cps2002.timetablingservice.web.controllers;

import com.cps2002.timetablingservice.services.TimetablingService;
import com.cps2002.timetablingservice.services.models.Booking;
import com.cps2002.timetablingservice.web.controllers.requests.CanBookRequest;
import com.cps2002.timetablingservice.web.controllers.requests.CreateBookingRequest;
import com.cps2002.timetablingservice.web.controllers.responses.CanBookResponse;
import com.cps2002.timetablingservice.web.controllers.responses.CreateBookingResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@RestController
public class TimetablingController {
    private final ModelMapper mapper;
    private final TimetablingService timetablingService;

    public TimetablingController(ModelMapper mapper, TimetablingService timetablingService) {
        this.mapper = mapper;
        this.timetablingService = timetablingService;
    }

    @PostMapping(value = "can-book", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> canBook(@RequestBody CanBookRequest request) {
        LocalDateTime start = null;
        LocalDateTime end = null;

        try {
            start = LocalDateTime.parse(request.getStart());
            end = LocalDateTime.parse(request.getEnd());
        } catch (DateTimeParseException exception) {
            exception.printStackTrace();
            return Error.message("cannot parse timestamps");
        }

        Booking booking = Booking.builder()
                .consultantUuid(request.getConsultantUuid())
                .start(start)
                .end(end)
                .build();

        boolean canBeBooked = timetablingService.canBook(booking);

        return ResponseEntity.ok(new CanBookResponse(canBeBooked));
    }

    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CreateBookingRequest request) {
        LocalDateTime start = null;
        LocalDateTime end = null;

        try {
            start = LocalDateTime.parse(request.getStart());
            end = LocalDateTime.parse(request.getEnd());
        } catch (DateTimeParseException exception) {
            exception.printStackTrace();
            return Error.message("cannot parse timestamps");
        }

        Booking booking = Booking.builder()
                .consultantUuid(request.getConsultantUuid())
                .start(start)
                .end(end)
                .build();

        Optional<String> uuid = timetablingService.createBooking(booking);

        if (!uuid.isPresent()) {
            return Error.message("could not create customer");
        }

        return ResponseEntity.ok(new CreateBookingResponse(uuid.get()));
    }
//
//    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> get(@RequestParam String uuid) {
//        Optional<Customer> customer = customerManagementService.getCustomer(uuid);
//
//        if (customer.isEmpty()) {
//            return java.lang.Error.message("user with specified uuid does not exist");
//        }
//
//        GetCustomerResponse response = new GetCustomerResponse();
//
//        return ResponseEntity.ok(mapper.map(customer.get(), GetCustomerResponse.class));
//    }
//
//
//    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getAll() {
//        Optional<List<Customer>> customers = customerManagementService.getAllCustomers();
//
//        if (customers.isEmpty()) {
//            return java.lang.Error.message("user with specified uuid does not exist");
//        }
//
//        List<GetCustomerResponse> customersResponse = new LinkedList<>();
//
//        for (var customer : customers.get()) {
//            customersResponse.add(mapper.map(customer, GetCustomerResponse.class));
//        }
//
//        return ResponseEntity.ok(customersResponse);
//    }
//
//    @DeleteMapping (value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> delete(@RequestParam String uuid) {
//        boolean deleted = customerManagementService.deleteCustomer(uuid);
//
//        if (!deleted){
//            return java.lang.Error.message("user with specified uuid could not be deleted or does not exist");
//        }
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> update(@RequestBody UpdateCustomerRequest request) {
//        boolean updated = customerManagementService.updateCustomer(mapper.map(request, Customer.class));
//
//        if (!updated){
//            return java.lang.Error.message("user with specified uuid could not be update or does not exist");
//        }
//
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping(value = "add-booked-consultant", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> addBookedConsultant(@RequestBody CreateCustomerRequest request) {
//        return null;
//    }

}
