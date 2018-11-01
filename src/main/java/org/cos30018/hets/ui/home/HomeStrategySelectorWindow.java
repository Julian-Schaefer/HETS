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
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.ui.custom.StrategyConfigurationPanel;

public class HomeStrategySelectorWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6413805511782661143L;

	private StrategyConfigurationPanel strategyConfigurationPanel;
	private Home home;
	private boolean selling;

	public HomeStrategySelectorWindow(Home home, boolean selling) {
		super("Negotiation Strategy");
		this.home = home;
		this.selling = selling;
		setLayout(new BorderLayout());
		setSize(new Dimension(500, 400));
		setLocationRelativeTo(null);
		setUp();
		setVisible(true);
	}

	private void setUp() {
		Strategy strategy = selling ? home.getSellingStrategy() : home.getBuyingStrategy();
		strategyConfigurationPanel = new StrategyConfigurationPanel(strategy, true, true);
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
			if (selling) {
				home.setSellingStrategy(strategyConfigurationPanel.getStrategy());
			} else {
				home.setBuyingStrategy(strategyConfigurationPanel.getStrategy());
			}

			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	};

	private ActionListener onCancelButtonClickListener = (actionEvent) -> {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	};
}
