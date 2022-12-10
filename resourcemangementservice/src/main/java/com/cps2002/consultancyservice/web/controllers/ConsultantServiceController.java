package com.cps2002.consultancyservice.web.controllers;

import com.cps2002.consultancyservice.web.controllers.requests.CreateConsultantRequest;
import com.cps2002.consultancyservice.web.controllers.requests.UpdateConsultantRequest;
import com.cps2002.consultancyservice.web.controllers.responses.*;
import com.cps2002.consultancyservice.web.controllers.requests.BookConsultantRequest;
import com.cps2002.consultancyservice.services.models.Consultant;
import com.cps2002.consultancyservice.services.ConsultantsService;
import com.cps2002.consultancyservice.services.models.Booking;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConsultantServiceController {

  @Autowired
  ConsultantsService consultantsService;

  

    @Autowired
    private final ModelMapper mapper;

    public ConsultantServiceController(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping(value = "new_consultant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreateConsultantResponse> submit(@RequestHeader(name = "X-Consultant-Id")  @RequestBody CreateConsultantRequest request) {


        Consultant consultantCreation = mapper.map(request.getValue(), Consultant.class);

        System.out.println(consultantCreation.toString());

        String consultantId = consultantsService.CreateConsultant(consultantCreation);

        consultantCreation.setUuid(consultantId);
        

        return ResponseEntity.ok(new CreateConsultantResponse(consultantId));
    }
    



    @PostMapping(value = "new_booking", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookConsultantResponse> submit(@RequestHeader(name = "X-Customer-Id")  @RequestBody BookConsultantRequest request) {
       

        //create order instance 
        Booking consultantBooking = mapper.map(request.getValue(), Booking.class);

        System.out.println(consultantBooking.toString());
        String bookingId = consultantsService.BookConsultant(consultantBooking);
        consultantBooking.setUuid(bookingId);

        return ResponseEntity.ok(new BookConsultantResponse(bookingId));

        
    }

    @GetMapping(value = "consultant/{consultantId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetConsultantResponse> get( @PathVariable String consultantId) {


       Consultant consultant = consultantsService.GetConsultant(consultantId); ;
             System.out.println(consultant +"this is it");
       if(consultant == null){
        return ResponseEntity.notFound().build();
       }else {

           GetConsultantResponse getConsultantResponse = mapper.map(consultant, GetConsultantResponse.class);
           return ResponseEntity.ok(getConsultantResponse);
       }
    }

    @GetMapping(value = "consultants", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetConsultantsResponse> getAll() {

       //ConsultantService.java includes all functions/operations
       //this gets specific consultant, you can format to any type of query you'd like 

       ArrayList<Consultant> consultants = consultantsService.GetConsultants(); ;

       System.out.println(consultants);

       if(consultants == null){
        return ResponseEntity.notFound().build();
       }

        GetConsultantsResponse getConsultantsResponse= mapper.map(consultants, GetConsultantsResponse.class);
        return ResponseEntity.ok(getConsultantsResponse);
    }

    @DeleteMapping(value = "/delete/{consultantId}")
    public ResponseEntity<DeleteConsultantResponse> deletePost(@PathVariable String consultantId) {
        String isRemoved;
         isRemoved = consultantsService.DeleteConsultant(consultantId);

        if (isRemoved == null) {
            return ResponseEntity.notFound().build();
        }
        DeleteConsultantResponse deleteConsultantResponse =mapper.map(isRemoved, DeleteConsultantResponse.class);
        return  ResponseEntity.ok(deleteConsultantResponse);
    }
    @DeleteMapping(value = "/deleteB/{bookingId}")
    public ResponseEntity<DeleteBookingResponse> deletePostB(@PathVariable String bookingId) {
        String isRemoved;
        isRemoved = consultantsService.DeleteBooking(bookingId);

        if (isRemoved == null) {
            return ResponseEntity.notFound().build();
        }
        DeleteBookingResponse deleteBookingResponse =mapper.map(isRemoved, DeleteBookingResponse.class);
        return  ResponseEntity.ok(deleteBookingResponse);
    }

    @PostMapping(value = "/update_consultant", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdateConsultantResponse> update(@RequestHeader(name = "X-Consultant-Id")  @RequestBody UpdateConsultantRequest request) {


        Consultant consultantCreation = mapper.map(request.getValue(), Consultant.class);
        System.out.println("test");
        System.out.println(consultantCreation.toString());

        String consultantId = consultantsService.UpdateConsultant(consultantCreation.getUuid(),consultantCreation);
        System.out.println(consultantId);
        return ResponseEntity.ok(new UpdateConsultantResponse(consultantId));
    }

    @GetMapping(value = "getallbookings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetBookingsResponse> getAllB() {

        //ConsultantService.java includes all functions/operations
        //this gets specific consultant, you can format to any type of query you'd like

        ArrayList<Booking> bookings = consultantsService.GetBookings(); ;

        System.out.println(bookings);

        if(bookings == null){
            return ResponseEntity.notFound().build();
        }

        GetBookingsResponse getBookingsResponse= mapper.map(bookings, GetBookingsResponse.class);
        return ResponseEntity.ok(getBookingsResponse);
    }

    
    }

