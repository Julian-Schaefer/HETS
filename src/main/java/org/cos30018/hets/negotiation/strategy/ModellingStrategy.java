package org.cos30018.hets.negotiation.strategy;

import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.negotiation.tariff.Tariff;

public class ModellingStrategy extends Strategy {

	private int deadline;
	private double reservationValue;

	private Offer lastIncomingOffer;
	private Offer lastCounterOffer;

	private int currentRound;

	public ModellingStrategy(Tariff tariff, int deadline, double reservationValue) {
		super(tariff);
		this.deadline = deadline;
		this.reservationValue = reservationValue;
	}

	@Override
	public Offer getCounterOffer(Offer incomingOffer) {
		if (currentRound == deadline) {
			return Offer.refuse();
		}

		Offer counterOffer = incomingOffer.createCounterOffer();
		if (lastCounterOffer == null) {
			counterOffer.setPrice(tariff.getPrice(incomingOffer.getAmount(), incomingOffer.getStartPeriod()));
		} else {
			double lastOfferedPrice = lastCounterOffer.getPrice();
			double incomingOfferPrice = incomingOffer.getPrice();
			if (incomingOfferPrice < lastOfferedPrice) {
				double lastIncomingOfferPrice = lastIncomingOffer.getPrice();
				if (incomingOfferPrice > lastIncomingOfferPrice) {
					double newOfferPrice = lastOfferedPrice - (incomingOfferPrice - lastIncomingOfferPrice);
					if (newOfferPrice >= reservationValue) {
						counterOffer.setPrice(incomingOfferPrice - lastIncomingOfferPrice);
					} else {
						counterOffer = Offer.refuse();
					}
				} else {
					counterOffer = Offer.refuse();
				}
			}
		}

		lastIncomingOffer = incomingOffer;
		lastCounterOffer = counterOffer;

		currentRound++;

		return counterOffer;
	}

	@Override
	public void reset() {
		currentRound = 0;
		lastCounterOffer = null;
		lastIncomingOffer = null;
	}
}
