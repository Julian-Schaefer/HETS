package org.cos30018.hets.logic.home.behaviour;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.cos30018.hets.logic.home.HomeAgent;
import org.cos30018.hets.negotiation.Negotiation;
import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.negotiation.Offer.Status;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.utility.OfferUtility;

import jade.core.AID;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import jade.proto.ContractNetInitiator;

public class HomeAgentNegotiationBehaviour extends ContractNetInitiator {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422652535325745455L;

	private final int period;

	private OfferUtility utility;

	public static HomeAgentNegotiationBehaviour create(HomeAgent homeAgent) {
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

		return new HomeAgentNegotiationBehaviour(homeAgent, period, msg);
	}

	private HomeAgent homeAgent;

	private Map<ACLMessage, Offer> acceptedOfferMessages = new HashMap<>();

	private HomeAgentNegotiationBehaviour(HomeAgent homeAgent, int period, ACLMessage msg) {
		super(homeAgent, msg);
		this.homeAgent = homeAgent;
		this.period = period;

		initNegotiation();
	}

	private Map<AID, Negotiation> negotiations = new HashMap<>();

	private void initNegotiation() {
		utility = new OfferUtility(0, homeAgent.getNegotiationStrategy().getReservationValue(), 1, 0);

		for (AID retailerAID : homeAgent.getRetailers()) {
			try {
				Strategy strategy = (Strategy) homeAgent.getNegotiationStrategy().clone();
				strategy.reset(0);

				Negotiation negotiation = new Negotiation(strategy, utility);
				negotiations.put(retailerAID, negotiation);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void handleAllResponses(Vector responses, Vector acceptances) {
		List<ACLMessage> propositions = getPropositions(responses);

		if (propositions.size() > 1) {
			Map<ACLMessage, Offer> newIterationMessages = new HashMap<>();
			boolean counterOfferMade = false;

			for (Object o : propositions) {
				if (o instanceof ACLMessage) {
					ACLMessage proposition = (ACLMessage) o;
					ACLMessage response = proposition.createReply();

					Offer incomingOffer = null;

					try {
						incomingOffer = (Offer) proposition.getContentObject();
					} catch (UnreadableException e) {
						e.printStackTrace();
					}

					if (incomingOffer.getStatus() == Status.ACCEPT) {
						response.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
						acceptedOfferMessages.put(response, incomingOffer);
					} else {
						Negotiation negotiation = negotiations.get(proposition.getSender());
						Offer counterOffer = negotiation.createCounterOffer(incomingOffer);
						switch (counterOffer.getStatus()) {
						case ACCEPT:
							response.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
							acceptedOfferMessages.put(response, counterOffer);
							break;
						case REFUSE:
							response.setPerformative(ACLMessage.REJECT_PROPOSAL);
							newIterationMessages.put(response, incomingOffer);
							break;
						case COUNTEROFFER:
							counterOfferMade = true;
							response.setPerformative(ACLMessage.CFP);
							try {
								response.setContentObject(counterOffer);
							} catch (IOException e) {
								e.printStackTrace();
							}
							newIterationMessages.put(response, incomingOffer);
							break;
						}
					}
				}
			}

			if (newIterationMessages.size() > 0) {
				if (counterOfferMade) {
					newIteration(new Vector(newIterationMessages.keySet()));
				} else {
					for (Map.Entry<ACLMessage, Offer> newIterationMessage : newIterationMessages.entrySet()) {
						acceptedOfferMessages.put(newIterationMessage.getKey(), newIterationMessage.getValue());
					}

					for (ACLMessage response : calculateAcceptances()) {
						acceptances.add(response);
					}
				}
			} else if (acceptedOfferMessages.size() > 0) {
				for (ACLMessage response : calculateAcceptances()) {
					acceptances.add(response);
				}
			}
		} else if (propositions.size() == 1) {
			ACLMessage proposition = (ACLMessage) propositions.get(0);
			ACLMessage acceptance = proposition.createReply();
			acceptance.setPerformative(ACLMessage.ACCEPT_PROPOSAL);

			try {
				Offer incomingOffer = (Offer) proposition.getContentObject();
				acceptedOfferMessages.put(acceptance, incomingOffer);
			} catch (UnreadableException e) {
				e.printStackTrace();
			}

			for (ACLMessage response : calculateAcceptances()) {
				acceptances.add(response);
			}
		}
	}

	private List<ACLMessage> calculateAcceptances() {
		List<ACLMessage> acceptances = new ArrayList<>();

		Map.Entry<ACLMessage, Offer> bestAcceptedOfferMessage = acceptedOfferMessages.entrySet().iterator().next();

		for (Map.Entry<ACLMessage, Offer> acceptedOfferMessage : acceptedOfferMessages.entrySet()) {
			Offer offer = acceptedOfferMessage.getValue();
			Offer bestOffer = bestAcceptedOfferMessage.getValue();
			if (utility.getUtility(offer) > utility.getUtility(bestOffer)) {
				bestAcceptedOfferMessage = acceptedOfferMessage;
			}
		}

		for (Map.Entry<ACLMessage, Offer> acceptedOfferMessage : acceptedOfferMessages.entrySet()) {
			ACLMessage message = acceptedOfferMessage.getKey();
			try {
				message.setContentObject(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (message == bestAcceptedOfferMessage.getKey()) {
				message.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
			} else {
				message.setPerformative(ACLMessage.REJECT_PROPOSAL);
			}

			acceptances.add(message);
		}

		return acceptances;
	}

	private List<ACLMessage> getPropositions(@SuppressWarnings("rawtypes") Vector responses) {
		List<ACLMessage> propositions = new ArrayList<>();
		for (Object o : responses) {
			if (o instanceof ACLMessage) {
				ACLMessage response = (ACLMessage) o;
				if (response.getPerformative() == ACLMessage.PROPOSE) {
					propositions.add(response);
				}
			}
		}

		return propositions;
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
