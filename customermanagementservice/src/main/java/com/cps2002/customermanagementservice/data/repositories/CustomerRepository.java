package com.cps2002.customermanagementservice.data.repositories;

import com.cps2002.customermanagementservice.data.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {

}
