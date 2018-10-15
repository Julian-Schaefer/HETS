package org.cos30018.hets.ui.retailer;

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

public class RetailerPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 417841290515799971L;

	private RetailerPanelController controller;
	private RetailerPanelListener listener;

	private JButton addApplianceButton;
	private JPanelList panelList;

	private Map<AID, JPanel> agentPanelForAID = new HashMap<>();

	public RetailerPanel() {
		setLayout(new BorderLayout());
		setup();
		this.controller = new RetailerPanelController(this);
	}

	private void setup() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		addApplianceButton = new JButton("Add Retailer");
		addApplianceButton.addActionListener(this);
		buttonPanel.add(addApplianceButton);
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		add(buttonPanel, BorderLayout.NORTH);

		panelList = new JPanelList();

		JScrollPane scrollPane = new JScrollPane(panelList);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void addRetailerAgent(AID aid) {			
		AgentPanel agentPanel = new AgentPanel(aid);
		agentPanel.setAgentPanelListener(controller);
		agentPanel.setBorder(new LineBorder(Color.GRAY, 2, true));
        
		agentPanelForAID.put(aid, panelList.addJPanel(agentPanel));
	}
	
	public void removeRetailerAgent(AID aid) {
		panelList.remove(agentPanelForAID.get(aid));
		agentPanelForAID.remove(aid);
		updateUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		listener.onRetailerAddButtonClick();
	}
	
	public void setRetailerPanelListener(RetailerPanelListener listener) {
		this.listener = listener;
	}
	
	public interface RetailerPanelListener {
		void onRetailerAddButtonClick();
	}
}