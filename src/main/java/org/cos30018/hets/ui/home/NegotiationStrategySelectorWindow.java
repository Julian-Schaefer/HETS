package org.cos30018.hets.ui.home;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.ui.custom.StrategyConfigurationPanel;

public class NegotiationStrategySelectorWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6413805511782661143L;

	private StrategyConfigurationPanel strategyConfigurationPanel;
	private Home home;

	public NegotiationStrategySelectorWindow(Home home) {
		super("Negotiation Strategy");
		this.home = home;
		setLayout(new BorderLayout());
		setSize(new Dimension(500, 400));
		setLocationRelativeTo(null);
		setUp();
		setVisible(true);
	}

	private void setUp() {
		strategyConfigurationPanel = new StrategyConfigurationPanel(home.getNegotiationStrategy(), true);
		add(strategyConfigurationPanel, BorderLayout.CENTER);

		JPanel bottomButtonPanel = new JPanel();

		JButton okButton = new JButton("Ok");
		okButton.addActionListener(onOkButtonClickListener);
		bottomButtonPanel.add(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(onCancelButtonClickListener);
		bottomButtonPanel.add(cancelButton);

		add(bottomButtonPanel, BorderLayout.SOUTH);
	}

	private ActionListener onOkButtonClickListener = (actionEvent) -> {
		try {
			home.setNegotiationStrategy(strategyConfigurationPanel.getStrategy());
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	};

	private ActionListener onCancelButtonClickListener = (actionEvent) -> {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	};
}
