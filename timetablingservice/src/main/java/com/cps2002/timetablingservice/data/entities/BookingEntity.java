package com.cps2002.timetablingservice.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class BookingEntity {
    @Id
    private String uuid;
    private String consultantUuid;
    private String customerUuid;
    private LocalDateTime start;
    private LocalDateTime end;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getConsultantUuid() {
        return consultantUuid;
    }

    public void setConsultantUuid(String consultantUuid) {
        this.consultantUuid = consultantUuid;
    }

    public String getCustomerUuid() {
        return customerUuid;
    }

    public void setCustomerUuid(String customerUuid) {
        this.customerUuid = customerUuid;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}
