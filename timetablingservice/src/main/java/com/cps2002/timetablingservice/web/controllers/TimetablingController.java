package com.cps2002.timetablingservice.web.controllers;

import com.cps2002.timetablingservice.data.repositories.BookingRepository;
import com.cps2002.timetablingservice.services.TimetablingService;
import com.cps2002.timetablingservice.services.models.Booking;
import com.cps2002.timetablingservice.web.controllers.requests.CanBookRequest;
import com.cps2002.timetablingservice.web.controllers.requests.CreateBookingRequest;
import com.cps2002.timetablingservice.web.controllers.responses.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
public class TimetablingController {
    private final ModelMapper mapper;
    private final TimetablingService timetablingService;
    private final BookingRepository bookingRepository;

    public TimetablingController(ModelMapper mapper, TimetablingService timetablingService,
                                 BookingRepository bookingRepository) {
        this.mapper = mapper;
        this.timetablingService = timetablingService;
        this.bookingRepository = bookingRepository;
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

    @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@RequestParam String uuid) {
        boolean deleted = timetablingService.deleteBooking(uuid);

        if (!deleted) {
            return Error.message("could not delete booking");
        }

        return ResponseEntity.ok(new DeleteBookingResponse(deleted));
    }

    @GetMapping(value = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> get(@RequestParam String uuid) {
        Optional<Booking> booking = timetablingService.getBooking(uuid);

        if (!booking.isPresent()) {
            return Error.message("user with specified uuid does not exist");
        }

        return ResponseEntity.ok(mapper.map(booking.get(), GetBookingResponse.class));
    }

    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        Optional<List<Booking>> bookings = timetablingService.getAllBookings();

        if (!bookings.isPresent()) {
            return Error.message("could not load all bookings");
        }

        List<GetBookingResponse> customersResponse = new LinkedList<>();

        for (Booking customer : bookings.get()) {
            customersResponse.add(mapper.map(customer, GetBookingResponse.class));
        }

        return ResponseEntity.ok(customersResponse);
    }
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
