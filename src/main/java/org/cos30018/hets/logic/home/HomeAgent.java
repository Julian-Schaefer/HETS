package org.cos30018.hets.logic.home;

import java.util.ArrayList;
import java.util.List;

import org.cos30018.hets.logic.home.behaviour.HomerRegisterApplianceBehaviour;
import org.cos30018.hets.logic.home.behaviour.PeriodicApplianceRequestBehaviour;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class HomeAgent extends Agent implements Home {

	public static final String HOME_AGENT_SERVICE = "home_agent";

	/**
	 * 
	 */
	private static final long serialVersionUID = -4685491195096413651L;
	
	private HomeListener listener;

	private List<AID> applianceAIDs = new ArrayList<>();
	private List<AID> retailerAIDs = new ArrayList<>();
	private PeriodicApplianceRequestBehaviour periodicApplianceRequestBehaviour;

	private int forecastPeriodCount;
	private double totalUsageForecast;
	
	public HomeAgent() {
		registerO2AInterface(Home.class, this);
	}
	
	@Override
	protected void setup() {
		ServiceDescription sd = new ServiceDescription();
		sd.setType(HOME_AGENT_SERVICE);
		sd.setName(getLocalName());
		register(sd);
		
		addBehaviour(new HomerRegisterApplianceBehaviour(this));

		periodicApplianceRequestBehaviour = new PeriodicApplianceRequestBehaviour(this, 5000);
		addBehaviour(periodicApplianceRequestBehaviour);
	}
	
	public void registerAppliance(AID applianceAID) {
		applianceAIDs.add(applianceAID);
		listener.onApplianceAdded(applianceAID);
	}
	
	public void registerRetailer(AID retailerAID) {
		retailerAIDs.add(retailerAID);
		listener.onRetailerAdded(retailerAID);
	}

	private void register(ServiceDescription serviceDescription) {
		DFAgentDescription dfAgentDescription = new DFAgentDescription();
		dfAgentDescription.setName(getAID());
		dfAgentDescription.addServices(serviceDescription);

		try {
			DFService.register(this, dfAgentDescription);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}

	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (Exception e) {
		}
	}

	@Override
	public List<AID> getAppliances() {
		return applianceAIDs;
	}
	
	@Override
	public void setCheckInterval(long period) {
		periodicApplianceRequestBehaviour.reset(period);
	}

	@Override
	public void setForecastPeriodCount(int forecastPeriodCount) {
		this.forecastPeriodCount = forecastPeriodCount;
	}
	
	@Override
	public int getForecastPeriodCount() {
		return forecastPeriodCount;
	}

	@Override
	public void setTotalUsageForecast(double totalUsageForecast) {
		this.totalUsageForecast = totalUsageForecast;
		listener.onTotalUsageForecastUpdated(totalUsageForecast);
	}
	
	@Override
	public double getTotalUsageForecast() {
		return totalUsageForecast;
	}

	@Override
	public void setListener(HomeListener listener) {
		this.listener = listener;
	}
}
