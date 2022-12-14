package com.cps2002.customermanagementservice.data.repositories;

import com.cps2002.customermanagementservice.data.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, String> {
    @Query("select c from CustomerEntity c where c.specialityPreference like concat('%', ?1, '%')")
    List<CustomerEntity> findAllLikeSpecialityPreference(String preference);
}
