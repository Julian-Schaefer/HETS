package org.cos30018.hets.ui.custom;

import jade.core.AID;
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
import java.util.ArrayList;
import java.util.List;

public class WriteFile {

    private static String FILE_NAME = "config.xml";

    public void writeXmlFile(List<AID> list) {

        try {

            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("Agents");
            doc.appendChild(root);

            Element appliances = doc.createElement("Appliances");
            root.appendChild(appliances);


            for(int i = 0; i < list.size(); i++) {

                Element appliance = doc.createElement("Appliance");
                appliances.appendChild(appliance);

                Element name = doc.createElement("Name");
                name.appendChild(doc.createTextNode(String.valueOf(list.get(i))));
                appliance.appendChild(name);
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
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

}
