package org.cos30018.hets.ui.retailer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.retailer.Retailer;

import jade.core.AID;

public class RetailerDetailsWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7525774958428721478L;
	
	private Retailer retailer;
	
	public RetailerDetailsWindow(AID aid) {
		super("Retailer: " + aid.getLocalName());
		this.retailer = JadeController.getInstance().getRetailer(aid);
		setLayout(new BorderLayout());
		setSize(new Dimension(600, 400));
		setLocationRelativeTo(null);
		setUp();
		setVisible(true);
	}
	
	private void setUp() {
		JPanel content = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel negotiationStrategyTitleLbl = new JLabel("Negotiation Strategy: ");
		content.add(negotiationStrategyTitleLbl, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		JLabel negotiationStrategyLbl = new JLabel(retailer.getNegotiationStrategy().name());
		content.add(negotiationStrategyLbl, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		JLabel pricingStrategyTitleLbl = new JLabel("Pricing Strategy: ");
		content.add(pricingStrategyTitleLbl, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		JLabel pricingStrategyLbl = new JLabel(retailer.getPricingStrategy().name());
		content.add(pricingStrategyLbl, gbc);
		
		add(content, BorderLayout.CENTER);
	}
}
