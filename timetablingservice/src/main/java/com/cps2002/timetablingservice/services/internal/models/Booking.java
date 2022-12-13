package com.cps2002.timetablingservice.services.internal.models;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class Booking {
    private String uuid;
    private String consultantUuid;
    private String customerUuid;
    private LocalDateTime start;
    private LocalDateTime end;

    private Booking() {
    }

    public static Builder builder() {
        return new Builder();
    }

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

    public boolean equals(Booking other) {
        return
            this.uuid.equals(other.uuid) &&
            this.consultantUuid.equals(other.consultantUuid) &&
            this.customerUuid.equals(other.customerUuid) &&
            this.start.isEqual(other.start) &&
            this.end.isEqual(other.end);
    }

    public static class Builder {
        private final Booking booking;

        private Builder() {
            booking = new Booking();
        }

        public Booking build() {
            return booking;
        }

        public Builder uuid(String uuid) {
            booking.setUuid(uuid);
            return this;
        }

        public Builder consultantUuid(String consultantUuid) {
            booking.setConsultantUuid(consultantUuid);
            return this;
        }

        public Builder customerUuid(String customerUuid) {
            booking.setCustomerUuid(customerUuid);
            return this;
        }

        public Builder start(LocalDateTime start) {
            booking.setStart(start);
            return this;
        }

        public Builder end(LocalDateTime end) {
            booking.setEnd(end);
            return this;
        }
    }
}
