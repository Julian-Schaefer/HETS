package org.cos30018.hets.ui.custom;

import jade.core.AID;
import org.cos30018.hets.App;
import org.cos30018.hets.logic.appliance.Appliance;
import org.cos30018.hets.logic.appliance.ApplianceAgent;
import org.cos30018.hets.logic.retailer.Retailer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class WriteFile {

    private static String FILE_NAME = "config.xml";
    private Document doc;
    private Element root;

    public WriteFile() {

        try {

            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            doc = builder.newDocument();

            root = doc.createElement("Agents");
            doc.appendChild(root);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    public void writeAppliances(List<Appliance> list) {

        try {

            Element appliances = doc.createElement("Appliances");
            root.appendChild(appliances);


            for(int i = 0; i < list.size(); i++) {

                Element appliance = doc.createElement("Appliance");
                appliances.appendChild(appliance);

                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(String.valueOf(list.get(i))));
                appliance.appendChild(name);

                Element type = doc.createElement("Type");
                type.appendChild(doc.createTextNode(String.valueOf(list.get(i).getType())));
                appliance.appendChild(type);

                Element forecast = doc.createElement("Forecast");
                forecast.appendChild(doc.createTextNode(String.valueOf(list.get(i).getForecastingMethod())));
                appliance.appendChild(forecast);
            }

            //saving the document to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");


            DOMSource source = new DOMSource(doc);

            try {
                File configFile = new File(FILE_NAME);
                System.out.println(configFile.getAbsolutePath());

                FileWriter fileWriter = new FileWriter(configFile.getAbsolutePath());
                StreamResult result = new StreamResult(fileWriter);
                transformer.transform(source, result);

               // System.out.println(fileWriter);

            } catch (IOException | TransformerException e) {
                e.printStackTrace();
            }


        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

    }



    public void writeRetailers(List<Retailer> list) {

        try {

            Element appliances = doc.createElement("Retailers");
            root.appendChild(appliances);


            for(int i = 0; i < list.size(); i++) {

                Element retailer = doc.createElement("Retailer");
                appliances.appendChild(retailer);

                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(String.valueOf(list.get(i))));
                retailer.appendChild(name);

                Element strategy = doc.createElement("Strategy");
                strategy.appendChild(doc.createTextNode(String.valueOf(list.get(i).getStrategy())));
                retailer.appendChild(strategy);

                Element tariff = doc.createElement("Tariff");
                tariff.appendChild(doc.createTextNode(String.valueOf(list.get(i).getTariff())));
                retailer.appendChild(tariff);

            }

            //saving the document to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");


            DOMSource source = new DOMSource(doc);

            try {
                File configFile = new File(FILE_NAME);
                System.out.println(configFile.getAbsolutePath());

                FileWriter fileWriter = new FileWriter(configFile.getAbsolutePath());
                StreamResult result = new StreamResult(fileWriter);
                transformer.transform(source, result);

                // System.out.println(fileWriter);

            } catch (IOException | TransformerException e) {
                e.printStackTrace();
            }


        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

    }


}
