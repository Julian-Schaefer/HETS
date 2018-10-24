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

import org.cos30018.hets.negotiation.tariff.RandomTariff;
import org.cos30018.hets.negotiation.tariff.Tariff;

public class TariffConfigurationPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3592252209273234073L;

	private JComboBox<String> tariffComboBox;
	private JPanel tariffSpecificationPanel;

	private JTextField fixedPriceTextField;
	private JTextField deadLineTextField;
	private JTextField reservationValueTextField;

	public TariffConfigurationPanel() {
		setLayout(new BorderLayout());
		setUp();
	}

	private void setUp() {
		JPanel tariffSelectorPanel = new JPanel(new GridLayout(1, 2, 20, 20));
		JLabel tariffLabel = new JLabel("Tariff:");
		tariffSelectorPanel.add(addToJPanel(tariffLabel));

		String[] tariffs = { Tariff.TARIFF_RANDOM, Tariff.TARIFF_BLOCK, Tariff.TARIFF_TIME_OF_USE };
		tariffComboBox = new JComboBox<>(tariffs);
		tariffComboBox.addActionListener(this);
		tariffSelectorPanel.add(addToJPanel(tariffComboBox));

		add(tariffSelectorPanel, BorderLayout.NORTH);

		tariffSpecificationPanel = new JPanel(new BorderLayout());
		add(tariffSpecificationPanel, BorderLayout.CENTER);

		tariffComboBox.setSelectedItem(Tariff.TARIFF_RANDOM);
	}

	public Tariff getTariff() {
		String selectedTariff = (String) tariffComboBox.getSelectedItem();
		switch (selectedTariff) {
		case Tariff.TARIFF_RANDOM:
			return new RandomTariff();
		case Tariff.TARIFF_BLOCK:
			throw new RuntimeException("Not supported yet!");
		case Tariff.TARIFF_TIME_OF_USE:
			throw new RuntimeException("Not supported yet!");
		default:
			throw new RuntimeException("Selected Tariff could not be created.");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tariffComboBox) {
			tariffSpecificationPanel.removeAll();

			String selectedTariff = (String) tariffComboBox.getSelectedItem();
			switch (selectedTariff) {
			case Tariff.TARIFF_RANDOM:
				break;
			case Tariff.TARIFF_BLOCK:
				break;
			case Tariff.TARIFF_TIME_OF_USE:
				break;
			default:
				throw new RuntimeException("Selected Tariff could not be selected.");
			}

			updateUI();
		}
	}

	private JPanel getFixedPricePanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2));

		JLabel fixedPriceTextLbl = new JLabel("Fixed Price:");
		panel.add(addToJPanel(fixedPriceTextLbl));

		fixedPriceTextField = new JTextField(8);
		panel.add(addToJPanel(fixedPriceTextField));

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

	private JPanel addToJPanel(JComponent component) {
		JPanel container = new JPanel();
		container.add(component);
		return container;
	}

}
