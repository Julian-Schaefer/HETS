package org.cos30018.hets.logic.retailer;

import java.util.LinkedList;
import java.util.List;

import org.cos30018.hets.logic.common.RegisteringAgent;
import org.cos30018.hets.logic.home.HomeAgent;
import org.cos30018.hets.logic.retailer.behaviour.RetailerResponderBehaviour;

import jade.lang.acl.ACLMessage;

public class RetailerAgent extends RegisteringAgent implements Retailer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 374925778728983618L;

	private List<RetailerListener> listeners = new LinkedList<>();

	private List<String> negotiationMessages = new LinkedList<>();

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

	public void addNegotiationMessage(ACLMessage message) {
		StringBuilder stringBuilder = new StringBuilder().append(message.getSender().getLocalName()).append("send a ")
				.append(ACLMessage.getAllPerformativeNames()[message.getPerformative()]).append(": ")
				.append(message.getContent());

		String negotiationMessage = stringBuilder.toString();

		negotiationMessages.add(negotiationMessage);
		for (RetailerListener listener : listeners) {
			listener.onNegotiationMessageAdded(negotiationMessage);
		}
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

	@Override
	public void setVolumeCharge(double volumeCharge) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getVolumeCharge() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setFeedInRate(double feedInRate) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getFeedInRate() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addRetailerListener(RetailerListener listener) {
		listeners.add(listener);
	}

	@Override
	public List<String> getNegotiationMessages() {
		return negotiationMessages;
	}
}
