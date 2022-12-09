package com.cps2002.customermanagementservice.services;

import com.cps2002.customermanagementservice.data.repositories.CustomerRepository;
import com.cps2002.customermanagementservice.data.repositories.BookedConsultantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerManagementService {

    private final ModelMapper mapper;
    private final CustomerRepository customerRepo;
    private final BookedConsultantRepository bookedConsultantRepo;

    public CustomerManagementService(ModelMapper mapper, CustomerRepository customerRepo, BookedConsultantRepository bookedConsultantRepo) {
        this.mapper = mapper;
        this.customerRepo = customerRepo;
        this.bookedConsultantRepo = bookedConsultantRepo;
    }

}