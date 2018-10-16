package prediction;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Predictor {
    public int actual;
    public int lastp;
    public int disc;
    public int newp;
    public NeuralNetwork NN;


    //DATA STORED LOCALLY ON AGENT
    //list of the actual electricity data values
    private List<Integer> dataElectricity = new ArrayList<Integer>();
    //list of actual data values for training
    private List<Integer> dataTrainingElectricity = new ArrayList<>();

    //current index of the electricity data list
    private int indexElectricity;
    //current index of the weather lists (hourly)
    private int indexWeather = 0;

    //time in minutes, 24 hour (resets at 1140)
    private int time = 0;

    public void ReadLocalData(){
        //actual reading
        actual = 0;
        //last predicted reading
        lastp = 0;
        //discrepancy
        disc = 0;
        //new prediction
        newp = 0;

        //name of appliance (according to csv file)
        String dataToGet = "HPE";
        String weather;

        //index is initialised to -1 flag
        int indexToGet = -1;

        //read data from csv file
        try {
            FileReader filereader = new FileReader("Electricity_P.csv");
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            String [] header = csvReader.readNext();



            for (int i = 0; i < (header.length)-1; i++) {
                if (header[i].equals(dataToGet)){
                    indexToGet = i;
                }
            }

            //double checks valid input from the file
            if (indexToGet!=-1) { System.out.println("Data location: success"); }
            else{System.out.println("Data Location: fail");}

            //read the first 800,000 data values from the csv file, given the specified appliance.
            //Ignores null values
            for (int i = 0; i < 800000; i++) {
                nextRecord = csvReader.readNext();
                if (nextRecord!=null)
                {
                    if ((nextRecord[indexToGet]!=null) && (indexToGet!=-1)){
                        //get 600,000 results for training and the rest for actual
                        if (i < 600000)
                        {
                            dataTrainingElectricity.add(Integer.parseInt(nextRecord[indexToGet]));
                        }
                        else{
                            dataElectricity.add(Integer.parseInt(nextRecord[indexToGet]));
                        }

                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReadWorldData()
    {

    }

    public Predictor(){
        ReadLocalData();
        NN = new NeuralNetwork();

        List<List<Integer>> tempData = new ArrayList<>();

        tempData.add(dataElectricity);

        NN.Train(dataElectricity,tempData,0,0);

    }

    //test run method
    public void RunBasic(){

        actual = actual + 1;
        lastp = newp;
        disc = lastp - newp;
        newp = actual + 1;
    }

    //prediction method where t+1 prediction is the value of t (current)
    public void RunTPlus(){
        actual = dataElectricity.get(indexElectricity);
        disc = actual - newp;
        lastp = newp;

        newp = actual;


        indexElectricity = indexElectricity + 1;
        //if all data has been used, start again from index 0
        if (indexElectricity ==dataElectricity.size())
        {
            indexElectricity = 0;
        }

    }


    public void RunNeuralNetwork(){
        actual = dataElectricity.get(indexElectricity);
        disc = actual - newp;
        lastp = newp;

        List<Integer> inputList = new ArrayList<>();
        inputList.add(actual);

        newp = NN.Run(inputList);
        indexElectricity = indexElectricity + 1;
        time = time + 1;

        if (time==1440)
        {
            time = 0;
        }

        //iterate weather every hour
        if (indexElectricity%60==0){
            indexWeather = indexWeather + 1;
            System.out.println(indexWeather);
        }

        //if all data has been used, start again from index 0
        //NOTE: Do the same for weather when possible
        if (indexElectricity ==dataElectricity.size())
        {
            indexElectricity = 0;
        }
    }
}
