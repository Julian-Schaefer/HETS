package org.cos30018.hets.ui.home.dashboard;

import java.awt.*;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.negotiation.Offer;
import org.cos30018.hets.ui.custom.button.StyledInfoCardUI;

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
		JPanel nextPeriodBase = new JPanel(new BorderLayout());
		nextPeriodBase.setBackground(Color.WHITE);
		nextPeriodBase.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel nextPeriod = new JPanel(new BorderLayout());
		nextPeriod.setUI(new StyledInfoCardUI());

		JLabel nextPeriodLbl = new JLabel("Next period:");
		nextPeriodLbl.setForeground((new Color(0x2dce98)));
		nextPeriodLbl.setFont(new Font("Raleway", Font.BOLD, 14));
		nextPeriodLbl.setHorizontalAlignment(JLabel.CENTER);
		nextPeriod.add(nextPeriodLbl, BorderLayout.NORTH);

		JPanel nextPeriodGrid = new JPanel(new GridLayout(6, 1));
		nextPeriodGrid.setBackground(Color.WHITE);

		JLabel nextNegotiatedPriceTextLbl = new JLabel("Negotiated Price:");
		nextNegotiatedPriceTextLbl.setFont(new Font("Raleway", Font.PLAIN, 12));
		addCenteredLabel(nextPeriodGrid, nextNegotiatedPriceTextLbl);

		nextNegotiatedPriceLbl = new JLabel();
		nextNegotiatedPriceLbl.setFont(new Font("Roboto", Font.BOLD, 12));
		addCenteredLabel(nextPeriodGrid, nextNegotiatedPriceLbl);

		JLabel nextTotalPriceTextLbl = new JLabel("Anticipated total price:");
		nextTotalPriceTextLbl.setFont(new Font("Raleway", Font.PLAIN, 12));
		addCenteredLabel(nextPeriodGrid, nextTotalPriceTextLbl);

		nextAnticipatedTotalPriceLbl = new JLabel();
		nextAnticipatedTotalPriceLbl.setFont(new Font("Roboto", Font.BOLD, 14));
		addCenteredLabel(nextPeriodGrid, nextAnticipatedTotalPriceLbl);

		JLabel nextSelectedRetailerTextLbl = new JLabel("Selected Retailer:");
		nextSelectedRetailerTextLbl.setFont(new Font("Raleway", Font.PLAIN, 12));
		addCenteredLabel(nextPeriodGrid, nextSelectedRetailerTextLbl);

		nextSelectedRetailerLbl = new JLabel();
		nextSelectedRetailerLbl.setFont(new Font("Roboto", Font.BOLD, 14));
		addCenteredLabel(nextPeriodGrid, nextSelectedRetailerLbl);

		nextPeriod.add(nextPeriodGrid, BorderLayout.CENTER);
		nextPeriodBase.add(nextPeriod);

		return nextPeriodBase;
	}

	private JPanel getCurrentPeriodPanel() {
		JPanel currentPeriodBase = new JPanel(new BorderLayout());
		currentPeriodBase.setBackground(Color.WHITE);
		currentPeriodBase.setBorder(new EmptyBorder(5, 5, 5, 5));
		JPanel currentPeriod = new JPanel(new BorderLayout());
		currentPeriod.setUI(new StyledInfoCardUI());
		currentPeriod.setBackground(Color.WHITE);

		JLabel currentPeriodLbl = new JLabel("Current period:");
		currentPeriodLbl.setForeground((new Color(0x2dce98)));
		currentPeriodLbl.setFont(new Font("Raleway", Font.BOLD, 14));

		currentPeriodLbl.setHorizontalAlignment(JLabel.CENTER);
		currentPeriod.add(currentPeriodLbl, BorderLayout.NORTH);

		JPanel currentPeriodGrid = new JPanel(new GridLayout(8, 1));
		currentPeriodGrid.setBackground(Color.WHITE);

		JLabel currentNegotiatedPriceTextLbl = new JLabel("Negotiated Price:");
		currentNegotiatedPriceTextLbl.setFont(new Font("Raleway", Font.PLAIN, 12));
		addCenteredLabel(currentPeriodGrid, currentNegotiatedPriceTextLbl);

		currentNegotiatedPriceLbl = new JLabel("-");
		currentNegotiatedPriceLbl.setFont(new Font("Roboto", Font.BOLD, 12));
		addCenteredLabel(currentPeriodGrid, currentNegotiatedPriceLbl);

		JLabel currentAnticipatedTotalPriceTextLbl = new JLabel("Anticipated total price:");
		currentAnticipatedTotalPriceTextLbl.setFont(new Font("Raleway", Font.PLAIN, 12));
		addCenteredLabel(currentPeriodGrid, currentAnticipatedTotalPriceTextLbl);

		currentAnticipatedTotalPriceLbl = new JLabel("-");
		currentAnticipatedTotalPriceLbl.setFont(new Font("Roboto", Font.BOLD, 12));
		addCenteredLabel(currentPeriodGrid, currentAnticipatedTotalPriceLbl);

		JLabel currentSelectedRetailerTextLbl = new JLabel("Selected retailer: ");
		currentSelectedRetailerTextLbl.setFont(new Font("Raleway", Font.PLAIN, 12));
		addCenteredLabel(currentPeriodGrid, currentSelectedRetailerTextLbl);

		currentSelectedRetailerLbl = new JLabel("-");
		currentSelectedRetailerLbl.setFont(new Font("Roboto", Font.BOLD, 12));
		addCenteredLabel(currentPeriodGrid, currentSelectedRetailerLbl);

		JLabel currentActualTotalPriceTextLbl = new JLabel("Actual total price:");
		currentActualTotalPriceTextLbl.setFont(new Font("Raleway", Font.PLAIN, 12));
		addCenteredLabel(currentPeriodGrid, currentActualTotalPriceTextLbl);

		currentActualTotalPriceLbl = new JLabel("-");
		currentActualTotalPriceLbl.setFont(new Font("Roboto", Font.BOLD, 12));
		addCenteredLabel(currentPeriodGrid, currentActualTotalPriceLbl);

		currentPeriod.add(currentPeriodGrid, BorderLayout.CENTER);
		currentPeriodBase.add(currentPeriod);

		return currentPeriodBase;
	}

	private void addCenteredLabel(JPanel panel, JLabel label) {
		label.setHorizontalAlignment(JLabel.CENTER);
		panel.add(label);
	}

	public void update() {
		if (home.getNegotiatedOffers().containsKey(home.getNextPeriod())) {
			Offer negotiatedOffer = home.getNegotiatedOffers().get(home.getNextPeriod());
			nextNegotiatedPriceLbl.setText(String.valueOf(negotiatedOffer.getPrice()));
			nextAnticipatedTotalPriceLbl.setText(
					String.valueOf(home.getTotalUsageForecast(home.getNextPeriod()) * negotiatedOffer.getPrice()));
			nextSelectedRetailerLbl.setText(String.valueOf(negotiatedOffer.getRetailerId().getLocalName()));
		}

		if (home.getNegotiatedOffers().containsKey(home.getCurrentPeriod())) {
			Offer negotiatedOffer = home.getNegotiatedOffers().get(home.getCurrentPeriod());
			currentNegotiatedPriceLbl.setText(String.valueOf(negotiatedOffer.getPrice()));
			currentAnticipatedTotalPriceLbl.setText(
					String.valueOf(home.getTotalUsageForecast(home.getCurrentPeriod()) * negotiatedOffer.getPrice()));
			currentSelectedRetailerLbl.setText(String.valueOf(negotiatedOffer.getRetailerId().getLocalName()));
		}
	}

}