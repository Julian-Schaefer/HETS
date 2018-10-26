package org.cos30018.hets.logic.retailer.behaviour;

import org.cos30018.hets.logic.retailer.RetailerAgent;

import jade.core.behaviours.Behaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SSResponderDispatcher;

public class RetailerResponderBehaviour extends SSResponderDispatcher {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960196745935851735L;

	private RetailerAgent retailerAgent;

	public RetailerResponderBehaviour(RetailerAgent retailerAgent) {
		super(retailerAgent, MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.CFP),
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_ITERATED_CONTRACT_NET)));
		this.retailerAgent = retailerAgent;
	}

	@Override
	protected Behaviour createResponder(ACLMessage initiationMessage) {
		return new RetailerNegotiationBehaviour(retailerAgent, initiationMessage);
	}
}