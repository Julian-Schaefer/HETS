package org.cos30018.hets.ui.retailer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.ui.custom.AgentPanel;
import org.cos30018.hets.ui.custom.JPanelList;

import jade.core.AID;
import org.cos30018.hets.ui.custom.StyledButtonUI;

public class RetailerPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 417841290515799971L;

	private RetailerPanelController controller;
	private RetailerPanelListener listener;

	private JButton addRetailerButton;
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
		//buttonPanel.setBackground(Color.WHITE);

		addRetailerButton = new JButton("Add Retailer");
		addRetailerButton.setFont(new Font("Raleway", Font.BOLD, 18));
		addRetailerButton.setBackground(new Color(0x2dce98));
		addRetailerButton.setForeground(Color.white);
		addRetailerButton.setUI(new StyledButtonUI());
		addRetailerButton.addActionListener(this);
		buttonPanel.add(addRetailerButton);
		buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		add(buttonPanel, BorderLayout.NORTH);

		panelList = new JPanelList();

		JScrollPane scrollPane = new JScrollPane(panelList);
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void addRetailerAgent(AID aid) {			
		AgentPanel agentPanel = new AgentPanel(aid);
		agentPanel.setAgentPanelListener(controller);
        
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