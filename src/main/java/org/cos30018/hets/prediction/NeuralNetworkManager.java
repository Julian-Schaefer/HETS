package org.cos30018.hets.prediction;

import static org.cos30018.hets.logic.appliance.Appliance.ApplianceType.DISHWASHER;
import static org.cos30018.hets.logic.appliance.Appliance.ApplianceType.DRYER;
import static org.cos30018.hets.logic.appliance.Appliance.ApplianceType.FRIDGE;
import static org.cos30018.hets.logic.appliance.Appliance.ApplianceType.HEAT_PUMP;
import static org.cos30018.hets.logic.appliance.Appliance.ApplianceType.HIFI;
import static org.cos30018.hets.logic.appliance.Appliance.ApplianceType.HOT_WATER_SYSTEM;
import static org.cos30018.hets.logic.appliance.Appliance.ApplianceType.WASHING_MACHINE;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cos30018.hets.logic.appliance.Appliance;

import com.opencsv.CSVReader;

public class NeuralNetworkManager {

	public double actual;
	public double lastPrediction;
	public double discrepency;
	public double newPrediction;
	public NeuralNetwork neuralNetwork;

	private Map<Appliance.ApplianceType, String> applianceTypeCodeMap = new HashMap<Appliance.ApplianceType, String>();

	// Data stored locally on agents
	// list of the actual electricity data values
	private List<Double> dataElectricity = new ArrayList<>();
	// list of actual data values for training
	private List<Double> dataTrainingElectricity = new ArrayList<>();
	// current index of the electricity data list
	private int indexElectricity;

	// Global data, read and stored only once
	private static List<Double> dataTrainingTemperature = new ArrayList<>();
	private static List<Double> dataTemperature = new ArrayList<>();

	// current index of the weather lists (hourly)
	private int indexWeather = 0;

	// time in minutes, 24 hour (resets at 1140)
	private int time = 0;

	private void readLocalData(String applianceReferenceCode) {
		// actual reading
		actual = 0;
		// last predicted reading
		lastPrediction = 0;
		// discrepancy
		discrepency = 0;
		// new prediction
		newPrediction = 0;

		// name of appliance (according to csv file)
		String dataToGet = applianceReferenceCode;

		// index is initialised to -1 flag
		int indexToGet = -1;

		// read data from csv file
		try {
			FileReader filereader = new FileReader("Electricity_P_DS.csv");
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord;
			String[] header = csvReader.readNext();

			for (int i = 0; i < (header.length) - 1; i++) {
				if (header[i].equals(dataToGet)) {
					indexToGet = i;
				}
			}

			// double checks valid input from the file
			if (indexToGet != -1) {
				System.out.println("Data location: success");
			} else {
				System.out.println("Data Location: fail");
			}

			// read the first 30,000 data values from the csv file, given the specified
			// appliance.
			// Ignores null values
			for (int i = 0; i < 35000; i++) {
				nextRecord = csvReader.readNext();
				if (nextRecord != null) {
					if ((nextRecord[indexToGet] != null) && (indexToGet != -1)) {
						// get 5,000 results for training and the rest for actual
						if (i < 30000) {
							dataTrainingElectricity.add(Double.parseDouble(nextRecord[indexToGet]));
						} else {
							dataElectricity.add(Double.parseDouble(nextRecord[indexToGet]));
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void readWorldData() {
		// name of appliance (according to csv file)
		String dataToGet = "Temp (C)";

		// index is initialised to -1 flag
		int indexToGet = -1;

		// read data from csv file
		try {
			FileReader filereader = new FileReader("Climate_HourlyWeather.csv");
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord;
			String[] header = csvReader.readNext();

			for (int i = 0; i < (header.length) - 1; i++) {
				if (header[i].equals(dataToGet)) {
					indexToGet = i;
				}
			}

			// double checks valid input from the file
			if (indexToGet != -1) {
				System.out.println("Data location: success");
			} else {
				System.out.println("Data Location: fail");
			}

			// read the first 30,000 data values from the csv file, given the specified
			// appliance.
			// Ignores null values
			for (int i = 0; i < 35000; i++) {
				nextRecord = csvReader.readNext();
				if (indexToGet != -1) {
					if (nextRecord[indexToGet] != null && !nextRecord[indexToGet].isEmpty()) {
						// get 5,000 results for training and the rest for actual
						if (i < 30000) {
							dataTrainingTemperature.add(Double.parseDouble((nextRecord[indexToGet])));
							i++;
							dataTrainingTemperature.add(Double.parseDouble((nextRecord[indexToGet])));
						} else {
							dataTemperature.add(Double.parseDouble(nextRecord[indexToGet]));
							i++;
							dataTemperature.add(Double.parseDouble(nextRecord[indexToGet]));
						}
					}

					// if null, use the last known data point to populate
					else {
						Double toAdd;
						if (i < 30000) {
							toAdd = dataTrainingTemperature.get(dataTrainingTemperature.size() - 1);
							dataTrainingTemperature.add(toAdd);

							i++;
							dataTrainingTemperature.add(toAdd);
						} else {
							toAdd = dataTemperature.get(dataTemperature.size() - 1);
							dataTemperature.add(toAdd);

							i++;
							dataTemperature.add(toAdd);
						}

					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public NeuralNetworkManager() {
		// actual reading
		actual = 0;
		// last predicted reading
		lastPrediction = 0;
		// discrepancy
		discrepency = 0;
		// new prediction
		newPrediction = 0;

		// read in global data on the initial run
		readWorldData();
		;
		readLocalData("HPE");
		neuralNetwork = new NeuralNetwork();

		List<List<Double>> tempData = new ArrayList<>();

		tempData.add(dataElectricity);

		neuralNetwork.Train(dataElectricity, tempData, 0, 0);

	}

	public NeuralNetworkManager(Appliance.ApplianceType type) {
		// DISHWASHER, FRIDGE, WASHING_MACHINE,DRYER, HEAT_PUMP, HOT_WATER_SYSTEM, HIFI
		// Setup dictionary values
		applianceTypeCodeMap.put(DISHWASHER, "DWE");
		applianceTypeCodeMap.put(FRIDGE, "FGE");
		applianceTypeCodeMap.put(WASHING_MACHINE, "CWE");
		applianceTypeCodeMap.put(DRYER, "CDE");
		applianceTypeCodeMap.put(HEAT_PUMP, "HPE");
		applianceTypeCodeMap.put(HOT_WATER_SYSTEM, "HTE");
		applianceTypeCodeMap.put(HIFI, "TVE");

		// actual reading
		actual = 0;
		// last predicted reading
		lastPrediction = 0;
		// discrepancy
		discrepency = 0;
		// new prediction
		newPrediction = 0;
		readWorldData();
		readLocalData(applianceTypeCodeMap.get(type));
		neuralNetwork = new NeuralNetwork();

		List<List<Double>> tempData = new ArrayList<>();

		tempData.add(dataElectricity);

		neuralNetwork.Train(dataElectricity, tempData, 0, 0);

	}

	public void TrainNeuralNetwork() {
		List<List<Double>> tempData = new ArrayList<>();

		tempData.add(dataTrainingElectricity);
		tempData.add(dataTrainingTemperature);

		neuralNetwork.Train(dataElectricity, tempData, 0, 0);
	}

	// test run method
	public void RunBasic() {

		actual = actual + 1;
		lastPrediction = newPrediction;
		discrepency = lastPrediction - newPrediction;
		newPrediction = actual + 1;
	}

	// prediction method where t+1 prediction is the value of t (current)
	public void RunTPlus() {
		actual = dataElectricity.get(indexElectricity);
		discrepency = actual - newPrediction;
		lastPrediction = newPrediction;

		newPrediction = actual;

		indexElectricity = indexElectricity + 1;
		// if all data has been used, start again from index 0
		if (indexElectricity == dataElectricity.size()) {
			indexElectricity = 0;
		}

	}

	public void runNeuralNetwork(int time) {
		indexElectricity = time;
		indexWeather = time;
		actual = dataElectricity.get(indexElectricity);
		discrepency = actual - newPrediction;
		lastPrediction = newPrediction;

		List<Double> inputList = new ArrayList<>();
		inputList.add(actual);
		newPrediction = neuralNetwork.Run(inputList);
		indexElectricity = indexElectricity + 1;
		time = time + 1;

		if (time == 1440) {
			time = 0;
		}

		// iterate weather every hour

		// if all data has been used, start again from index 0
		// NOTE: Do the same for weather when possible
		if (indexElectricity == dataElectricity.size()) {
			indexElectricity = 0;
		}
	}

	public double getActualUsage(int period) {
		return dataElectricity.get(period);
	}
}
