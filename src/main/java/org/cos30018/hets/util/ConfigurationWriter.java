package org.cos30018.hets.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.cos30018.hets.Configuration;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
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
			SimpleModule mapSerializerModule = new SimpleModule("MapSerializerModule");
			mapSerializerModule.addSerializer(new MapToTuple());
			xmlMapper.registerModule(mapSerializerModule);

			try {
				xmlMapper.writeValue(configFile, config);
			} catch (IOException e) {
				e.printStackTrace();
			}

			JOptionPane.showMessageDialog(null, "Configuration has been written!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private static class MapToTuple extends JsonSerializer<Map<DoubleRange, DoubleRange>> {

		@Override
		public void serialize(Map<DoubleRange, DoubleRange> value, JsonGenerator gen, SerializerProvider serializers)
				throws IOException {
			gen.writeStartObject();
			gen.writeFieldName("blockRates");

			gen.writeStartArray();
			for (Map.Entry<DoubleRange, DoubleRange> entry : value.entrySet()) {
				gen.writeStartObject();
				gen.writeNumberField("from", entry.getKey().firstValue);
				gen.writeNumberField("upTo", entry.getKey().secondValue);
				gen.writeNumberField("volumeCharge", entry.getValue().firstValue);
				gen.writeNumberField("feedInRate", entry.getValue().secondValue);
				gen.writeEndObject();
			}

			gen.writeEndArray();
			gen.writeEndObject();
		}

		@Override
		public Class<Map<DoubleRange, DoubleRange>> handledType() {
			@SuppressWarnings("unchecked")
			Class<Map<DoubleRange, DoubleRange>> typeClass = (Class<Map<DoubleRange, DoubleRange>>) (Class<?>) Map.class;
			return typeClass;
		}
	}
}
