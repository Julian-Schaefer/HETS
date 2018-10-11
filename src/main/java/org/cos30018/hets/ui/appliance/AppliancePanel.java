package org.cos30018.hets.ui.appliance;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.cos30018.hets.ui.custom.AgentPanel;

public class AppliancePanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8249612384152137579L;

	private AppliancePanelListener listener;
	
	private JButton addApplianceButton;
	private JScrollPane scrollPane;
	private JPanel listPanel;
	
	public AppliancePanel() {
		setLayout(new BorderLayout());
		setup();
		setBorder(new EmptyBorder(10, 10, 10, 10));
		new AppliancePanelController(this);
	}
	
	private void setup() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		addApplianceButton = new JButton("Add Appliance");
		addApplianceButton.addActionListener(this);
		buttonPanel.add(addApplianceButton);
		
		add(buttonPanel, BorderLayout.NORTH);
		
		listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

		scrollPane = new JScrollPane(listPanel);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void addApplianceAgent(String name) {
		JPanel container = new JPanel();
		container.setBorder(new EmptyBorder(14, 14, 14, 14));
		
		AgentPanel agentPanel = new AgentPanel(name);
		agentPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
		container.add(agentPanel);
		
		listPanel.add(container);

		updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(listener != null) listener.onApplianceAddButtonClick();
	}
	
	public void setAppliancePanelListener(AppliancePanelListener listener) {
		this.listener = listener;
	}
	
	public interface AppliancePanelListener {
		void onApplianceAddButtonClick();
		void onShowAgentClicked(String name);
	}
}
