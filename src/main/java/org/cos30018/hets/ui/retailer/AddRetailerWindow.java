package org.cos30018.hets.ui.retailer;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.ui.custom.StrategyConfigurationPanel;
import org.cos30018.hets.ui.custom.TariffConfigurationPanel;
import org.cos30018.hets.ui.custom.button.StyledJPanelUI;
import org.cos30018.hets.util.GuiUtil;

public class AddRetailerWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8051907939971247603L;

	private JTextField nameTextField;

	private StrategyConfigurationPanel sellingStrategyConfigurationPanel;
	private StrategyConfigurationPanel buyingStrategyConfigurationPanel;
	private TariffConfigurationPanel tariffConfigurationPanel;

	public AddRetailerWindow() {
		super("Add Retailer");
		setLayout(new BorderLayout());
		setSize(new Dimension(550, 700));
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

		JPanel strategiesAndTariffPanel = new JPanel(new GridLayout(3, 1, 10, 10));
		strategiesAndTariffPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		strategiesAndTariffPanel.setPreferredSize(new Dimension(0, 700));

		sellingStrategyConfigurationPanel = new StrategyConfigurationPanel(null, false, true);
		strategiesAndTariffPanel.add(getCardPanel("Selling Strategy", sellingStrategyConfigurationPanel));

		buyingStrategyConfigurationPanel = new StrategyConfigurationPanel(null, false, true);
		strategiesAndTariffPanel.add(getCardPanel("Buying Strategy", buyingStrategyConfigurationPanel));

		tariffConfigurationPanel = new TariffConfigurationPanel(null, true);
		strategiesAndTariffPanel.add(getCardPanel("Tariff", tariffConfigurationPanel));

		JScrollPane scrollPane = new JScrollPane(strategiesAndTariffPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		add(scrollPane, BorderLayout.CENTER);

		JPanel bottomButtonPanel = new JPanel();

		JButton okButton = new JButton("Ok");
		okButton.addActionListener(onOkButtonClickListener);
		bottomButtonPanel.add(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(onCancelButtonClickListener);
		bottomButtonPanel.add(cancelButton);

		add(bottomButtonPanel, BorderLayout.SOUTH);
	}

	private JPanel getCardPanel(String title, JPanel child) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setUI(new StyledJPanelUI());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		JLabel titleLbl = new JLabel(title);
		titleLbl.setHorizontalAlignment(JLabel.LEFT);
		panel.add(titleLbl, BorderLayout.NORTH);

		JPanel cardPanel = new JPanel(new BorderLayout());
		cardPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		cardPanel.add(child);
		panel.add(cardPanel);

		GuiUtil.setPanelBackground(panel, Color.WHITE);
		return panel;
	}

	private ActionListener onOkButtonClickListener = (actionEvent) -> {
		if (!nameTextField.getText().isEmpty()) {
			try {
				JadeController.getInstance().addRetailerAgent(nameTextField.getText(),
						sellingStrategyConfigurationPanel.getStrategy(), buyingStrategyConfigurationPanel.getStrategy(),
						tariffConfigurationPanel.getTariff(), true);

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