package com.cps2002.timetablingservice.services.proxy;

import com.cps2002.timetablingservice.services.TimetablingService;
import com.cps2002.timetablingservice.services.internal.models.Booking;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class TimetablingServiceProxy implements TimetablingService {
    private final TimetablingService timeTablingService;

    public TimetablingServiceProxy(@Qualifier("internal") TimetablingService timetablingService) {
        this.timeTablingService = timetablingService;
    }


    @Override
    public boolean canBook(Booking booking) {
        return timeTablingService.canBook(booking);
    }

    @Override
    public Optional<String> createBooking(Booking booking) {
        return timeTablingService.createBooking(booking);
    }

    @Override
    public boolean deleteBooking(String uuid) {
        return timeTablingService.deleteBooking(uuid);
    }

    @Override
    public Optional<Booking> getBooking(String uuid) {
        return timeTablingService.getBooking(uuid);
    }

    @Override
    public Optional<List<Booking>> getAllBookings(String consultantUuid, String customerUuid) {
        return timeTablingService.getAllBookings(consultantUuid, customerUuid);
    }
}
