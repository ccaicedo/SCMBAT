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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ieee.dyspansc._1900._5.scm.RatingType;
import org.ieee.dyspansc._1900._5.scm.RxModelType;
import org.ieee.dyspansc._1900._5.scm.SCMScheduleType;
import org.ieee.dyspansc._1900._5.scm.TxModelType;

import SCM_home.Home;
import dk.ange.octave.OctaveEngine;
import dk.ange.octave.OctaveEngineFactory;
import dk.ange.octave.type.OctaveDouble;
import dk.ange.octave.type.OctaveString;

public class MethodAnalysis {
	
	PrintTxText printTx = new PrintTxText();
	PrintRxText printRx = new PrintRxText();	
	Warn warningDisplay = new Warn();
	
	final Logger logger = Logger.getLogger(MethodAnalysis.class);
	
	//Adding the flag required for showing the warnings in a single window
	public boolean warningFlag = false;
	
	//the warning message containing all the warnings
	public  String warningMessage = "\n";
	
	
	//For environment variables
	 ProcessBuilder pb = new ProcessBuilder("echo", "");
     Map<String, String> envMap = pb.environment();
	
	//Finding the path where the script files are stored and setting the environment variable
	private void setFilePathEnvironment()
	{
		String line;
		String homeDirectoryPath="";
		try {
            FileReader filereader =  new FileReader("HomeDirectoryPath.txt");

            BufferedReader bufferedReader = new BufferedReader(filereader);

            while((line = bufferedReader.readLine()) != null) {
              homeDirectoryPath =  line.split("=")[1];
            }   

            // Always close files.
            bufferedReader.close();
            
            //Setting the environment variable for the complete path
           
            envMap.put("SCM_PATH", homeDirectoryPath);
        }
        catch(FileNotFoundException ex) {
            ex.printStackTrace();             
        }
        catch(IOException ex) {
           ex.printStackTrace();
        }
		
	}
	//Getting the environment variable 
	public String getFilePath()
	{
		String directoryPath = "";
		 Set<String> keys = envMap.keySet();
		    for(String key:keys){
		    	if(key.equals("SCM_PATH"))
		    	{
		    		directoryPath = envMap.get(key);
			     //   System.out.println(key+" ==> "+directoryPath);
			        return directoryPath;
		    	}
		    }
		    return directoryPath;
	}
	
	public MethodAnalysis()
	{
		//Set the environment variable
		setFilePathEnvironment();
		logger.addAppender(Home.appender);
	}
	// Analysing which compatibility method to use based on Model types.
	public String analyseRatedMethod(ArrayList<TxModelType> TxData,ArrayList<RxModelType> RxData){
		
		String ratedMethod = "null";

		if(RxData.size()==1){
			
			RxModelType Rx=RxData.get(0);
			
			if(Rx.getUnderlayMask().get(0).getRating()!=null){
				RatingType ratedMask = Rx.getUnderlayMask().get(0).getRating();
				if(ratedMask.getRatedBW()!=0.0){
					ratedMethod = "ratedBW";
				}
				if(ratedMask.getBwRatedList()!=null){
					ratedMethod = "bwRatedList";
				}
				if(ratedMask.getRatedBTP()!=0.0){
					ratedMethod = "ratedBTP";
				}
				if(ratedMask.getBtpRatedList()!=null){
					ratedMethod = "btpRatedList";
				}
				if(ratedMask.getDcRatedList()!=null){
					ratedMethod = "dcRatedList";
				}
				if(ratedMask.getPorpIndex()!=0){
					ratedMethod = "pOrPIndex";
				}
			}else{
				ratedMethod=Rx.getUnderlayMask().get(0).getMaskPowerMarginMethod();
			}
		}
		return ratedMethod;
	}

// Executing compatibility based on method specified. 	
	public void execCompat(String method, 
			int[] txIndex, ArrayList<TxModelType> TxData, 
			ArrayList<SCM_home.Model> txArray, int[] 
					rxIndex, ArrayList<RxModelType> RxData, ArrayList<SCM_home.Model> rxArray){
		
		ExecuteFrame execFrame =null;
		String Command0=null;
		String Command1=null;
		String CompatStat=null;
		String PowerMargin=null;
			
		switch(method){
		case "TotalPower": System.out.println("Total Power Method being executed");
							logger.info("Total Power Method being executed");
			warningMessage= warningMessage + printTx.printText(TxData.get(0),"SCM_transmitter_java.txt");
			warningMessage = warningMessage + printRx.printText(RxData.get(0),"SCM_receiver_java.txt");
			
			int o = 0;
			SCMScheduleType rxSched = RxData.get(0).getScmSchedule().get(o); 
			SCMScheduleType txSched = TxData.get(0).getScmSchedule().get(o);
			GregorianCalendar Tx_Start_time = txSched.getStartTime().toGregorianCalendar();
			GregorianCalendar Tx_End_time = txSched.getEndTime().toGregorianCalendar();
			GregorianCalendar Rx_Start_time = rxSched.getStartTime().toGregorianCalendar();
			GregorianCalendar Rx_End_time = rxSched.getEndTime().toGregorianCalendar();
			
			if((Tx_Start_time.before(Rx_Start_time) && Tx_End_time.before(Rx_Start_time)) 
					|| (Tx_Start_time.after(Rx_End_time) && Tx_End_time.after(Rx_End_time)) ){
				
				warningFlag = true;
				warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";
			//	new Warn().setWarn("Systems Compatible", "Systems are compatible due to non-overlapping schedules");
				
			}else{
				String dirName = getFilePath();
				String totPowFile = dirName+"TotPow.sh "+dirName;
				Command0 = "chmod u+x "+totPowFile;
				Command1 = totPowFile;
				
				
				Process p1;
				try{
					p1 = Runtime.getRuntime().exec(Command0);
					p1 = Runtime.getRuntime().exec(Command1);
					InputStream is = p1.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String line;
					ArrayList<String> dispData = new ArrayList<String>();
					try {
						while((line = br.readLine())!=null){
							System.out.println(line);
							
							logger.info(line);
						dispData.add(line);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					int DataLength=dispData.size();
					CompatStat=dispData.get(DataLength-2);
					PowerMargin=dispData.get(DataLength-1);
					p1.waitFor();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				execFrame = new ExecuteFrame();
				execFrame.getFrame(CompatStat,PowerMargin);
			}			
			
			break;
		case "MaximumPowerDensity": System.out.println("Max Power Density Method being executed");
				logger.info("Max Power Density Method being executed");
		warningMessage= warningMessage+printTx.printText(TxData.get(0),"SCM_transmitter_java.txt");
		warningMessage = warningMessage + printRx.printText(RxData.get(0),"SCM_receiver_java.txt");
			
			o = 0;
			SCMScheduleType rxSched2 = RxData.get(0).getScmSchedule().get(o); 
			SCMScheduleType txSched2 = TxData.get(0).getScmSchedule().get(o);
			GregorianCalendar Tx_Start_time2 = txSched2.getStartTime().toGregorianCalendar();
			GregorianCalendar Tx_End_time2 = txSched2.getEndTime().toGregorianCalendar();
			GregorianCalendar Rx_Start_time2 = rxSched2.getStartTime().toGregorianCalendar();
			GregorianCalendar Rx_End_time2 = rxSched2.getEndTime().toGregorianCalendar();
			
			if((Tx_Start_time2.before(Rx_Start_time2) && Tx_End_time2.before(Rx_Start_time2)) 
					|| (Tx_Start_time2.after(Rx_End_time2) && Tx_End_time2.after(Rx_End_time2)) ){
				
				warningFlag = true;
				warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";
			//	new Warn().setWarn("Systems Compatible", "Systems are compatible due to non-overlapping schedules");
				
			}else{
				String dirName = getFilePath();
				String maxPowFile = dirName+"MaxPow.sh "+dirName;
				Command0 = "chmod u+x "+maxPowFile;
				Command1 = maxPowFile;			
				Process p2;
				try{
					p2 = Runtime.getRuntime().exec(Command0);
					p2 = Runtime.getRuntime().exec(Command1);
					InputStream is = p2.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(isr);
					String line;
					ArrayList<String> dispData = new ArrayList<String>();
					try {
						while((line = br.readLine())!=null){
							dispData.add(line);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					int DataLength=dispData.size();
					CompatStat=dispData.get(DataLength-2);
					PowerMargin=dispData.get(DataLength-1);
					p2.waitFor();
				}catch(Exception e){
					e.printStackTrace();
				}
				
				execFrame = new ExecuteFrame();
				execFrame.getFrame(CompatStat,PowerMargin);

			}
			
			break;
// Rated BW Analysis
			
		case "ratedBW":	System.out.println("Rated BW Analysis Running");
						logger.info("Rated BW Analysis Running");
		
		warningMessage = warningMessage + printRx.printText(RxData.get(0),"SCM_receiver_java.txt");
		
		o = 0;
		SCMScheduleType rxSched_bw = RxData.get(0).getScmSchedule().get(o); 
		SCMScheduleType txSched_bw = TxData.get(0).getScmSchedule().get(o);
		GregorianCalendar Tx_Start_time_bw = txSched_bw.getStartTime().toGregorianCalendar();
		GregorianCalendar Tx_End_time_bw = txSched_bw.getEndTime().toGregorianCalendar();
		GregorianCalendar Rx_Start_time_bw = rxSched_bw.getStartTime().toGregorianCalendar();
		GregorianCalendar Rx_End_time_bw = rxSched_bw.getEndTime().toGregorianCalendar();
		
		if((Tx_Start_time_bw.before(Rx_Start_time_bw) && Tx_End_time_bw.before(Rx_Start_time_bw)) 
				|| (Tx_Start_time_bw.after(Rx_End_time_bw) && Tx_End_time_bw.after(Rx_End_time_bw)) ){
			
			warningFlag = true;
			warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";
			
		//	new Warn().setWarn("Systems Compatible", "Systems are compatible due to non-overlapping schedules");
			
		}else{

			ArrayList<OctaveDouble> SpecMask = new ArrayList<OctaveDouble>();
			final ArrayList<OctaveDouble> PSD = new ArrayList<OctaveDouble>();
			final ArrayList<OctaveDouble> BW = new ArrayList<OctaveDouble>();
			ArrayList<OctaveDouble> compatBWList = new ArrayList<OctaveDouble>();
			
			File workingDir = new File("Octave");

			OctaveEngineFactory octaveFactory = new OctaveEngineFactory();
			octaveFactory.setWorkingDir(workingDir);
			OctaveEngine octave = octaveFactory.getScriptEngine();

			octave.eval("fig1=figure");
			octave.eval("retval = plotBWRated();");

		//	Process p3;
			try{
				
				for(int i=0; i<TxData.size(); i++){
					
					warningMessage = warningMessage+printTx.printText(TxData.get(i),"SCM_transmitter_java.txt");	
								
					octave.eval("[SpecMask,PSD,BW,compatBWList] = TxMPSD();");
					SpecMask.add(octave.get(OctaveDouble.class, "SpecMask"));
					PSD.add(octave.get(OctaveDouble.class, "PSD"));
					BW.add(octave.get(OctaveDouble.class, "BW")); 
					compatBWList.add(octave.get(OctaveDouble.class, "compatBWList")); 
					
					OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
					octave.put("specTag",specTag);
					octave.eval("plot(SpecMask(1:2:end-1),SpecMask(2:2:end),'r.-','LineWidth',2)");
					octave.eval("xpoint=(SpecMask(length(SpecMask)/2-1)+SpecMask(length(SpecMask)/2+1))/2;");
					octave.eval("minSpecPow=min(SpecMask(2:2:end));");
					octave.eval("text(xpoint,minSpecPow-1,specTag)");					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			octave.eval("saveas(fig1,'BWRatedAnalysis.png')");
			octave.close();
							
			ArrayList<String> compatModelList = new ArrayList<String>();
			ArrayList<String> nonCompatModelList = new ArrayList<String>();
			ArrayList<String> allCompatModelList = new ArrayList<String>();
				
			ExecuteBWRated execBWRated = new ExecuteBWRated();
			
			final ArrayList<Integer> indexList = new ArrayList<Integer>();
			for(int i=0; i<PSD.size(); i++){
				
				if(compatBWList.get(i).getData()[0]==1.00){
					allCompatModelList.add(txArray.get(txIndex[i]).ModelName);
					// Pass the model name to all compatible
				}else{
					if(compatBWList.get(i).getData()[0]==0.00){
						nonCompatModelList.add(txArray.get(txIndex[i]).ModelName);
						// Pass the model name to all non compatible
					}else{
						compatModelList.add(txArray.get(txIndex[i]).ModelName + " with " + Arrays.toString(compatBWList.get(i).getData()));
						indexList.add(i);
					}
				}
				
			}
			
			execBWRated.buildAllCompatList(allCompatModelList);
			execBWRated.buildCompatList(compatModelList);
			execBWRated.buildNonCompatList(nonCompatModelList);
			execBWRated.getFrame(BW,PSD,indexList);

		}
		
		break;
		
// bw Rated List
		
		case "bwRatedList":System.out.println("BW Rated List ANalysis Running"); 
		logger.info("BW Rated List ANalysis Running");
			
		warningMessage = warningMessage + printRx.printText(RxData.get(0),"SCM_receiver_java.txt");
				
		o = 0;
		SCMScheduleType rxSched3 = RxData.get(0).getScmSchedule().get(o); 
		SCMScheduleType txSched3 = TxData.get(0).getScmSchedule().get(o);
		GregorianCalendar Tx_Start_time3 = txSched3.getStartTime().toGregorianCalendar();
		GregorianCalendar Tx_End_time3 = txSched3.getEndTime().toGregorianCalendar();
		GregorianCalendar Rx_Start_time3 = rxSched3.getStartTime().toGregorianCalendar();
		GregorianCalendar Rx_End_time3 = rxSched3.getEndTime().toGregorianCalendar();
		
		if((Tx_Start_time3.before(Rx_Start_time3) && Tx_End_time3.before(Rx_Start_time3)) 
				|| (Tx_Start_time3.after(Rx_End_time3) && Tx_End_time3.after(Rx_End_time3)) ){
			
			warningFlag = true;
			warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";
			
		//	new Warn().setWarn("Systems Compatible", "Systems are compatible due to non-overlapping schedules");
			
		}else{
			
			ArrayList<OctaveDouble> SpecMask = new ArrayList<OctaveDouble>();
			final ArrayList<OctaveDouble> PSD = new ArrayList<OctaveDouble>();
			final ArrayList<OctaveDouble> BW = new ArrayList<OctaveDouble>();
			ArrayList<OctaveDouble> compatBWList = new ArrayList<OctaveDouble>();
			
			File workingDir = new File("Octave");

			OctaveEngineFactory octaveFactory = new OctaveEngineFactory();
			octaveFactory.setWorkingDir(workingDir);
			OctaveEngine octave = octaveFactory.getScriptEngine();

			octave.eval("fig1=figure");
			octave.eval("retval = plotBWRated();");

		//	Process p3;
			try{
				
				for(int i=0; i<TxData.size(); i++){
					
					warningMessage= warningMessage+printTx.printText(TxData.get(i),"SCM_transmitter_java.txt");	
					
				//	p3 = Runtime.getRuntime().exec(Command0);
				//	p3 = Runtime.getRuntime().exec(Command1);
					
					octave.eval("[SpecMask,PSD,BW,compatBWList] = TxMPSD();");
					SpecMask.add(octave.get(OctaveDouble.class, "SpecMask"));
					PSD.add(octave.get(OctaveDouble.class, "PSD"));
					BW.add(octave.get(OctaveDouble.class, "BW")); 
					compatBWList.add(octave.get(OctaveDouble.class, "compatBWList")); 
					
					OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
					octave.put("specTag",specTag);
					octave.eval("plot(SpecMask(1:2:end-1),SpecMask(2:2:end),'r.-','LineWidth',2)");
					octave.eval("xpoint=(SpecMask(length(SpecMask)/2-1)+SpecMask(length(SpecMask)/2+1))/2;");
					octave.eval("minSpecPow=min(SpecMask(2:2:end));");
					octave.eval("text(xpoint,minSpecPow-1,specTag)");					
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			octave.eval("saveas(fig1,'BWRatedAnalysis.png')");
			octave.close();
							
			ArrayList<String> compatModelList = new ArrayList<String>();
			ArrayList<String> nonCompatModelList = new ArrayList<String>();
			ArrayList<String> allCompatModelList = new ArrayList<String>();
				
			ExecuteBWRated execBWRated = new ExecuteBWRated();
			
			final ArrayList<Integer> indexList = new ArrayList<Integer>();
			for(int i=0; i<PSD.size(); i++){
				
				if(compatBWList.get(i).getData()[0]==1.00){
					allCompatModelList.add(txArray.get(txIndex[i]).ModelName);
					// Pass the model name to all compatible
				}else{
					if(compatBWList.get(i).getData()[0]==0.00){
						nonCompatModelList.add(txArray.get(txIndex[i]).ModelName);
						// Pass the model name to all non compatible
					}else{
						compatModelList.add(txArray.get(txIndex[i]).ModelName + " with " + Arrays.toString(compatBWList.get(i).getData()));
						indexList.add(i);
					}
				}
				
			}
			
			execBWRated.buildAllCompatList(allCompatModelList);
			execBWRated.buildCompatList(compatModelList);
			execBWRated.buildNonCompatList(nonCompatModelList);
			execBWRated.getFrame(BW,PSD,indexList);
			
		}
		
		break;

		case "ratedBTP": System.out.println("Rated BTP Analysis Running");
		logger.info("Rated BTP Analysis Running");
		
		warningMessage = warningMessage + printRx.printText(RxData.get(0),"SCM_receiver_java.txt");
		
		o = 0;
		SCMScheduleType rxSched_btp = RxData.get(0).getScmSchedule().get(o); 
		SCMScheduleType txSched_btp = TxData.get(0).getScmSchedule().get(o);
		GregorianCalendar Tx_Start_time_btp = txSched_btp.getStartTime().toGregorianCalendar();
		GregorianCalendar Tx_End_time_btp = txSched_btp.getEndTime().toGregorianCalendar();
		GregorianCalendar Rx_Start_time_btp = rxSched_btp.getStartTime().toGregorianCalendar();
		GregorianCalendar Rx_End_time_btp = rxSched_btp.getEndTime().toGregorianCalendar();
		
		if((Tx_Start_time_btp.before(Rx_Start_time_btp) && Tx_End_time_btp.before(Rx_Start_time_btp)) 
				|| (Tx_Start_time_btp.after(Rx_End_time_btp) && Tx_End_time_btp.after(Rx_End_time_btp)) ){
			
			warningFlag = true;
			warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";
		//	new Warn().setWarn("Systems Compatible", "Systems are compatible due to non-overlapping schedules");
			
		}else{
			ArrayList<OctaveDouble> Spec_BTP = new ArrayList<OctaveDouble>();
			ArrayList<OctaveDouble> compatBTPList = new ArrayList<OctaveDouble>();
			
			File workingBTPDir = new File("Octave");

			OctaveEngineFactory BTPoctaveFactory = new OctaveEngineFactory();
			BTPoctaveFactory.setWorkingDir(workingBTPDir);
			OctaveEngine octaveBTP = BTPoctaveFactory.getScriptEngine();

			octaveBTP.eval("fig2=figure");
			octaveBTP.eval("retval = plotBTPRated();");
			
					for(int i=0; i<TxData.size(); i++){
					
						if(TxData.get(i).getSpectrumMask().get(o).getHoppingData()!=null){
							
							warningMessage=warningMessage+printTx.printText(TxData.get(i),"SCM_transmitter_java.txt");
							
							if(TxData.get(i).getSpectrumMask().get(o).getHoppingData().
									getFrequencyList()!=null){
								octaveBTP.eval("[Spec_BTP,ExtSpecMask,compatBTPList] = TxHop_FreqList()");
								octaveBTP.eval("plot(ExtSpecMask(1:2:end-1),ExtSpecMask(2:2:end),'r.-','LineWidth',2)");
								OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
								octaveBTP.put("specTag",specTag);
								octaveBTP.eval("xpoint=ExtSpecMask(1);");
								octaveBTP.eval("minSpecPow=min(ExtSpecMask(2:2:end));");
								octaveBTP.eval("text(xpoint,minSpecPow-1,specTag)");
								
							}else{
								octaveBTP.eval("[Spec_BTP,NewBandList,Spec_MaxPower,compatBTPList] = TxHop_BandList()");	
								octaveBTP.eval("plot(NewBandList,Spec_MaxPower*ones(1,length(NewBandList)),'r.-','LineWidth',2)");
								OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
								octaveBTP.put("specTag",specTag);
							//	octaveBTP.eval("NewBandList");
								octaveBTP.eval("xpoint=NewBandList(1);");
								octaveBTP.eval("minSpecPow=Spec_MaxPower;");
								octaveBTP.eval("text(xpoint,minSpecPow-1,specTag)");
							}
							
							Spec_BTP.add(octaveBTP.get(OctaveDouble.class, "Spec_BTP"));
							compatBTPList.add(octaveBTP.get(OctaveDouble.class,"compatBTPList"));						
							
						}else{
							warningFlag = true;
							warningMessage = warningMessage + "\nSpectrum Mask "+txArray.get(txIndex[i]).ModelName + " is not a hopping system";
							
							/*warningDisplay.setWarn("Warning","Spectrum Mask "+
						txArray.get(txIndex[i]).ModelName + " is not a hopping system");*/
						}				
					}
				
					octaveBTP.eval("saveas(fig2,'BTPRatedAnalysis.png')");
					octaveBTP.close();
									
					ArrayList<String> compatList = new ArrayList<String>();
					ArrayList<String> nonCompatList = new ArrayList<String>();
					
					ExecuteBTPRated execBTPRated = new ExecuteBTPRated();
					
					for(int i=0; i<Spec_BTP.size(); i++){
						
						if(compatBTPList.get(i).getData()[0]==0.00){
							nonCompatList.add(txArray.get(txIndex[i]).ModelName);
							// Pass the model name to all compatible
						}else{
								compatList.add(txArray.get(txIndex[i]).ModelName + " with " + Arrays.toString(compatBTPList.get(i).getData()));
							}
					}
					
					execBTPRated.buildCompatList(compatList);
					execBTPRated.buildNonCompatList(nonCompatList);
					execBTPRated.getFrame();

		}

		break;
		
// BTP Rated List Analysis		
		case "btpRatedList": System.out.println("BTP Rated List Analysis Running");
		logger.info("BTP Rated List Analysis Running");
		warningMessage = warningMessage + printRx.printText(RxData.get(0),"SCM_receiver_java.txt");
		
		o = 0;
		SCMScheduleType rxSched4 = RxData.get(0).getScmSchedule().get(o); 
		SCMScheduleType txSched4 = TxData.get(0).getScmSchedule().get(o);
		GregorianCalendar Tx_Start_time4 = txSched4.getStartTime().toGregorianCalendar();
		GregorianCalendar Tx_End_time4 = txSched4.getEndTime().toGregorianCalendar();
		GregorianCalendar Rx_Start_time4 = rxSched4.getStartTime().toGregorianCalendar();
		GregorianCalendar Rx_End_time4 = rxSched4.getEndTime().toGregorianCalendar();
		
		if((Tx_Start_time4.before(Rx_Start_time4) && Tx_End_time4.before(Rx_Start_time4)) 
				|| (Tx_Start_time4.after(Rx_End_time4) && Tx_End_time4.after(Rx_End_time4)) ){
			
				warningFlag = true;
				warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";
			
			//new Warn().setWarn("Systems Compatible", "Systems are compatible due to non-overlapping schedules");
			
		}else{
			ArrayList<OctaveDouble> Spec_BTP = new ArrayList<OctaveDouble>();
			ArrayList<OctaveDouble> compatBTPList = new ArrayList<OctaveDouble>();
			
			File workingBTPDir = new File("Octave");

			OctaveEngineFactory BTPoctaveFactory = new OctaveEngineFactory();
			BTPoctaveFactory.setWorkingDir(workingBTPDir);
			OctaveEngine octaveBTP = BTPoctaveFactory.getScriptEngine();

			octaveBTP.eval("fig2=figure");
			octaveBTP.eval("retval = plotBTPRated();");
			
					for(int i=0; i<TxData.size(); i++){
					
						if(TxData.get(i).getSpectrumMask().get(o).getHoppingData()!=null){
							
							warningMessage = warningMessage+printTx.printText(TxData.get(i),"SCM_transmitter_java.txt");
							
							if(TxData.get(i).getSpectrumMask().get(o).getHoppingData().
									getFrequencyList()!=null){
								octaveBTP.eval("[Spec_BTP,ExtSpecMask,compatBTPList] = TxHop_FreqList()");
								octaveBTP.eval("plot(ExtSpecMask(1:2:end-1),ExtSpecMask(2:2:end),'r.-','LineWidth',2)");
								OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
								octaveBTP.put("specTag",specTag);
								octaveBTP.eval("xpoint=ExtSpecMask(1);");
								octaveBTP.eval("minSpecPow=min(ExtSpecMask(2:2:end));");
								octaveBTP.eval("text(xpoint,minSpecPow-1,specTag)");
								
							}else{
								octaveBTP.eval("[Spec_BTP,NewBandList,Spec_MaxPower,compatBTPList] = TxHop_BandList()");	
								octaveBTP.eval("plot(NewBandList,Spec_MaxPower*ones(1,length(NewBandList)),'r.-','LineWidth',2)");
								OctaveString specTag = new OctaveString(txArray.get(txIndex[i]).ModelName);
								octaveBTP.put("specTag",specTag);
							//	octaveBTP.eval("NewBandList");
								octaveBTP.eval("xpoint=NewBandList(1);");
								octaveBTP.eval("minSpecPow=Spec_MaxPower;");
								octaveBTP.eval("text(xpoint,minSpecPow-1,specTag)");
							}
							
							Spec_BTP.add(octaveBTP.get(OctaveDouble.class, "Spec_BTP"));
							compatBTPList.add(octaveBTP.get(OctaveDouble.class,"compatBTPList"));						
							
						}else{
							
							warningFlag = true;
							warningMessage = warningMessage + "\nSpectrum Mask"+txArray.get(txIndex[i]).ModelName + "is not a hopping system";
							/*
							warningDisplay.setWarn("Warning","Spectrum Mask "+
						txArray.get(txIndex[i]).ModelName + " is not a hopping system");*/
						}				
					}
				
					octaveBTP.eval("saveas(fig2,'BTPRatedAnalysis.png')");
					octaveBTP.close();
									
					ArrayList<String> compatList = new ArrayList<String>();
					ArrayList<String> nonCompatList = new ArrayList<String>();
					
					ExecuteBTPRated execBTPRated = new ExecuteBTPRated();
					
					for(int i=0; i<Spec_BTP.size(); i++){
						
						if(compatBTPList.get(i).getData()[0]==0.00){
							nonCompatList.add(txArray.get(txIndex[i]).ModelName);
							// Pass the model name to all compatible
						}else{
								compatList.add(txArray.get(txIndex[i]).ModelName + " with " + Arrays.toString(compatBTPList.get(i).getData()));
							}
					}
					
					execBTPRated.buildCompatList(compatList);
					execBTPRated.buildNonCompatList(nonCompatList);
					execBTPRated.getFrame();

		}
		
				break;

		case "dcRatedList": System.out.println("DC Rated List Analysis Running");
		logger.info("DC Rated List Analysis Running");
		
		warningMessage = warningMessage + printRx.printText(RxData.get(0),"SCM_receiver_java.txt");
		
		o = 0;
		SCMScheduleType rxSched_dc = RxData.get(0).getScmSchedule().get(o); 
		SCMScheduleType txSched_dc = TxData.get(0).getScmSchedule().get(o);
		GregorianCalendar Tx_Start_time_dc = txSched_dc.getStartTime().toGregorianCalendar();
		GregorianCalendar Tx_End_time_dc = txSched_dc.getEndTime().toGregorianCalendar();
		GregorianCalendar Rx_Start_time_dc = rxSched_dc.getStartTime().toGregorianCalendar();
		GregorianCalendar Rx_End_time_dc = rxSched_dc.getEndTime().toGregorianCalendar();
		
		if((Tx_Start_time_dc.before(Rx_Start_time_dc) && Tx_End_time_dc.before(Rx_Start_time_dc)) 
				|| (Tx_Start_time_dc.after(Rx_End_time_dc) && Tx_End_time_dc.after(Rx_End_time_dc)) ){
		
			warningFlag = true;
			warningMessage = warningMessage + "\nSystems are compatible due to non-overlapping schedules";
			
			//new Warn().setWarn("Systems Compatible", "Systems are compatible due to non-overlapping schedules");
			
		}else{
			ArrayList<OctaveDouble> compatDutyList = new ArrayList<OctaveDouble>();
			
			File workingDutyDir = new File("Octave");

			OctaveEngineFactory dutyOctaveFactory = new OctaveEngineFactory();
			dutyOctaveFactory.setWorkingDir(workingDutyDir);
			OctaveEngine octaveDuty = dutyOctaveFactory.getScriptEngine();

			octaveDuty.eval("fig3=figure");
		//	octaveDuty.eval("retval = plotDutyRated();");
			
			for(int i=0; i<TxData.size(); i++){
				
				if(TxData.get(i).getSpectrumMask().get(o).getHoppingData()!=null){
					
					warningMessage= warningMessage+printTx.printText(TxData.get(i),"SCM_transmitter_java.txt");
					
					if(TxData.get(i).getSpectrumMask().get(o).getHoppingData().getFrequencyList()!=null){
						octaveDuty.eval("[Spec_mask_new,p_Tx_new,compatDutyList] = TxDuty_FreqList();");
						
					//	octaveBTP.eval("plot(ExtSpecMask(1:2:end-1),ExtSpecMask(2:2:end),'r.-','LineWidth',2)");
					}else{
						octaveDuty.eval("[Spec_mask_new,p_Tx_new,compatDutyList] = TxDuty_BandList();");	
					//	octaveBTP.eval("plot(NewBandList,Spec_MaxPower*ones(1,length(NewBandList)),'r.-','LineWidth',2)");
					}
					
					compatDutyList.add(octaveDuty.get(OctaveDouble.class,"compatDutyList"));						
					
				}else{
					warningFlag = true;
					warningMessage = warningMessage + "\nSpectrum Mask "+ txArray.get(txIndex[i]).ModelName+ " is not a hopping system";
					/*
					warningDisplay.setWarn("Warning","Spectrum Mask "+ txArray.get(txIndex[i]).ModelName
							+ " is not a hopping system");*/
				}				
			}
					
					octaveDuty.close();
					
					ArrayList<String> dutyCompatList = new ArrayList<String>();
					ArrayList<String> dutyNonCompatList = new ArrayList<String>();
					
					ExecuteDuty execDutyRated = new ExecuteDuty();
					
					for(int i=0; i<compatDutyList.size(); i++){
						
						if(compatDutyList.get(i).getData()[0]==0.00){
							dutyNonCompatList.add(txArray.get(txIndex[i]).ModelName);
							// Pass the model name to all compatible
						}else{
								dutyCompatList.add(txArray.get(txIndex[i]).ModelName
										+ " with Duty Cycle " + Arrays.toString(compatDutyList.get(i).getData()));
							}
					}
					
					execDutyRated.buildCompatList(dutyCompatList);
					execDutyRated.buildNonCompatList(dutyNonCompatList);
					execDutyRated.getFrame();

		}
				
				break;
				
		default:break;
		}
	}
}
