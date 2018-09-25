package org.cos30018.hets.logic.home;

import java.util.Date;
import java.util.Vector;

import org.cos30018.hets.logic.MessageConstants;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class ApplianceRequestBehaviour extends AchieveREInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422652535325745455L;

	public static ApplianceRequestBehaviour create(Agent a, AID applianceAID) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(applianceAID);
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		msg.setContent(MessageConstants.USAGE);
		
		return new ApplianceRequestBehaviour(a, msg);
	}
	
	private ApplianceRequestBehaviour(Agent a, ACLMessage msg) {
		super(a, msg);
	}

	@Override
	protected void handleAgree(ACLMessage agree) {
		System.out.println("Agreed!");
	}
	
	@Override
	protected void handleInform(ACLMessage inform) {
		System.out.println("Informed!");
	}
	
	@Override
	protected void handleRefuse(ACLMessage refuse) {
		System.out.println("Refused!");
	}
	
	@Override
	protected void handleFailure(ACLMessage failure) {
		System.out.println("Failed!");
	}
	
	@Override
	protected void handleAllResultNotifications(Vector resultNotifications) {
		System.out.println("All handled!");
	}
}
