package com.cps2002.timetablingservice.data.repositories.customermanagement;

import com.cps2002.timetablingservice.data.entities.customermanagement.BookedConsultantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<BookedConsultantEntity, String> {

}
