package org.cos30018.hets.logic.appliance;

import java.util.List;

public interface Appliance {	
	double getLastActualUsage();
	List<Double> getPastActualUsages();
	double[] getUsageForecast(int numberOfPeriods);
	void setForecastingMethod(int forecastingMethod);
}
