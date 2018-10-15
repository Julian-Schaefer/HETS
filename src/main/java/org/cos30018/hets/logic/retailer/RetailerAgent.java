package org.cos30018.hets.logic.retailer;

import org.cos30018.hets.logic.common.RegisteringAgent;
import org.cos30018.hets.logic.home.HomeAgent;
import org.cos30018.hets.logic.retailer.behaviour.RetailerResponderBehaviour;

public class RetailerAgent extends RegisteringAgent implements Retailer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 374925778728983618L;

	private NegotiationStrategy negotiationStrategy;
	private PricingStrategy pricingStrategy;
	
	public RetailerAgent() {
		super(HomeAgent.HOME_AGENT_SERVICE, RetailerMessage.REGISTER, RetailerMessage.UNREGISTER);
		registerO2AInterface(Retailer.class, this);
	}
	
	@Override
	protected void setup() {
		super.setup();
	
		Object[] arguments = getArguments();
		negotiationStrategy = (NegotiationStrategy) arguments[0];
		pricingStrategy = (PricingStrategy) arguments[1];
		
		addBehaviour(new RetailerResponderBehaviour(this));
	}

	@Override
	public void setNegotiationStrategy(NegotiationStrategy negotiationStrategy) {
		this.negotiationStrategy = negotiationStrategy;
	}

	@Override
	public NegotiationStrategy getNegotiationStrategy() {
		return negotiationStrategy;
	}

	@Override
	public void setPricingStrategy(PricingStrategy pricingStrategy) {
		this.pricingStrategy = pricingStrategy;
	}

	@Override
	public PricingStrategy getPricingStrategy() {
		return pricingStrategy;
	}
}
