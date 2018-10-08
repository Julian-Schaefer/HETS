package org.cos30018.hets.logic.appliance;

import java.util.List;

import org.cos30018.hets.logic.appliance.behaviour.ApplianceRegisterBehaviour;
import org.cos30018.hets.logic.appliance.behaviour.ApplianceResponderBehaviour;
import org.cos30018.hets.logic.appliance.forecast.SimpleUsageForecast;
import org.cos30018.hets.logic.appliance.forecast.UsageForecast;
import org.cos30018.hets.logic.home.HomeAgent;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class ApplianceAgent extends Agent implements Appliance {

	public static final int FORECASTING_SIMPLE = 1;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2702213410686638092L;
	
	private UsageForecast usageForecast;
	private ApplianceType applianceType;
	
	
	public ApplianceAgent() {
		registerO2AInterface(Appliance.class, this);
	}
	
	@Override
	protected void setup() {
		AID homeAgentAID = getService(HomeAgent.HOME_AGENT_SERVICE)[0].getName();
		addBehaviour(ApplianceRegisterBehaviour.create(this, homeAgentAID));
		
		Object[] arguments = getArguments();
		applianceType = (ApplianceType) arguments[0];
		int forecastingMethod = (int) arguments[1];
		setForecastingMethod(forecastingMethod);
		
		addBehaviour(new ApplianceResponderBehaviour(this));
	}

	private DFAgentDescription[] getService(String service) {
		DFAgentDescription dfAgentDescription = new DFAgentDescription();
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType(service);
		dfAgentDescription.addServices(serviceDescription);
		
		try {
			DFAgentDescription[] result = DFService.search(this, dfAgentDescription);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
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
	public void setForecastingMethod(int forecastingMethod) {
		switch(forecastingMethod) {
		case FORECASTING_SIMPLE: usageForecast = new SimpleUsageForecast(this); break;
		}
	}

}
