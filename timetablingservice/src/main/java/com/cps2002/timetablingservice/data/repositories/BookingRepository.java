package com.cps2002.timetablingservice.data.repositories;

import com.cps2002.timetablingservice.data.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, String> {
    String CAN_BOOK =
            "SELECT * FROM booking_entity booking " +
                    "WHERE booking.consultant_uuid = :consultantUuid " +
                    "AND booking.start <= :start " +
                    "AND :start <= booking.end " +
                    "AND booking.start <= :end " +
                    "AND :end <= booking.end";

    @Query(value = CAN_BOOK, nativeQuery = true)
    List<BookingEntity> canBook(@Param("consultantUuid") String consultantUuid, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
