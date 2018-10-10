package org.cos30018.hets.logic.appliance;

import java.util.List;

public interface Appliance {	
	public enum ApplianceType {
		DISHWASHER, LIGHTBULB, TV, HIFI, PC
	}

	public enum ForecastingMethod {
		SIMPLE, MODERATE, COMPLEX
	}
	
	void setApplianceType(ApplianceType applianceType);
	ApplianceType getType();
	double getLastActualUsage();
	List<Double> getPastActualUsages();
	double[] getUsageForecast(int numberOfPeriods);
	void setForecastingMethod(ForecastingMethod forecastingMethod);
}
