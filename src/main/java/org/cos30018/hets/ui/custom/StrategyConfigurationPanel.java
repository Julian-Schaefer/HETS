package org.cos30018.hets.ui.custom;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cos30018.hets.negotiation.strategy.FixedPriceStrategy;
import org.cos30018.hets.negotiation.strategy.ModellingStrategy;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.strategy.TimeDependentStrategy;

public class StrategyConfigurationPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8590964401098439400L;

	private JComboBox<String> strategyComboBox;
	private JPanel strategySpecificationPanel;

	private JTextField deadLineTextField;
	private JTextField reservationValueTextField;

	private JTextField betaTextField;

	public StrategyConfigurationPanel() {
		setLayout(new BorderLayout());
		setUp();
	}

	private void setUp() {
		JPanel strategySelectorPanel = new JPanel(new GridLayout(1, 2, 20, 20));
		JLabel strategyLabel = new JLabel("Strategy:");
		strategySelectorPanel.add(addToJPanel(strategyLabel));

		String[] strategies = { Strategy.STRATEGY_FIXED_PRICE, Strategy.STRATEGY_MODELLING,
				Strategy.STRATEGY_TIME_DEPENDENT };
		strategyComboBox = new JComboBox<>(strategies);
		strategyComboBox.addActionListener(this);
		strategySelectorPanel.add(addToJPanel(strategyComboBox));

		add(strategySelectorPanel, BorderLayout.NORTH);

		strategySpecificationPanel = new JPanel(new BorderLayout());
		add(strategySpecificationPanel, BorderLayout.CENTER);

		strategyComboBox.setSelectedItem(Strategy.STRATEGY_FIXED_PRICE);
	}

	public Strategy getStrategy() {
		String selectedStrategy = (String) strategyComboBox.getSelectedItem();
		switch (selectedStrategy) {
		case Strategy.STRATEGY_FIXED_PRICE:
			try {
				double reservationValue = Double.valueOf(reservationValueTextField.getText());
				if (reservationValue < 0) {
					throw new RuntimeException("Please enter a positive numbers for the Fixed Price Strategy.");
				}

				return new FixedPriceStrategy(reservationValue);
			} catch (NumberFormatException e) {
				throw new RuntimeException("Please enter a valid numbers for the Fixed Price Strategy.");
			}
		case Strategy.STRATEGY_MODELLING:
			try {
				int deadline = Integer.valueOf(deadLineTextField.getText());
				double reservationValue = Double.valueOf(reservationValueTextField.getText());
				if (deadline <= 0 || reservationValue < 0) {
					throw new RuntimeException("Please enter a positive numbers for the Modelling Strategy.");
				}

				return new ModellingStrategy(deadline, reservationValue);
			} catch (NumberFormatException e) {
				throw new RuntimeException("Please enter a valid numbers for the Modelling Strategy.");
			}
		case Strategy.STRATEGY_TIME_DEPENDENT:
			try {
				int deadline = Integer.valueOf(deadLineTextField.getText());
				double reservationValue = Double.valueOf(reservationValueTextField.getText());
				double beta = Double.valueOf(betaTextField.getText());

				if (deadline <= 0 || reservationValue < 0 || beta <= 0) {
					throw new RuntimeException("Please enter a positive numbers for the Time-dependent Strategy.");
				}

				return new TimeDependentStrategy(deadline, reservationValue, beta);
			} catch (NumberFormatException e) {
				throw new RuntimeException("Please enter a valid numbers for the Time-dependent Strategy.");
			}
		default:
			throw new RuntimeException("Selected Strategy not found.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == strategyComboBox) {
			strategySpecificationPanel.removeAll();

			String selectedStrategy = (String) strategyComboBox.getSelectedItem();
			switch (selectedStrategy) {
			case Strategy.STRATEGY_FIXED_PRICE:
				strategySpecificationPanel.add(getFixedStrategyPanel());
				break;
			case Strategy.STRATEGY_MODELLING:
				strategySpecificationPanel.add(getModellingPanel());
				break;
			case Strategy.STRATEGY_TIME_DEPENDENT:
				strategySpecificationPanel.add(getTimeDependentPanel());
				break;
			default:
				break;
			}

			updateUI();
		}
	}

	JPanel getFixedStrategyPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		JLabel reservationValueTextLbl = new JLabel("Reservation Value:");
		panel.add(addToJPanel(reservationValueTextLbl));

		reservationValueTextField = new JTextField(8);
		panel.add(addToJPanel(reservationValueTextField));

		return panel;
	}

	private JPanel getModellingPanel() {
		JPanel panel = new JPanel(new GridLayout(2, 2));

		JLabel deadlineTextLbl = new JLabel("Deadline (Rounds):");
		panel.add(addToJPanel(deadlineTextLbl));

		deadLineTextField = new JTextField(8);
		panel.add(addToJPanel(deadLineTextField));

		JLabel reservationValueTextLbl = new JLabel("Reservation Value:");
		panel.add(addToJPanel(reservationValueTextLbl));

		reservationValueTextField = new JTextField(8);
		panel.add(addToJPanel(reservationValueTextField));

		return panel;
	}

	private JPanel getTimeDependentPanel() {
		JPanel panel = new JPanel(new GridLayout(3, 2));

		JLabel deadlineTextLbl = new JLabel("Deadline (Rounds):");
		panel.add(addToJPanel(deadlineTextLbl));

		deadLineTextField = new JTextField(8);
		panel.add(addToJPanel(deadLineTextField));

		JLabel minimumValueLbl = new JLabel("Reservation Value:");
		panel.add(addToJPanel(minimumValueLbl));

		reservationValueTextField = new JTextField(8);
		panel.add(addToJPanel(reservationValueTextField));

		JLabel betaLbl = new JLabel("Beta Value:");
		panel.add(addToJPanel(betaLbl));

		betaTextField = new JTextField(8);
		panel.add(addToJPanel(betaTextField));

		return panel;
	}

	private JPanel addToJPanel(JComponent component) {
		JPanel container = new JPanel();
		container.add(component);
		return container;
	}

}
