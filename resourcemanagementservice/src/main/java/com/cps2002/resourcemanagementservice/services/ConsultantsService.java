package com.cps2002.resourcemanagementservice.services;


import com.cps2002.resourcemanagementservice.data.entities.ConsultantEntity;

import com.cps2002.resourcemanagementservice.data.repositories.ConsultantRepository;
import com.cps2002.resourcemanagementservice.services.models.Consultant;
import com.cps2002.resourcemanagementservice.services.strategy.ExecutiveStrategy;
import com.cps2002.resourcemanagementservice.services.strategy.JuniorStrategy;
import com.cps2002.resourcemanagementservice.services.strategy.SeniorStrategy;
import com.cps2002.resourcemanagementservice.services.subscribers.TimetablingConsultantSubscriber;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;


@Service
public class ConsultantsService {

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private TimetablingConsultantSubscriber subscriber;

    ModelMapper mapper = new ModelMapper();


    public String CreateConsultant(Consultant consultant) {

        String type = consultant.getType();

            if(Objects.equals(type, "Senior")){
        double companyCut=consultant.Commision(new SeniorStrategy(0.15));
             consultant.setCompanyRate(companyCut);
            }else if(Objects.equals(type, "Junior")){
                double companyCut=consultant.Commision(new JuniorStrategy(0.05));
                consultant.setCompanyRate(companyCut);
            }else if(Objects.equals(type, "Executive")){
                double companyCut=consultant.Commision(new ExecutiveStrategy(0.10));
                consultant.setCompanyRate(companyCut);
            }


        ConsultantEntity consultantEntity = mapper.map(consultant, ConsultantEntity.class);
        consultantEntity.setUuid(UUID.randomUUID().toString());

        consultantEntity = consultantRepository.save(consultantEntity);


        return consultantEntity.getUuid();


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

        System.out.println(consultantEntity.toString());

        if (consultantEntity == null) {
            return null;
        }

        Consultant consultant = mapper.map(consultantEntity, Consultant.class);
        System.out.println(consultant.toString());
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

    public String DeleteConsultant(String id) {
        //delete consultant
        consultantRepository.deleteById(id);

        ConsultantEntity consultantEntity = consultantRepository.findById(id).orElse(null);

        if (consultantEntity == null) {
            ResponseEntity<String> response = subscriber.notifyOfDelete(id);

            return response.getBody();
        } else {
            return null;
        }
    }


    public String UpdateConsultant(String Id, Consultant consultant) {
        //update consultant
        String type = consultant.getType();
        
        if(Objects.equals(type, "Senior")){
    double companyCut=consultant.Commision(new SeniorStrategy(0.15));
         consultant.setCompanyRate(companyCut);
        }else if(Objects.equals(type, "Junior")){
            double companyCut=consultant.Commision(new JuniorStrategy(0.05));
            consultant.setCompanyRate(companyCut);
        }else if(Objects.equals(type, "Executive")){
            double companyCut=consultant.Commision(new ExecutiveStrategy(0.10));
            consultant.setCompanyRate(companyCut);
        }

        ConsultantEntity consultantEntity = mapper.map(consultant, ConsultantEntity.class);
        consultantEntity.setUuid(Id);


        System.out.println(consultantEntity.toString());
        consultantRepository.save(consultantEntity);
        System.out.println("updated");

        return consultantEntity.getUuid();

    }


}