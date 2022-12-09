package com.cps2002.timetablingservice.data.entities.customermanagement;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class CustomerEntity {
    @Id
    private String uuid;
    private String name;
    private String specialityPreference;

    @OneToMany(mappedBy = "customer")
    private Set<BookedConsultantEntity> bookedConsultants;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialityPreference() {
        return specialityPreference;
    }

    public void setSpecialityPreference(String specialityPreference) {
        this.specialityPreference = specialityPreference;
    }

    public Set<BookedConsultantEntity> getBookedConsultants() {
        return bookedConsultants;
    }

    public void setBookedConsultants(Set<BookedConsultantEntity> bookedConsultants) {
        this.bookedConsultants = bookedConsultants;
    }
}
