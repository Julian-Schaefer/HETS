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

	//constants to set neural network structure values
	public static final int HIDDEN_LAYERS = 1;
	public static final int HIDDEN_LAYER_SIZE = 16;
	public static final int TRAINING_SESSIONS = 100;
	public static final int TOTAL_RECORDS = 35000;
	public static final int TRAINING_RECORDS = 30000;



	public double actual;
	public double lastPrediction;
	public double discrepancy;
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
	// current index of the weather lists (half-hourly)
	private int indexWeather = 0;

	private static List<Double> dataTrainingTime = new ArrayList<>();
	private static List<Double> dataTime = new ArrayList<>();
	private int indexTime = 0;

	private static List<Double> dataTrainingDay = new ArrayList<>();
	private static List<Double> dataDay = new ArrayList<>();
	private int indexDay = 0;



	// time in minutes, 24 hour (resets at 1140)
	private int time = 0;

	private void readLocalData(String applianceReferenceCode) {
		// actual reading
		actual = 0;
		// last predicted reading
		lastPrediction = 0;
		// discrepancy
		discrepancy = 0;
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
			for (int i = 0; i < TOTAL_RECORDS; i++) {
				nextRecord = csvReader.readNext();
				if (nextRecord != null) {
					if ((nextRecord[indexToGet] != null) && (indexToGet != -1)) {
						// get 5,000 results for training and the rest for actual
						if (i < TRAINING_RECORDS) {
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
			for (int i = 0; i < TOTAL_RECORDS; i++) {
				nextRecord = csvReader.readNext();
				if (indexToGet != -1) {
					if (nextRecord[indexToGet] != null && !nextRecord[indexToGet].isEmpty()) {
						// get 5,000 results for training and the rest for actual
						if (i < TRAINING_RECORDS) {
							dataTrainingTemperature.add(Double.parseDouble((nextRecord[indexToGet])));
							dataTrainingTime.add(periodToTime(i));
							dataTrainingDay.add(periodToDay(i));
							i++;
							dataTrainingTemperature.add(Double.parseDouble((nextRecord[indexToGet])));
							dataTrainingTime.add(periodToTime(i));
							dataTrainingDay.add(periodToDay(i));
						} else {
							dataTemperature.add(Double.parseDouble(nextRecord[indexToGet]));
							dataTime.add(periodToTime(i));
							dataDay.add(periodToDay(i));
							i++;
							dataTemperature.add(Double.parseDouble(nextRecord[indexToGet]));
							dataTime.add(periodToTime(i));
							dataDay.add(periodToDay(i));
						}
					}

					// if null, use the last known data point to populate
					else {
						Double toAdd;
						if (i < TRAINING_RECORDS) {
							toAdd = dataTrainingTemperature.get(dataTrainingTemperature.size() - 1);
							dataTrainingTemperature.add(toAdd);
							dataTrainingTime.add(periodToTime(i));
							dataTrainingDay.add(periodToDay(i));
							i++;
							dataTrainingTemperature.add(toAdd);
							dataTrainingTime.add(periodToTime(i));
							dataTrainingDay.add(periodToDay(i));
						} else {
							toAdd = dataTemperature.get(dataTemperature.size() - 1);
							dataTemperature.add(toAdd);
							dataTime.add(periodToTime(i));
							dataDay.add(periodToDay(i));

							i++;
							dataTemperature.add(toAdd);
							dataTime.add(periodToTime(i));
							dataDay.add(periodToDay(i));
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
		discrepancy = 0;
		// new prediction
		newPrediction = 0;

		// read in global data on the initial run
		readWorldData();
		readLocalData("HPE");
		neuralNetwork = new NeuralNetwork();

		List<List<Double>> tempData = new ArrayList<>();

		tempData.add(dataElectricity);

		neuralNetwork.train(dataElectricity, tempData, HIDDEN_LAYERS, HIDDEN_LAYER_SIZE, TRAINING_SESSIONS);

	}

	public NeuralNetworkManager(Appliance.ApplianceType type, boolean toTrain) {
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
		discrepancy = 0;
		// new prediction
		newPrediction = 0;
		readWorldData();
		readLocalData(applianceTypeCodeMap.get(type));
		neuralNetwork = new NeuralNetwork();

		if (toTrain)
		{
			TrainNeuralNetwork(0,0);
		}

	}

	public void TrainNeuralNetwork(int hiddenLayers, int layerSize) {
		List<List<Double>> tempData = new ArrayList<>();

		tempData.add(dataTrainingElectricity);
		tempData.add(dataTrainingTemperature);
		tempData.add(dataTrainingTime);
		tempData.add(dataTrainingDay);

		neuralNetwork.train(dataElectricity, tempData, HIDDEN_LAYERS, HIDDEN_LAYER_SIZE, TRAINING_SESSIONS);
	}

	// test run method
	public void RunBasic(int time) {

		actual = actual + 1;
		lastPrediction = newPrediction;
		discrepancy = lastPrediction - newPrediction;
		newPrediction = actual + 1;
	}

	// prediction method where t+1 prediction is the output of t (current)
	public void RunTPlus(int time) {
		actual = dataElectricity.get(indexElectricity);
		discrepancy = actual - newPrediction;
		lastPrediction = newPrediction;

		newPrediction = actual;

		indexElectricity = indexElectricity + 1;
		// if all data has been used, start again from index 0
		if (indexElectricity == dataElectricity.size()) {
			indexElectricity = 0;
		}

	}

	//current period to thirty minute time interval
	public static double periodToTime(int period)
	{
		return period%48;
	}

	public static double periodToDay(int period)
	{
		int day = 0;
		int time = period%336;

		if ((time >= 0 ) && (time < 48))
		{
			day = 1;
		}
		if ((time >= 48 ) && (time < 96))
		{
			day = 2;
		}
		if ((time >= 96 ) && (time < 144))
		{
			day = 3;
		}
		if ((time >= 148 ) && (time < 192))
		{
			day = 4;
		}
		if ((time >= 192 ) && (time < 240))
		{
			day = 5;
		}
		if ((time >= 240 ) && (time < 288))
		{
			day = 6;
		}
		if ((time >= 288 ) && (time < 336))
		{
			day = 7;
		}

		return day;
	}

	public void runNeuralNetwork(int period) {
		indexElectricity = period*2;
		indexWeather = period*2;
		indexTime = period*2;
		indexDay = period*2;



		discrepancy = actual - newPrediction;
		lastPrediction = newPrediction;

		List<Double> inputList = new ArrayList<>();
		inputList.add(actual);
		inputList.add(dataTemperature.get(indexWeather));
		inputList.add(dataTime.get(indexTime));
		inputList.add(dataDay.get(indexDay));
		//inputList.add(time);
		//inputList.add(day);

		newPrediction = neuralNetwork.run(inputList);
		indexElectricity = indexElectricity + 1;


		// if all data has been used, start again from index 0
		if (indexElectricity >= dataElectricity.size()) {
			indexElectricity = 0;
		}
        if (indexWeather >= dataTemperature.size()) {
            indexElectricity = 0;
        }
		if (indexTime >= dataTime.size()) {
			indexTime = 0;
		}
		if(indexDay >= dataDay.size()){
			indexDay = 0;
		}

		actual = dataElectricity.get(indexElectricity);

	}

	public double getActualUsage(int period) {
		return dataElectricity.get(period*2);
	}
}
