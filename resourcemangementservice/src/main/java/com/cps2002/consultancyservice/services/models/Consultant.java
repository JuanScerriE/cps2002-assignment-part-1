package com.cps2002.consultancyservice.services.models;

public class Consultant {
    private String uuid;
    private String name;
    private String type;
    private String speciality;
    private int rate;

   public String getUuid(){
    return uuid;
   }

   public void setUuid(String uuid) {
        this.uuid = uuid;
    }

   public String getName(){
    return name;
   }
 public void setName(String name) {
        this.name = name;
    }
   public String getType(){
    return type;
   }
   public void setType(String type) {
    this.type = type;
}
   public String getSpeciality(){
    return speciality;
   }
   public void setSpeciality(String speciality) {
    this.speciality = speciality;
}

   public int getRate(){
    return rate;
   }
   public void setRate(int rate) {
    this.rate = rate;
}

    @Override
    public String toString() {
        //override to string method to return consultant object

        return "Consultant{" +"\n"+
                "Name" + name +"\n"+
                "Type" + type +"\n"+
                "Speciality" + speciality +"\n"+
                "Rate" + rate +"\n"+
                "Id" + uuid +"\n"+
                '}';
    }



}
