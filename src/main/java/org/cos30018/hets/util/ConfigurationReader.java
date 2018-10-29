package org.cos30018.hets.util;

import java.io.File;
import java.io.IOException;

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
import org.cos30018.hets.negotiation.strategy.Strategy;
import org.cos30018.hets.negotiation.strategy.TimeDependentStrategy;
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

	private static final String ELEMENT_HOME_NEGOTIATION_STRATEGY = "negotiationStrategy";

	private static final String ELEMENT_RETAILER_STRATEGY = "strategy";
	private static final String ELEMENT_RETAILER_STRATEGY_NAME = "name";
	private static final String ELEMENT_RETAILER_STRATEGY_RESERVATION_VALUE = "reservationValue";
	private static final String ELEMENT_RETAILER_STRATEGY_DEADLINE = "deadline";
	private static final String ELEMENT_RETAILER_STRATEGY_BETA = "beta";

	private static final String ELEMENT_RETAILER_TARIFF = "tariff";
	private static final String ELEMENT_RETAILER_TARIFF_NAME = "name";
	private static final String ELEMENT_RETAILER_TARIFF_VC_MIN = "volumeChargeMinValue";
	private static final String ELEMENT_RETAILER_TARIFF_VC_MAX = "volumeChargeMaxValue";
	private static final String ELEMENT_RETAILER_TARIFF_FI_MIN = "feedInRateMinValue";
	private static final String ELEMENT_RETAILER_TARIFF_FI_MAX = "feedInRateMaxValue";
	private static final String ELEMENT_RETAILER_TARIFF_VOLUME_CHARGES = "volumeCharges";
	private static final String ELEMENT_RETAILER_TARIFF_FEED_IN_RATES = "feedInRates";

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
		Element strategyElement = (Element) homeRoot.getElementsByTagName(ELEMENT_HOME_NEGOTIATION_STRATEGY).item(0);
		Strategy strategy = getStrategy(strategyElement);

		Home home = jadeController.getHome();
		home.setNegotiationStrategy(strategy);
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
				Element strategyElement = (Element) element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY).item(0);
				Strategy strategy = getStrategy(strategyElement);

				Element tariffElement = (Element) element.getElementsByTagName(ELEMENT_RETAILER_TARIFF).item(0);
				Tariff tariff = getTariff(tariffElement);

				try {
					jadeController.addRetailerAgent(name, strategy, tariff, false);
				} catch (StaleProxyException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static Strategy getStrategy(Element element) {
		String name = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_NAME).item(0).getTextContent();

		switch (name) {
		case Strategy.STRATEGY_FIXED_PRICE:
			return new FixedPriceStrategy();
		case Strategy.STRATEGY_TIME_DEPENDENT:
			String reservationValueText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_RESERVATION_VALUE)
					.item(0).getTextContent();
			double reservationValue = Double.valueOf(reservationValueText);

			String deadlineText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_DEADLINE).item(0)
					.getTextContent();
			int deadline = Integer.valueOf(deadlineText);

			String betaText = element.getElementsByTagName(ELEMENT_RETAILER_STRATEGY_BETA).item(0).getTextContent();
			double beta = Double.valueOf(betaText);

			return new TimeDependentStrategy(deadline, reservationValue, beta);
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
		default:
			return null;
		}
	}

}
