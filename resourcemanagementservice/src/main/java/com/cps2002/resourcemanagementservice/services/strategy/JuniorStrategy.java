package com.cps2002.resourcemanagementservice.services.strategy;

public class JuniorStrategy implements CommisionStrategy{
    private double commision = 0.05;

    @Override
    public double commision(int rate) {
        double com = rate * commision;
       
        return Math.round(com * 100.0) / 100.0;
    }
    
}
