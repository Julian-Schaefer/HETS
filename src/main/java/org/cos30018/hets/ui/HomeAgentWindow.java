package org.cos30018.hets.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import org.cos30018.hets.ui.appliance.AppliancePanel;

public class HomeAgentWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4924347209446756399L;

	private AppliancePanel appliancePanel;
	private JScrollPane rightScrollPane;
	
	public HomeAgentWindow() {
		super("HETS");
		setSize(1200, 700);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
        setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUp();
		setVisible(true);
	}
	
	private void setUp() {
		appliancePanel = new AppliancePanel();
		appliancePanel.setPreferredSize(new Dimension(350, 0));
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
