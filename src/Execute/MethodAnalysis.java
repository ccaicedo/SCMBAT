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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import dk.ange.octave.type.OctaveString;
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
				if (ratedMask.getRatedBW() != 0.0) {
					ratedMethod = "ratedBW";
				}
				if (ratedMask.getBwRatedList() != null) {
					ratedMethod = "bwRatedList";
				}
				if (ratedMask.getRatedBTP() != 0.0) {
					ratedMethod = "ratedBTP";
				}
				if (ratedMask.getBtpRatedList() != null) {
					ratedMethod = "btpRatedList";
				}
				if (ratedMask.getDcRatedList() != null) {
					ratedMethod = "dcRatedList";
				}
				if (ratedMask.getPorPIndex() != 0) {
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

// Executing compatibility based on method specified. 	
	public void execCompat(String method, int[] txIndex, ArrayList<TxModel> TxData, ArrayList<SCM_home.Model> txArray,
			int[] rxIndex, ArrayList<RxModel> RxData, ArrayList<SCM_home.Model> rxArray) {

		// ExecuteFrame execFrame =null;
		String Command0 = null;
		String Command1 = null;
		String CompatStat = null;
		String PowerMargin = null;
		String dirName = getFilePath();
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
		// String printTFile = current_Dir + "/SCM_transmitter_java.txt";
		// String printRFile = current_Dir + "/SCM_receiver_java.txt";

		String printTFile = dirName + "Octave/SCM_transmitter_java.txt";
		String printRFile = dirName + "Octave/SCM_receiver_java.txt";
		String resultFile = current_Dir + "/Results.txt";

		try {
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

		switch (method) {
		case "TotalPower":
			System.out.println("Total Power Method being executed");
			logger.info("Total Power Method being executed");
			warningMessage = warningMessage + printTx.printText(TxData.get(0), printTFile);
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
				
				String totPowFile = dirName + "TotPow.sh " + dirName + " "+compatTestDirectory;
				System.out.println("total power file is : " + totPowFile);
				Command0 = "chmod u+x " + totPowFile;
				Command1 = totPowFile;
				System.out.println("Command0 is : " + Command0);
				System.out.println("Command1 is : " + Command1);

				Process p1;
				try {
					p1 = Runtime.getRuntime().exec(Command0);
					p1 = Runtime.getRuntime().exec(Command1);
					InputStream is = p1.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String line;
					ArrayList<String> dispData = new ArrayList<String>();
					try {
						while ((line = br.readLine()) != null) {
							System.out.println(line);

							logger.info(line);
							dispData.add(line);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					int DataLength = dispData.size();
					CompatStat = dispData.get(DataLength - 2);
					PowerMargin = dispData.get(DataLength - 1);
					p1.waitFor();
				} catch (Exception e) {
					e.printStackTrace();
				}

				/*
				 * Write the results into the Report file
				 */
				try {
					// Add in the results file

					printfile.println("");
					printfile.println("Method: Total Power");
					printfile.println("Compatibility Result: " + CompatStat);
					printfile.println("");
					printfile.println("Power Margin: " + PowerMargin);

				} catch (Exception e) {
					logger.error(e.toString());
				}

				/*
				 * execFrame = new ExecuteFrame(); execFrame.setPlotPath(compatTestDirectory);
				 * execFrame.getFrame(CompatStat,PowerMargin);
				 */

				// Call the load html page method
				loadCompRep.displayCompatibilityReport(CompatStat, PowerMargin, compatTestDirectory);
			}

			break;
		case "MaximumPowerDensity":
			System.out.println("Max Power Density Method being executed");
			logger.info("Max Power Density Method being executed");
			warningMessage = warningMessage + printTx.printText(TxData.get(0), printTFile);
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
				Command1 = maxPowFile;
				Process p2;
				try {
					p2 = Runtime.getRuntime().exec(Command0);
					p2 = Runtime.getRuntime().exec(Command1);
					InputStream is = p2.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String line;
					ArrayList<String> dispData = new ArrayList<String>();
					try {
						while ((line = br.readLine()) != null) {
							dispData.add(line);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					int DataLength = dispData.size();
					CompatStat = dispData.get(DataLength - 2);
					PowerMargin = dispData.get(DataLength - 1);
					p2.waitFor();
				} catch (Exception e) {
					e.printStackTrace();
				}

				/*
				 * Write the results into the Report file
				 */
				try {
					// Add in the results file

					printfile.println("");
					printfile.println("Method: MaximumPowerDensity");
					printfile.println("Compatibility Result: " + CompatStat);
					printfile.println("");
					printfile.println("Power Margin: " + PowerMargin);
					printfile.println("");
				} catch (Exception e) {
					logger.error(e.toString());
				}

				/*
				 * execFrame = new ExecuteFrame(); execFrame.getFrame(CompatStat,PowerMargin);
				 */
				// Call the load html page method
				loadCompRep.displayCompatibilityReport(CompatStat, PowerMargin, compatTestDirectory);
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

				File workingDir = new File(dirName + "Octave");

				OctaveEngineFactory octaveFactory = new OctaveEngineFactory();
				octaveFactory.setWorkingDir(workingDir);
				OctaveEngine octave = octaveFactory.getScriptEngine();

				octave.eval("fig1=figure");
				octave.eval("retval = plotBWRated();");

				// Save the intermediate figures into the report folder
				octave.eval("saveas(fig1, 'Analysis_Figure_1.png')");
				octave.eval("movefile('Analysis_Figure_1.png','" + compatTestDirectory + "')");

				// Process p3;
				try {

					for (int i = 0; i < TxData.size(); i++) {

						warningMessage = warningMessage + printTx.printText(TxData.get(i), printTFile);

						octave.eval("[SpecMask,PSD,BW,compatBWList] = TxMPSD();");
						SpecMask.add(octave.get(OctaveDouble.class, "SpecMask"));
						PSD.add(octave.get(OctaveDouble.class, "PSD"));
						BW.add(octave.get(OctaveDouble.class, "BW"));
						compatBWList.add(octave.get(OctaveDouble.class, "compatBWList"));

						OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
						octave.put("specTag", specTag);
						octave.eval("plot(SpecMask(1:2:end-1),SpecMask(2:2:end),'r.-','LineWidth',2)");
						octave.eval("xpoint=(SpecMask(length(SpecMask)/2-1)+SpecMask(length(SpecMask)/2+1))/2;");
						octave.eval("minSpecPow=min(SpecMask(2:2:end));");
						octave.eval("text(xpoint,minSpecPow-1,specTag)");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				octave.eval("saveas(fig1,'BWRatedAnalysis.png')");

				/*****
				 * After evaluation, move the images to the respective folder in the Reports
				 * directory
				 ************/
				String moveCmd = "movefile('BWRatedAnalysis.png','" + compatTestDirectory + "')";
				octave.eval(moveCmd);

				octave.close();

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

				/*
				 * execBWRated.setPlotPath(compatTestDirectory);
				 * execBWRated.buildAllCompatList(allCompatModelList);
				 * execBWRated.buildCompatList(compatModelList);
				 * execBWRated.buildNonCompatList(nonCompatModelList);
				 * execBWRated.getFrame(BW,PSD,indexList);
				 */

				// Load the html page using the method
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

				File workingDir = new File(dirName + "Octave");

				OctaveEngineFactory octaveFactory = new OctaveEngineFactory();
				octaveFactory.setWorkingDir(workingDir);
				OctaveEngine octave = octaveFactory.getScriptEngine();

				octave.eval("fig1=figure");
				octave.eval("retval = plotBWRated();");

				// Process p3;

				octave.eval("saveas(fig1, 'Analysis_Figure_1.png')");
				octave.eval("movefile('Analysis_Figure_1.png','" + compatTestDirectory + "')");

				// octave.eval("fig2=figure");
				try {

					for (int i = 0; i < TxData.size(); i++) {

						warningMessage = warningMessage + printTx.printText(TxData.get(i), printTFile);

						// p3 = Runtime.getRuntime().exec(Command0);
						// p3 = Runtime.getRuntime().exec(Command1);

						octave.eval("[SpecMask,PSD,BW,compatBWList] = TxMPSD();");
						SpecMask.add(octave.get(OctaveDouble.class, "SpecMask"));
						PSD.add(octave.get(OctaveDouble.class, "PSD"));
						BW.add(octave.get(OctaveDouble.class, "BW"));
						compatBWList.add(octave.get(OctaveDouble.class, "compatBWList"));

						OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
						octave.put("specTag", specTag);
						octave.eval("plot(SpecMask(1:2:end-1),SpecMask(2:2:end),'r.-','LineWidth',2)");
						octave.eval("xpoint=(SpecMask(length(SpecMask)/2-1)+SpecMask(length(SpecMask)/2+1))/2;");
						octave.eval("minSpecPow=min(SpecMask(2:2:end));");
						octave.eval("text(xpoint,minSpecPow-1,specTag)");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				octave.eval("saveas(fig1,'BWRatedAnalysis.png')");

				/*****
				 * After evaluation, move the images to the respective folder in the Reports
				 * directory
				 ************/
				String moveCmd = "movefile('BWRatedAnalysis.png','" + compatTestDirectory + "')";
				octave.eval(moveCmd);

				octave.close();

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
				printRep.printBWRated(printfile, allCompatModelList, nonCompatModelList, compatModelList, 1);

				/*
				 * execBWRated.setPlotPath(compatTestDirectory);
				 * execBWRated.buildAllCompatList(allCompatModelList);
				 * execBWRated.buildCompatList(compatModelList);
				 * execBWRated.buildNonCompatList(nonCompatModelList);
				 * execBWRated.getFrame(BW,PSD,indexList);
				 */
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

				File workingBTPDir = new File(dirName + "Octave");

				OctaveEngineFactory BTPoctaveFactory = new OctaveEngineFactory();
				BTPoctaveFactory.setWorkingDir(workingBTPDir);
				OctaveEngine octaveBTP = BTPoctaveFactory.getScriptEngine();

				octaveBTP.eval("fig2=figure");
				octaveBTP.eval("retval = plotBTPRated();");

				octaveBTP.eval("saveas(fig2, 'Analysis_Figure_1.png')");
				octaveBTP.eval("movefile('Analysis_Figure_1.png','" + compatTestDirectory + "')");

				for (int i = 0; i < TxData.size(); i++) {

					if (TxData.get(i).getSpectrumMask().get(o).getHoppingData() != null) {

						warningMessage = warningMessage + printTx.printText(TxData.get(i), printTFile);

						if (TxData.get(i).getSpectrumMask().get(o).getHoppingData().getFrequencyList() != null) {
							octaveBTP.eval("[Spec_BTP,ExtSpecMask,compatBTPList] = TxHop_FreqList()");
							octaveBTP.eval("plot(ExtSpecMask(1:2:end-1),ExtSpecMask(2:2:end),'r.-','LineWidth',2)");
							OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
							octaveBTP.put("specTag", specTag);
							octaveBTP.eval("xpoint=ExtSpecMask(1);");
							octaveBTP.eval("minSpecPow=min(ExtSpecMask(2:2:end));");
							octaveBTP.eval("text(xpoint,minSpecPow-1,specTag)");

						} else {
							octaveBTP.eval("[Spec_BTP,NewBandList,Spec_MaxPower,compatBTPList] = TxHop_BandList()");
							octaveBTP.eval(
									"plot(NewBandList,Spec_MaxPower*ones(1,length(NewBandList)),'r.-','LineWidth',2)");
							OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
							octaveBTP.put("specTag", specTag);
							// octaveBTP.eval("NewBandList");
							octaveBTP.eval("xpoint=NewBandList(1);");
							octaveBTP.eval("minSpecPow=Spec_MaxPower;");
							octaveBTP.eval("text(xpoint,minSpecPow-1,specTag)");
						}

						Spec_BTP.add(octaveBTP.get(OctaveDouble.class, "Spec_BTP"));
						compatBTPList.add(octaveBTP.get(OctaveDouble.class, "compatBTPList"));

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

				octaveBTP.eval("saveas(fig2,'BTPRatedAnalysis.png')");

				/*****
				 * After evaluation, move the images to the respective folder in the Reports
				 * directory
				 ************/
				String moveCmd = "movefile('BTPRatedAnalysis.png','" + compatTestDirectory + "')";
				octaveBTP.eval(moveCmd);

				octaveBTP.close();

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

				/*
				 * execBTPRated.setPlotPath(compatTestDirectory);
				 * execBTPRated.buildCompatList(compatList);
				 * execBTPRated.buildNonCompatList(nonCompatList); execBTPRated.getFrame();
				 */
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

				File workingBTPDir = new File(dirName + "Octave");

				OctaveEngineFactory BTPoctaveFactory = new OctaveEngineFactory();
				BTPoctaveFactory.setWorkingDir(workingBTPDir);
				OctaveEngine octaveBTP = BTPoctaveFactory.getScriptEngine();

				octaveBTP.eval("fig2=figure");
				octaveBTP.eval("retval = plotBTPRated();");

				octaveBTP.eval("saveas(fig2, 'Analysis_Figure_1.png')");
				octaveBTP.eval("movefile('Analysis_Figure_1.png','" + compatTestDirectory + "')");

				for (int i = 0; i < TxData.size(); i++) {

					if (TxData.get(i).getSpectrumMask().get(o).getHoppingData() != null) {

						warningMessage = warningMessage + printTx.printText(TxData.get(i), printTFile);

						if (TxData.get(i).getSpectrumMask().get(o).getHoppingData().getFrequencyList() != null) {
							octaveBTP.eval("[Spec_BTP,ExtSpecMask,compatBTPList] = TxHop_FreqList()");
							octaveBTP.eval("plot(ExtSpecMask(1:2:end-1),ExtSpecMask(2:2:end),'r.-','LineWidth',2)");
							OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
							octaveBTP.put("specTag", specTag);
							octaveBTP.eval("xpoint=ExtSpecMask(1);");
							octaveBTP.eval("minSpecPow=min(ExtSpecMask(2:2:end));");
							octaveBTP.eval("text(xpoint,minSpecPow-1,specTag)");

						} else {
							octaveBTP.eval("[Spec_BTP,NewBandList,Spec_MaxPower,compatBTPList] = TxHop_BandList()");
							octaveBTP.eval(
									"plot(NewBandList,Spec_MaxPower*ones(1,length(NewBandList)),'r.-','LineWidth',2)");
							OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
							octaveBTP.put("specTag", specTag);
							// octaveBTP.eval("NewBandList");
							octaveBTP.eval("xpoint=NewBandList(1);");
							octaveBTP.eval("minSpecPow=Spec_MaxPower;");
							octaveBTP.eval("text(xpoint,minSpecPow-1,specTag)");
						}

						Spec_BTP.add(octaveBTP.get(OctaveDouble.class, "Spec_BTP"));
						compatBTPList.add(octaveBTP.get(OctaveDouble.class, "compatBTPList"));

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

				octaveBTP.eval("saveas(fig2,'BTPRatedAnalysis.png')");

				/*****
				 * After evaluation, move the images to the respective folder in the Reports
				 * directory
				 ************/
				String moveCmd = "movefile('BTPRatedAnalysis.png','" + compatTestDirectory + "')";
				octaveBTP.eval(moveCmd);

				octaveBTP.close();

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
				loadCompRep.displayBTPRatedAnalysis(nonCompatList, compatList, compatTestDirectory);

			}

			break;

		case "dcRatedList":
			System.out.println("DC Rated List Analysis Running");
			logger.info("DC Rated List Analysis Running");

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

				File workingDutyDir = new File(dirName + "Octave");

				OctaveEngineFactory dutyOctaveFactory = new OctaveEngineFactory();
				dutyOctaveFactory.setWorkingDir(workingDutyDir);
				OctaveEngine octaveDuty = dutyOctaveFactory.getScriptEngine();

				octaveDuty.eval("fig3=figure");
				// octaveDuty.eval("retval = plotDutyRated();");

				for (int i = 0; i < TxData.size(); i++) {

					if (TxData.get(i).getSpectrumMask().get(o).getHoppingData() != null) {

						warningMessage = warningMessage + printTx.printText(TxData.get(i), printTFile);

						if (TxData.get(i).getSpectrumMask().get(o).getHoppingData().getFrequencyList() != null) {
							octaveDuty.eval("[Spec_mask_new,p_Tx_new,compatDutyList] = TxDuty_FreqList();");

							// octaveBTP.eval("plot(ExtSpecMask(1:2:end-1),ExtSpecMask(2:2:end),'r.-','LineWidth',2)");
						} else {
							octaveDuty.eval("[Spec_mask_new,p_Tx_new,compatDutyList] = TxDuty_BandList();");
							// octaveBTP.eval("plot(NewBandList,Spec_MaxPower*ones(1,length(NewBandList)),'r.-','LineWidth',2)");
						}

						compatDutyList.add(octaveDuty.get(OctaveDouble.class, "compatDutyList"));

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
				octaveDuty.eval("saveas(fig3,'BTPRatedAnalysis.png')");

				/*****
				 * After evaluation, move the images to the respective folder in the Reports
				 * directory
				 ************/
				String moveCmd = "movefile('BTPRatedAnalysis.png','" + compatTestDirectory + "')";
				octaveDuty.eval(moveCmd);

				octaveDuty.close();

				ArrayList<String> dutyCompatList = new ArrayList<String>();
				ArrayList<String> dutyNonCompatList = new ArrayList<String>();

				// ExecuteDuty execDutyRated = new ExecuteDuty();

				for (int i = 0; i < compatDutyList.size(); i++) {

					if (compatDutyList.get(i).getData()[0] == 0.00) {
						dutyNonCompatList.add(txArray.get(txIndex[i]).ModelName);
						// Pass the model name to all compatible
					} else {
						dutyCompatList.add(txArray.get(txIndex[i]).ModelName + " with Duty Cycle "
								+ Arrays.toString(compatDutyList.get(i).getData()));
					}
				}

				printRep.printDuty(printfile, dutyCompatList, dutyNonCompatList);

				/*
				 * execDutyRated.setPlotPath(compatTestDirectory);
				 * execDutyRated.buildCompatList(dutyCompatList);
				 * execDutyRated.buildNonCompatList(dutyNonCompatList);
				 * execDutyRated.getFrame();
				 */
				loadCompRep.displayDCRatedAnalysis(dutyNonCompatList, dutyCompatList, compatTestDirectory);

			}

			break;

		default:
			break;
		}

		printfile.close();
	}
}
