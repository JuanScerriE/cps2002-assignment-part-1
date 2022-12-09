package com.cps2002.timetablingservice.data.repositories.customermanagement;

import com.cps2002.timetablingservice.data.entities.customermanagement.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

}
