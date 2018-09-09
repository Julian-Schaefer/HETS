package org.cos30018.hets.appliance;

import jade.core.Agent;

public class ApplianceAgent extends Agent {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2702213410686638092L;

	@Override
	protected void setup() {
		addBehaviour(new ApplianceResponderBehaviour(this));
	}
}
