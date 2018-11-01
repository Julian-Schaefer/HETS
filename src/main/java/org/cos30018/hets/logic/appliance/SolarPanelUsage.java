package org.cos30018.hets.logic.appliance;

public class SolarPanelUsage {

    //area in square metres

    private double area;
    private double efficiency;
    private static final double WATTAGE_PER_SQ_METRE = 1000;

    public SolarPanelUsage(double area, double efficiency)
    {
        this.area = area;
        this.efficiency = efficiency;
    }

    public double getUsage(int period)
    {
        int time = period%24;

        if ((time >= 7) && (time <= 17)){
            //solar panel returns a negative value
            return -1*WATTAGE_PER_SQ_METRE*area*efficiency;
        }
        else{
            return 0;
        }
    }
}
