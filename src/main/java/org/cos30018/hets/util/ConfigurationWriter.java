package org.cos30018.hets.util;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.cos30018.hets.Configuration;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ConfigurationWriter {

	public static void writeConfig(Configuration config) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File configFile = chooser.getSelectedFile();

			XmlMapper xmlMapper = new XmlMapper();
			xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

			try {
				xmlMapper.writeValue(configFile, config);
			} catch (IOException e) {
				e.printStackTrace();
			}

			JOptionPane.showMessageDialog(null, "Configuration has been written!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
