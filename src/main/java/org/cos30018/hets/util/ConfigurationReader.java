package org.cos30018.hets.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.cos30018.hets.logic.JadeController;
import org.cos30018.hets.logic.appliance.Appliance.ApplianceType;
import org.cos30018.hets.logic.appliance.Appliance.ForecastingMethod;
import org.cos30018.hets.logic.home.Home;
import org.cos30018.hets.negotiation.strategy.FixedPriceStrategy;
import org.cos30018.hets.negotiation.strategy.RandomTitForTatStrategy;
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.strategy.TimeDependentStrategy;
import org.cos30018.hets.negotiation.tariff.BlockTariff;
import org.cos30018.hets.negotiation.tariff.RandomTariff;
import org.cos30018.hets.negotiation.tariff.Tariff;
import org.cos30018.hets.negotiation.tariff.TimeOfUseTariff;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jade.wrapper.StaleProxyException;

public class ConfigurationReader {

	private static final String TAG_HOME = "home";
	private static final String TAG_APPLIANCES = "appliances";
	private static final String TAG_RETAILERS = "retailers";

	private static final String ELEMENT_LOCALNAME = "localName";

	private static final String ELEMENT_APPLIANCE_TYPE = "type";
	private static final String ELEMENT_APPLIANCE_FORECASTING_METHOD = "forecastingMethod";

	private static final String ELEMENT_HOME_BUYING_STRATEGY = "buyingStrategy";
	private static final String ELEMENT_HOME_SELLING_STRATEGY = "sellingStrategy";
	private static final String ELEMENT_HOME_EXCESS_PRICE = "excessPrice";

	private static final String ELEMENT_RETAILER_SELLING_STRATEGY = "sellingStrategy";
	private static final String ELEMENT_RETAILER_BUYING_STRATEGY = "buyingStrategy";
	private static final String ELEMENT_RETAILER_STRATEGY_NAME = "name";
	private static final String ELEMENT_RETAILER_STRATEGY_INITIAL_VALUE = "initialValue";
	private static final String ELEMENT_RETAILER_STRATEGY_RESERVATION_VALUE = "reservationValue";
	private static final String ELEMENT_RETAILER_STRATEGY_DEADLINE = "deadline";
	private static final String ELEMENT_RETAILER_STRATEGY_BETA = "beta";
	private static final String ELEMENT_RETAILER_STRATEGY_FACTOR = "factor";

	private static final String ELEMENT_RETAILER_TARIFF = "tariff";
	private static final String ELEMENT_RETAILER_TARIFF_NAME = "name";
	private static final String ELEMENT_RETAILER_TARIFF_VC_MIN = "volumeChargeMinValue";
	private static final String ELEMENT_RETAILER_TARIFF_VC_MAX = "volumeChargeMaxValue";
	private static final String ELEMENT_RETAILER_TARIFF_FI_MIN = "feedInRateMinValue";
	private static final String ELEMENT_RETAILER_TARIFF_FI_MAX = "feedInRateMaxValue";
	private static final String ELEMENT_RETAILER_TARIFF_VOLUME_CHARGES = "volumeCharges";
	private static final String ELEMENT_RETAILER_TARIFF_FEED_IN_RATES = "feedInRates";
	private static final String ELEMENT_RETAILER_TARIFF_BLOCK_RATES = "blockRates";
	private static final String ELEMENT_RETAILER_TARIFF_FROM = "from";
	private static final String ELEMENT_RETAILER_TARIFF_UP_TO = "upTo";
	private static final String ELEMENT_RETAILER_TARIFF_VOLUME_CHARGE = "volumeCharge";
	private static final String ELEMENT_RETAILER_TARIFF_FEED_IN_RATE = "feedInRate";

	private static JadeController jadeController = JadeController.getInstance();

	public static void readConfiguration() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File configFile = chooser.getSelectedFile();

			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(configFile);
				readDocument(document);
			} catch (ParserConfigurationException | SAXException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void readDocument(Document document) {
		jadeController.killAllAgents();

		Element root = document.getDocumentElement();
		NodeList rootChildNodes = root.getChildNodes();

		for (int i = 0; i < rootChildNodes.getLength(); i++) {
			Node node = rootChildNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				switch (element.getTagName()) {
				case TAG_HOME:
					loadHome(element);
					break;
				case TAG_APPLIANCES:
					loadAppliances(element);
					break;
				case TAG_RETAILERS:
					loadRetailers(element);
					break;
				}
			}
		}
	}

	private static void loadHome(Element homeRoot) {
		Element buyingStrategyElement = (Element) homeRoot.getElementsByTagName(ELEMENT_HOME_BUYING_STRATEGY).item(0);
		Strategy buyingStrategy = getStrategy(buyingStrategyElement);

		Element sellingStrategyElement = (Element) homeRoot.getElementsByTagName(ELEMENT_HOME_SELLING_STRATEGY).item(0);
		Strategy sellingStrategy = getStrategy(sellingStrategyElement);

		String excessPriceText = homeRoot.getElementsByTagName(ELEMENT_HOME_EXCESS_PRICE).item(0).getTextContent();
		double excessPrice = Double.valueOf(excessPriceText);

		Home home = jadeController.getHome();
		home.reset();
		home.setBuyingStrategy(buyingStrategy);
		home.setSellingStrategy(sellingStrategy);
		home.setExcessPrice(excessPrice);
	}

	private static void loadAppliances(Element applianceRoot) {
		NodeList applianceNodes = applianceRoot.getElementsByTagName(TAG_APPLIANCES);

		for (int i = 0; i < applianceNodes.getLength(); i++) {
			Node node = applianceNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				String name = element.getElementsByTagName(ELEMENT_LOCALNAME).item(0).getTextContent();
				ApplianceType applianceType = ApplianceType
						.valueOf(element.getElementsByTagName(ELEMENT_APPLIANCE_TYPE).item(0).getTextContent());
				ForecastingMethod forecastingMethod = ForecastingMethod.valueOf(
						element.getElementsByTagName(ELEMENT_APPLIANCE_FORECASTING_METHOD).item(0).getTextContent());

				try {
					jadeController.addApplianceAgent(name, applianceType, forecastingMethod, false);
				} catch (StaleProxyException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void loadRetailers(Element retailerRoot) {
		NodeList retailerNodes = retailerRoot.getElementsByTagName(TAG_RETAILERS);

		for (int i = 0; i < retailerNodes.getLength(); i++) {
			Node node = retailerNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				String name = element.getElementsByTagName(ELEMENT_LOCALNAME).item(0).getTextContent();
				Element sellingStrategyElement = (Element) element
						.getElementsByTagName(ELEMENT_RETAILER_SELLING_STRATEGY).item(0);
				Strategy sellingStrategy = getStrategy(sellingStrategyElement);

				Element buyingStrategyElement = (Element) element.getElementsByTagName(ELEMENT_RETAILER_BUYING_STRATEGY)
						.item(0);
				Strategy buyingStrategy = getStrategy(buyingStrategyElement);

				Element tariffElement = (Element) element.getElementsByTagName(ELEMENT_RETAILER_TARIFF).item(0);
				Tariff tariff = getTariff(tariffElement);

				try {
					jadeController.addRetailerAgent(name, sellingStrategy, buyingStrategy, tariff, false);
				} catch (StaleProxyException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static Strategy getStrategy(Element element) {
		String name = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_NAME).item(0).getTextContent();

		switch (name) {
		case Strategy.STRATEGY_FIXED_PRICE: {
			String initialValueText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_INITIAL_VALUE).item(0)
					.getTextContent();
			double initialValue = Double.valueOf(initialValueText);
			return new FixedPriceStrategy(initialValue);
		}
		case Strategy.STRATEGY_TIME_DEPENDENT: {
			String initialValueText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_INITIAL_VALUE).item(0)
					.getTextContent();
			double initialValue = Double.valueOf(initialValueText);

			String reservationValueText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_RESERVATION_VALUE)
					.item(0).getTextContent();
			double reservationValue = Double.valueOf(reservationValueText);

			String deadlineText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_DEADLINE).item(0)
					.getTextContent();
			int deadline = Integer.valueOf(deadlineText);

			String betaText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_BETA).item(0).getTextContent();
			double beta = Double.valueOf(betaText);

			return new TimeDependentStrategy(deadline, initialValue, reservationValue, beta);
		}
		case Strategy.STRATEGY_RANDOM_TIT_FOR_TAT: {
			String initialValueText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_INITIAL_VALUE).item(0)
					.getTextContent();
			double initialValue = Double.valueOf(initialValueText);

			String reservationValueText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_RESERVATION_VALUE)
					.item(0).getTextContent();
			double reservationValue = Double.valueOf(reservationValueText);

			String deadlineText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_DEADLINE).item(0)
					.getTextContent();
			int deadline = Integer.valueOf(deadlineText);

			String factorText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_FACTOR).item(0).getTextContent();
			double factor = Double.valueOf(factorText);

			return new RandomTitForTatStrategy(deadline, initialValue, reservationValue, factor);
		}
		default:
			return null;
		}
	}

	private static Tariff getTariff(Element element) {
		String name = element.getElementsByTagName(ELEMENT_RETAILER_TARIFF_NAME).item(0).getTextContent();

		switch (name) {
		case Tariff.TARIFF_RANDOM:
			String vcMinText = element.getElementsByTagName(ELEMENT_RETAILER_TARIFF_VC_MIN).item(0).getTextContent();
			double vcMin = Double.valueOf(vcMinText);

			String vcMaxText = element.getElementsByTagName(ELEMENT_RETAILER_TARIFF_VC_MAX).item(0).getTextContent();
			double vcMax = Double.valueOf(vcMaxText);

			String fiMinText = element.getElementsByTagName(ELEMENT_RETAILER_TARIFF_FI_MIN).item(0).getTextContent();
			double fiMin = Double.valueOf(fiMinText);

			String fiMaxText = element.getElementsByTagName(ELEMENT_RETAILER_TARIFF_FI_MAX).item(0).getTextContent();
			double fiMax = Double.valueOf(fiMaxText);

			return new RandomTariff(vcMin, vcMax, fiMin, fiMax);
		case Tariff.TARIFF_TIME_OF_USE:
			double[] volumeCharges = new double[24];
			double[] feedInRates = new double[24];

			NodeList childNodes = element.getChildNodes();

			for (int childCounter = 0; childCounter < childNodes.getLength(); childCounter++) {
				Node node = childNodes.item(childCounter);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element childElement = (Element) node;

					switch (childElement.getTagName()) {
					case ELEMENT_RETAILER_TARIFF_VOLUME_CHARGES:
						NodeList volumeChargeNodes = childElement
								.getElementsByTagName(ELEMENT_RETAILER_TARIFF_VOLUME_CHARGES);
						for (int i = 0; i < volumeChargeNodes.getLength(); i++) {
							double volumeCharge = Double.valueOf(volumeChargeNodes.item(i).getTextContent());
							volumeCharges[i] = volumeCharge;
						}

						break;
					case ELEMENT_RETAILER_TARIFF_FEED_IN_RATES:
						NodeList feedInRateNodes = childElement
								.getElementsByTagName(ELEMENT_RETAILER_TARIFF_FEED_IN_RATES);
						for (int i = 0; i < feedInRateNodes.getLength(); i++) {
							double feedInRate = Double.valueOf(feedInRateNodes.item(i).getTextContent());
							feedInRates[i] = feedInRate;
						}
						break;
					}
				}
			}

			return new TimeOfUseTariff(volumeCharges, feedInRates);
		case Tariff.TARIFF_BLOCK:
			Map<DoubleRange, DoubleRange> blockRates = new HashMap<>();

			NodeList children = element.getChildNodes();
			for (int child = 0; child < children.getLength(); child++) {
				Node node = children.item(child);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element blockRateElement = (Element) node;
					if (blockRateElement.getTagName().equals(ELEMENT_RETAILER_TARIFF_BLOCK_RATES)) {

						NodeList blockRatesRoot = blockRateElement
								.getElementsByTagName(ELEMENT_RETAILER_TARIFF_BLOCK_RATES);
						for (int i = 0; i < blockRatesRoot.getLength(); i++) {
							Node blockRateNode = blockRatesRoot.item(i);
							if (node.getNodeType() == Node.ELEMENT_NODE) {
								Element blockRate = (Element) blockRateNode;

								String fromValueText = blockRate.getElementsByTagName(ELEMENT_RETAILER_TARIFF_FROM)
										.item(0).getTextContent();
								double fromValue = Double.valueOf(fromValueText);

								String upToValueText = blockRate.getElementsByTagName(ELEMENT_RETAILER_TARIFF_UP_TO)
										.item(0).getTextContent();
								double upToValue = Double.valueOf(upToValueText);

								String volumeChargeText = blockRate
										.getElementsByTagName(ELEMENT_RETAILER_TARIFF_VOLUME_CHARGE).item(0)
										.getTextContent();
								double volumeCharge = Double.valueOf(volumeChargeText);

								String feedInRateText = blockRate
										.getElementsByTagName(ELEMENT_RETAILER_TARIFF_FEED_IN_RATE).item(0)
										.getTextContent();
								double feedInRate = Double.valueOf(feedInRateText);

								blockRates.put(new DoubleRange(fromValue, upToValue),
										new DoubleRange(volumeCharge, feedInRate));
							}
						}
					}
				}
			}

			return new BlockTariff(blockRates);
		default:
			return null;
		}
	}

}
