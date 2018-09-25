package org.cos30018.hets.logic.appliance;

import java.util.Date;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class ApplianceRegisterRequestBehaviour extends AchieveREInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5927343857776372041L;

	public static ApplianceRegisterRequestBehaviour create(Agent a, AID homeAgentAID) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(homeAgentAID);
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		msg.setContent("REGISTER");
		
		return new ApplianceRegisterRequestBehaviour(a, msg);
	}
	
	private ApplianceRegisterRequestBehaviour(Agent a, ACLMessage msg) {
		super(a, msg);
	}

	@Override
	protected void handleAgree(ACLMessage agree) {
		System.out.println("Register Appliance: Agreed!");
	}
	
	
	@Override
	protected void handleRefuse(ACLMessage refuse) {
		myAgent.doDelete();
	}
	
	@Override
	protected void handleFailure(ACLMessage failure) {
		myAgent.doDelete();
	}	
}
