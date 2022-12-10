package com.cps2002.consultancyservice.services;
import com.cps2002.consultancyservice.services.models.Consultant;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cps2002.consultancyservice.data.entities.BookingEntity;
import com.cps2002.consultancyservice.data.entities.ConsultantEntity;
import com.cps2002.consultancyservice.data.repositories.BookingRepository;
import com.cps2002.consultancyservice.data.repositories.ConsultantRepository;
import com.cps2002.consultancyservice.services.models.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ConsultantsService {
    
//create
//get all
//get under certain parameters
//delete
//update
//book/order
@Autowired
ConsultantRepository consultantRepository;
@Autowired
BookingRepository bookingRepository ;


ModelMapper mapper = new ModelMapper();



   


    public String CreateConsultant(Consultant consultant){


    ConsultantEntity consultantEntity = mapper.map(consultant, ConsultantEntity.class);
    consultantEntity.setUuid(UUID.randomUUID().toString());

    consultantEntity = consultantRepository.save(consultantEntity);

    return consultantEntity.getUuid();


   
}

public String BookConsultant(Booking booking){
 
     BookingEntity bookingEntity = mapper.map(booking, BookingEntity.class);
     bookingEntity.setUuid(UUID.randomUUID().toString());
     bookingEntity = bookingRepository.save(bookingEntity);

     return bookingEntity.getUuid();


}



public ArrayList<Consultant> GetConsultants(){
   //get all consultants
    ArrayList<Consultant> consultants = new ArrayList<Consultant>();
    Iterable<ConsultantEntity> consultantEntities = consultantRepository.findAll();
    for(ConsultantEntity consultantEntity : consultantEntities){
        Consultant consultant = mapper.map(consultantEntity, Consultant.class);
        consultants.add(consultant);
    }
    return consultants;

}


public Consultant GetConsultant(String id){
    //get consultant by id
    //fetch consultant from db

    ConsultantEntity consultantEntityToFind = new ConsultantEntity();
    consultantEntityToFind.setUuid(id);
    ConsultantEntity consultantEntity = consultantRepository.findById(id).orElse(null);

    Consultant consultant = mapper.map(consultantEntity, Consultant.class);

    return consultant;

}
public String  DeleteConsultant(String id){
    //delete consultant
    consultantRepository.deleteById(id);
    ConsultantEntity consultantEntity = consultantRepository.findById(id).orElse(null);
    if(consultantEntity == null){

        return id;
    }else{
       return null;
    }

}
public String DeleteBooking(String id){
    //delete consultant
    bookingRepository.deleteById(id);
    BookingEntity bookingEntity = bookingRepository.findById(id).orElse(null);
    if(bookingEntity == null){

        return id;
    }else{
        return null;
    }
}

public String UpdateConsultant( String Id,Consultant consultant){
    //update consultant
    ConsultantEntity consultantEntity = mapper.map(consultant, ConsultantEntity.class);
    consultantEntity.setUuid(Id);


    System.out.println(consultantEntity.toString());
    consultantRepository.save(consultantEntity);
    System.out.println("updated");

    return consultantEntity.getUuid();

}

public ArrayList<Booking> GetBookings(){

    ArrayList<Booking> bookings = new ArrayList<Booking>();
    Iterable<BookingEntity> bookingEntities = bookingRepository.findAll();
    for(BookingEntity bookingEntity : bookingEntities){
        Booking booking = mapper.map(bookingEntity, Booking.class);
        bookings.add(booking);
    }
    return bookings;


}

}