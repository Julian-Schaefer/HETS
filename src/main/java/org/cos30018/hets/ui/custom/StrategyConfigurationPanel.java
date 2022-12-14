package org.cos30018.hets.ui.custom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.cos30018.hets.negotiation.strategy.FixedPriceStrategy;
import org.cos30018.hets.negotiation.strategy.RandomTitForTatStrategy;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.strategy.TimeDependentStrategy;
import org.cos30018.hets.util.GuiUtil;

public class StrategyConfigurationPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8590964401098439400L;

	private JComboBox<String> strategyComboBox;
	private JPanel strategySpecificationPanel;

	private JTextField deadLineTextField;
	private JTextField initialValueTextField;
	private JTextField reservationValueTextField;
	private JTextField factorTextField;
	private JTextField betaTextField;

	private boolean showInitialValue;
	private boolean isEnabled;

	public StrategyConfigurationPanel(Strategy strategy, boolean showInitialValue, boolean isEnabled) {
		this.showInitialValue = showInitialValue;
		this.isEnabled = isEnabled;
		setLayout(new BorderLayout());
		setUp(strategy);
	}

	private void setUp(Strategy strategy) {
		JPanel strategySelectorPanel = new JPanel(new GridLayout(1, 2, 20, 20));
		JLabel strategyLabel = new JLabel("Negotiation Strategy:");
		strategySelectorPanel.add(addToJPanel(strategyLabel));

		String[] strategies = { Strategy.STRATEGY_FIXED_PRICE, Strategy.STRATEGY_RANDOM_TIT_FOR_TAT,
				Strategy.STRATEGY_TIME_DEPENDENT };
		strategyComboBox = new JComboBox<>(strategies);
		strategyComboBox.addActionListener(this);
		strategySelectorPanel.add(addToJPanel(strategyComboBox));

		add(strategySelectorPanel, BorderLayout.NORTH);

		strategySpecificationPanel = new JPanel(new BorderLayout());
		add(strategySpecificationPanel, BorderLayout.CENTER);

		if (strategy != null) {
			if (strategy instanceof FixedPriceStrategy) {
				strategyComboBox.setSelectedItem(Strategy.STRATEGY_FIXED_PRICE);

				if (showInitialValue) {
					initialValueTextField.setText(String.valueOf(strategy.getInitialValue()));
				}

			} else if (strategy instanceof TimeDependentStrategy) {
				strategyComboBox.setSelectedItem(Strategy.STRATEGY_TIME_DEPENDENT);

				TimeDependentStrategy timeDependentStrategy = (TimeDependentStrategy) strategy;
				deadLineTextField.setText(String.valueOf(timeDependentStrategy.getDeadline()));
				if (showInitialValue) {
					initialValueTextField.setText(String.valueOf(timeDependentStrategy.getInitialValue()));
				}
				reservationValueTextField.setText(String.valueOf(timeDependentStrategy.getReservationValue()));
				betaTextField.setText(String.valueOf(timeDependentStrategy.getBeta()));
			} else if (strategy instanceof RandomTitForTatStrategy) {
				strategyComboBox.setSelectedItem(Strategy.STRATEGY_RANDOM_TIT_FOR_TAT);

				RandomTitForTatStrategy randomTitForTatStrategy = (RandomTitForTatStrategy) strategy;
				deadLineTextField.setText(String.valueOf(randomTitForTatStrategy.getDeadline()));
				if (showInitialValue) {
					initialValueTextField.setText(String.valueOf(randomTitForTatStrategy.getInitialValue()));
				}
				factorTextField.setText(String.valueOf(randomTitForTatStrategy.getFactor()));
				reservationValueTextField.setText(String.valueOf(randomTitForTatStrategy.getReservationValue()));
			}
		} else {
			strategyComboBox.setSelectedItem(Strategy.STRATEGY_FIXED_PRICE);
		}

		GuiUtil.setComponentsInPanelEnabled(this, isEnabled);
	}

	public Strategy getStrategy() {
		String selectedStrategy = (String) strategyComboBox.getSelectedItem();
		if (selectedStrategy == null) {
			throw new RuntimeException("Please select a strategy.");
		}

		switch (selectedStrategy) {
		case Strategy.STRATEGY_FIXED_PRICE:
			try {
				double initialValue = 0;
				if (showInitialValue) {
					initialValue = Double.valueOf(initialValueTextField.getText());
				}

				return new FixedPriceStrategy(initialValue);
			} catch (NumberFormatException e) {
				throw new RuntimeException("Please enter a valid numbers for the Fixed Price Strategy.");
			}
		case Strategy.STRATEGY_RANDOM_TIT_FOR_TAT:
			try {
				int deadline = Integer.valueOf(deadLineTextField.getText());
				double initialValue = 0;
				if (showInitialValue) {
					initialValue = Double.valueOf(initialValueTextField.getText());
				}
				double reservationValue = Double.valueOf(reservationValueTextField.getText());
				if (deadline <= 0 || reservationValue < 0) {
					throw new RuntimeException("Please enter a positive numbers for the Random Tit-for-tat Strategy.");
				}
				double factor = Double.valueOf(factorTextField.getText());

				return new RandomTitForTatStrategy(deadline, initialValue, reservationValue, factor);
			} catch (NumberFormatException e) {
				throw new RuntimeException("Please enter a valid numbers for the Random Tit-for-tat Strategy.");
			}
		case Strategy.STRATEGY_TIME_DEPENDENT:
			try {
				int deadline = Integer.valueOf(deadLineTextField.getText());
				double initialValue = 0;
				if (showInitialValue) {
					initialValue = Double.valueOf(initialValueTextField.getText());
				}
				double reservationValue = Double.valueOf(reservationValueTextField.getText());
				double beta = Double.valueOf(betaTextField.getText());

				if (deadline <= 0 || reservationValue < 0 || beta <= 0) {
					throw new RuntimeException("Please enter a positive numbers for the Time-dependent Strategy.");
				}

				return new TimeDependentStrategy(deadline, initialValue, reservationValue, beta);
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
				strategySpecificationPanel.add(getFixedPricePanel());
				break;
			case Strategy.STRATEGY_RANDOM_TIT_FOR_TAT:
				strategySpecificationPanel.add(getRandomTitForTatPanel());
				break;
			case Strategy.STRATEGY_TIME_DEPENDENT:
				strategySpecificationPanel.add(getTimeDependentPanel());
				break;
			default:
				break;
			}

			GuiUtil.setPanelBackground(strategySpecificationPanel, Color.WHITE);
			updateUI();
		}
	}

	private JPanel getFixedPricePanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		if (showInitialValue) {
			JLabel initialValueLbl = new JLabel("Initial Value:");
			panel.add(addToJPanel(initialValueLbl));

			initialValueTextField = new JTextField(8);
			panel.add(addToJPanel(initialValueTextField));
		}

		return panel;
	}

	private JPanel getRandomTitForTatPanel() {
		int rows = showInitialValue ? 4 : 3;
		JPanel panel = new JPanel(new GridLayout(rows, 2));

		JLabel deadlineTextLbl = new JLabel("Deadline (Rounds):");
		panel.add(addToJPanel(deadlineTextLbl));

		deadLineTextField = new JTextField(8);
		panel.add(addToJPanel(deadLineTextField));

		if (showInitialValue) {
			JLabel initialValueLbl = new JLabel("Initial Value:");
			panel.add(addToJPanel(initialValueLbl));

			initialValueTextField = new JTextField(8);
			panel.add(addToJPanel(initialValueTextField));
		}

		JLabel reservationValueTextLbl = new JLabel("Reservation Value:");
		panel.add(addToJPanel(reservationValueTextLbl));

		reservationValueTextField = new JTextField(8);
		panel.add(addToJPanel(reservationValueTextField));

		JLabel factorTextLbl = new JLabel("Random Factor (x times randomness):");
		panel.add(addToJPanel(factorTextLbl));

		factorTextField = new JTextField(8);
		panel.add(addToJPanel(factorTextField));

		return panel;
	}

	private JPanel getTimeDependentPanel() {
		int rows = showInitialValue ? 4 : 3;
		JPanel panel = new JPanel(new GridLayout(rows, 2));

		JLabel deadlineTextLbl = new JLabel("Deadline (Rounds):");
		panel.add(addToJPanel(deadlineTextLbl));

		deadLineTextField = new JTextField(8);
		panel.add(addToJPanel(deadLineTextField));

		if (showInitialValue) {
			JLabel initialValueLbl = new JLabel("Initial Value:");
			panel.add(addToJPanel(initialValueLbl));

			initialValueTextField = new JTextField(8);
			panel.add(addToJPanel(initialValueTextField));
		}

		JLabel reservationValueLbl = new JLabel("Reservation Value:");
		panel.add(addToJPanel(reservationValueLbl));

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
