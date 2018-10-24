package org.cos30018.hets.logic.retailer.behaviour;

import org.cos30018.hets.logic.retailer.RetailerAgent;
import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.negotiation.strategy.Strategy;

import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.SSIteratedContractNetResponder;

public class RetailerNegotiationBehaviour extends SSIteratedContractNetResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -359348855873352783L;

	private RetailerAgent retailerAgent;
	private Strategy strategy;

	public RetailerNegotiationBehaviour(RetailerAgent retailerAgent, ACLMessage initiationMessage) {
		super(retailerAgent, initiationMessage);
		this.retailerAgent = retailerAgent;

		retailerAgent.clearNegotiationMessages();

		try {
			strategy = (Strategy) retailerAgent.getStrategy().clone();
			initStrategy(initiationMessage);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	private void initStrategy(ACLMessage initiationMessage) throws CloneNotSupportedException {
		try {
			Offer initialOffer = (Offer) initiationMessage.getContentObject();
			double initialPrice = retailerAgent.getTariff().getPrice(initialOffer.getAmount(),
					initialOffer.getStartPeriod());
			strategy.reset(initialPrice);
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
		retailerAgent.addNegotiationMessage(cfp);

		try {
			Offer incomingOffer = (Offer) cfp.getContentObject();
			Offer counterOffer = strategy.getCounterOffer(incomingOffer);

			ACLMessage reply = cfp.createReply();
			reply.setSender(getAgent().getAID());
			reply.setPerformative(ACLMessage.PROPOSE);
			reply.setContentObject(counterOffer);

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
			retailerAgent.addNegotiationMessage(accept);
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
