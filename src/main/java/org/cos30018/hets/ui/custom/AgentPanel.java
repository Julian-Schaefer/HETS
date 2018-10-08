package org.cos30018.hets.ui.custom;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AgentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4879082512248930600L;

	private String name;
	
	private JButton showButton;
	private JButton deleteButton;
	
	public AgentPanel(String name) {
		this.name = name;
		setup();
	}
	
	private void setup() {
		JPanel container = new JPanel(new BorderLayout());
		container.setBorder(new EmptyBorder(14, 14, 14, 14));
		
		JLabel nameLbl = new JLabel(name);
		container.add(nameLbl, BorderLayout.CENTER);

		add(container);
		
		JPanel bottom = new JPanel();
		
		showButton = new JButton("Show Details");
		bottom.add(showButton);

		deleteButton = new JButton("Delete");
		bottom.add(deleteButton);		

		add(bottom, BorderLayout.SOUTH);
	}
	
	public void addShowAgentClickListener(ActionListener listener) {
		showButton.addActionListener(listener);
	}
}
