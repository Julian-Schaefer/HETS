package org.cos30018.hets.logic.home.behaviour;

import org.cos30018.hets.logic.appliance.ApplianceMessage;
import org.cos30018.hets.logic.home.HomeAgent;

import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class HomerRegisterApplianceBehaviour extends AchieveREResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960196745935851735L;
	
	private static MessageTemplate template = MessageTemplate.and(
			MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
			MessageTemplate.MatchPerformative(ACLMessage.REQUEST));


	private HomeAgent homeAgent;
	
	public HomerRegisterApplianceBehaviour(HomeAgent homeAgent) {
		super(homeAgent, template);
		this.homeAgent = homeAgent;
	}
	
	@Override
	protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
		if(request.getContent().equals(ApplianceMessage.REGISTER)) {
			homeAgent.registerAppliance(request.getSender());
			
			ACLMessage agreeMessage = request.createReply();
			agreeMessage.setPerformative(ACLMessage.INFORM);
			agreeMessage.setContent(ApplianceMessage.ACCEPTED);
			return agreeMessage;
		} else {
			throw new RefuseException("Wrong content");
		}
	}
}
