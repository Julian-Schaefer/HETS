package org.cos30018.hets.logic.appliance.behaviour;

import org.cos30018.hets.logic.appliance.ApplianceAgent;
import org.cos30018.hets.logic.appliance.ApplianceMessage;
import org.cos30018.hets.logic.home.HomeMessage;

import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

public class ApplianceResponderBehaviour extends AchieveREResponder {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960196745935851735L;

	private static MessageTemplate template = MessageTemplate.and(
			MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST),
			MessageTemplate.and(MessageTemplate.MatchOntology(HomeMessage.ONTOLOGY_USAGE),
					MessageTemplate.MatchPerformative(ACLMessage.REQUEST)));

	private ApplianceAgent applianceAgent;

	public ApplianceResponderBehaviour(ApplianceAgent applianceAgent) {
		super(applianceAgent, template);
		this.applianceAgent = applianceAgent;
	}

	@Override
	protected ACLMessage prepareResponse(ACLMessage request) throws NotUnderstoodException, RefuseException {
		if (request.getContent().startsWith(ApplianceMessage.LAST_USAGE)
				|| request.getContent().startsWith(ApplianceMessage.FORECAST)) {
			ACLMessage agreeMessage = request.createReply();
			agreeMessage.setPerformative(ACLMessage.AGREE);
			return agreeMessage;
		} else {
			throw new RefuseException("Wrong content");
		}
	}

	@Override
	protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response) throws FailureException {
		if (request.getContent().startsWith(ApplianceMessage.LAST_USAGE)) {
			response.setPerformative(ACLMessage.INFORM);
			response.setContent(ApplianceMessage.LAST_USAGE + " " + applianceAgent.getLastActualUsage());
		} else if (request.getContent().startsWith(ApplianceMessage.FORECAST)) {
			String periodText = request.getContent().replace(ApplianceMessage.FORECAST, "").trim();
			int period = Integer.valueOf(periodText);

			response.setPerformative(ACLMessage.INFORM);
			response.setContent(ApplianceMessage.FORECAST + applianceAgent.getUsageForecast(period, 1)[0]);
		}
		return response;
	}
}
