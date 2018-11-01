package org.cos30018.hets.negotiation;

import org.cos30018.hets.negotiation.Offer.Status;
import org.cos30018.hets.negotiation.strategy.DeadlineExceededException;
import org.cos30018.hets.negotiation.strategy.RandomTitForTatStrategy;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.utility.OfferUtility;

/**
 * This class represents a negotiation between two participants. It is a 1:1
 * negotiation and creates the counter offers based on the incoming offers.
 */
public class Negotiation {

	private Strategy strategy;
	private OfferUtility utility;

	private Offer lastOutgoingOffer;

	public Negotiation(Strategy strategy, OfferUtility utility) {
		this.strategy = strategy;
		this.utility = utility;
	}

	public Offer createCounterOffer(Offer incomingOffer) {
		return createCounterOffer(incomingOffer, 0.0);
	}

	/**
	 * This method creates a counter offer for an incoming offer and you can also
	 * pass some constraints. It uses the implenentation of utility to check if it
	 * still needs to create a counter offer. It also handles the
	 * DeadlineExceededException that can be thrown by strategies. If the deadline
	 * is exceeded, it returns a REFUSE offer
	 */
	public Offer createCounterOffer(Offer incomingOffer, double constraints) {
		try {
			Offer counterOffer = incomingOffer.createCounterOffer();
			if (lastOutgoingOffer == null) {
				counterOffer.setPrice(getNewValue(incomingOffer, constraints));
			} else {
				double incomingOfferUtility = utility.getUtility(incomingOffer);
				double lastOutgoingOfferUtility = utility.getUtility(lastOutgoingOffer);
				if (incomingOfferUtility >= lastOutgoingOfferUtility) {
					counterOffer.setStatus(Status.ACCEPT);
				} else {
					counterOffer.setPrice(getNewValue(incomingOffer, constraints));
				}
			}

			lastOutgoingOffer = counterOffer;
			return counterOffer;
		} catch (DeadlineExceededException e) {
			return Offer.refuse();
		}
	}

	/**
	 * This method is used to implement special constraints to the strategy that it
	 * needs to generate a new value. In this case, the RandomTitForTatStrategy need
	 * the incoming price to get a new value because it compares it to the last
	 * value before.
	 */
	private double getNewValue(Offer offer, double constraints) throws DeadlineExceededException {
		if (strategy instanceof RandomTitForTatStrategy) {
			return strategy.getNewValue(offer.getPrice());
		}

		return strategy.getNewValue(constraints);
	}

	public int getRound() {
		return strategy.getRound() + 1;
	}
}
