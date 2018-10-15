package org.cos30018.hets.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.cos30018.hets.ui.appliance.AppliancePanel;
import org.cos30018.hets.ui.home.HomePanel;
import org.cos30018.hets.ui.home.HomePanelController;
import org.cos30018.hets.ui.retailer.RetailerPanel;

public class HomeAgentWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4924347209446756399L;

	private AppliancePanel appliancePanel;
	private RetailerPanel retailerPanel;
	
	public HomeAgentWindow() {
		super("HETS");
		setSize(1200, 700);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUp();
		setVisible(true);
	}
	
	private void setUp() {
		appliancePanel = new AppliancePanel();
		appliancePanel.setPreferredSize(new Dimension(350, 0));
		add(appliancePanel, BorderLayout.WEST);
		
		HomePanel homePanel = new HomePanel();
		add(homePanel, BorderLayout.CENTER);
		new HomePanelController(homePanel);	

		retailerPanel = new RetailerPanel();
		retailerPanel.setPreferredSize(new Dimension(350, 0));
		add(retailerPanel, BorderLayout.EAST);
	}
	
	public AppliancePanel getAppliancePanel() {
		return appliancePanel;
	}
	
	public RetailerPanel getRetailerPanel() {
		return retailerPanel;
	}
}
