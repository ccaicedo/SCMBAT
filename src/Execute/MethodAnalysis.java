/*
Copyright (C) 2016 Syracuse University

This file is part of the Spectrum Consumption Model Builder and
Analysis Tool

This program is free software; you can redistribute it and/or modify it
under the terms of the GNU General Public License as published by the
Free Software Foundation; either version 3 of the License, or (at your
option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
for more details.

You should have received a copy of the GNU General Public License
along with program.  If not, see <http://www.gnu.org/licenses/>.

*/

/**
* MethodAnalysis.java
* Computes compatibility for Tx and Rx models 
* depending on the selected compatibility method.  
*/

package Execute;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ieee.dyspansc._1900._5.scm.Rating;
import org.ieee.dyspansc._1900._5.scm.RxModel;
import org.ieee.dyspansc._1900._5.scm.SCMSchedule;
import org.ieee.dyspansc._1900._5.scm.TxModel;

import SCM_home.Home;
import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;
import dk.ange.octave.type.OctaveDouble;
import dk.ange.octave.type.OctaveObject;
import dk.ange.octave.type.OctaveString;
import dk.ange.octave.io.*;
import dk.ange.octave.io.spi.OctaveDataReader;
import reportsGeneration.LoadCompatibilityReport;

public class MethodAnalysis {

	PrintTxText printTx = new PrintTxText();
	PrintRxText printRx = new PrintRxText();
	Warn warningDisplay = new Warn();

	// Create the print results object
	PrintReportResults printRep = new PrintReportResults();

	final Logger logger = Logger.getLogger(MethodAnalysis.class);

	// Adding the flag required for showing the warnings in a single window
	public boolean warningFlag = false;

	// the warning message containing all the warnings
	public String warningMessage = "\n";

	// For environment variables
	ProcessBuilder pb = new ProcessBuilder("echo", "");
	Map<String, String> envMap = pb.environment();

	// Directory Path name variables
	public String compatTestDirectory = "";
	
	//string to store the result and store it
	public String result = "";
	
	//Lists for the Octave return values, 4 lists are returned irrespective of which function is called
	public ArrayList<OctaveDouble> retVal1 = new ArrayList<OctaveDouble>();
	public ArrayList<OctaveDouble> retVal2 = new ArrayList<OctaveDouble>();
	public ArrayList<OctaveDouble> retVal3 = new ArrayList<OctaveDouble>();
	public ArrayList<OctaveDouble> retVal4 = new ArrayList<OctaveDouble>();

	// Finding the path where the script files are stored and setting the
	// environment variable
	private void setFilePathEnvironment() {
		String line;
		String homeDirectoryPath = "";
		try {
			FileReader filereader = new FileReader("HomeDirectoryPath.txt");

			BufferedReader bufferedReader = new BufferedReader(filereader);

			while ((line = bufferedReader.readLine()) != null) {
				if (line != null && !line.isEmpty()) {
					homeDirectoryPath = line.split("=")[1];
				}
			}

			// Always close files.
			bufferedReader.close();

			// Setting the environment variable for the complete path

			envMap.put("SCM_PATH", homeDirectoryPath);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	// Getting the environment variable
	public String getFilePath() {
		String directoryPath = "";
		try {
			Set<String> keys = envMap.keySet();
			for (String key : keys) {
				if (key.equals("SCM_PATH")) {
					directoryPath = envMap.get(key);
					// System.out.println(key+" ==> "+directoryPath);
					return directoryPath;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return directoryPath;
	}

	public MethodAnalysis() {
		// Set the environment variable
		setFilePathEnvironment();
		logger.addAppender(Home.appender);
	}

	// Analysing which compatibility method to use based on Model types.
	public String analyseRatedMethod(ArrayList<TxModel> TxData, ArrayList<RxModel> RxData) {

		String ratedMethod = "null";

		if (RxData.size() == 1) {

			RxModel Rx = RxData.get(0);

			if (Rx.getUnderlayMask().get(0).getRating() != null) {
				Rating ratedMask = Rx.getUnderlayMask().get(0).getRating();
				if (ratedMask.getRatedBW() != null) {
					ratedMethod = "ratedBW";
				}
				if (ratedMask.getBwRatedList() != null) {
					ratedMethod = "bwRatedList";
				}
				if (ratedMask.getRatedBTP() != null) {
					ratedMethod = "ratedBTP";
				}
				if (ratedMask.getBtpRatedList() != null) {
					ratedMethod = "btpRatedList";
				}
				if (ratedMask.getDcRatedList() != null) {
					ratedMethod = "dcRatedList";
				}
				if (ratedMask.getPorPIndex() != null) {
					ratedMethod = "pOrPIndex";
				}
			} else {
				ratedMethod = Rx.getUnderlayMask().get(0).getMaskPowerMarginMethod();
			}
		}
		return ratedMethod;
	}

	public String getCompatTestDirectory() {
		return compatTestDirectory;
	}

	public void setCompatTestDirectory(String compatTestDirectory) {
		this.compatTestDirectory = compatTestDirectory;
	}
	
	//split arrayList on the basis of delimiter number - used from the reply received from Octave
	public ArrayList<OctaveDouble> splistArrayList(ArrayList<OctaveDouble> list) {
		ArrayList<OctaveDouble> al = new ArrayList<OctaveDouble>();

		int ptr1 = 0, ptr2 = 0;
		
		//keep on looping while ptr2 reaches the end
		while(ptr2 < list.size()) {
			//first find the delimiter
			while(true) {
				if(Double.valueOf(list.get(ptr2).toString()) == 123456.789)
					break;
				ptr2++;
			}
			//reaching here we found our delimiter
			//now we take out the substring
			al.add((OctaveDouble) list.subList(ptr1, ptr2));
			ptr1 = ptr2++;
		}
		return al;
	}
	
//function to generate result file
	public void genreateResultFile(String resultFilePath, String method, String result, String powerMargin) {
		PrintWriter writer = null;
		File file = new File(resultFilePath);
		file.delete();
		try {
			writer = new PrintWriter(resultFilePath, "UTF-8");
			writer.println("Compatibility Analysis Report Results");
		} catch (FileNotFoundException e1) {
			logger.error("File not found " + resultFilePath);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		/*
		 * Write the results into the Report file
		 */
		try {
			// Add in the results file

			writer.println(method);
			writer.println();
			writer.println("Compatibility Result: " + result);
			if(powerMargin != null) {
				writer.println("");
				writer.println("Power Margin: " + powerMargin);
			}

		} catch (Exception e) {
			logger.error(e.toString());
		}
		writer.close();
	}
	
	//replace all the non- alphanumeric chars  in the fileName before sending it to the Octave
	public String replaceNonAlnum(String strng) {
		return strng.replaceAll("[^A-Za-z0-9]", "");
	}
	
	//this function is responsible for initiating the connection to the Octave part of the application
	//it uses JavaOctave library for establishing the communication
	public ArrayList<String> initOctave(File workingDir, String methodTyp, String loggingEnabled, String reportDir, String specTagg, String numberOfTx, String execPattern) {
		String secondLastLine = null, lastLine = null;
		try {
			//create the JavaOctave objects
			OctaveEngineFactory octaveFactory = new OctaveEngineFactory();
			octaveFactory.setWorkingDir(workingDir);
			OctaveEngine octave = octaveFactory.getScriptEngine();
			
			//convert normal Java string into Octave String, that could be sent to Octave by JavaOctave
			OctaveString methodType = new OctaveString(methodTyp);
			OctaveString logging = new OctaveString(loggingEnabled);
			OctaveString reportdirectoryString = new OctaveString(reportDir);
			OctaveString specTag = new OctaveString(specTagg);
			OctaveString transmitterNum = new OctaveString(numberOfTx);
			OctaveString executionPattern = new OctaveString(execPattern);
			
			//add the newly created OctaveStrings to the Octave object, so that it may be sent
			octave.put("methodType", methodType);
			octave.put("logging", logging);
			octave.put("reportdirectoryString", reportdirectoryString);
			octave.put("specTag", specTag);
			octave.put("transmitterNum", transmitterNum);
			octave.put("executionPattern", executionPattern);
			
			//set custom Octave writer so that we may read the output
			StringWriter writer = new StringWriter();
			octave.setWriter(writer);
			
			//heart of the function, initiates the Octave connection
			try {
			octave.eval("[retVal1, retVal2, retVal3, retVal4] = Coupler(reportdirectoryString, methodType, logging, specTag, transmitterNum, executionPattern)");
			}
			catch(Exception e) {}
			
			//fetch the returned values by the Octave
			retVal1.add(octave.get(OctaveDouble.class, "retVal1"));
			retVal2.add(octave.get(OctaveDouble.class, "retVal2"));
			retVal3.add(octave.get(OctaveDouble.class, "retVal3"));
			retVal4.add(octave.get(OctaveDouble.class, "retVal4"));

			//convert the writer output into string
			String reader = writer.toString();
			
			//declare string variable to read line by line data
			String line;
			ArrayList<String> dispData = new ArrayList<String>();
			
			//converting data into inputStream
			InputStream is = new ByteArrayInputStream( reader.getBytes( "UTF-8"));
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			
			try {
				while (br.ready()) {
					line = br.readLine();
					
					//if line contains the word compatibility, then this is the result line
					//this result as of now is used only in case of dutyCycle.
					if(line.contains("compatible"))
						result = line;
					
					System.out.println(line);
	
					logger.info(line);
					dispData.add(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//fetching the overall size of data
			int DataLength = dispData.size();
			
			//fetch the secondLast line - this tells whether system is compatible or not
			//or this may also be set to result
			secondLastLine = dispData.get(DataLength - 6);
			
			//fetch the lastLine, this tells the value
			lastLine = dispData.get(DataLength - 5);
	
			octave.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (new ArrayList<String>(Arrays.asList(secondLastLine, lastLine)));
	}

// Executing compatibility based on method specified. 	
	public void execCompat(String method, int[] txIndex, ArrayList<TxModel> TxData, ArrayList<SCM_home.Model> txArray,
			int[] rxIndex, ArrayList<RxModel> RxData, ArrayList<SCM_home.Model> rxArray, Boolean OctaveLogging,
			Boolean reportGeneration, String resultFilePath) {

		// ExecuteFrame execFrame =null;
		String Command0 = null;
		String Command1 = null;
		String CompatStat = null;
		String PowerMargin = null;
		String dirName = getFilePath();
		String txFileName = "";
		PrintWriter printfile = null;
		
		if (dirName == "") {
			warningMessage = warningMessage + "\nError in getting directory path for Compatability Analysis";
		}

		// Create the folder for each test
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM_HH:mm");
		Timestamp current_timestamp = new Timestamp(System.currentTimeMillis());
		String current_Dir = dirName + "Reports/" + sdf.format(current_timestamp);

		boolean created_success = new File(current_Dir).mkdirs();

		// Update the file that must the results of transmitter and receiver
		// compatibility report details
		String printTFile = dirName + "Octave/SCM_transmitter_java";
		String printRFile = dirName + "Octave/SCM_receiver_java.txt";
		String resultFile = current_Dir + "/Results.txt";

		try {
			File prntFile = new File(resultFile);
			prntFile.getParentFile().mkdirs();
			printfile = new PrintWriter(resultFile);
			printfile.println("Compatibility Analysis Report Results");
		} catch (FileNotFoundException e1) {

			logger.error("File not found " + resultFile);
		}

		if (!created_success) {
			warningMessage = warningMessage + "\nError in creating the directory for Compatability Analysis Test";
			return;
		}
		this.setCompatTestDirectory(current_Dir);

		// Set the template File path
		LoadCompatibilityReport loadCompRep = new LoadCompatibilityReport(dirName + "Reports/HTMLTemplates");
		File workingDir = new File(dirName + "Octave");

		switch (method) {
		case "TotalPower":
			System.out.println("Total Power Method being executed");
			logger.info("Total Power Method being executed");
			
			//modifying the name of the printTFile
			//since in this case we only have a single transmitter file we will name it 1.txt
			txFileName = printTFile + "1" + ".txt";
			warningMessage = warningMessage + printTx.printText(TxData.get(0), txFileName);
			warningMessage = warningMessage + printRx.printText(RxData.get(0), printRFile);

			int o = 0;
			SCMSchedule rxSched = RxData.get(0).getScmSchedule().get(o);
			SCMSchedule txSched = TxData.get(0).getScmSchedule().get(o);
			GregorianCalendar Tx_Start_time = txSched.getStartTime().toGregorianCalendar();
			GregorianCalendar Tx_End_time = txSched.getEndTime().toGregorianCalendar();
			GregorianCalendar Rx_Start_time = rxSched.getStartTime().toGregorianCalendar();
			GregorianCalendar Rx_End_time = rxSched.getEndTime().toGregorianCalendar();

			if ((Tx_Start_time.before(Rx_Start_time) && Tx_End_time.before(Rx_Start_time))
					|| (Tx_Start_time.after(Rx_End_time) && Tx_End_time.after(Rx_End_time))) {

				warningFlag = true;
				warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";
			//	new Warn().setWarn("Systems Compatible", "Systems are compatible due to non-overlapping schedules");
				
			}else{
				
				String totPowFile = dirName + "TotPow.sh " + dirName + " " + compatTestDirectory;
				System.out.println("total power file is : " + totPowFile);
				Command0 = "chmod u+x " + totPowFile;
				Command1 = totPowFile + " " + OctaveLogging;
				System.out.println("Command0 is : " + Command0);
				System.out.println("Command1 is : " + Command1);

				//passing arguments to be passed to the octave code, therefore they have been formatted so that octave code
				//may understand them
				ArrayList<String> returnedList = initOctave(workingDir, "TotalPower", OctaveLogging.toString(), compatTestDirectory, "null", "1", "null");
				CompatStat = (String) returnedList.get(0);
				PowerMargin =  (String) returnedList.get(1);
				
				genreateResultFile(resultFile, "Method: Total Power", CompatStat, PowerMargin);

				/*
				 * execFrame = new ExecuteFrame(); execFrame.setPlotPath(compatTestDirectory);
				 * execFrame.getFrame(CompatStat,PowerMargin);
				 */

				// Call the load html page method
				if(reportGeneration)
					loadCompRep.displayCompatibilityReport(CompatStat, PowerMargin, compatTestDirectory, "Total Power");
				
				//generate result file from the command line if the path is specified
				if(resultFilePath != null)
					genreateResultFile(resultFilePath, "Method: Total Power", CompatStat, PowerMargin);
			}

			break;
		case "MaximumPowerDensity":
			System.out.println("Max Power Density Method being executed");
			logger.info("Max Power Density Method being executed");
			//modifying the name of the printTFile
			//since in this case we only have a single transmitter file we will name it 1.txt
			txFileName = printTFile + "1" + ".txt";
			warningMessage = warningMessage + printTx.printText(TxData.get(0), txFileName);
			warningMessage = warningMessage + printRx.printText(RxData.get(0), printRFile);

			o = 0;
			SCMSchedule rxSched2 = RxData.get(0).getScmSchedule().get(o);
			SCMSchedule txSched2 = TxData.get(0).getScmSchedule().get(o);
			GregorianCalendar Tx_Start_time2 = txSched2.getStartTime().toGregorianCalendar();
			GregorianCalendar Tx_End_time2 = txSched2.getEndTime().toGregorianCalendar();
			GregorianCalendar Rx_Start_time2 = rxSched2.getStartTime().toGregorianCalendar();
			GregorianCalendar Rx_End_time2 = rxSched2.getEndTime().toGregorianCalendar();

			if ((Tx_Start_time2.before(Rx_Start_time2) && Tx_End_time2.before(Rx_Start_time2))
					|| (Tx_Start_time2.after(Rx_End_time2) && Tx_End_time2.after(Rx_End_time2))) {

				warningFlag = true;
				warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";
				// new Warn().setWarn("Systems Compatible", "Systems are compatible due to
				// non-overlapping schedules");

			} else {

				String maxPowFile = dirName + "MaxPow.sh " + dirName + " " + compatTestDirectory;
				Command0 = "chmod u+x " + maxPowFile;
				Command1 = maxPowFile + " " + OctaveLogging;
				System.out.println("The command 0 is : " + Command0);
				System.out.println("The command 1 is : " + Command1);
				
				//passing arguments to be passed to the octave code, therefore they have been formatted so that octave code
				//may understand them
				ArrayList<String> returnedList = initOctave(workingDir, "MaxPower", OctaveLogging.toString(), compatTestDirectory, "null", "1", "null");
				CompatStat = (String) returnedList.get(0);
				PowerMargin =  (String) returnedList.get(1);

				//generate result file in the reports folder
				genreateResultFile(resultFile, "Method: MaximumPowerDensity", CompatStat, null);

				/*
				 * execFrame = new ExecuteFrame(); execFrame.getFrame(CompatStat,PowerMargin);
				 */
				// Call the load html page method
				if(reportGeneration)
					loadCompRep.displayCompatibilityReport(CompatStat, PowerMargin, compatTestDirectory, "MaximumPowerDensity");
				
				//generate result file from the command line if the path is specified
				if(resultFilePath != null)
					genreateResultFile(resultFilePath, "Method: MaximumPowerDensity", CompatStat, null);
			}

			break;
// Rated BW Analysis

		case "ratedBW":
			System.out.println("Rated BW Analysis Running");
			logger.info("Rated BW Analysis Running");

			warningMessage = warningMessage + printRx.printText(RxData.get(0), printRFile);

			o = 0;
			SCMSchedule rxSched_bw = RxData.get(0).getScmSchedule().get(o);
			SCMSchedule txSched_bw = TxData.get(0).getScmSchedule().get(o);
			GregorianCalendar Tx_Start_time_bw = txSched_bw.getStartTime().toGregorianCalendar();
			GregorianCalendar Tx_End_time_bw = txSched_bw.getEndTime().toGregorianCalendar();
			GregorianCalendar Rx_Start_time_bw = rxSched_bw.getStartTime().toGregorianCalendar();
			GregorianCalendar Rx_End_time_bw = rxSched_bw.getEndTime().toGregorianCalendar();

			if ((Tx_Start_time_bw.before(Rx_Start_time_bw) && Tx_End_time_bw.before(Rx_Start_time_bw))
					|| (Tx_Start_time_bw.after(Rx_End_time_bw) && Tx_End_time_bw.after(Rx_End_time_bw))) {

				warningFlag = true;
				warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";

				// new Warn().setWarn("Systems Compatible", "Systems are compatible due to
				// non-overlapping schedules");

			} else {

				ArrayList<OctaveDouble> SpecMask = new ArrayList<OctaveDouble>();
				final ArrayList<OctaveDouble> PSD = new ArrayList<OctaveDouble>();
				final ArrayList<OctaveDouble> BW = new ArrayList<OctaveDouble>();
				ArrayList<OctaveDouble> compatBWList = new ArrayList<OctaveDouble>();
				
				//the names of all the transmitter files are concatenated into the same list 
				//because they need to be sent to Octave for plotting
				String specNameList = "";


					for (int i = 0; i < TxData.size(); i++) {
						//modifying the name of the printTFile
						//since in this case we only have a single transmitter file we will name it as 1.txt, 2.txt ...so on
						int ind = i+1;
						txFileName = printTFile + ind + ".txt";
						warningMessage = warningMessage + printTx.printText(TxData.get(i), txFileName);
						
						//fetch the model name and add it to the string along with the string length
						//we add string length so that while reading inside Octave code we can identify how long is the name of 
						//current transmitter model
						String modelName = txArray.get(txIndex[i]).ModelName;
						modelName = replaceNonAlnum(modelName);
						if(modelName.length() < 10)
							specNameList = specNameList + 0 + modelName.length() + modelName;
						else
							specNameList = specNameList + modelName.length() + modelName;
						}

					initOctave(workingDir, "PlotBWRated", OctaveLogging.toString(), compatTestDirectory, specNameList, Integer.toString((TxData.size())), "null");
					
					SpecMask.addAll(retVal1);
					PSD.addAll(retVal2);
					BW.addAll(retVal3);
					compatBWList = splistArrayList(retVal4);

				/*****
				 * After evaluation, move the images to the respective folder in the Reports
				 * directory
				 ************/

				ArrayList<String> compatModelList = new ArrayList<String>();
				ArrayList<String> nonCompatModelList = new ArrayList<String>();
				ArrayList<String> allCompatModelList = new ArrayList<String>();

				// ExecuteBWRated execBWRated = new ExecuteBWRated();

				final ArrayList<Integer> indexList = new ArrayList<Integer>();
				for (int i = 0; i < PSD.size(); i++) {

					if (compatBWList.get(i).getData()[0] == 1.00) {
						allCompatModelList.add(txArray.get(txIndex[i]).ModelName);
						// Pass the model name to all compatible
					} else {
						if (compatBWList.get(i).getData()[0] == 0.00) {
							nonCompatModelList.add(txArray.get(txIndex[i]).ModelName);
							// Pass the model name to all non compatible
						} else {
							compatModelList.add(txArray.get(txIndex[i]).ModelName + " with "
									+ Arrays.toString(compatBWList.get(i).getData()));
							indexList.add(i);
						}
					}

				}

				printRep.printBWRated(printfile, allCompatModelList, nonCompatModelList, compatModelList, 0);


				// Load the html page using the method
				if(reportGeneration)
					loadCompRep.displayBWRatedAnalysis(allCompatModelList, nonCompatModelList, compatModelList,
						compatTestDirectory);

			}

			break;

// bw Rated List

		case "bwRatedList":
			System.out.println("BW Rated List Analysis Running");
			logger.info("BW Rated List ANalysis Running");

			warningMessage = warningMessage + printRx.printText(RxData.get(0), printRFile);

			o = 0;
			SCMSchedule rxSched3 = RxData.get(0).getScmSchedule().get(o);
			SCMSchedule txSched3 = TxData.get(0).getScmSchedule().get(o);
			GregorianCalendar Tx_Start_time3 = txSched3.getStartTime().toGregorianCalendar();
			GregorianCalendar Tx_End_time3 = txSched3.getEndTime().toGregorianCalendar();
			GregorianCalendar Rx_Start_time3 = rxSched3.getStartTime().toGregorianCalendar();
			GregorianCalendar Rx_End_time3 = rxSched3.getEndTime().toGregorianCalendar();

			if ((Tx_Start_time3.before(Rx_Start_time3) && Tx_End_time3.before(Rx_Start_time3))
					|| (Tx_Start_time3.after(Rx_End_time3) && Tx_End_time3.after(Rx_End_time3))) {

				warningFlag = true;
				warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";

				// new Warn().setWarn("Systems Compatible", "Systems are compatible due to
				// non-overlapping schedules");

			} else {

				ArrayList<OctaveDouble> SpecMask = new ArrayList<OctaveDouble>();
				final ArrayList<OctaveDouble> PSD = new ArrayList<OctaveDouble>();
				final ArrayList<OctaveDouble> BW = new ArrayList<OctaveDouble>();
				ArrayList<OctaveDouble> compatBWList = new ArrayList<OctaveDouble>();

				//the names of all the transmitter files are concatenated into the same list 
				//because they need to be sent to Octave for plotting
				String specNameList = "";


					for (int i = 0; i < TxData.size(); i++) {

						//modifying the name of the printTFile
						//since in this case we only have a single transmitter file we will name it as 1.txt, 2.txt ...so on
						int ind = i+1;
						txFileName = printTFile + ind + ".txt";
						warningMessage = warningMessage + printTx.printText(TxData.get(i), txFileName);

						//fetch the model name and add it to the string along with the string length
						//we add string length so that while reading inside Octave code we can identify how long is the name of 
						//current transmitter model
						String modelName = txArray.get(txIndex[i]).ModelName;
						modelName = replaceNonAlnum(modelName);
						if(modelName.length() < 10)
							specNameList = specNameList + 0 + modelName.length() + modelName;
						else
							specNameList = specNameList + modelName.length() + modelName;
					}
					
					initOctave(workingDir, "PlotBWRated", OctaveLogging.toString(), compatTestDirectory, specNameList, Integer.toString((TxData.size())), "null");

					SpecMask.addAll(retVal1);
					PSD.addAll(retVal2);
					BW.addAll(retVal3);
					compatBWList = splistArrayList(retVal4);

				/*****
				 * After evaluation, move the images to the respective folder in the Reports
				 * directory
				 ************/

				ArrayList<String> compatModelList = new ArrayList<String>();
				ArrayList<String> nonCompatModelList = new ArrayList<String>();
				ArrayList<String> allCompatModelList = new ArrayList<String>();


				final ArrayList<Integer> indexList = new ArrayList<Integer>();
				for (int i = 0; i < PSD.size(); i++) {

					if (compatBWList.get(i).getData()[0] == 1.00) {
						allCompatModelList.add(txArray.get(txIndex[i]).ModelName);
						// Pass the model name to all compatible
					} else {
						if (compatBWList.get(i).getData()[0] == 0.00) {
							nonCompatModelList.add(txArray.get(txIndex[i]).ModelName);
							// Pass the model name to all non compatible
						} else {
							compatModelList.add(txArray.get(txIndex[i]).ModelName + " with "
									+ Arrays.toString(compatBWList.get(i).getData()));
							indexList.add(i);
						}
					}

				}
				printRep.printBWRated(printfile, allCompatModelList, nonCompatModelList, compatModelList, 1);

				if(reportGeneration)
					loadCompRep.displayBWRatedAnalysis(allCompatModelList, nonCompatModelList, compatModelList,
						compatTestDirectory);

			}

			break;

		case "ratedBTP":
			System.out.println("Rated BTP Analysis Running");
			logger.info("Rated BTP Analysis Running");

			warningMessage = warningMessage + printRx.printText(RxData.get(0), printRFile);

			o = 0;
			SCMSchedule rxSched_btp = RxData.get(0).getScmSchedule().get(o);
			SCMSchedule txSched_btp = TxData.get(0).getScmSchedule().get(o);
			GregorianCalendar Tx_Start_time_btp = txSched_btp.getStartTime().toGregorianCalendar();
			GregorianCalendar Tx_End_time_btp = txSched_btp.getEndTime().toGregorianCalendar();
			GregorianCalendar Rx_Start_time_btp = rxSched_btp.getStartTime().toGregorianCalendar();
			GregorianCalendar Rx_End_time_btp = rxSched_btp.getEndTime().toGregorianCalendar();

			if ((Tx_Start_time_btp.before(Rx_Start_time_btp) && Tx_End_time_btp.before(Rx_Start_time_btp))
					|| (Tx_Start_time_btp.after(Rx_End_time_btp) && Tx_End_time_btp.after(Rx_End_time_btp))) {

				warningFlag = true;
				warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";
				// new Warn().setWarn("Systems Compatible", "Systems are compatible due to
				// non-overlapping schedules");

			} else {
				ArrayList<OctaveDouble> Spec_BTP = new ArrayList<OctaveDouble>();
				ArrayList<OctaveDouble> compatBTPList = new ArrayList<OctaveDouble>();
				String execPattern = "";
				
				//the names of all the transmitter files are concatenated into the same list 
				//because they need to be sent to Octave for plotting
				String specNameList = "";
				
				//boolean to check if the request should be sent to the octave code
				//it will be false in case if the program reaches the else block inside the loop
				boolean validHoppingSystem = false;

				for (int i = 0; i < TxData.size(); i++) {

					if (TxData.get(i).getSpectrumMask().get(o).getHoppingData() != null) {
						
						//set the boolean to true
						validHoppingSystem = true;

						//modifying the name of the printTFile
						//since in this case we only have a single transmitter file we will name it as 1.txt, 2.txt ...so on
						int ind = i+1;
						txFileName = printTFile + ind + ".txt";
						warningMessage = warningMessage + printTx.printText(TxData.get(i), txFileName);
						
						//fetch the model name and add it to the string along with the string length
						//we add string length so that while reading inside Octave code we can identify how long is the name of 
						//current transmitter model
						String modelName = txArray.get(txIndex[i]).ModelName;
						modelName = replaceNonAlnum(modelName);
						if(modelName.length() < 10)
							specNameList = specNameList + 0 + modelName.length() + modelName;
						else
							specNameList = specNameList + modelName.length() + modelName;

						
						//identify whether the model is frequency based or bandwidth based
						if (TxData.get(i).getSpectrumMask().get(o).getHoppingData().getFrequencyList() != null) {
							execPattern += "f";

						} else {
							execPattern += "b";
						}

					} else {
						warningFlag = true;
						warningMessage = warningMessage + "\nSpectrum Mask " + txArray.get(txIndex[i]).ModelName
								+ " is not a hopping system";

						/*
						 * warningDisplay.setWarn("Warning","Spectrum Mask "+
						 * txArray.get(txIndex[i]).ModelName + " is not a hopping system");
						 */
					}
				}
				
				//send the request to Octave only if it's a valid Hopping system
				if(validHoppingSystem) {
					initOctave(workingDir, "PlotBTPRated", OctaveLogging.toString(), compatTestDirectory, specNameList, Integer.toString((TxData.size())), execPattern);
					Spec_BTP.addAll(retVal1);
					compatBTPList = splistArrayList(retVal3);
				}


				/*****
				 * After evaluation, move the images to the respective folder in the Reports
				 * directory
				 ************/

				ArrayList<String> compatList = new ArrayList<String>();
				ArrayList<String> nonCompatList = new ArrayList<String>();

				/// ExecuteBTPRated execBTPRated = new ExecuteBTPRated();

				for (int i = 0; i < Spec_BTP.size(); i++) {

					if (compatBTPList.get(i).getData()[0] == 0.00) {
						nonCompatList.add(txArray.get(txIndex[i]).ModelName);
						// Pass the model name to all compatible
					} else {
						compatList.add(txArray.get(txIndex[i]).ModelName + " with "
								+ Arrays.toString(compatBTPList.get(i).getData()));
					}
				}

				printRep.printBTPRated(printfile, compatList, nonCompatList, 0);

				if(reportGeneration)
					loadCompRep.displayBTPRatedAnalysis(nonCompatList, compatList, compatTestDirectory);

			}

			break;

// BTP Rated List Analysis		
		case "btpRatedList":
			System.out.println("BTP Rated List Analysis Running");
			logger.info("BTP Rated List Analysis Running");
			warningMessage = warningMessage + printRx.printText(RxData.get(0), printRFile);

			o = 0;
			SCMSchedule rxSched4 = RxData.get(0).getScmSchedule().get(o);
			SCMSchedule txSched4 = TxData.get(0).getScmSchedule().get(o);
			GregorianCalendar Tx_Start_time4 = txSched4.getStartTime().toGregorianCalendar();
			GregorianCalendar Tx_End_time4 = txSched4.getEndTime().toGregorianCalendar();
			GregorianCalendar Rx_Start_time4 = rxSched4.getStartTime().toGregorianCalendar();
			GregorianCalendar Rx_End_time4 = rxSched4.getEndTime().toGregorianCalendar();

			if ((Tx_Start_time4.before(Rx_Start_time4) && Tx_End_time4.before(Rx_Start_time4))
					|| (Tx_Start_time4.after(Rx_End_time4) && Tx_End_time4.after(Rx_End_time4))) {

				warningFlag = true;
				warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";

				// new Warn().setWarn("Systems Compatible", "Systems are compatible due to
				// non-overlapping schedules");

			} else {
				ArrayList<OctaveDouble> Spec_BTP = new ArrayList<OctaveDouble>();
				ArrayList<OctaveDouble> compatBTPList = new ArrayList<OctaveDouble>();
				String execPattern = "";

				//the names of all the transmitter files are concatenated into the same list 
				//because they need to be sent to Octave for plotting
				String specNameList = "";

				//boolean to check if the request should be sent to the octave code
				//it will be false in case if the program reaches the else block inside the loop
				boolean validHoppingSystem = false;
				

//				initOctave(workingDir, "PlotBTPRated", OctaveLogging.toString(), compatTestDirectory, "null");

				for (int i = 0; i < TxData.size(); i++) {

					if (TxData.get(i).getSpectrumMask().get(o).getHoppingData() != null) {
						//setting boolean to true
						validHoppingSystem = true;

						//modifying the name of the printTFile
						//since in this case we only have a single transmitter file we will name it as 1.txt, 2.txt ...so on
						int ind = i+1;
						txFileName = printTFile + ind + ".txt";
						warningMessage = warningMessage + printTx.printText(TxData.get(i), txFileName);

						//fetch the model name and add it to the string along with the string length
						//we add string length so that while reading inside Octave code we can identify how long is the name of 
						//current transmitter model
						String modelName = txArray.get(txIndex[i]).ModelName;
						modelName = replaceNonAlnum(modelName);
						if(modelName.length() < 10)
							specNameList = specNameList + 0 + modelName.length() + modelName;
						else
							specNameList = specNameList + modelName.length() + modelName;

						
						//identify whether the model is frequency based or bandwidth based
						if (TxData.get(i).getSpectrumMask().get(o).getHoppingData().getFrequencyList() != null) {
							execPattern += "f";

						} else {
							execPattern += "b";
						}
						
					} else {

						warningFlag = true;
						warningMessage = warningMessage + "\nSpectrum Mask" + txArray.get(txIndex[i]).ModelName
								+ "is not a hopping system";
						/*
						 * warningDisplay.setWarn("Warning","Spectrum Mask "+
						 * txArray.get(txIndex[i]).ModelName + " is not a hopping system");
						 */
					}
				}
				
				if(validHoppingSystem) {
					initOctave(workingDir, "PlotBTPRated", OctaveLogging.toString(), compatTestDirectory, specNameList, Integer.toString((TxData.size())), execPattern);
					Spec_BTP.addAll(retVal1);
					compatBTPList = splistArrayList(retVal3);
				}

//				octaveBTP.eval("saveas(fig2,'BTPRatedAnalysis.png')");

				/*****
				 * After evaluation, move the images to the respective folder in the Reports
				 * directory
				 ************/

				ArrayList<String> compatList = new ArrayList<String>();
				ArrayList<String> nonCompatList = new ArrayList<String>();

				// ExecuteBTPRated execBTPRated = new ExecuteBTPRated();

				for (int i = 0; i < Spec_BTP.size(); i++) {

					if (compatBTPList.get(i).getData()[0] == 0.00) {
						nonCompatList.add(txArray.get(txIndex[i]).ModelName);
						// Pass the model name to all compatible
					} else {
						compatList.add(txArray.get(txIndex[i]).ModelName + " with "
								+ Arrays.toString(compatBTPList.get(i).getData()));
					}
				}

				printRep.printBTPRated(printfile, compatList, nonCompatList, 1);

				/*
				 * execBTPRated.setPlotPath(compatTestDirectory);
				 * execBTPRated.buildCompatList(compatList);
				 * execBTPRated.buildNonCompatList(nonCompatList); execBTPRated.getFrame();
				 */
				if(reportGeneration)
					loadCompRep.displayBTPRatedAnalysis(nonCompatList, compatList, compatTestDirectory);

			}

			break;

		case "dcRatedList":
			System.out.println("DC Rated List Analysis Running");
			logger.info("DC Rated List Analysis Running");
			String execPattern = "";

			warningMessage = warningMessage + printRx.printText(RxData.get(0), printRFile);

			o = 0;
			SCMSchedule rxSched_dc = RxData.get(0).getScmSchedule().get(o);
			SCMSchedule txSched_dc = TxData.get(0).getScmSchedule().get(o);
			GregorianCalendar Tx_Start_time_dc = txSched_dc.getStartTime().toGregorianCalendar();
			GregorianCalendar Tx_End_time_dc = txSched_dc.getEndTime().toGregorianCalendar();
			GregorianCalendar Rx_Start_time_dc = rxSched_dc.getStartTime().toGregorianCalendar();
			GregorianCalendar Rx_End_time_dc = rxSched_dc.getEndTime().toGregorianCalendar();

			if ((Tx_Start_time_dc.before(Rx_Start_time_dc) && Tx_End_time_dc.before(Rx_Start_time_dc))
					|| (Tx_Start_time_dc.after(Rx_End_time_dc) && Tx_End_time_dc.after(Rx_End_time_dc))) {

				warningFlag = true;
				warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";

				// new Warn().setWarn("Systems Compatible", "Systems are compatible due to
				// non-overlapping schedules");

			} else {
				ArrayList<OctaveDouble> compatDutyList = new ArrayList<OctaveDouble>();
				
				//the names of all the transmitter files are concatenated into the same list 
				//because they need to be sent to Octave for plotting
				String specNameList = "";
				
				//boolean to check if the request should be sent to the octave code
				//it will be false in case if the program reaches the else block inside the loop
				boolean validHoppingSystem = false;

//				initOctave(workingDir, "PlotDCRated", OctaveLogging.toString(), compatTestDirectory, "null");
				// octaveDuty.eval("retval = plotDutyRated();");

				for (int i = 0; i < TxData.size(); i++) {

					if (TxData.get(i).getSpectrumMask().get(o).getHoppingData() != null) {
						//setting the boolean to true
						validHoppingSystem = true;

						//modifying the name of the printTFile
						//since in this case we only have a single transmitter file we will name it as 1.txt, 2.txt ...so on
						int ind = i+1;
						txFileName = printTFile + ind + ".txt";
						warningMessage = warningMessage + printTx.printText(TxData.get(i), txFileName);

						//fetch the model name and add it to the string along with the string length
						//we add string length so that while reading inside Octave code we can identify how long is the name of 
						//current transmitter model
						String modelName = txArray.get(txIndex[i]).ModelName;
						modelName = replaceNonAlnum(modelName);
						if(modelName.length() < 10)
							specNameList = specNameList + 0 + modelName.length() + modelName;
						else
							specNameList = specNameList + modelName.length() + modelName;

						
						//identify whether the model is frequency based or bandwidth based
						if (TxData.get(i).getSpectrumMask().get(o).getHoppingData().getFrequencyList() != null) {
							execPattern += "f";
						} else {
							execPattern += "b";
						}

//						compatDutyList.addAll(retVal3);

					} else {
						warningFlag = true;
						warningMessage = warningMessage + "\nSpectrum Mask " + txArray.get(txIndex[i]).ModelName
								+ " is not a hopping system";
						/*
						 * warningDisplay.setWarn("Warning","Spectrum Mask "+
						 * txArray.get(txIndex[i]).ModelName + " is not a hopping system");
						 */
					}
				}
				

				if(validHoppingSystem) {
					initOctave(workingDir, "PlotDCRated", OctaveLogging.toString(), compatTestDirectory, specNameList, Integer.toString((TxData.size())), execPattern);
					compatDutyList = splistArrayList(retVal3);
				}
				

				/*****
				 * After evaluation, move the images to the respective folder in the Reports
				 * directory
				 ************/

				ArrayList<String> dutyCompatList = new ArrayList<String>();
				ArrayList<String> dutyNonCompatList = new ArrayList<String>();


				for (int i = 0; i < compatDutyList.size(); i++) {
					try {
						if (compatDutyList.get(i).getData()[0] == 0.0) {
							dutyNonCompatList.add(txArray.get(txIndex[i]).ModelName);
							// Pass the model name to all compatible
						} else {
							dutyCompatList.add(txArray.get(txIndex[i]).ModelName + " with Duty Cycle "
									+ Arrays.toString(compatDutyList.get(i).getData()));
						}
					}catch(Exception e) {
						if(result == "result:System is compatible")
							dutyCompatList.add(txArray.get(txIndex[i]).ModelName);
					}
				}

				printRep.printDuty(printfile, dutyCompatList, dutyNonCompatList);

				if(reportGeneration)
					loadCompRep.displayDCRatedAnalysis(dutyNonCompatList, dutyCompatList, compatTestDirectory);

			}

			break;

		default:
			break;
		}

		printfile.close();
	}
}
