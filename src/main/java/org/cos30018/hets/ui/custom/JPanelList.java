package org.cos30018.hets.ui.custom;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.Border;

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
		return addJPanel(panel, null);
	}

	public JPanel addJPanel(JPanel panel, Border border) {
		JPanel container = new JPanel();
		if (border != null) {
			container.setBorder(border);
		}
		container.add(panel);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		add(container, gbc, getComponentCount() - 1);
		updateUI();

		return container;
	}
}
