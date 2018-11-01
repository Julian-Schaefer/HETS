package org.cos30018.hets.negotiation;

import org.cos30018.hets.negotiation.Offer.Status;
import org.cos30018.hets.negotiation.strategy.DeadlineExceededException;
import org.cos30018.hets.negotiation.strategy.RandomTitForTatStrategy;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.utility.OfferUtility;

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
