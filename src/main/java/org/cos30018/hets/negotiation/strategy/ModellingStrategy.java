package org.cos30018.hets.negotiation.strategy;

import org.cos30018.hets.negotiation.Offer;

public class ModellingStrategy extends Strategy {

	private int deadline;

	private Offer lastIncomingOffer;
	private Offer lastCounterOffer;

	public ModellingStrategy(int deadline, double reservationValue) {
		super(reservationValue);
		this.deadline = deadline;
		this.reservationValue = reservationValue;
	}

	@Override
	public double getNewValue() {
		// TODO Auto-generated method stub
		round++;
		return 0;
	}
//
//	@Override
//	public Offer getCounterOffer(Offer incomingOffer) {
//		if (round == deadline) {
//			return Offer.refuse();
//		}
//
//		Offer counterOffer = incomingOffer.createCounterOffer();
//		if (lastCounterOffer == null) {
//			counterOffer.setPrice(initialValue);
//		} else {
//			double lastOfferedPrice = lastCounterOffer.getPrice();
//			double incomingOfferPrice = incomingOffer.getPrice();
//			if (incomingOfferPrice < lastOfferedPrice) {
//				double lastIncomingOfferPrice = lastIncomingOffer.getPrice();
//				if (incomingOfferPrice > lastIncomingOfferPrice) {
//					double newOfferPrice = lastOfferedPrice - (incomingOfferPrice - lastIncomingOfferPrice);
//					if (newOfferPrice >= reservationValue) {
//						counterOffer.setPrice(incomingOfferPrice - lastIncomingOfferPrice);
//					} else {
//						counterOffer = Offer.refuse();
//					}
//				} else {
//					counterOffer = Offer.refuse();
//				}
//			}
//		}
//
//		lastIncomingOffer = incomingOffer;
//		lastCounterOffer = counterOffer;
//
//		round++;
//
//		return counterOffer;
//	}

	@Override
	public void reset(double initialPrice) {
		super.reset(initialPrice);
		lastCounterOffer = null;
		lastIncomingOffer = null;
	}

	@Override
	public String getName() {
		return Strategy.STRATEGY_MODELLING;
	}
}
