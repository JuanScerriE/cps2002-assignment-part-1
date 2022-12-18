package com.cps2002.resourcemanagementservice.services.strategy;

public class SeniorStrategy implements CommisionStrategy{
    private double commision = 0.15;


    @Override
    public double commision(int rate) {
        double com = rate * commision;
      
        
        return Math.round(com * 100.0) / 100.0;
    }
    
}
