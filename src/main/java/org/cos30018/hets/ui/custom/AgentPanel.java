package org.cos30018.hets.ui.custom;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import jade.core.AID;

public class AgentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4879082512248930600L;

	private AID aid;
	
	private JButton showDetailsButton;
	private JButton deleteButton;
	
	private AgentPanelListener agentPanelListener;
	
	public AgentPanel(AID aid) {
		this.aid = aid;
		setup();
	}
	
	private void setup() {
		JPanel container = new JPanel(new BorderLayout());
		container.setBorder(new EmptyBorder(14, 14, 14, 14));
		
		JLabel nameLbl = new JLabel(aid.getLocalName());
		container.add(nameLbl, BorderLayout.CENTER);

		add(container);
		
		JPanel bottom = new JPanel();
		
		showDetailsButton = new JButton("Show Details");
		showDetailsButton.addActionListener(showDetailsButtonActionListener);
		bottom.add(showDetailsButton);

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(deleteButtonActionListener);
		bottom.add(deleteButton);		

		container.add(bottom, BorderLayout.SOUTH);
		add(container);
	}

	private ActionListener showDetailsButtonActionListener = (e) -> {
		if(agentPanelListener != null) agentPanelListener.onShowDetailsClicked(aid);
	};
	
	private ActionListener deleteButtonActionListener = (e) -> {
		if(agentPanelListener != null) agentPanelListener.onDeleteClicked(aid);
	};
	
	public void setAgentPanelListener(AgentPanelListener agentPanelListener) {
		this.agentPanelListener = agentPanelListener;
	}
	
	public interface AgentPanelListener {
		void onShowDetailsClicked(AID aid);
		void onDeleteClicked(AID aid);
	}
}
