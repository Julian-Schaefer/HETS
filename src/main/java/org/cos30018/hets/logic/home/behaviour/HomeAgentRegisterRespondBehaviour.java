package org.cos30018.hets.logic.home.behaviour;

import org.cos30018.hets.logic.appliance.ApplianceMessage;
import org.cos30018.hets.logic.home.HomeAgent;
import org.cos30018.hets.logic.home.HomeMessage;
import org.cos30018.hets.logic.retailer.RetailerMessage;

import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class HomeAgentRegisterRespondBehaviour extends AchieveREResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960196745935851735L;
	
	private static MessageTemplate template = MessageTemplate.and(
			MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
			MessageTemplate.and(
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
					MessageTemplate.MatchOntology(HomeMessage.ONTOLOGY_REGISTRATION)));


	private HomeAgent homeAgent;
	
	public HomeAgentRegisterRespondBehaviour(HomeAgent homeAgent) {
		super(homeAgent, template);
		this.homeAgent = homeAgent;
	}
	
	@Override
	protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {
		switch(request.getContent()) {
		case ApplianceMessage.REGISTER: return registerAppliance(request);
		case ApplianceMessage.UNREGISTER: return unregisterAppliance(request);
		case RetailerMessage.REGISTER: return registerRetailer(request);
		case RetailerMessage.UNREGISTER: return unregisterRetailer(request);
		default: throw new RefuseException("Wrong content");
		}
	}
	
	private ACLMessage registerAppliance(ACLMessage request) {
		homeAgent.registerAppliance(request.getSender());
		return createAcceptedMessage(request);
	}
	
	private ACLMessage unregisterAppliance(ACLMessage request) {
		homeAgent.unregisterAppliance(request.getSender());
		return createOkMessage(request);
	}
	
	private ACLMessage registerRetailer(ACLMessage request) {
		homeAgent.registerRetailer(request.getSender());
		return createAcceptedMessage(request);
	}
	
	private ACLMessage unregisterRetailer(ACLMessage request) {
		homeAgent.unregisterRetailer(request.getSender());
		return createOkMessage(request);
	}
	
	private ACLMessage createAcceptedMessage(ACLMessage request) {
		ACLMessage agreeMessage = request.createReply();
		agreeMessage.setPerformative(ACLMessage.INFORM);
		agreeMessage.setContent(HomeMessage.ACCEPTED);
		return agreeMessage;
	}
	
	private ACLMessage createOkMessage(ACLMessage request) {
		ACLMessage agreeMessage = request.createReply();
		agreeMessage.setPerformative(ACLMessage.INFORM);
		agreeMessage.setContent(HomeMessage.OK);
		return agreeMessage;
	}
}
