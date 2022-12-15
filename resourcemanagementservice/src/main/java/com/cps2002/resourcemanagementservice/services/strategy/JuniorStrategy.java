package com.cps2002.resourcemanagementservice.services.strategy;

public class JuniorStrategy implements CommisionStrategy{
    private double commision;

    public JuniorStrategy(double commision) {
        this.commision = commision;
    }


    @Override
    public double commision(int rate) {
        double com = rate * commision;
        //rounding to 2 decimal places
        return Math.round(com * 100.0) / 100.0;
    }
    
}
