package com.cps2002.timetablingservice.web.controllers;

import com.cps2002.timetablingservice.services.EchoService;
import com.cps2002.timetablingservice.services.models.Echo;
import com.cps2002.timetablingservice.services.models.UniqueEcho;
import com.cps2002.timetablingservice.web.controllers.requests.EchoRequest;
import com.cps2002.timetablingservice.web.controllers.responses.EchoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class EchoController {

    private final ModelMapper mapper;
    private final EchoService echoService;

    public EchoController(ModelMapper mapper, EchoService echoService) {
        this.mapper = mapper;
        this.echoService = echoService;
    }

    @PostMapping(value = "echo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EchoResponse> echo(@RequestBody EchoRequest request) {
        UniqueEcho uniqueEcho = echoService.makeUniqueEcho(mapper.map(request, Echo.class));

        return ResponseEntity.ok(mapper.map(uniqueEcho, EchoResponse.class));
    }

    @GetMapping(value = "get-echo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EchoResponse> getEcho(@RequestParam String uuid) {
        UniqueEcho uniqueEcho = echoService.getUniqueEcho(uuid);

        if (uniqueEcho == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(mapper.map(uniqueEcho, EchoResponse.class));
    }


    @GetMapping(value = "getecho", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> getEcho2() {
        System.out.println("I received");
        return ResponseEntity.ok("You entered");
    }

}
