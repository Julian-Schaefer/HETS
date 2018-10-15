package org.cos30018.hets.logic.retailer;

import org.cos30018.hets.logic.common.RegisteringAgent;
import org.cos30018.hets.logic.home.HomeAgent;
import org.cos30018.hets.logic.retailer.behaviour.RetailerResponderBehaviour;

public class RetailerAgent extends RegisteringAgent implements Retailer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 374925778728983618L;

	public RetailerAgent() {
		super(HomeAgent.HOME_AGENT_SERVICE, RetailerMessage.REGISTER, RetailerMessage.UNREGISTER);
		registerO2AInterface(Retailer.class, this);
	}
	
	@Override
	protected void setup() {
		super.setup();
		
		addBehaviour(new RetailerResponderBehaviour(this));
	}
}
