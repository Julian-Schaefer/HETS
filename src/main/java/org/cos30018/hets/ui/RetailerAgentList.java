package org.cos30018.hets.ui;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.cos30018.hets.logic.retailer.RetailerAgent;
import org.cos30018.hets.ui.custom.AgentPanel;

public class RetailerAgentList extends JPanel {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8816403958031695286L;

	public RetailerAgentList() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		addRetailerAgent(new RetailerAgent());
		addRetailerAgent(new RetailerAgent());
		addRetailerAgent(new RetailerAgent());
		addRetailerAgent(new RetailerAgent());
		addRetailerAgent(new RetailerAgent());
	}
	
	public void addRetailerAgent(RetailerAgent agent) {
		JPanel container = new JPanel();
		container.setBorder(new EmptyBorder(14, 14, 14, 14));
		
		//AgentPanel agentPanel = new AgentPanel(agent.getAID());
		//agentPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
		//container.add(agentPanel);
		
		add(container);
	}
	
}
