package org.cos30018.hets.home;

import jade.core.Agent;

public class HomeAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4685491195096413651L;


	@Override
	protected void setup() {		
		addBehaviour(ApplianceRequestBehaviour.create(this));
		addBehaviour(RetailerRequestBehaviour.create(this));
	}	
}
