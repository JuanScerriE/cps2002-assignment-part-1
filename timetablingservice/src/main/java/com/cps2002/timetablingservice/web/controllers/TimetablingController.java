package com.cps2002.timetablingservice.web.controllers;

import com.cps2002.timetablingservice.services.TimetablingService;
import com.cps2002.timetablingservice.services.internal.models.Booking;
import com.cps2002.timetablingservice.web.controllers.dto._Interval;
import com.cps2002.timetablingservice.web.controllers.requests.CanBookRequest;
import com.cps2002.timetablingservice.web.controllers.requests.CreateBookingRequest;
import com.cps2002.timetablingservice.web.controllers.responses.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
public class TimetablingController {
    private final ModelMapper mapper;
    private final TimetablingService timetablingService;

    public TimetablingController(ModelMapper mapper, TimetablingService timetablingService) {
        this.mapper = mapper;
        this.timetablingService = timetablingService;
    }

    private _Interval<LocalDateTime> parseStringTimestamps(String start, String end) {
        return new _Interval<>(LocalDateTime.parse(start).truncatedTo(ChronoUnit.SECONDS), LocalDateTime.parse(end).truncatedTo(ChronoUnit.SECONDS));
    }

    @PostMapping(value = "can-book", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> canBook(@RequestBody CanBookRequest request) {
        _Interval<LocalDateTime> timestamps = null;

        try {
            timestamps = parseStringTimestamps(request.getStart(), request.getEnd());
        } catch (DateTimeParseException exception) {
            exception.printStackTrace();

            return Error.message("cannot parse timestamps");
        }

        Booking booking = Booking.builder()
                .consultantUuid(request.getConsultantUuid())
                .start(timestamps.getStart())
                .end(timestamps.getEnd())
                .build();

        boolean canBeBooked = timetablingService.canBook(booking);

        return ResponseEntity.ok(new CanBookResponse(canBeBooked));
    }


    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody CreateBookingRequest request) {
        _Interval<LocalDateTime> timestamps = null;

        try {
            timestamps = parseStringTimestamps(request.getStart(), request.getEnd());
        } catch (DateTimeParseException exception) {
            exception.printStackTrace();

            return Error.message("cannot parse timestamps");
        }

        Booking booking = Booking.builder()
            .customerUuid(request.getCustomerUuid())
            .consultantUuid(request.getConsultantUuid())
            .start(timestamps.getStart())
            .end(timestamps.getEnd())
            .build();

        Optional<String> uuid = timetablingService.createBooking(booking);

        if (!uuid.isPresent()) {
            return Error.message("could not create booking");
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
            return Error.message("booking with specified uuid does not exist");
        }

        return ResponseEntity.ok(mapper.map(booking.get(), GetBookingResponse.class));
    }

    private ResponseEntity<?> handleGetAll(Optional<List<Booking>> bookings) {
        if (!bookings.isPresent()) {
            return Error.message("could not load all bookings");
        }

        List<GetBookingResponse> customersResponse = new LinkedList<>();

        for (Booking customer : bookings.get()) {
            customersResponse.add(mapper.map(customer, GetBookingResponse.class));
        }

        return ResponseEntity.ok(customersResponse);
    }

    @GetMapping(value = "get-all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAll() {
        Optional<List<Booking>> bookings = timetablingService.getAllBookings(null, null);

        return handleGetAll(bookings);
    }


    @GetMapping(value = "get-all-by-consultant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllByConsultant(@RequestParam String consultantUuid) {
        Optional<List<Booking>> bookings = timetablingService.getAllBookings(consultantUuid, null);

        return handleGetAll(bookings);
    }

    @GetMapping(value = "get-all-by-customer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllByCustomer(@RequestParam String customerUuid) {
        Optional<List<Booking>> bookings = timetablingService.getAllBookings(null, customerUuid);

        return handleGetAll(bookings);
    }
}
