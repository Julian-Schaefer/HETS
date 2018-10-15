package org.cos30018.hets.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.cos30018.hets.ui.appliance.AppliancePanel;
import org.cos30018.hets.ui.appliance.AppliancePanelController;

public class HomeAgentWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4924347209446756399L;

	private AppliancePanel appliancePanel;
	private JScrollPane rightScrollPane;
	
	public HomeAgentWindow() {
		super("HETS");
        setSize(1280, 600);
		setLayout(new BorderLayout());
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUp();
		setVisible(true);
	}
	
	private void setUp() {
		appliancePanel = new AppliancePanel();
		new AppliancePanelController(appliancePanel);
		add(appliancePanel, BorderLayout.WEST);
		
		rightScrollPane = new JScrollPane(new RetailerAgentList(), 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(rightScrollPane, BorderLayout.EAST);
	}
	
	public AppliancePanel getAppliancePanel() {
		return appliancePanel;
	}	
}
