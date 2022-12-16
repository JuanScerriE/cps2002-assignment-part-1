package com.cps2002.resourcemanagementservice.services.subscribers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class TimetablingConsultantSubscriber implements Subscriber<Boolean> {
    @Autowired
    private RestTemplate rest;

    @Override
    public Boolean notifyOfDelete(String uuid) {
        try {
            rest.put("http://TIMETABLING/internal/null-consultant/?consultantUuid=" + uuid, null);
        } catch (HttpClientErrorException exception) {
            exception.printStackTrace();

            return false;
        }

        return true;
    }
}
