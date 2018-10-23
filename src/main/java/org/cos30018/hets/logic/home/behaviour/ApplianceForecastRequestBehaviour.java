package org.cos30018.hets.logic.home.behaviour;

import java.util.List;
import java.util.Vector;

import org.cos30018.hets.logic.appliance.ApplianceMessage;
import org.cos30018.hets.logic.home.HomeAgent;
import org.cos30018.hets.logic.home.HomeMessage;

import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class ApplianceForecastRequestBehaviour extends AchieveREInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422652535325745455L;

	public static ApplianceForecastRequestBehaviour create(HomeAgent homeAgent, List<AID> applianceAIDs) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		for (AID applianceAID : applianceAIDs) {
			msg.addReceiver(applianceAID);
		}
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		int period = homeAgent.getNextPeriod();
		msg.setContent(ApplianceMessage.FORECAST + period);
		msg.setOntology(HomeMessage.ONTOLOGY_USAGE);

		return new ApplianceForecastRequestBehaviour(homeAgent, period, msg);
	}

	private HomeAgent homeAgent;
	private int period;

	private ApplianceForecastRequestBehaviour(HomeAgent homeAgent, int period, ACLMessage msg) {
		super(homeAgent, msg);
		this.homeAgent = homeAgent;
		this.period = period;
	}

	@Override
	protected void handleAgree(ACLMessage agree) {
		System.out.println(myAgent.getLocalName() + ": " + agree.getSender().getLocalName() + ", Agreed!");
	}

	@Override
	protected void handleInform(ACLMessage inform) {
		System.out.println(myAgent.getLocalName() + ": " + inform.getSender().getLocalName() + ": Informed: "
				+ inform.getContent());
	}

	@Override
	protected void handleRefuse(ACLMessage refuse) {
		System.out.println("Refused!");
	}

	@Override
	protected void handleFailure(ACLMessage failure) {
		System.out.println("Failed!");
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected void handleAllResultNotifications(Vector resultNotifications) {
		double totalUsageForecast = 0;
		for (Object o : resultNotifications) {
			if (o instanceof ACLMessage) {
				ACLMessage message = (ACLMessage) o;
				if (message.getPerformative() == ACLMessage.INFORM
						&& message.getContent().startsWith(ApplianceMessage.FORECAST)) {
					String usageText = message.getContent().replace(ApplianceMessage.FORECAST, "").trim();
					double usage = Double.valueOf(usageText);
					totalUsageForecast += usage;
				}
			}
		}

		homeAgent.setTotalUsageForecast(period, totalUsageForecast);
	}
}
