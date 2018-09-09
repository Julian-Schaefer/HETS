package org.cos30018.hets.home;

import java.util.Date;
import java.util.Vector;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class RetailerRequestBehaviour extends AchieveREInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422652535325745455L;

	public static RetailerRequestBehaviour create(Agent a) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		msg.addReceiver(new AID("retailer1", AID.ISLOCALNAME));
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
		msg.setContent("OFFER");
		
		return new RetailerRequestBehaviour(a, msg);
	}
	
	private RetailerRequestBehaviour(Agent a, ACLMessage msg) {
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
