package com.cps2002.customermanagementservice.services.subscribers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class TimetablingCustomerSubscriber implements Subscriber<Boolean> {
    @Autowired
    public RestTemplate rest;

    @Override
    public Boolean notifyOfDelete(String uuid) {
        try {
            rest.put("http://TIMETABLING/internal/null-customer?customerUuid=" + uuid, null);
        } catch (HttpClientErrorException exception) {
            exception.printStackTrace();

            return false;
        }

        return true;
    }
}
