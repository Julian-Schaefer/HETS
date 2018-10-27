package org.cos30018.hets.ui.home.dashboard;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.util.NumberUtil;

public class RetailerInformationPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6902463155733376886L;

	private Home home;

	private JLabel nextNegotiatedPriceLbl;
	private JLabel nextAnticipatedTotalPriceLbl;
	private JLabel nextSelectedRetailerLbl;

	private JLabel currentNegotiatedPriceLbl;
	private JLabel currentAnticipatedTotalPriceLbl;
	private JLabel currentSelectedRetailerLbl;
	private JLabel currentActualTotalPriceLbl;

	public RetailerInformationPanel(Home home) {
		this.home = home;
		setLayout(new GridLayout(2, 1));
		setUp();
		update();
	}

	private void setUp() {
		add(getNextPeriodPanel());
		add(getCurrentPeriodPanel());
	}

	private JPanel getNextPeriodPanel() {
		JPanel nextPeriod = new JPanel(new BorderLayout());
		JLabel nextPeriodLbl = new JLabel("Next period:");
		nextPeriodLbl.setHorizontalAlignment(JLabel.CENTER);
		nextPeriod.add(nextPeriodLbl, BorderLayout.NORTH);

		JPanel nextPeriodGrid = new JPanel(new GridLayout(3, 2));

		JLabel nextNegotiatedPriceTextLbl = new JLabel("Negotiated Price:");
		addCenteredLabel(nextPeriodGrid, nextNegotiatedPriceTextLbl);

		nextNegotiatedPriceLbl = new JLabel();
		addCenteredLabel(nextPeriodGrid, nextNegotiatedPriceLbl);

		JLabel nextTotalPriceTextLbl = new JLabel("Anticipated total price:");
		addCenteredLabel(nextPeriodGrid, nextTotalPriceTextLbl);

		nextAnticipatedTotalPriceLbl = new JLabel();
		addCenteredLabel(nextPeriodGrid, nextAnticipatedTotalPriceLbl);

		JLabel nextSelectedRetailerTextLbl = new JLabel("Selected Retailer:");
		addCenteredLabel(nextPeriodGrid, nextSelectedRetailerTextLbl);

		nextSelectedRetailerLbl = new JLabel();
		addCenteredLabel(nextPeriodGrid, nextSelectedRetailerLbl);

		nextPeriod.add(nextPeriodGrid, BorderLayout.CENTER);

		return nextPeriod;
	}

	private JPanel getCurrentPeriodPanel() {
		JPanel currentPeriod = new JPanel(new BorderLayout());
		JLabel currentPeriodLbl = new JLabel("Current period:");
		currentPeriodLbl.setHorizontalAlignment(JLabel.CENTER);
		currentPeriod.add(currentPeriodLbl, BorderLayout.NORTH);

		JPanel currentPeriodGrid = new JPanel(new GridLayout(4, 2));

		JLabel currentNegotiatedPriceTextLbl = new JLabel("Negotiated Price:");
		addCenteredLabel(currentPeriodGrid, currentNegotiatedPriceTextLbl);

		currentNegotiatedPriceLbl = new JLabel("-");
		addCenteredLabel(currentPeriodGrid, currentNegotiatedPriceLbl);

		JLabel currentAnticipatedTotalPriceTextLbl = new JLabel("Anticipated total price:");
		addCenteredLabel(currentPeriodGrid, currentAnticipatedTotalPriceTextLbl);

		currentAnticipatedTotalPriceLbl = new JLabel("-");
		addCenteredLabel(currentPeriodGrid, currentAnticipatedTotalPriceLbl);

		JLabel currentSelectedRetailerTextLbl = new JLabel("Selected retailer: ");
		addCenteredLabel(currentPeriodGrid, currentSelectedRetailerTextLbl);

		currentSelectedRetailerLbl = new JLabel("-");
		addCenteredLabel(currentPeriodGrid, currentSelectedRetailerLbl);

		JLabel currentActualTotalPriceTextLbl = new JLabel("Actual total price:");
		addCenteredLabel(currentPeriodGrid, currentActualTotalPriceTextLbl);

		currentActualTotalPriceLbl = new JLabel("-");
		addCenteredLabel(currentPeriodGrid, currentActualTotalPriceLbl);

		currentPeriod.add(currentPeriodGrid, BorderLayout.CENTER);

		return currentPeriod;
	}

	private void addCenteredLabel(JPanel panel, JLabel label) {
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
	}

	public void update() {
		if (home.getNegotiatedOffers().containsKey(home.getNextPeriod())) {
			Offer nextNegotiatedOffer = home.getNegotiatedOffers().get(home.getNextPeriod());
			nextNegotiatedPriceLbl.setText(NumberUtil.toString(nextNegotiatedOffer.getPrice()));
			nextAnticipatedTotalPriceLbl.setText(NumberUtil
					.toString(home.getTotalUsageForecast(home.getNextPeriod()) * nextNegotiatedOffer.getPrice()));
			nextSelectedRetailerLbl.setText(nextNegotiatedOffer.getRetailerId().getLocalName());
		}

		if (home.getNegotiatedOffers().containsKey(home.getCurrentPeriod())) {
			Offer currentNegotiatedOffer = home.getNegotiatedOffers().get(home.getCurrentPeriod());
			currentNegotiatedPriceLbl.setText(NumberUtil.toString(currentNegotiatedOffer.getPrice()));
			currentAnticipatedTotalPriceLbl.setText(NumberUtil
					.toString(home.getTotalUsageForecast(home.getCurrentPeriod()) * currentNegotiatedOffer.getPrice()));
			currentSelectedRetailerLbl.setText(currentNegotiatedOffer.getRetailerId().getLocalName());
		}
	}

}