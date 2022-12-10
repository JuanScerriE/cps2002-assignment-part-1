package com.cps2002.resourcemanagementservice.data.repositories;

import com.cps2002.resourcemanagementservice.data.entities.ConsultantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultantRepository extends JpaRepository<ConsultantEntity, String> {

}
