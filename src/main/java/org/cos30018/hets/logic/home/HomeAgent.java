package org.cos30018.hets.logic.home;

import java.util.ArrayList;
import java.util.List;

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
	private PeriodicApplianceRequestBehaviour periodicApplianceRequestBehaviour;

	
	public HomeAgent() {
		registerO2AInterface(Home.class, this);
	}
	
	@Override
	protected void setup() {
		ServiceDescription sd = new ServiceDescription();
		sd.setType(HOME_AGENT_SERVICE);
		sd.setName(getLocalName());
		register(sd);
		
		addBehaviour(new HomeAgentApplianceRegisterRespondBehaviour(this));

		periodicApplianceRequestBehaviour = new PeriodicApplianceRequestBehaviour(this, 500);
		addBehaviour(periodicApplianceRequestBehaviour);
	}
	
	public void registerAppliance(AID applianceAID) {
		applianceAIDs.add(applianceAID);
		listener.onApplianceAdded(applianceAID);
	}
	
	public List<AID> getApplianceAIDs() {
		return applianceAIDs;
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
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setCheckInterval() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setForecastPeriodCount() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListener(HomeListener listener) {
		this.listener = listener;
	}

}
