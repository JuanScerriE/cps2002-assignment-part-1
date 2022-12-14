package com.cps2002.timetablingservice;

import com.cps2002.timetablingservice.services.internal.TimetablingServiceInternal;
import com.cps2002.timetablingservice.services.internal.models.Booking;
import com.cps2002.timetablingservice.services.internal.models.Consultant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class TimetablingServiceTests extends Tests {
    @Mock
    private RestTemplate rest;

    @Autowired
    @InjectMocks
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
    public void testCanBook() {
        Booking booking = Booking.builder()
                .consultantUuid(UUID.randomUUID().toString())
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(2)
                        .withHour(10)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(2)
                        .withHour(11)
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        boolean canBook = timetablingService.canBook(booking);

        assertTrue(canBook);
    }


    @Test
    public void testCanBookMeetingLessThanAnHourLong() {
        Booking booking = Booking.builder()
                .consultantUuid(UUID.randomUUID().toString())
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(2)
                        .withHour(10)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(2)
                        .withHour(10)
                        .plusMinutes(30)
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        boolean canBook = timetablingService.canBook(booking);

        assertFalse(canBook);
    }

    @Test
    public void testCanBookMeetingNotOneDayInAdvance() {
        Booking booking = Booking.builder()
                .consultantUuid(UUID.randomUUID().toString())
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now()
                        .plusHours(12)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now()
                        .plusHours(12)
                        .plusHours(1)
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        boolean canBook = timetablingService.canBook(booking);

        assertFalse(canBook);
    }

    @Test
    public void testCanBookMeetingNotWithinWorkingHoursTooEarly() {
        Booking booking = Booking.builder()
                .consultantUuid(UUID.randomUUID().toString())
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(2)
                        .withHour(6)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(2)
                        .withHour(8)
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        boolean canBook = timetablingService.canBook(booking);

        assertFalse(canBook);
    }

    @Test
    public void testCanBookMeetingNotWithinWorkingHoursTooLate() {
        Booking booking = Booking.builder()
                .consultantUuid(UUID.randomUUID().toString())
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(2)
                        .withHour(16)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(2)
                        .withHour(18)
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        boolean canBook = timetablingService.canBook(booking);

        assertFalse(canBook);
    }

    @Test
    public void testCanBookConflictingMeeting() {
        String consultantUuid = UUID.randomUUID().toString();

        Booking booking1 = Booking.builder()
                .consultantUuid(consultantUuid)
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(1)
                        .withHour(10)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(1)
                        .withHour(12)
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        Booking booking2 = Booking.builder()
                .consultantUuid(consultantUuid)
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(1)
                        .withHour(11)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(1)
                        .withHour(13)
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        timetablingService.unsafeCreateBooking(booking1);

        boolean canBook = timetablingService.canBook(booking2);

        assertFalse(canBook);
    }

    @Test
    public void testCanBookInvalidConsultant() {
        String consultantUuid = UUID.randomUUID().toString();

        Mockito.when(rest.getForObject("http://RESOURCEMANAGEMENT/consultant/" + consultantUuid, Consultant.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        Booking booking = Booking.builder()
                .consultantUuid(consultantUuid)
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(2)
                        .withHour(10)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(2)
                        .withHour(12)
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        boolean canBook = timetablingService.canBook(booking);

        assertFalse(canBook);
    }

    @Test
    public void testCreateBooking() {
        Booking booking = Booking.builder()
                .consultantUuid(UUID.randomUUID().toString())
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(2)
                        .withHour(10)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(2)
                        .withHour(11)
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        String uuid = timetablingService.createBooking(booking).get();

        Booking result = timetablingService.getBooking(uuid).get();

        assertTrue(uuid.equals(result.getUuid()));
    }

    @Test
    public void testCreateBookingInvalidBookingMeetingLessThanAnHourLong() {
        Booking booking = Booking.builder()
                .consultantUuid(UUID.randomUUID().toString())
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(2)
                        .withHour(10)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(2)
                        .withHour(10)
                        .plusMinutes(30)
                        .truncatedTo(ChronoUnit.SECONDS))
                .build();

        assertFalse(timetablingService.createBooking(booking).isPresent());
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
                    .start(LocalDateTime.now().plusDays(i + 2)
                            .withHour(10)
                            .truncatedTo(ChronoUnit.SECONDS))
                    .end(LocalDateTime.now().plusDays(i + 2)
                            .withHour(12)
                            .truncatedTo(ChronoUnit.SECONDS))
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
                    .start(LocalDateTime.now().plusDays(i + 2)
                            .withHour(10)
                            .truncatedTo(ChronoUnit.SECONDS))
                    .end(LocalDateTime.now().plusDays(i + 2)
                            .withHour(12)
                            .truncatedTo(ChronoUnit.SECONDS))
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
                    .start(LocalDateTime.now().plusDays(i + 2)
                            .withHour(10)
                            .truncatedTo(ChronoUnit.SECONDS))
                    .end(LocalDateTime.now().plusDays(i + 2)
                            .withHour(12)
                            .truncatedTo(ChronoUnit.SECONDS))
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
                    .start(LocalDateTime.now().plusDays(i + 2)
                            .withHour(10)
                            .truncatedTo(ChronoUnit.SECONDS))
                    .end(LocalDateTime.now().plusDays(i + 2)
                            .withHour(12)
                            .truncatedTo(ChronoUnit.SECONDS))
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
                    .start(LocalDateTime.now().plusDays(i + 2)
                            .withHour(10)
                            .truncatedTo(ChronoUnit.SECONDS))
                    .end(LocalDateTime.now().plusDays(i + 2)
                            .withHour(12)
                            .truncatedTo(ChronoUnit.SECONDS))
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
