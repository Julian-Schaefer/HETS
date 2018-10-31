package org.cos30018.hets.ui.custom;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.cos30018.hets.negotiation.tariff.BlockTariff;
import org.cos30018.hets.negotiation.tariff.RandomTariff;
import org.cos30018.hets.negotiation.tariff.Tariff;
import org.cos30018.hets.negotiation.tariff.TimeOfUseTariff;
import org.cos30018.hets.util.DoubleRange;

public class TariffConfigurationPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3592252209273234073L;

	private JComboBox<String> tariffComboBox;
	private JPanel tariffSpecificationPanel;

	private JTextField fixedPriceVolumeChargeMinTextField;
	private JTextField fixedPriceVolumeChargeMaxTextField;
	private JTextField fixedPriceFeedInMinTextField;
	private JTextField fixedPriceFeedInMaxTextField;

	private JTextField[] touVolumeChargeTextField = new JTextField[24];
	private JTextField[] touFeedInRateTextField = new JTextField[24];

	private List<JTextField[]> blockRateTextFields;

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
			try {
				double minVolumeChargeValue = Double.valueOf(fixedPriceVolumeChargeMinTextField.getText());
				double maxVolumeChargeValue = Double.valueOf(fixedPriceVolumeChargeMaxTextField.getText());
				double minFeedInValue = Double.valueOf(fixedPriceFeedInMinTextField.getText());
				double maxFeedInValue = Double.valueOf(fixedPriceFeedInMaxTextField.getText());

				if (minVolumeChargeValue < 0 || maxVolumeChargeValue <= 0 || minFeedInValue < 0
						|| maxFeedInValue <= 0) {
					throw new RuntimeException("Please enter a positive numbers for the Random Tariff.");
				}

				return new RandomTariff(minVolumeChargeValue, maxVolumeChargeValue, minFeedInValue, maxFeedInValue);
			} catch (NumberFormatException e) {
				throw new RuntimeException("Please enter a valid numbers for the Random Tariff.");
			}

		case Tariff.TARIFF_BLOCK:
			Map<DoubleRange, DoubleRange> blockRates = new HashMap<>();
			try {
				for (JTextField[] blockRateTextField : blockRateTextFields) {
					double fromValue = Double.valueOf(blockRateTextField[0].getText());
					double upToValue = Double.valueOf(blockRateTextField[1].getText());
					double volumeChargeValue = Double.valueOf(blockRateTextField[2].getText());
					double feedInRateValue = Double.valueOf(blockRateTextField[3].getText());

					if (fromValue < 0 || upToValue <= 0 || volumeChargeValue <= 0 || feedInRateValue <= 0) {
						throw new RuntimeException("Please enter a positive numbers for the Block Tariff.");
					}

					blockRates.put(new DoubleRange(fromValue, upToValue),
							new DoubleRange(volumeChargeValue, feedInRateValue));
				}

				return new BlockTariff(blockRates);
			} catch (NumberFormatException e) {
				throw new RuntimeException("Please enter a valid numbers for the Block Tariff.");
			}
		case Tariff.TARIFF_TIME_OF_USE:
			double[] volumeCharges = new double[24];
			double[] feedInRates = new double[24];

			try {
				for (int i = 0; i < 24; i++) {
					double volumeCharge = Double.valueOf(touVolumeChargeTextField[i].getText());
					double feedInRate = Double.valueOf(touVolumeChargeTextField[i].getText());
					volumeCharges[i] = volumeCharge;
					feedInRates[i] = feedInRate;
				}

				return new TimeOfUseTariff(volumeCharges, feedInRates);
			} catch (NumberFormatException e) {
				throw new RuntimeException("Please enter a valid numbers for the Time-of-use Tariff.");
			}
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
				tariffSpecificationPanel.add(getFixedPricePanel());
				break;
			case Tariff.TARIFF_BLOCK:
				tariffSpecificationPanel.add(getBlockTariffPanel());
				break;
			case Tariff.TARIFF_TIME_OF_USE:
				tariffSpecificationPanel.add(getTimeOfUsePanel());
				break;
			default:
				throw new RuntimeException("Selected Tariff could not be selected.");
			}

			updateUI();
		}
	}

	private JPanel getFixedPricePanel() {
		JPanel panel = new JPanel(new GridLayout(4, 2));

		JLabel fixedPriceVolumeChargeMinTextLbl = new JLabel("Minimum Volume Charge Price:");
		panel.add(addToJPanel(fixedPriceVolumeChargeMinTextLbl));

		fixedPriceVolumeChargeMinTextField = new JTextField(8);
		panel.add(addToJPanel(fixedPriceVolumeChargeMinTextField));

		JLabel fixedPriceVolumeChargeMaxTextLbl = new JLabel("Maximum Volume Charge Price:");
		panel.add(addToJPanel(fixedPriceVolumeChargeMaxTextLbl));

		fixedPriceVolumeChargeMaxTextField = new JTextField(8);
		panel.add(addToJPanel(fixedPriceVolumeChargeMaxTextField));

		JLabel fixedPriceFeedInMinTextLbl = new JLabel("Minimum Feed-in Price:");
		panel.add(addToJPanel(fixedPriceFeedInMinTextLbl));

		fixedPriceFeedInMinTextField = new JTextField(8);
		panel.add(addToJPanel(fixedPriceFeedInMinTextField));

		JLabel fixedPriceFeedInMaxTextLbl = new JLabel("Maximum Feed-in Price:");
		panel.add(addToJPanel(fixedPriceFeedInMaxTextLbl));

		fixedPriceFeedInMaxTextField = new JTextField(8);
		panel.add(addToJPanel(fixedPriceFeedInMaxTextField));

		return panel;
	}

	private JScrollPane getTimeOfUsePanel() {
		JPanelList list = new JPanelList();
		JPanel headerRow = new JPanel(new GridLayout(1, 3, 10, 0));
		headerRow.add(new JLabel("Period:"));
		headerRow.add(new JLabel("Volume charge:"));
		headerRow.add(new JLabel("Feed-in rate:"));
		list.addJPanel(headerRow);

		for (int i = 0; i < 24; i++) {
			JPanel rowPanel = new JPanel(new GridLayout(1, 3, 10, 0));
			rowPanel.add(new JLabel("Period " + i));

			JTextField volumeChargeTextField = new JTextField(8);
			touVolumeChargeTextField[i] = volumeChargeTextField;
			rowPanel.add(volumeChargeTextField);

			JTextField feedInRateTextField = new JTextField(8);
			touFeedInRateTextField[i] = feedInRateTextField;
			rowPanel.add(feedInRateTextField);

			list.addJPanel(rowPanel);
		}
		return new JScrollPane(list);
	}

	private JPanel getBlockTariffPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanelList list = new JPanelList();
		JPanel headerRow = new JPanel(new GridLayout(1, 4, 10, 0));
		headerRow.add(new JLabel("From (kWh):"));
		headerRow.add(new JLabel("Up to (kWh):"));
		headerRow.add(new JLabel("Volume charge:"));
		headerRow.add(new JLabel("Feed-in rate:"));
		list.addJPanel(headerRow);
		panel.add(new JScrollPane(list), BorderLayout.CENTER);

		blockRateTextFields = new ArrayList<>();

		JButton addButton = new JButton("Add Block");
		addButton.addActionListener((e) -> {
			JPanel rowPanel = new JPanel(new GridLayout(1, 4, 10, 0));
			JTextField fromTextField = new JTextField(8);
			JTextField toTextField = new JTextField(8);
			JTextField volumeChargeTextField = new JTextField(8);
			JTextField feedInRateTextField = new JTextField(8);

			JTextField[] textFields = new JTextField[4];
			textFields[0] = fromTextField;
			textFields[1] = toTextField;
			textFields[2] = volumeChargeTextField;
			textFields[3] = feedInRateTextField;
			blockRateTextFields.add(textFields);

			rowPanel.add(fromTextField);
			rowPanel.add(toTextField);
			rowPanel.add(volumeChargeTextField);
			rowPanel.add(feedInRateTextField);
			list.addJPanel(rowPanel);
		});

		panel.add(addButton, BorderLayout.SOUTH);

		return panel;
	}

	private JPanel addToJPanel(JComponent component) {
		JPanel container = new JPanel();
		container.add(component);
		return container;
	}

}
