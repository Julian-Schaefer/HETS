package org.cos30018.hets.logic.retailer.behaviour;

import org.cos30018.hets.logic.retailer.RetailerAgent;

import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

public class RetailerResponderBehaviour extends ContractNetResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960196745935851735L;

	private static MessageTemplate template = MessageTemplate.and(
			MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
			MessageTemplate.MatchPerformative(ACLMessage.REQUEST));

	private RetailerAgent retailerAgent;

	public RetailerResponderBehaviour(RetailerAgent retailerAgent) {
		super(retailerAgent, createMessageTemplate(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET));
		this.retailerAgent = retailerAgent;
	}

	@Override
	protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
		retailerAgent.addNegotiationMessage(cfp);

		ACLMessage reply = cfp.createReply();
		reply.setSender(getAgent().getAID());
		reply.setPerformative(ACLMessage.PROPOSE);
		retailerAgent.addNegotiationMessage(reply);
		return reply;
	}

	@Override
	protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept)
			throws FailureException {
		retailerAgent.addNegotiationMessage(accept);

		ACLMessage reply = accept.createReply();
		reply.setPerformative(ACLMessage.INFORM);
		retailerAgent.addNegotiationMessage(accept);

		return reply;
	}

	@Override
	protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
		retailerAgent.addNegotiationMessage(reject);
	}

	@Override
	protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
		System.out.println(myAgent.getLocalName() + ": REQUEST received from " + request.getSender().getName()
				+ ". Query is " + request.getContent());

		if (request.getContent().equals("OFFER")) {
			ACLMessage agreeMessage = request.createReply();
			agreeMessage.setPerformative(ACLMessage.AGREE);
			return agreeMessage;
		} else {
			throw new RefuseException("Wrong content");
		}
	}
}