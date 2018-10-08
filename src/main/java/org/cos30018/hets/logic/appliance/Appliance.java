package org.cos30018.hets.logic.appliance;

import java.util.List;

public interface Appliance {	
	public enum ApplianceType {
		DISHWASHER, LIGHTBULB, TV, HIFI, PC
	}
	
	void setApplianceType(ApplianceType applianceType);
	ApplianceType getType();
	double getLastActualUsage();
	List<Double> getPastActualUsages();
	double[] getUsageForecast(int numberOfPeriods);
	void setForecastingMethod(int forecastingMethod);
}
