package org.cos30018.hets.logic.home;

import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class HomeAgent extends Agent {

	public static final String HOME_AGENT_SERVICE = "home_agent";

	/**
	 * 
	 */
	private static final long serialVersionUID = -4685491195096413651L;
	
	private List<AID> applianceAIDs = new ArrayList<AID>();
	private PeriodicApplianceRequestBehaviour periodicApplianceRequestBehaviour;

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

}
