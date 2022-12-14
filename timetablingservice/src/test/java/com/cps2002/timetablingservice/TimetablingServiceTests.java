package com.cps2002.timetablingservice;

import com.cps2002.timetablingservice.services.internal.TimetablingServiceInternal;
import com.cps2002.timetablingservice.services.internal.models.Booking;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimetablingServiceTests extends Tests {
    @Autowired
    private TimetablingServiceInternal timetablingService;

    @BeforeAll
    public void setup() {
        timetablingService.unsafeDeleteAllBookings();
    }

    @BeforeEach
    public void clearH2Db() {
        timetablingService.unsafeDeleteAllBookings();
    }

    @Test
    public void testGetBookingWithNullUuid() throws Exception {
        Optional<Booking> result = timetablingService.getBooking(null);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetAllBookings() throws Exception {
        List<Booking> bookings = new LinkedList<>();

        // create ten bookings
        for (int i = 0; i < 10; i++) {
            Booking booking = Booking.builder()
                    .consultantUuid(UUID.randomUUID().toString())
                    .customerUuid(UUID.randomUUID().toString())
                    .start(LocalDateTime.now().plusDays(i + 1)
                            .truncatedTo(ChronoUnit.SECONDS))
                    .end(LocalDateTime.now().plusDays(i + 1)
                            .plusHours(1).truncatedTo(ChronoUnit.SECONDS))
                    .build();

            Optional<String> uuid = timetablingService.unsafeCreateBooking(booking);

            booking.setUuid(uuid.get());

            bookings.add(booking);
        }

        List<Booking> result = timetablingService.getAllBookings(null, null).get();

        assertTrue(toJsonString(bookings).equals(toJsonString(result)));
    }

    @Test
    public void getAllBookingsWithSpecifiedCustomerUuid() {
        List<Booking> sameCustomerBookings = new LinkedList<>();

        String customerUuid = UUID.randomUUID().toString();

        // create five bookings with same customer uuid
        for (int i = 0; i < 5; i++) {
            Booking booking = Booking.builder()
                    .consultantUuid(UUID.randomUUID().toString())
                    .customerUuid(customerUuid)
                    .start(LocalDateTime.now().plusDays(i + 1)
                            .truncatedTo(ChronoUnit.SECONDS))
                    .end(LocalDateTime.now().plusDays(i + 1)
                            .plusHours(1).truncatedTo(ChronoUnit.SECONDS))
                    .build();

            Optional<String> uuid = timetablingService.unsafeCreateBooking(booking);

            booking.setUuid(uuid.get());

            sameCustomerBookings.add(booking);
        }

        // create another five bookings
        for (int i = 0; i < 5; i++) {
            Booking booking = Booking.builder()
                    .consultantUuid(UUID.randomUUID().toString())
                    .customerUuid(UUID.randomUUID().toString())
                    .start(LocalDateTime.now().plusDays(i + 1)
                            .truncatedTo(ChronoUnit.SECONDS))
                    .end(LocalDateTime.now().plusDays(i + 1)
                            .plusHours(1).truncatedTo(ChronoUnit.SECONDS))
                    .build();

            timetablingService.unsafeCreateBooking(booking);
        }

        List<Booking> result = timetablingService.getAllBookings(null, customerUuid).get();

        assertTrue(toJsonString(sameCustomerBookings).equals(toJsonString(result)));
    }

    @Test
    public void getAllBookingsWithSpecifiedConsultantUuid() {
        List<Booking> sameConsultantBookings = new LinkedList<>();

        String consultantUuid = UUID.randomUUID().toString();

        // create five bookings with same customer uuid
        for (int i = 0; i < 5; i++) {
            Booking booking = Booking.builder()
                    .consultantUuid(consultantUuid)
                    .customerUuid(UUID.randomUUID().toString())
                    .start(LocalDateTime.now().plusDays(i + 1)
                            .truncatedTo(ChronoUnit.SECONDS))
                    .end(LocalDateTime.now().plusDays(i + 1)
                            .plusHours(1).truncatedTo(ChronoUnit.SECONDS))
                    .build();

            Optional<String> uuid = timetablingService.unsafeCreateBooking(booking);

            booking.setUuid(uuid.get());

            sameConsultantBookings.add(booking);
        }

        // create another five bookings
        for (int i = 0; i < 5; i++) {
            Booking booking = Booking.builder()
                    .consultantUuid(UUID.randomUUID().toString())
                    .customerUuid(UUID.randomUUID().toString())
                    .start(LocalDateTime.now().plusDays(i + 1)
                            .truncatedTo(ChronoUnit.SECONDS))
                    .end(LocalDateTime.now().plusDays(i + 1)
                            .plusHours(1).truncatedTo(ChronoUnit.SECONDS))
                    .build();

            timetablingService.unsafeCreateBooking(booking);
        }

        List<Booking> result = timetablingService.getAllBookings(consultantUuid, null).get();

        assertTrue(toJsonString(sameConsultantBookings).equals(toJsonString(result)));
    }

    @Test
    public void testDeleteBookingWithNullUuid() {
        boolean deleted = timetablingService.deleteBooking(null);

        assertFalse(deleted);
    }
}
