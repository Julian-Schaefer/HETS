package org.cos30018.hets.logic.common;

import org.cos30018.hets.logic.home.HomeMessage;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public abstract class RegisteringAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1080675407037093914L;

	protected AID serviceAgentAID;
	
	private String serviceName;
	private String registerMessage;
	private String unregisterMessage;
	
	public RegisteringAgent(String serviceName, String registerMessage, String unregisterMessage) {
		this.serviceName = serviceName;
		this.registerMessage = registerMessage;
		this.unregisterMessage = unregisterMessage;
	}
	
	@Override
	protected void setup() {
		serviceAgentAID = getService(serviceName)[0].getName();
		addBehaviour(HomeAgentRegisterBehaviour.create(this, serviceAgentAID, registerMessage));
	}

	@Override
	protected void takeDown() {			
		ACLMessage unregisterACLMessage = new ACLMessage(ACLMessage.REQUEST);
		unregisterACLMessage.addReceiver(serviceAgentAID);
		unregisterACLMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		unregisterACLMessage.setOntology(HomeMessage.ONTOLOGY_REGISTRATION);
		unregisterACLMessage.setContent(unregisterMessage);
		send(unregisterACLMessage);
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
