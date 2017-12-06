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
 * PrintTxText.java 
 * Outputs Transmitter model data in text format for the Octave 
 * functions to process it 
*/

package Execute;

import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.ieee.dyspansc._1900._5.scm.BandType;
import org.ieee.dyspansc._1900._5.scm.HoppingDataType;
import org.ieee.dyspansc._1900._5.scm.InflectionPointType;
import org.ieee.dyspansc._1900._5.scm.TxModelType;

import SCM_home.Home;


public class PrintTxText extends PrintText{
	
	final Logger logger = Logger.getLogger(MethodAnalysis.class);
	public String printText(TxModelType model, String SaveName){
		
		logger.addAppender(Home.appender);
		String warningMessage= "\n";
		PrintWriter printfile;
			try {
				System.out.println("Tx Printing");
				logger.info("Tx Printing");
				//printfile = new PrintWriter ("Octave/" + SaveName);
				//Adding the Output folder for storing the output files
				
				MethodAnalysis meth = new MethodAnalysis();
				printfile = new PrintWriter (meth.getFilePath()+"Octave/" + SaveName);	
					int o = 0;
					if(model.getSpectrumMask().get(o) != null){
						System.out.println("Spec");
						logger.info("Spec");
						printfile.println("# name: "+"Tx_SpecMask");
						printfile.println("# type: matrix");
						printfile.println("# rows: 1");
						List<InflectionPointType> infPoint = model.getSpectrumMask().get(o).getScmMask().getInflectionPoint();
						String data = "";
						for(int i=0; i<infPoint.size(); i++){
							data = data + infPoint.get(i).getFrequency() + " ";
							data = data + infPoint.get(i).getRelativePower() + " ";
						}
						printfile.println("# columns: " + infPoint.size()*2);
					    printfile.println(data);
					    printfile.println("");
						printfile.println("");
						
						printfile.println("# name: "+"Tx_Centerfreq");
						printfile.println("# type: scalar");
						if(!String.valueOf(model.getSpectrumMask().get(o).getScmMask().getRefFrequency()).equals(null)){
						    printfile.println (model.getSpectrumMask().get(o).getScmMask().getRefFrequency());
						}else{
							printfile.println(0);
						}
					    printfile.println("");
						printfile.println("");
						
						printfile.println("# name: Tx_ResBW");
						printfile.println("# type: scalar");
						if(!String.valueOf(model.getSpectrumMask().get(o).getResolutionBW()).equals(null)){
						    printfile.println (model.getSpectrumMask().get(o).getResolutionBW());
						}
					    printfile.println("");
						printfile.println("");
						
						if(model.getSpectrumMask().get(o).getHoppingData()!=null){
							
							HoppingDataType hopData  = model.getSpectrumMask().get(o).getHoppingData();
							
							if(hopData.getBandList()!=null){
								List<BandType> bandList = hopData.getBandList().getBand();
								printfile.println("# name: Tx_BandList");
								printfile.println("# type: matrix");
								printfile.println("# rows: 1");				
								data = "";
								for(int i=0; i<bandList.size(); i++){
									data = data + bandList.get(i).getStartFrequency()+ " ";
									data = data + bandList.get(i).getEndFrequency() + " ";
								}
								printfile.println("# columns: " + bandList.size()*2);
							    printfile.println(data);
							    printfile.println("");
								printfile.println("");
							}
							if(hopData.getFrequencyList()!=null){
								List<Double> freqList = hopData.getFrequencyList().getReferenceFreq();
								printfile.println("# name: Tx_FreqList");
								printfile.println("# type: matrix");
								printfile.println("# rows: 1");
								data = "";
								for(int i=0; i<freqList.size(); i++){
									data = data + freqList.get(i) + " ";
								}
								printfile.println("# columns: " + freqList.size());
							    printfile.println(data);
							    printfile.println("");
								printfile.println("");
							}
							
							printfile.println("# name: Tx_DwellTime");
							printfile.println("# type: scalar");
							if(!String.valueOf(hopData.getDwellTime()).equals(null)){
								printfile.println (hopData.getDwellTime());
							}
							printfile.println("");
							printfile.println("");
							
							printfile.println("# name: Tx_RevisitPeriod");
							printfile.println("# type: scalar");
							if(!String.valueOf(hopData.getRevisitPeriod()).equals(null)){
							    printfile.println (hopData.getRevisitPeriod());
							}
						    printfile.println("");
							printfile.println("");
							
							
						}
					}
					warningMessage= warningMessage + printReferencePower(model.getReferencePower().get(o), printfile, "Tx");
					warningMessage= warningMessage +printPowerMap(model.getScmPowerMap().get(o),printfile,"Tx");
					warningMessage= warningMessage +printPropagationMap(model.getScmPropagationMap().get(o),printfile,"Tx");
					printLocation(model.getScmLocation().get(o),printfile,"Tx");
					printTime(model.getScmSchedule().get(o),printfile,"Tx");
					
					printfile.close ();
				}catch(Exception e){
					
					warningMessage = warningMessage + "\nERROR- Couldn't save the Transmitter data correctly";
					//new Warn().setWarn("Error", "Couldn't save the Transmitter data correctly");
				}
			return warningMessage;
	}

}
