package org.cos30018.hets.logic.common;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.cos30018.hets.logic.home.HomeMessage;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class HomeAgentRegisterBehaviour extends AchieveREInitiator {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5927343857776372041L;

	public static HomeAgentRegisterBehaviour create(Agent a, AID homeAgentAID, String content) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(homeAgentAID);
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setOntology(HomeMessage.ONTOLOGY_REGISTRATION);
		msg.setContent(content);
		
		return new HomeAgentRegisterBehaviour(a, msg);
	}
	
	private List<String> logMessages = new LinkedList<>();
	
	private HomeAgentRegisterBehaviour(Agent a, ACLMessage msg) {
		super(a, msg);
	}

	@Override
	protected void handleInform(ACLMessage inform) {
		if(HomeMessage.ACCEPTED.equals(inform.getContent())) {
			logMessage("Agent has been registered by HomeAgent.");
		} else {
			myAgent.doDelete();
		}
	}
	
	@Override
	protected void handleRefuse(ACLMessage refuse) {
		logMessage("REFUSED");
		myAgent.doDelete();
	}
	
	@Override
	protected void handleFailure(ACLMessage failure) {
		logMessage("FAILURE");
		myAgent.doDelete();
	}
	
	@Override
	protected void handleNotUnderstood(ACLMessage notUnderstood) {
		logMessage("NOT_UNDERSTOOD");
		myAgent.doDelete();
	}
	
	@Override
	protected void handleOutOfSequence(ACLMessage msg) {
		logMessage("OUT_OF_SEQUENCE");
		myAgent.doDelete();
	}
	
	private void logMessage(String message) {
		String logMessage = LocalDateTime.now().toString() + message;
		logMessages.add(logMessage);
	}
}
