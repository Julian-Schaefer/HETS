package ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class AgentPanel extends JPanel {

	private String name;
	
	public AgentPanel(String name) {
		this.name = name;
		setup();
	}
	
	private void setup() {
		JPanel container = new JPanel(new BorderLayout());
		container.setBorder(new EmptyBorder(14, 14, 14, 14));
		
		JLabel nameLbl = new JLabel(name);
		container.add(nameLbl, BorderLayout.CENTER);

		JButton openBtn = new JButton("Show Agent");
		container.add(openBtn, BorderLayout.SOUTH);
		
		add(container);
	}
	
	
}
