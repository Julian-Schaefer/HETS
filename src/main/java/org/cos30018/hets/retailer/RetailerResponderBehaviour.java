package org.cos30018.hets.retailer;

import jade.core.Agent;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class RetailerResponderBehaviour extends AchieveREResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960196745935851735L;
	
	private static MessageTemplate template = MessageTemplate.and(
			MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
			MessageTemplate.MatchPerformative(ACLMessage.REQUEST));


	public RetailerResponderBehaviour(Agent a) {
		super(a, template);
	}
	
	@Override
	protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
		System.out.println(myAgent.getLocalName() + ": REQUEST received from "
				+ request.getSender().getName() + ". Query is " + request.getContent());
		
		if(request.getContent().equals("OFFER")) {
			ACLMessage agreeMessage = request.createReply();
			agreeMessage.setPerformative(ACLMessage.AGREE);
			return agreeMessage;
		} else {
			throw new RefuseException("Wrong content");
		}
	}
	
	@Override
	protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
		response.setPerformative(ACLMessage.INFORM);
		response.setContent("Accepted");
		return response;
	}

}