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
public class TimetablingConsultantSubscriber implements Subscriber<ResponseEntity<String>> {
    @Autowired
    public RestTemplate rest;

    @Override
    public ResponseEntity<String> notifyOfDelete(String uuid) {
        try {
            String url = "http://TIMETABLING/internal/null-consultant/?consultantUuid=" + uuid;

            HttpEntity<String> request = new HttpEntity<String>(uuid);
            ResponseEntity<String> response = rest.exchange(url, HttpMethod.PUT, request, new ParameterizedTypeReference<String>() {
            });

            return response;
        } catch (HttpClientErrorException exception) {
            exception.printStackTrace();

            return null;
        }
    }
}
