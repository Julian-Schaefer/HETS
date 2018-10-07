package org.cos30018.hets.logic.appliance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class ApplianceRegisterBehaviour extends AchieveREInitiator {

	public static final String MSG_REGISTER = "REGISTER";
	public static final String MSG_ACCEPTED = "ACCEPTED";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5927343857776372041L;

	public static ApplianceRegisterBehaviour create(Agent a, AID homeAgentAID) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(homeAgentAID);
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		msg.setContent(MSG_REGISTER);
		
		return new ApplianceRegisterBehaviour(a, msg);
	}
	
	private List<String> logMessages = new LinkedList<>();
	
	private ApplianceRegisterBehaviour(Agent a, ACLMessage msg) {
		super(a, msg);
	}

	@Override
	protected void handleInform(ACLMessage inform) {
		if(MSG_ACCEPTED.equals(inform.getContent())) {
			logMessage("Appliance has been registered by HomeAgent.");
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
