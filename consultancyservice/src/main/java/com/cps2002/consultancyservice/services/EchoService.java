package com.cps2002.consultancyservice.services;

import com.cps2002.consultancyservice.services.models.Echo;
import com.cps2002.consultancyservice.services.models.UniqueEcho;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EchoService {

    private final ModelMapper mapper;

    public EchoService(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public UniqueEcho makeUniqueEcho(Echo echo) {
        UniqueEcho uniqueEcho = mapper.map(echo, UniqueEcho.class);
        uniqueEcho.setUuid(UUID.randomUUID().toString());

        return uniqueEcho;
    }

}