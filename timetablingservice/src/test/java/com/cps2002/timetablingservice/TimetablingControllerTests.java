package com.cps2002.timetablingservice;

import com.cps2002.timetablingservice.services.internal.TimetablingServiceInternal;
import com.cps2002.timetablingservice.services.internal.models.Booking;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TimetablingControllerTests extends Tests {
    @Autowired
    private TimetablingServiceInternal timetablingService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @BeforeEach
    public void clearH2Db() {
        timetablingService.unsafeDeleteAllBookings();
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetBooking() throws Exception {
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

        mockMvc.perform(get("/get?uuid=" + booking.getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(booking.getUuid()))
                .andExpect(jsonPath("$.consultantUuid").value(booking.getConsultantUuid()))
                .andExpect(jsonPath("$.customerUuid").value(booking.getCustomerUuid()))
                .andExpect(jsonPath("$.start").value(booking.getStart().toString()))
                .andExpect(jsonPath("$.end").value(booking.getEnd().toString()));
    }

    @Test
    public void testGetBookingWithInvalidUuid() throws Exception {
        mockMvc.perform(get("/get?uuid=bogus-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
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

        mockMvc.perform(get("/get-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(10)));
    }

    @Test
    public void getAllBookingsWithSpecifiedCustomerUuid() throws Exception {
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

        mockMvc.perform(get("/get-all-by-customer?customerUuid=" + customerUuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(sameCustomerBookings.size())));
    }

    @Test
    public void getAllBookingsWithSpecifiedConsultantUuid() throws Exception {
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

        mockMvc.perform(get("/get-all-by-consultant?consultantUuid=" + consultantUuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(sameConsultantBookings.size())));
    }

    @Test
    public void testDeleteBookingLate() throws Exception {
        Booking booking = Booking.builder()
                .consultantUuid(UUID.randomUUID().toString())
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(1).plusHours(1)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(1).plusHours(1)
                        .plusHours(1).truncatedTo(ChronoUnit.SECONDS))
                .build();

        Optional<String> uuid = timetablingService.unsafeCreateBooking(booking);

        booking.setUuid(uuid.get());

        mockMvc.perform(delete("/delete?uuid=" + booking.getUuid()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteBooking() throws Exception {
        Booking booking = Booking.builder()
                .consultantUuid(UUID.randomUUID().toString())
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(1).plusHours(1)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(1).plusHours(1)
                        .plusHours(1).truncatedTo(ChronoUnit.SECONDS))
                .build();

        Optional<String> uuid = timetablingService.unsafeCreateBooking(booking);

        booking.setUuid(uuid.get());

        mockMvc.perform(delete("/delete?uuid=" + booking.getUuid()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteBookingWithInvalidUuid() throws Exception {
        mockMvc.perform(delete("/delete?uuid=bogus-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }


    @Test
    public void testNullCustomer() throws Exception {
        Booking booking = Booking.builder()
                .consultantUuid(UUID.randomUUID().toString())
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(1).plusHours(1)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(1).plusHours(1)
                        .plusHours(1).truncatedTo(ChronoUnit.SECONDS))
                .build();

        Optional<String> uuid = timetablingService.unsafeCreateBooking(booking);

        booking.setUuid(uuid.get());

        mockMvc.perform(put("/internal/null-customer?customerUuid=" + booking.getCustomerUuid()))
                .andExpect(status().isOk());

        assertNull(timetablingService.getBooking(booking.getUuid()).get().getCustomerUuid());
    }

    @Test
    public void testNullConsultant() throws Exception {
        Booking booking = Booking.builder()
                .consultantUuid(UUID.randomUUID().toString())
                .customerUuid(UUID.randomUUID().toString())
                .start(LocalDateTime.now().plusDays(1).plusHours(1)
                        .truncatedTo(ChronoUnit.SECONDS))
                .end(LocalDateTime.now().plusDays(1).plusHours(1)
                        .plusHours(1).truncatedTo(ChronoUnit.SECONDS))
                .build();


        Optional<String> uuid = timetablingService.unsafeCreateBooking(booking);

        booking.setUuid(uuid.get());

        mockMvc.perform(put("/internal/null-consultant?consultantUuid=" + booking.getConsultantUuid()))
                .andExpect(status().isOk());

        assertNull(timetablingService.getBooking(booking.getUuid()).get().getConsultantUuid());
    }
}
