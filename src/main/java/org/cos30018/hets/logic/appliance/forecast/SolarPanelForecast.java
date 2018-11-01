package org.cos30018.hets.logic.appliance.forecast;

import org.cos30018.hets.logic.appliance.ActualApplianceUsage;
import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.logic.appliance.SolarPanelUsage;

public class SolarPanelForecast extends UsageForecast {

    //area in square metres
    private static double AREA = 10;

    //efficiency of the solar panel
    private static double EFFICIENCY = 0.90;

    private SolarPanelUsage solarPanelUsage;

    public SolarPanelForecast(Appliance appliance)
    {
        super(appliance);
        this.solarPanelUsage = new SolarPanelUsage(AREA, EFFICIENCY);
    }

    @Override
    public double[] calculateForecast(int period, int numberOfPeriods) {
        double[] forecasts = new double[numberOfPeriods];

        for (int p = 0; p < numberOfPeriods; p++) {
            forecasts[p] = solarPanelUsage.getUsage(period);
        }


        return forecasts;
    }
}
