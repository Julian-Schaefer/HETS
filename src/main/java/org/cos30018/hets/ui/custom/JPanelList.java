package org.cos30018.hets.ui.custom;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class JPanelList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1325782291429477575L;

	public JPanelList() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        add(new JPanel(), gbc);
	}
	
	public JPanel addJPanel(JPanel panel) {
		JPanel container = new JPanel();
		container.setBorder(new EmptyBorder(20, 10, 20, 10));
		container.add(panel);
		
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;   
        
        add(container, gbc, 0);
		updateUI();
		
		return container;
	}
}
