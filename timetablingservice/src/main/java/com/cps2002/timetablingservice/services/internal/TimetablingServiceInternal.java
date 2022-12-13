package com.cps2002.timetablingservice.services.internal;

import com.cps2002.timetablingservice.data.entities.BookingEntity;
import com.cps2002.timetablingservice.data.repositories.BookingRepository;
import com.cps2002.timetablingservice.services.TimetablingService;
import com.cps2002.timetablingservice.services.internal.models.Booking;
import com.cps2002.timetablingservice.services.internal.models.Consultant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Qualifier("internal")
public class TimetablingServiceInternal implements TimetablingService {
    private final ModelMapper mapper;
    private final BookingRepository bookingRepo;
    private final RestTemplate rest;

    private final static double START_OF_WORK_HOURS = 8;
    private final static double END_OF_WORK_HOURS = 17;

    public TimetablingServiceInternal(ModelMapper mapper, BookingRepository bookingRepo, RestTemplate rest) {
        this.mapper = mapper;
        this.bookingRepo = bookingRepo;
        this.rest = rest;
    }

    private double getTimeFromDate(LocalDateTime date) {
        return date.getHour() + date.getMinute() / 60.0;
    }

    public boolean canBook(Booking booking) {
        double startTime = getTimeFromDate(booking.getStart());
        double endTime = getTimeFromDate(booking.getEnd());

        // consultancy must last at least an hour and start < end
        if (endTime - startTime < 1) {
            return false;
        }

        // booking has to be done one day in advance
        if (!booking.getStart().isAfter(LocalDateTime.now().plusDays(1))) {
            return false;
        }

        // start and end must be within working hours
        if (
            startTime < START_OF_WORK_HOURS ||
                startTime > END_OF_WORK_HOURS ||
                endTime < START_OF_WORK_HOURS ||
                endTime > END_OF_WORK_HOURS
        ) {
            return false;
        }

        // consultant with specified uuid must exist
        try {
            rest.getForObject("http://RESOURCEMANAGEMENT/consultant/" + booking.getConsultantUuid(), Consultant.class);
        } catch (RestClientException exception) {
            exception.printStackTrace();
            return false;
        }

        // check for no conflicting bookings
        if (!bookingRepo.canBook(booking.getConsultantUuid(), booking.getStart()).isEmpty()) {
            return false;
        }

        return true;
    }

    public Optional<String> createBooking(Booking booking) {
        if (!canBook(booking)) {
            return Optional.empty();
        }

        BookingEntity bookingEntity = mapper.map(booking, BookingEntity.class);

        bookingEntity.setUuid(UUID.randomUUID().toString());

        bookingRepo.save(bookingEntity);

        return Optional.of(bookingEntity.getUuid());
    }

    public boolean deleteBooking(String uuid) {
        if (uuid == null) {
            return false;
        }

        Optional<BookingEntity> optionalBookingEntity = bookingRepo.findById(uuid);

        if (!optionalBookingEntity.isPresent()) {
            return false;
        }

        BookingEntity bookingEntity = optionalBookingEntity.get();

        // you can cancel a booking one day in advance
        if (!bookingEntity.getStart().isAfter(LocalDateTime.now().plusDays(1))) {
            return false;
        }

        bookingRepo.deleteById(uuid);

        return true;
    }

    public Optional<Booking> getBooking(String uuid) {
        if (uuid == null) {
            return Optional.empty();
        }

        Optional<BookingEntity> optionalBookingEntity = bookingRepo.findById(uuid);

        if (!optionalBookingEntity.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(mapper.map(optionalBookingEntity.get(), Booking.class));
    }

    public Optional<List<Booking>> getAllBookings(String consultantUuid, String customerUuid) {
        List<Booking> customers = new LinkedList<>();

        if (consultantUuid == null && consultantUuid == null) {
            for (BookingEntity customerEntity : bookingRepo.findAll()) {
                customers.add(mapper.map(customerEntity, Booking.class));
            }

            return Optional.of(customers);
        }

        if (consultantUuid != null) {
            for (BookingEntity customerEntity : bookingRepo.findAllByConsultantUuid(consultantUuid)) {
                customers.add(mapper.map(customerEntity, Booking.class));
            }

            return Optional.of(customers);
        }

        if (customerUuid != null) {
            for (BookingEntity customerEntity : bookingRepo.findAllByCustomerUuid(customerUuid)) {
                customers.add(mapper.map(customerEntity, Booking.class));
            }

            return Optional.of(customers);
        }

        return Optional.empty();
    }

    // helper methods to facilitate testing
    public Optional<String> unsafeCreateBooking(Booking booking) {
        BookingEntity bookingEntity = mapper.map(booking, BookingEntity.class);

        bookingEntity.setUuid(UUID.randomUUID().toString());

        bookingRepo.save(bookingEntity);

        return Optional.of(bookingEntity.getUuid());
    }
}
