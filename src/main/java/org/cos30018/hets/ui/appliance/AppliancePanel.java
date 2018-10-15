package org.cos30018.hets.ui.appliance;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.cos30018.hets.ui.custom.AgentPanel;
import org.cos30018.hets.ui.custom.JPanelList;

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
	private JPanelList panelList;
	
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
		
		panelList = new JPanelList();
        
		scrollPane = new JScrollPane(panelList);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void addApplianceAgent(AID aid) {			
		AgentPanel agentPanel = new AgentPanel(aid);
		agentPanel.setAgentPanelListener(controller);
		agentPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
        
		agentPanelForAID.put(aid, panelList.addJPanel(agentPanel));
	}
	
	public void removeApplianceAgent(AID aid) {
		panelList.remove(agentPanelForAID.get(aid));
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
