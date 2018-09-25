package org.cos30018.hets.ui.custom;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AgentPanel extends JPanel {

	private String name;
	
	private JButton showButton;
	
	public AgentPanel(String name) {
		this.name = name;
		setup();
	}
	
	private void setup() {
		JPanel container = new JPanel(new BorderLayout());
		container.setBorder(new EmptyBorder(14, 14, 14, 14));
		
		JLabel nameLbl = new JLabel(name);
		container.add(nameLbl, BorderLayout.CENTER);

		showButton = new JButton("Show Agent");
		container.add(showButton, BorderLayout.SOUTH);
		
		add(container);
	}
	
	public void addShowAgentClickListener(ActionListener listener) {
		showButton.addActionListener(listener);
	}
}
