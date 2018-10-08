package org.cos30018.hets.logic.home.behaviour;

import java.util.List;
import java.util.Vector;

import org.cos30018.hets.logic.appliance.ApplianceMessage;
import org.cos30018.hets.logic.home.HomeAgent;

import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

public class ApplianceUsageRequestBehaviour extends AchieveREInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422652535325745455L;

	private HomeAgent homeAgent;
	
	public static ApplianceUsageRequestBehaviour create(HomeAgent homeAgent, List<AID> applianceAIDs) {
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
		for(AID applianceAID : applianceAIDs) {
			msg.addReceiver(applianceAID);	
		}		
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		msg.setContent(ApplianceMessage.USAGE);
		
		return new ApplianceUsageRequestBehaviour(homeAgent, msg);
	}
	
	private ApplianceUsageRequestBehaviour(HomeAgent homeAgent, ACLMessage msg) {
		super(homeAgent, msg);
		this.homeAgent = homeAgent;
	}

	@Override
	protected void handleAgree(ACLMessage agree) {
		System.out.println(myAgent.getLocalName() + ": " + agree.getSender().getLocalName() + ", Agreed!");
	}
	
	@Override
	protected void handleInform(ACLMessage inform) {
		System.out.println(myAgent.getLocalName() + ": " + inform.getSender().getLocalName() + ": Informed: " + inform.getContent());
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
		for(Object o : resultNotifications) {
			if(o instanceof ACLMessage) {
				ACLMessage message = (ACLMessage) o;
				String usageText = message.getContent().replace(ApplianceMessage.USAGE, "").trim();
				double usage = Double.valueOf(usageText);
				totalUsageForecast += usage;
			}
		}
		
		homeAgent.setTotalUsageForecast(totalUsageForecast);
		System.out.println("All handled!");
	}
}
