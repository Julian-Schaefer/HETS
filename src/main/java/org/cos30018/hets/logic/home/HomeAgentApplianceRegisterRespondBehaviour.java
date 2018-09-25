package org.cos30018.hets.logic.home;

import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class HomeAgentApplianceRegisterRespondBehaviour extends AchieveREResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960196745935851735L;
	
	private static MessageTemplate template = MessageTemplate.and(
			MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
			MessageTemplate.MatchPerformative(ACLMessage.REQUEST));


	private HomeAgent homeAgent;
	
	public HomeAgentApplianceRegisterRespondBehaviour(HomeAgent homeAgent) {
		super(homeAgent, template);
		this.homeAgent = homeAgent;
	}
	
	@Override
	protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
		if(request.getContent().equals("REGISTER")) {
			homeAgent.registerAppliance(request.getSender());
			System.out.println(myAgent.getLocalName() + ": Appliance registered");
			
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
