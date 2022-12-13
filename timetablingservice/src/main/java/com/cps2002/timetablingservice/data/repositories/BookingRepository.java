package com.cps2002.timetablingservice.data.repositories;

import com.cps2002.timetablingservice.data.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, String>, JpaSpecificationExecutor<BookingEntity> {
    @Query("select b from BookingEntity b where b.consultantUuid = ?1 and b.end > ?2")
    List<BookingEntity> canBook(String consultantUuid, LocalDateTime start);
    @Query("select b from BookingEntity b where b.consultantUuid = ?1")
    List<BookingEntity> findAllByConsultantUuid(String consultantUuid);
    @Query("select b from BookingEntity b where b.customerUuid = ?1")
    List<BookingEntity> findAllByCustomerUuid(String customerUuid);
}
