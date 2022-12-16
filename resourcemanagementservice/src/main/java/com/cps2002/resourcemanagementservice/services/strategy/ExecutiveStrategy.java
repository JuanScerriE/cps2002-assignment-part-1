package com.cps2002.resourcemanagementservice.services.strategy;


public class ExecutiveStrategy implements CommisionStrategy{
    private double commision = 0.10;

    @Override
    public double commision(int rate) {
        double com = rate * commision;
        //rounding to 2 decimal places
        return Math.round(com * 100.0) / 100.0;

    }
    
}
