package com.cps2002.timetablingservice.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EchoEntity {
    @Id
    private String uuid;
    private String value;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
