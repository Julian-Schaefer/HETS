package org.cos30018.hets.logic.retailer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cos30018.hets.logic.common.RegisteringAgent;
import org.cos30018.hets.logic.home.HomeAgent;
import org.cos30018.hets.logic.retailer.behaviour.RetailerResponderBehaviour;
import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.tariff.Tariff;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

public class RetailerAgent extends RegisteringAgent implements Retailer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 374925778728983618L;

	private List<RetailerListener> listeners = new LinkedList<>();

	private List<String> negotiationMessages = new LinkedList<>();

	private Map<Integer, Offer> outgoingOffers = new HashMap<>();
	private Map<Integer, Offer> incomingOffers = new HashMap<>();

	private Tariff tariff;
	private Strategy strategy;

	public RetailerAgent() {
		super(HomeAgent.HOME_AGENT_SERVICE, RetailerMessage.REGISTER, RetailerMessage.UNREGISTER);
		registerO2AInterface(Retailer.class, this);
	}

	@Override
	protected void setup() {
		super.setup();

		Object[] arguments = getArguments();
		strategy = (Strategy) arguments[0];
		tariff = (Tariff) arguments[1];

		addBehaviour(new RetailerResponderBehaviour(this));
	}

	public void clearNegotiationLog() {
		negotiationMessages.clear();
		outgoingOffers.clear();
		incomingOffers.clear();

		for (RetailerListener listener : listeners) {
			listener.onNegotiationMessagesUpdated();
		}
	}

	public void addIncomingOffer(int round, Offer offer) {
		incomingOffers.put(round, offer);
	}

	@Override
	public Map<Integer, Offer> getIncomingOffers() {
		return incomingOffers;
	}

	public void addOutgoingOffer(int round, Offer offer) {
		outgoingOffers.put(round, offer);
	}

	@Override
	public Map<Integer, Offer> getOutgoingOffers() {
		return outgoingOffers;
	}

	public void addNegotiationMessage(ACLMessage message) {
		StringBuilder stringBuilder = new StringBuilder().append(message.getSender().getLocalName()).append(" send a ")
				.append(ACLMessage.getAllPerformativeNames()[message.getPerformative()]).append(": ");

		try {
			if (message.getContentObject() != null) {
				stringBuilder.append(message.getContentObject());
			} else {
				stringBuilder.append(" - ");
			}
		} catch (UnreadableException e) {
			stringBuilder.append(e.getMessage());
		}

		String negotiationMessage = stringBuilder.toString();

		negotiationMessages.add(negotiationMessage);
		for (RetailerListener listener : listeners) {
			listener.onNegotiationMessagesUpdated();
		}
	}

	@Override
	public Tariff getTariff() {
		return tariff;
	}

	@Override
	public Strategy getStrategy() {
		return strategy;
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
