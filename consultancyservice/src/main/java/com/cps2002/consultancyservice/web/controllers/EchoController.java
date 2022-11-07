package com.cps2002.consultancyservice.web.controllers;

import com.cps2002.consultancyservice.web.controllers.requests.EchoRequest;
import com.cps2002.consultancyservice.web.controllers.responses.EchoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EchoController {

    final ModelMapper mapper;

    public EchoController(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @PostMapping(value = "echo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EchoResponse> echo(@RequestBody EchoRequest request) {
        return ResponseEntity.ok(mapper.map(request, EchoResponse.class));
    }

}
