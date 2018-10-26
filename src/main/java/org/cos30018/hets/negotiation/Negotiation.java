package org.cos30018.hets.negotiation;

import org.cos30018.hets.negotiation.Offer.Status;
import org.cos30018.hets.negotiation.strategy.DeadlineExceededException;
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
		try {
			Offer counterOffer = incomingOffer.createCounterOffer();
			if (lastOutgoingOffer == null) {
				counterOffer.setPrice(strategy.getNewValue());
			} else {
				double incomingOfferUtility = utility.getUtility(incomingOffer);
				double lastOutgoingOfferUtility = utility.getUtility(lastOutgoingOffer);
				if (incomingOfferUtility >= lastOutgoingOfferUtility) {
					counterOffer.setStatus(Status.ACCEPT);
				} else {
					counterOffer.setPrice(strategy.getNewValue());
				}
			}

			lastOutgoingOffer = counterOffer;
			return counterOffer;
		} catch (DeadlineExceededException e) {
			return Offer.refuse();
		}
	}

	public int getRound() {
		return strategy.getRound();
	}
}
