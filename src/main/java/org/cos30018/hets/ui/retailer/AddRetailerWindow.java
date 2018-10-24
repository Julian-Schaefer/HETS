package org.cos30018.hets.ui.retailer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.ui.custom.StrategyConfigurationPanel;
import org.cos30018.hets.ui.custom.TariffConfigurationPanel;

public class AddRetailerWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8051907939971247603L;

	private JTextField nameTextField;

	private StrategyConfigurationPanel strategyConfigurationPanel;
	private TariffConfigurationPanel tariffConfigurationPanel;

	public AddRetailerWindow() {
		super("Add Retailer");
		setLayout(new BorderLayout());
		setSize(new Dimension(500, 300));
		setLocationRelativeTo(null);
		setUp();
		setVisible(true);
	}

	private void setUp() {
		JPanel generalPanel = new JPanel(new GridLayout(1, 2, 20, 20));
		generalPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

		JLabel nameTextLbl = new JLabel("Name:");
		generalPanel.add(addToJPanel(nameTextLbl));

		nameTextField = new JTextField();
		generalPanel.add(nameTextField);

		add(generalPanel, BorderLayout.NORTH);

		JPanel strategyAndTariffPanel = new JPanel(new GridLayout(2, 1));

		strategyConfigurationPanel = new StrategyConfigurationPanel();
		strategyAndTariffPanel.add(strategyConfigurationPanel);

		tariffConfigurationPanel = new TariffConfigurationPanel();
		strategyAndTariffPanel.add(tariffConfigurationPanel);

		add(strategyAndTariffPanel, BorderLayout.CENTER);

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
		if (!nameTextField.getText().isEmpty()) {
			try {
				JadeController.getInstance().addRetailerAgent(nameTextField.getText(),
						strategyConfigurationPanel.getStrategy(), tariffConfigurationPanel.getTariff());

				dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Retailer name is empty.");
		}
	};

	private ActionListener onCancelButtonClickListener = (actionEvent) -> {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	};

	private JPanel addToJPanel(JComponent component) {
		JPanel container = new JPanel();
		container.add(component);
		return container;
	}
}