package org.cos30018.hets.ui.appliance;

import java.awt.*;
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
import org.cos30018.hets.ui.custom.StyledButtonUI;
import org.cos30018.hets.ui.custom.StyledJPanelUI;

public class AppliancePanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8249612384152137579L;

	private AppliancePanelController controller;
	private AppliancePanelListener listener;
	
	private JButton addApplianceButton;
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
		buttonPanel.setBackground(Color.WHITE);
		
		addApplianceButton = new JButton("Add Appliance");
		addApplianceButton.setFont(new Font("Raleway", Font.BOLD, 18));
		addApplianceButton.setBackground(new Color(0x2dce98));
		addApplianceButton.setForeground(Color.white);
		addApplianceButton.setUI(new StyledButtonUI());
		addApplianceButton.addActionListener(this);
		buttonPanel.add(addApplianceButton);
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		add(buttonPanel, BorderLayout.NORTH);

		panelList = new JPanelList();

		JScrollPane scrollPane = new JScrollPane(panelList);
		add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * Function to ADDING an Appliance Agent
	 * @param aid: appliance agent ID
	 */
	
	public void addApplianceAgent(AID aid) {			
		AgentPanel agentPanel = new AgentPanel(aid);
		agentPanel.setAgentPanelListener(controller);
        
		agentPanelForAID.put(aid, panelList.addJPanel(agentPanel));
	}

	/**
	 * Function to REMOVING an Appliance Agent
	 * @param aid: appliance agent ID
	 */
	
	public void removeApplianceAgent(AID aid) {
		panelList.remove(agentPanelForAID.get(aid));
		agentPanelForAID.remove(aid);
		updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		listener.onApplianceAddButtonClick();
	}
	
	public void setAppliancePanelListener(AppliancePanelListener listener) {
		this.listener = listener;
	}
	
	public interface AppliancePanelListener {
		void onApplianceAddButtonClick();
	}
}
