package org.cos30018.hets.logic.appliance;

import java.util.List;

import org.cos30018.hets.logic.appliance.behaviour.ApplianceResponderBehaviour;
import org.cos30018.hets.logic.appliance.forecast.NeuralNetworkForecast;
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

		// DISHWASHER, FRIDGE, WASHING_MACHINE, DRYER, HEAT_PUMP, HOT_WATER_SYSTEM, HIFI
		switch (applianceType) {
		case DISHWASHER:
			return 10;
		case HIFI:
			return 5;
		case FRIDGE:
			return 6;
		case WASHING_MACHINE:
			return 12;
		case DRYER:
			return 4;
		case HEAT_PUMP:
			return 20;
		case HOT_WATER_SYSTEM:
			return 3;
		}

		return 0;
	}

	@Override
	public List<Double> getPastActualUsages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getUsageForecast(int period, int numberOfPeriods) {
		return usageForecast.calculateForecast(period, numberOfPeriods);
	}

	@Override
	public void setForecastingMethod(ForecastingMethod forecastingMethod) {
		switch (forecastingMethod) {
		case SIMPLE:
			usageForecast = new SimpleUsageForecast(this);
			break;
		case COMPLEX:
			usageForecast = new NeuralNetworkForecast(this);
			break;
		}
	}

}
