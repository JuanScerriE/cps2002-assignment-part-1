package com.cps2002.resourcemanagementservice.services;

import com.cps2002.resourcemanagementservice.data.entities.BookingEntity;
import com.cps2002.resourcemanagementservice.data.entities.ConsultantEntity;
import com.cps2002.resourcemanagementservice.data.repositories.BookingRepository;
import com.cps2002.resourcemanagementservice.data.repositories.ConsultantRepository;
import com.cps2002.resourcemanagementservice.services.models.Booking;
import com.cps2002.resourcemanagementservice.services.models.Consultant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.UUID;


@Service
public class ConsultantsService {


    @Autowired
    ConsultantRepository consultantRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    RestTemplate restTemplate;

    ModelMapper mapper = new ModelMapper();


    public String CreateConsultant(Consultant consultant) {


        ConsultantEntity consultantEntity = mapper.map(consultant, ConsultantEntity.class);
        consultantEntity.setUuid(UUID.randomUUID().toString());

        consultantEntity = consultantRepository.save(consultantEntity);

        return consultantEntity.getUuid();


    }

    public String BookConsultant(Booking booking) {

        BookingEntity bookingEntity = mapper.map(booking, BookingEntity.class);
        bookingEntity.setUuid(UUID.randomUUID().toString());
        bookingEntity = bookingRepository.save(bookingEntity);

        return bookingEntity.getUuid();


    }


    public ArrayList<Consultant> GetConsultants() {
        //get all consultants
        ArrayList<Consultant> consultants = new ArrayList<Consultant>();
        Iterable<ConsultantEntity> consultantEntities = consultantRepository.findAll();
        System.out.println(consultantEntities);
        for (ConsultantEntity consultantEntity : consultantEntities) {
            Consultant consultant = mapper.map(consultantEntity, Consultant.class);
            consultants.add(consultant);
        }

        return consultants;

    }


    public Consultant GetConsultant(String id) {
        //get consultant by id
        //fetch consultant from db

        ConsultantEntity consultantEntityToFind = new ConsultantEntity();
        consultantEntityToFind.setUuid(id);
        ConsultantEntity consultantEntity = consultantRepository.findById(id).orElse(null);

        if (consultantEntity == null) {
            return null;
        }

        Consultant consultant = mapper.map(consultantEntity, Consultant.class);

        return consultant;

    }

    //get Consultants by speciality
    public ArrayList<Consultant> GetConsultantsBySpeciality(String speciality) {
        //get all consultants
        ArrayList<Consultant> consultants = new ArrayList<Consultant>();
        Iterable<ConsultantEntity> consultantEntities = consultantRepository.findAll();

        for (ConsultantEntity consultantEntity : consultantEntities) {
            Consultant consultant = mapper.map(consultantEntity, Consultant.class);
            if (consultant.getSpeciality().contains(speciality)) {
                consultants.add(consultant);
            }
        }
        System.out.println(consultants);
        return consultants;

    }

    // public List<Booking> getBookingsByConsultantId(String consultantId) {
    //     List<Booking> bookings = new ArrayList<>();

    //     String url = "http://TIMETABLING/get-all-by-consultant?consultantUuid=" + consultantId;
    //     RestTemplate restTemplate = new RestTemplate();
    //     ResponseEntity<List<Booking>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Booking>>() {
    //     });
    //     bookings = response.getBody();
    //     return bookings;
    // }

    public String DeleteConsultant(String id) {
        //delete consultant
        consultantRepository.deleteById(id);

        ConsultantEntity consultantEntity = consultantRepository.findById(id).orElse(null);

        if (consultantEntity == null) {
            //create observer that removes consultant from all bookings
            //get all bookings related to consultant and update their consultant id to null

            //get all bookings related to consultant id with http request from timetabling service
            //update all bookings related to consultant id with http request from timetabling service

            String url = "http://TIMETABLING/internal/null-consultant/?consultantUuid=" + id;
            //call url to delete consultant from all bookings

            //call url to put consultant id to null in all bookings
            HttpEntity<String> request = new HttpEntity<String>(id);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, new ParameterizedTypeReference<String>() {
            });

            return response.getBody();
        } else {
            return null;
        }

    }


    public String UpdateConsultant(String Id, Consultant consultant) {
        //update consultant
        ConsultantEntity consultantEntity = mapper.map(consultant, ConsultantEntity.class);
        consultantEntity.setUuid(Id);


        System.out.println(consultantEntity.toString());
        consultantRepository.save(consultantEntity);
        System.out.println("updated");

        return consultantEntity.getUuid();

    }


}