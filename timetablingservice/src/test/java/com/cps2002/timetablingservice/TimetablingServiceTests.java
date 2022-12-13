package com.cps2002.timetablingservice;

import com.cps2002.timetablingservice.services.internal.TimetablingServiceInternal;
import com.cps2002.timetablingservice.services.internal.models.Booking;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class TimetablingServiceTests {
    @Autowired
    private TimetablingServiceInternal timetablingService;

    @Test
    public void testCreateBooking() {
        Booking booking = Booking.builder()
            .consultantUuid(UUID.randomUUID().toString())
            .customerUuid(UUID.randomUUID().toString())
            .start(LocalDateTime.now().plusDays(1)
                .truncatedTo(ChronoUnit.SECONDS))
            .end(LocalDateTime.now().plusDays(1)
                .plusHours(1).truncatedTo(ChronoUnit.SECONDS))
            .build();

        Optional<String> uuid = timetablingService.unsafeCreateBooking(booking);

        booking.setUuid(uuid.get());

        Optional<Booking> result = timetablingService.getBooking(booking.getUuid());

        assertTrue(result.get().equals(booking));
    }

    @Test
    public void testGetBookingWithNullUuid() {
        Optional<Booking> result = timetablingService.getBooking(null);

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetBookingWithInvalidUuid() {
        Optional<Booking> result = timetablingService.getBooking("bogus uuid");

        assertFalse(result.isPresent());
    }

    @Test
    public void testGetAllBookings() {
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

        assertTrue(asJsonS);
    }
}
