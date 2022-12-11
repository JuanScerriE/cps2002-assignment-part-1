package com.cps2002.resourcemanagementservice.data.repositories;

import com.cps2002.resourcemanagementservice.data.entities.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, String> {

}