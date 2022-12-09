package com.cps2002.customermanagementservice.data.repositories;

import com.cps2002.customermanagementservice.data.entities.BookedConsultantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedConsultantRepository extends JpaRepository<BookedConsultantEntity, String> {

}
