package com.cps2002.resourcemanagementservice.services.strategy;


public class ExecutiveStrategy implements CommissionStrategy {
    private double commission = 0.10;

    @Override
    public double commission(int rate) {
        double com = rate * commission;

        return Math.round(com * 100.0) / 100.0;

    }

}



