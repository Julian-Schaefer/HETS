package org.cos30018.hets.ui.custom;

import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
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
		setBackground(Color.WHITE);
		setUI(new StyledJPanelUI());
		setPreferredSize(getPreferredSize());
	}
	
	private void setup() {
		JPanel container = new JPanel(new BorderLayout());
		container.setBackground(Color.WHITE);
		container.setBorder(new EmptyBorder(14, 14, 14, 14));
		
		JLabel nameLbl = new JLabel(aid.getLocalName());
		nameLbl.setFont(new Font("Raleway", Font.BOLD, 14));
		container.add(nameLbl, BorderLayout.CENTER);

		add(container);
		
		JPanel bottom = new JPanel();
		bottom.setBackground(Color.WHITE);
		
		showDetailsButton = new JButton("Show Details");
		showDetailsButton.setFont(new Font("Raleway", Font.BOLD, 14));
		showDetailsButton.setBackground(new Color(0x2dce98));
		showDetailsButton.setForeground(Color.white);
		showDetailsButton.setUI(new StyledButtonUI());
		showDetailsButton.addActionListener(showDetailsButtonActionListener);
		bottom.add(showDetailsButton);

		deleteButton = new JButton(new ImageIcon(getClass().getResource("/images/delete_outline_18dp.png")));
		deleteButton.setBackground(Color.red);
		deleteButton.setForeground(Color.white);
		deleteButton.setUI(new StyledButtonUI());
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
