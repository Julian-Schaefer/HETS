package org.cos30018.hets.logic.appliance;

import org.cos30018.hets.logic.home.HomeAgent;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class ApplianceAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2702213410686638092L;
	
	

	@Override
	protected void setup() {
		AID homeAgentAID = getService(HomeAgent.HOME_AGENT_SERVICE)[0].getName();
		addBehaviour(ApplianceRegisterBehaviour.create(this, homeAgentAID));
		
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

}
