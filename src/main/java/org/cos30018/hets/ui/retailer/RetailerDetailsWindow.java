package org.cos30018.hets.ui.retailer;

import java.awt.BorderLayout;
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
import org.cos30018.hets.ui.custom.StrategyConfigurationPanel;
import org.cos30018.hets.ui.custom.TariffConfigurationPanel;
import org.cos30018.hets.ui.custom.graph.OfferAndCounterOfferGraph;
import org.cos30018.hets.util.GuiUtil;

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
		setSize(new Dimension(1150, 700));
		setLocationRelativeTo(null);
		setUp();
		update();
		new RetailerDetailsWindowController(this, retailer);
		setVisible(true);
	}

	private void setUp() {
		JPanel informationPanel = new JPanel(new GridLayout(3, 1, 10, 10));
		informationPanel.setPreferredSize(new Dimension(460, 0));
		informationPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		StrategyConfigurationPanel sellingStrategyConfigurationPanel = new StrategyConfigurationPanel(
				retailer.getSellingStrategy(), false, false);
		informationPanel.add(GuiUtil.getCardPanel("Selling Strategy", sellingStrategyConfigurationPanel));

		StrategyConfigurationPanel buyingStrategyConfigurationPanel = new StrategyConfigurationPanel(
				retailer.getBuyingStrategy(), false, false);
		informationPanel.add(GuiUtil.getCardPanel("Buying Strategy", buyingStrategyConfigurationPanel));

		TariffConfigurationPanel tariffConfigurationPanel = new TariffConfigurationPanel(retailer.getTariff(), false);
		informationPanel.add(GuiUtil.getCardPanel("Tariff", tariffConfigurationPanel));

		add(informationPanel, BorderLayout.WEST);

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
		negotiationMessagesList.invalidate();
		negotiationMessagesList.repaint();

		offerAndCounterOfferGraph.setIncomingOffers(retailer.getIncomingOffers());
		offerAndCounterOfferGraph.setOutgoingOffers(retailer.getOutgoingOffers());
		offerAndCounterOfferGraph.update();
	}
}
