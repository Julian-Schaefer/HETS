package org.cos30018.hets.ui.appliance;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.cos30018.hets.ui.custom.AgentPanel;

import jade.core.AID;

public class AppliancePanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8249612384152137579L;

	private AppliancePanelController controller;
	private AppliancePanelListener listener;
	
	private JButton addApplianceButton;
	private JScrollPane scrollPane;
	private JPanel listPanel;
	
	private Map<AID, JPanel> agentPanelForAID = new HashMap<>();
	
	public AppliancePanel() {
		setLayout(new BorderLayout());
		setup();
		this.controller = new AppliancePanelController(this);
	}
	
	private void setup() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		addApplianceButton = new JButton("Add Appliance");
		addApplianceButton.addActionListener(this);
		buttonPanel.add(addApplianceButton);
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		add(buttonPanel, BorderLayout.NORTH);
		
		listPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        listPanel.add(new JPanel(), gbc);
        
		scrollPane = new JScrollPane(listPanel);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void addApplianceAgent(AID aid) {	
		JPanel container = new JPanel();
		container.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		AgentPanel agentPanel = new AgentPanel(aid);
		agentPanel.setAgentPanelListener(controller);
		agentPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
		container.add(agentPanel);		
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;   
        
        listPanel.add(container, gbc, 0);
		agentPanelForAID.put(aid, container);
		
		updateUI();
	}
	
	public void removeApplianceAgent(AID aid) {
		listPanel.remove(agentPanelForAID.get(aid));
		agentPanelForAID.remove(aid);
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
	}
}
