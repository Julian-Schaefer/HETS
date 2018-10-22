package org.cos30018.hets.logic.home.behaviour;

import java.util.Vector;

import org.cos30018.hets.logic.NegotiationMessage;
import org.cos30018.hets.logic.home.HomeAgent;
import org.cos30018.hets.negotiation.Offer;

import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

public class RetailerRequestBehaviour extends ContractNetInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422652535325745455L;

	private int currentRound = 0;
	private int deadLineRound = 20;

	public static RetailerRequestBehaviour create(HomeAgent homeAgent) {
		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_ITERATED_CONTRACT_NET);
		for (AID retailerAID : homeAgent.getRetailers()) {
			msg.addReceiver(retailerAID);
		}

		msg.setContent(NegotiationMessage.OFFER + homeAgent.getTotalUsageForecast());

		return new RetailerRequestBehaviour(homeAgent, msg);
	}

	private HomeAgent homeAgent;

	private RetailerRequestBehaviour(HomeAgent homeAgent, ACLMessage msg) {
		super(homeAgent, msg);
		this.homeAgent = homeAgent;
	}

//	@Override
//	protected void handlePropose(ACLMessage propose, Vector acceptances) {
//		ACLMessage acceptance = propose.createReply();
//		if (propose.getPerformative() == ACLMessage.PROPOSE && currentRound < deadLineRound) {
//			acceptance.setPerformative(ACLMessage.REJECT_PROPOSAL);
//		} else {
//			acceptance.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
//		}
//
//		acceptances.add(acceptance);
//	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void handleAllResponses(Vector responses, Vector acceptances) {
		for (Object o : responses) {
			if (o instanceof ACLMessage) {
				ACLMessage response = (ACLMessage) o;
				ACLMessage acceptance;
				if (response.getPerformative() == ACLMessage.PROPOSE && currentRound < deadLineRound) {
					acceptance = response.createReply();
					acceptance.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					acceptances.add(acceptance);
					newIteration(acceptances);
				} else {
					acceptance = response.createReply();
					acceptance.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					acceptances.add(acceptance);
				}
			}
		}
		currentRound++;
	}

	@Override
	protected void handleInform(ACLMessage inform) {
		System.out.println("Inform!");
		homeAgent.setNegotiatedOffer(homeAgent.getNextPeriod(),
				new Offer(homeAgent.getRetailers().get(0), Math.random() * 70, homeAgent.getNextPeriod(), 1));
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
