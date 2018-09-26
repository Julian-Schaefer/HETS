package org.cos30018.hets.logic.home;

import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;

public class PeriodicApplianceRequestBehaviour extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3679750059191181891L;

	private HomeAgent homeAgent;
	
	public PeriodicApplianceRequestBehaviour(HomeAgent homeAgent, long period) {
		super(homeAgent, period);
		this.homeAgent = homeAgent;
	}

	@Override
	protected void onTick() {
		for(AID applianceAID : homeAgent.getApplianceAIDs()) {
			homeAgent.addBehaviour(ApplianceRequestBehaviour.create(myAgent, applianceAID));
		}
	}
}
