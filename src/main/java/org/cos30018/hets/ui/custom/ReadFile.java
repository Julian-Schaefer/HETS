package org.cos30018.hets.ui.custom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {

    private static String FILE_NAME = "config.xml";
    private Document doc;
//    private Element root;

    public ReadFile() throws ParserConfigurationException, IOException, SAXException {

        File file = new File(FILE_NAME);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        doc = builder.parse(file);
        doc.getDocumentElement().normalize();
    }


    public List<List<String>> LoadAppliances() {

        NodeList nodeList = doc.getElementsByTagName("Appliance");

        List<List<String>> appList = new ArrayList<List<String>>();

        for (int i = 0; i < nodeList.getLength(); i++) {

            appList.add(getAppliance(nodeList.item(i)));
        }

        return appList;
    }


    private List<String> getAppliance(Node node) {

        String name = null, type = null, forecast = null;
        List<String> appliance = new ArrayList<>();

        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) node;
            name = getTagValue("Name", element);
            type = getTagValue("Type", element);
            forecast = getTagValue("Forecast", element);

            appliance.add(name);
            appliance.add(type);
            appliance.add(forecast);
        }
        return appliance;
    }


    public List<List<String>> LoadRetailers() {

        NodeList nodeList = doc.getElementsByTagName("Retailers");

        List<List<String>> retailerList = new ArrayList<List<String>>();

        for (int i = 0; i < nodeList.getLength(); i++) {

            retailerList.add(getRetailer(nodeList.item(i)));
        }

        return retailerList;
    }


    private List<String> getRetailer(Node node) {

        String name = null, strategy = null, tariff = null;
        List<String> retailer = new ArrayList<>();

        if (node.getNodeType() == Node.ELEMENT_NODE) {

            Element element = (Element) node;
            name = getTagValue("Name", element);
            strategy = getTagValue("Strategy", element);
            tariff = getTagValue("Tariff", element);

            retailer.add(name);
            retailer.add(strategy);
            retailer.add(tariff);
        }
        return retailer;
    }


    private String getTagValue(String name, Element element) {

        NodeList nodeList = element.getElementsByTagName(name).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);

        return node.getNodeValue();
    }
}
