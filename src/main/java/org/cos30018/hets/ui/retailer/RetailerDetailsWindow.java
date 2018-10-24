package org.cos30018.hets.ui.retailer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.retailer.Retailer;
import org.cos30018.hets.ui.custom.graph.OfferAndCounterOfferGraph;

import jade.core.AID;

public class RetailerDetailsWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7525774958428721478L;

	private Retailer retailer;

	private OfferAndCounterOfferGraph offerAndCounterOfferGraph;
	private JList<String> negotiationMessagesList;

	public RetailerDetailsWindow(AID aid) {
		super("Retailer: " + aid.getLocalName());
		this.retailer = JadeController.getInstance().getRetailer(aid);
		setLayout(new BorderLayout());
		setSize(new Dimension(600, 700));
		setLocationRelativeTo(null);
		setUp();
		update();
		new RetailerDetailsWindowController(this, retailer);
		setVisible(true);
	}

	private void setUp() {
		JPanel informationPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		informationPanel.setBackground(Color.WHITE);
		informationPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		JLabel negotiationStrategyTitleLbl = new JLabel("Negotiation Strategy: ");
		negotiationStrategyTitleLbl.setHorizontalAlignment(JLabel.CENTER);
		informationPanel.add(negotiationStrategyTitleLbl);

		JLabel negotiationStrategyLbl = new JLabel(retailer.getStrategy().getName());
		negotiationStrategyLbl.setHorizontalAlignment(JLabel.CENTER);
		informationPanel.add(negotiationStrategyLbl);

		JLabel pricingStrategyTitleLbl = new JLabel("Pricing Strategy: ");
		pricingStrategyTitleLbl.setHorizontalAlignment(JLabel.CENTER);
		informationPanel.add(pricingStrategyTitleLbl);

		JLabel pricingStrategyLbl = new JLabel(retailer.getTariff().getName());
		pricingStrategyLbl.setHorizontalAlignment(JLabel.CENTER);
		informationPanel.add(pricingStrategyLbl);

		add(informationPanel, BorderLayout.NORTH);

		JPanel centerPanel = new JPanel(new GridLayout(2, 1, 0, 20));

		offerAndCounterOfferGraph = new OfferAndCounterOfferGraph();
		centerPanel.add(offerAndCounterOfferGraph);

		JPanel negotiationMessagesPanel = new JPanel(new BorderLayout());
		JLabel interchangedMessagesLbl = new JLabel("Interchanged Negotiation Messages");
		interchangedMessagesLbl.setHorizontalAlignment(JLabel.CENTER);
		negotiationMessagesPanel.add(interchangedMessagesLbl, BorderLayout.NORTH);
		negotiationMessagesList = new JList<>();
		JScrollPane listScrollPane = new JScrollPane(negotiationMessagesList);
		negotiationMessagesPanel.add(listScrollPane, BorderLayout.CENTER);
		centerPanel.add(negotiationMessagesPanel);

		add(centerPanel, BorderLayout.CENTER);
	}

	public void update() {
		negotiationMessagesList.setListData(retailer.getNegotiationMessages().toArray(new String[] {}));
		negotiationMessagesList.updateUI();

		offerAndCounterOfferGraph.setIncomingOffers(retailer.getIncomingOffers());
		offerAndCounterOfferGraph.setOutgoingOffers(retailer.getOutgoingOffers());
		offerAndCounterOfferGraph.update();
	}
}
