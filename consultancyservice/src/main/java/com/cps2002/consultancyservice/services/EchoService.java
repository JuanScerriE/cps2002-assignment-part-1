package com.cps2002.consultancyservice.services;

import com.cps2002.consultancyservice.data.entities.EchoEntity;
import com.cps2002.consultancyservice.data.repositories.EchoRepository;
import com.cps2002.consultancyservice.services.models.Echo;
import com.cps2002.consultancyservice.services.models.UniqueEcho;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class EchoService {

    private final ModelMapper mapper;
    private final EchoRepository repo;

    public EchoService(ModelMapper mapper, EchoRepository repo) {
        this.mapper = mapper;
        this.repo = repo;
    }

    public UniqueEcho makeUniqueEcho(Echo echo) {
        UniqueEcho uniqueEcho = mapper.map(echo, UniqueEcho.class);
        uniqueEcho.setUuid(UUID.randomUUID().toString());

        repo.save(mapper.map(uniqueEcho, EchoEntity.class));

        return uniqueEcho;
    }

    @Transactional
    public UniqueEcho getUniqueEcho(String uuid) {
        EchoEntity echoEntity = repo.getById(uuid);

        if (echoEntity == null) {
            return null;
        }

        return mapper.map(echoEntity, UniqueEcho.class);
    }

}