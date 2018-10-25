package org.cos30018.hets.logic.home.behaviour;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.cos30018.hets.logic.home.HomeAgent;
import org.cos30018.hets.negotiation.Negotiation;
import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.negotiation.Offer.Status;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.strategy.TimeDependentStrategy;
import org.cos30018.hets.negotiation.utility.OfferUtility;

import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetInitiator;

public class RetailerRequestBehaviour extends ContractNetInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422652535325745455L;

	private int deadLineRound = 20;
	private final int period;

	public static RetailerRequestBehaviour create(HomeAgent homeAgent) {
		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
		msg.setProtocol(FIPANames.InteractionProtocol.FIPA_ITERATED_CONTRACT_NET);
		for (AID retailerAID : homeAgent.getRetailers()) {
			msg.addReceiver(retailerAID);
		}

		int period = homeAgent.getNextPeriod();
		Offer initialOffer = new Offer(0.0, homeAgent.getTotalUsageForecast(period), period, 1);
		try {
			msg.setContentObject(initialOffer);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new RetailerRequestBehaviour(homeAgent, period, msg);
	}

	private HomeAgent homeAgent;

	private RetailerRequestBehaviour(HomeAgent homeAgent, int period, ACLMessage msg) {
		super(homeAgent, msg);
		this.homeAgent = homeAgent;
		this.period = period;

		initNegotiation();
	}

	private Map<AID, Negotiation> negotiations = new HashMap<>();

	private void initNegotiation() {
		for (AID retailerAID : homeAgent.getRetailers()) {
			Strategy strategy = new TimeDependentStrategy(deadLineRound, 100, 4);
			strategy.reset(0);

			OfferUtility utility = new OfferUtility(0, 100, 1, 0);

			Negotiation negotiation = new Negotiation(strategy, utility);
			negotiations.put(retailerAID, negotiation);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void handleAllResponses(Vector responses, Vector acceptances) {

		Vector propositions = new Vector<>();
		for (Object o : responses) {
			if (o instanceof ACLMessage) {
				ACLMessage response = (ACLMessage) o;
				if (response.getPerformative() == ACLMessage.PROPOSE) {
					propositions.add(response);
				}
			}
		}

		if (propositions.size() > 1) {
			Vector newIteration = new Vector<>();
			for (Object o : propositions) {
				if (o instanceof ACLMessage) {
					ACLMessage proposition = (ACLMessage) o;
					ACLMessage response = proposition.createReply();

					if (acceptances.size() > 0) {
						response.setPerformative(ACLMessage.REJECT_PROPOSAL);
						acceptances.add(response);
					} else {
						Offer incomingOffer = null;

						try {
							incomingOffer = (Offer) proposition.getContentObject();
						} catch (UnreadableException e1) {
							e1.printStackTrace();
						}

						if (incomingOffer.getStatus() == Status.ACCEPT) {
							response.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
						} else {
							Negotiation negotiation = negotiations.get(proposition.getSender());
							Offer counterOffer = negotiation.createCounterOffer(incomingOffer);
							switch (counterOffer.getStatus()) {
							case ACCEPT:
								response.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
								acceptances.addElement(response);
								break;
							case REFUSE:
								response.setPerformative(ACLMessage.REJECT_PROPOSAL);
								newIteration.add(response);
								break;
							case COUNTEROFFER:
								response.setPerformative(ACLMessage.CFP);
								try {
									response.setContentObject(counterOffer);
								} catch (IOException e) {
									e.printStackTrace();
								}
								newIteration.add(response);
								break;
							}

						}
					}
				}
			}

			if (acceptances.size() == 0) {
				newIteration(newIteration);
			}
		} else if (propositions.size() == 1) {
			ACLMessage proposition = (ACLMessage) propositions.get(0);
			ACLMessage acceptance = proposition.createReply();
			acceptance.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
			acceptances.add(acceptance);
		}
	}

	@Override
	protected void handleInform(ACLMessage inform) {
		try {
			Offer offer = (Offer) inform.getContentObject();
			offer.setRetailerId(inform.getSender());
			homeAgent.setNegotiatedOffer(period, offer);
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}
}
