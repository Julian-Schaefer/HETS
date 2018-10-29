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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jade.wrapper.StaleProxyException;

public class ConfigurationReader {

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
				if (element.getTagName().equals("appliances")) {
					loadAppliances(element);
				}
			}
		}
	}

	private static void loadAppliances(Element applianceRoot) {
		NodeList applianceNodes = applianceRoot.getElementsByTagName("appliances");

		for (int i = 0; i < applianceNodes.getLength(); i++) {
			Node node = applianceNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;

				String name = element.getElementsByTagName("localName").item(0).getTextContent();
				ApplianceType applianceType = ApplianceType
						.valueOf(element.getElementsByTagName("type").item(0).getTextContent());
				ForecastingMethod forecastingMethod = ForecastingMethod
						.valueOf(element.getElementsByTagName("forecastingMethod").item(0).getTextContent());

				try {
					jadeController.addApplianceAgent(name, applianceType, forecastingMethod, false);
				} catch (StaleProxyException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
