package com.cps2002.consultancyservice.data.repositories;

import com.cps2002.consultancyservice.data.entities.ConsultantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<ConsultantEntity, String> {

}
