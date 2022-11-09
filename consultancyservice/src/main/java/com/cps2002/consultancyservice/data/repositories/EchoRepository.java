package com.cps2002.consultancyservice.data.repositories;

import com.cps2002.consultancyservice.data.entities.EchoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EchoRepository extends JpaRepository<EchoEntity, String> {

}
