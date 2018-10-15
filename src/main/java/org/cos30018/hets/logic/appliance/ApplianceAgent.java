package org.cos30018.hets.logic.appliance;

import java.util.List;

import org.cos30018.hets.logic.appliance.behaviour.ApplianceResponderBehaviour;
import org.cos30018.hets.logic.appliance.forecast.SimpleUsageForecast;
import org.cos30018.hets.logic.appliance.forecast.UsageForecast;
import org.cos30018.hets.logic.common.RegisteringAgent;
import org.cos30018.hets.logic.home.HomeAgent;

public class ApplianceAgent extends RegisteringAgent implements Appliance {

	public static final int FORECASTING_SIMPLE = 1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2702213410686638092L;
	
	private UsageForecast usageForecast;
	private ApplianceType applianceType;
	
	public ApplianceAgent() {
		super(HomeAgent.HOME_AGENT_SERVICE, ApplianceMessage.REGISTER, ApplianceMessage.UNREGISTER);
		registerO2AInterface(Appliance.class, this);
	}
	
	@Override
	protected void setup() {
		super.setup();
		
		Object[] arguments = getArguments();
		applianceType = (ApplianceType) arguments[0];
		ForecastingMethod forecastingMethod = (ForecastingMethod) arguments[1];
		setForecastingMethod(forecastingMethod);
		
		addBehaviour(new ApplianceResponderBehaviour(this));
	}

	@Override
	public void setApplianceType(ApplianceType applianceType) {
		this.applianceType = applianceType;
	}

	@Override
	public ApplianceType getType() {
		return applianceType;
	}
	
	@Override
	public double getLastActualUsage() {
		switch(applianceType) {
		case DISHWASHER: return 10;
		case HIFI: return 5;
		case LIGHTBULB: return 1;
		case PC: return 6;
		case TV: return 7;
		}
		
		return 0;
	}

	@Override
	public List<Double> getPastActualUsages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getUsageForecast(int numberOfPeriods) {
		return usageForecast.calculateForecast(numberOfPeriods);
	}

	@Override
	public void setForecastingMethod(ForecastingMethod forecastingMethod) {
		switch(forecastingMethod) {
		case SIMPLE: usageForecast = new SimpleUsageForecast(this); break;
		}
	}

}
