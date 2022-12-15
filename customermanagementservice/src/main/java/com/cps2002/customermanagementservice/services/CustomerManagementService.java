package com.cps2002.customermanagementservice.services;

import com.cps2002.customermanagementservice.data.entities.CustomerEntity;
import com.cps2002.customermanagementservice.data.repositories.CustomerRepository;
import com.cps2002.customermanagementservice.services.models.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerManagementService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private RestTemplate rest;

    public Optional<String> createCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().isEmpty()) {
            return Optional.empty();
        }

        if (customer.getSpecialityPreference() == null || customer.getSpecialityPreference().isEmpty()) {
            customer.setSpecialityPreference("No Preference");
        }

        customer.setUuid(UUID.randomUUID().toString());

        customerRepo.save(mapper.map(customer, CustomerEntity.class));

        return Optional.of(customer.getUuid());
    }

    @Transactional(readOnly = true)
    public Optional<Customer> getCustomer(String uuid) {
        Optional<Customer> optionalCustomer = Optional.empty();

        try {
            optionalCustomer = Optional.of(mapper.map(customerRepo.getById(uuid), Customer.class));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return optionalCustomer;
    }

    public Optional<List<Customer>> getAllCustomers(String preference) {
        List<Customer> customers = new LinkedList<>();

        if (preference == null) {
            for (CustomerEntity customerEntity : customerRepo.findAll()) {
                customers.add(mapper.map(customerEntity, Customer.class));
            }

            return Optional.of(customers);
        }

        for (CustomerEntity customerEntity : customerRepo.findAllLikeSpecialityPreference(preference)) {
            customers.add(mapper.map(customerEntity, Customer.class));
        }

        return Optional.of(customers);
    }

    public boolean deleteCustomer(String uuid) {
        boolean deleted = false;

        try {
            customerRepo.deleteById(uuid);

            rest.put("http://TIMETABLING/internal/null-customer?customerUuid=" + uuid, null);

            deleted = true;
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return deleted;
    }

    public boolean updateCustomer(Customer customer) {
        if (customer.getUuid() == null || !customerRepo.findById(customer.getUuid()).isPresent()) {
            return false;
        }

        if (customer.getName() == null || customer.getName().isEmpty()) {
            return false;
        }

        if (customer.getSpecialityPreference() == null || customer.getSpecialityPreference().isEmpty()) {
            customer.setSpecialityPreference("No Preference");
        }

        // TODO: Make sure to include also the previous bookings
        customerRepo.save(mapper.map(customer, CustomerEntity.class));

        return true;
    }

}