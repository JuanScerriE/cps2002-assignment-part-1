package com.cps2002.timetablingservice.services;

import com.cps2002.timetablingservice.services.internal.models.Booking;

import java.util.List;
import java.util.Optional;

public interface TimetablingService {
    boolean canBook(Booking booking);
    Optional<String> createBooking(Booking booking);
    boolean deleteBooking(String uuid);
    Optional<Booking> getBooking(String uuid);
    Optional<List<Booking>> getAllBookings(String consultantUuid, String customerUuid);
}
