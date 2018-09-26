package org.cos30018.hets.logic.retailer;

import jade.core.Agent;

public class RetailerAgent extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 374925778728983618L;

	@Override
	protected void setup() {
		addBehaviour(new RetailerResponderBehaviour(this));
	}
}
