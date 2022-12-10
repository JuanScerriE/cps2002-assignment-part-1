package com.cps2002.consultancyservice.services.models;

public class Booking {
    private String customerId;
    private String uuid;
    private String consultantId;
    private String date;
    private String time;
    private int hours;
    

    public String getCustomerId(){
        return customerId;
       }
    
       public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }
    
       public String getConsultantId(){
        return consultantId;
       }
     public void setConsultantId(String consultantId) {
            this.consultantId = consultantId;
        }

    public String getUuid(){
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

       public String getDate(){
        return date;
       }
       public void setDate(String date) {
        this.date = date;
    }
    public String getTime(){
        return time;
       }
       public void setTime(String time) {
        this.time = time;
    }
    
      
       public int getHours(){
        return hours;
       }
       public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public String toString() {
        //override to string method to return consultant object

        return "Consultant{" +"\n"+
                "Customer Id\t" + customerId+"\n"+
                "Consultant Id\t" + consultantId +"\n"+
                "Date\t" + date +"\n"+
                "Time\t" + time +"\n"+
                "Hours\t" + hours +"\n"+
                "ID\t"+ uuid+
                '}';
    }

   


}
