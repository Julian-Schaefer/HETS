package org.cos30018.hets.logic.retailer.behaviour;

import org.cos30018.hets.logic.retailer.RetailerAgent;
import org.cos30018.hets.negotiation.Negotiation;
import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.utility.OfferUtility;

import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.proto.SSIteratedContractNetResponder;

public class RetailerNegotiationBehaviour extends SSIteratedContractNetResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -359348855873352783L;

	private RetailerAgent retailerAgent;

	private Negotiation negotiation;

	public RetailerNegotiationBehaviour(RetailerAgent retailerAgent, ACLMessage initiationMessage) {
		super(retailerAgent, initiationMessage);
		this.retailerAgent = retailerAgent;

		retailerAgent.clearNegotiationLog();
		initNegotiation(initiationMessage);
	}

	private void initNegotiation(ACLMessage initiationMessage) {
		try {
			Strategy strategy = (Strategy) retailerAgent.getStrategy().clone();

			Offer initialOffer = (Offer) initiationMessage.getContentObject();
			double initialPrice = retailerAgent.getTariff().getVolumeCharge(initialOffer.getAmount(),
					initialOffer.getStartPeriod());
			strategy.reset(initialPrice);

			OfferUtility utility = new OfferUtility(strategy.getReservationValue(), initialPrice, 0, 1);
			negotiation = new Negotiation(strategy, utility);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
		retailerAgent.addNegotiationMessage(cfp);

		try {
			int round = negotiation.getRound();
			Offer incomingOffer = (Offer) cfp.getContentObject();
			Offer counterOffer = negotiation.createCounterOffer(incomingOffer);

			ACLMessage reply = cfp.createReply();
			reply.setSender(getAgent().getAID());

			if (counterOffer.getStatus() == Offer.Status.REFUSE) {
				reply.setPerformative(ACLMessage.REFUSE);
			} else {
				reply.setPerformative(ACLMessage.PROPOSE);
				reply.setContentObject(counterOffer);
				retailerAgent.addIncomingOffer(round, incomingOffer);
				retailerAgent.addOutgoingOffer(round, counterOffer);
			}

			retailerAgent.addNegotiationMessage(reply);
			return reply;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept)
			throws FailureException {
		retailerAgent.addNegotiationMessage(accept);

		try {
			ACLMessage reply = accept.createReply();
			reply.setContentObject(propose.getContentObject());
			reply.setPerformative(ACLMessage.INFORM);
			return reply;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
		retailerAgent.addNegotiationMessage(reject);
	}
}
