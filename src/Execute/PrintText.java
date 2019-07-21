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
* PrintText.java
* Outputs common data for transmitter and receiver models in text
* format for the Octave functions to process it 
*/

package Execute;

import java.io.PrintWriter;
import java.util.List;

import org.ieee.dyspansc._1900._5.scm.GainMapValue;
import org.ieee.dyspansc._1900._5.scm.PropMapValue;
import org.ieee.dyspansc._1900._5.scm.PropagationModel;
import org.ieee.dyspansc._1900._5.scm.ReferencePower;
import org.ieee.dyspansc._1900._5.scm.SCMLocation;
import org.ieee.dyspansc._1900._5.scm.SCMPowerMap;
import org.ieee.dyspansc._1900._5.scm.SCMPropagationMap;
import org.ieee.dyspansc._1900._5.scm.SCMSchedule;

public class PrintText {

	public String printReferencePower(ReferencePower refPower, PrintWriter printfile, String device) {
		String warningMessage = "\n";
		try {
			Double data = refPower.getValue();
			printfile.println("# name: " + device + "_TotPow");
			printfile.println("# type: scalar");
			printfile.println(data);
			printfile.println("");
			printfile.println("");
		} catch (Exception e) {
			e.printStackTrace();
			warningMessage = warningMessage + "\nIn the " + device
					+ " model, Inconsistent Data, the data for reference power is inconsisent";

			// new Warn().setWarn("Inconsistent Data", "the data for reference power is
			// inconsisent");
		}
		return warningMessage;
	}

	public String printPowerMap(SCMPowerMap powMap, PrintWriter printfile, String device) {
		String warningMessage = "\n";
		if (powMap.getGainMap() != null) {
			String PowerPrintData;
			try {
				List<GainMapValue> gainMapValue = powMap.getGainMap().getGainMapValue();

				StringBuilder PowerStringBuilder = new StringBuilder();
				PowerStringBuilder.setLength(0);

				String ele = "";
				String azi = "";
				String gain = "";
				double prevElevation = 0.0;

				String dataValue = "";
				String strData = "";

				for (int i = 0; i < gainMapValue.size(); i++) {
					ele = String.valueOf(gainMapValue.get(i).getElevation());
					azi = String.valueOf(gainMapValue.get(i).getAzimuth());
					gain = String.valueOf(gainMapValue.get(i).getGain());
					if (ele != null && !ele.equalsIgnoreCase("null")) {
						PowerStringBuilder.append(360 + " " + String.valueOf(ele) + " ");
					} else if (azi != null && !azi.equalsIgnoreCase("null")) {
						PowerStringBuilder.append(azi + " ");
					} else if (gain != null && !gain.equalsIgnoreCase("null")) {
						PowerStringBuilder.append(gain + " ");
					}
				}
				PowerStringBuilder.append(0.0);
				PowerPrintData = PowerStringBuilder.toString();
				String PowerTest = PowerPrintData.replaceAll(" ", "");
				if (PowerTest.equals("") || PowerTest.equals("0")) {
					PowerPrintData = "0 0";
				}
			} catch (Exception e) {
				warningMessage = warningMessage + "\nIn the " + device
						+ " model, Inconsistent Data, the data for power map is inconsistent or necessary";

				// new Warn().setWarn("Inconsistent Data", "the data for power map is
				// inconsistent or necessary");
				PowerPrintData = "0 0";
			}

			printfile.println("# name: " + device + "_PowerMap");
			printfile.println("# type: matrix");
			printfile.println("# rows: 1");
			String[] dataList = PowerPrintData.split(" ");
			printfile.println("# columns: " + dataList.length);
			printfile.println(PowerPrintData);
			printfile.println("");
			printfile.println("");

		}
		return warningMessage;
	}

	public String printPropagationMap(SCMPropagationMap propMap, PrintWriter printfile, String device) {

		String warningMessage = "\n";
		if (propMap.getPropMap() != null) {
			String propPrintData;
			try {
				List<PropMapValue> propMapValue = propMap.getPropMap().getPropMapValue();

				StringBuilder propStringBuilder = new StringBuilder();
				propStringBuilder.setLength(0);

				String ele = "";
				String azi = "";
				String n1 = "";
				String dist = "";
				String n2 = "";

				double prevElevation = 0.0;

				for (int i = 0; i < propMapValue.size(); i++) {

					ele = String.valueOf(propMapValue.get(i).getElevation());
					azi = String.valueOf(propMapValue.get(i).getAzimuth());
					PropagationModel propModal = (propMapValue.get(i).getPropagationModel());

					if (ele != null && !ele.equalsIgnoreCase("null")) {
//						propStringBuilder.append(360 + " " + String.valueOf(ele) + " ");
						propStringBuilder.append(String.valueOf(ele) + " ");
					} else if (azi != null && !azi.equalsIgnoreCase("null")) {
						propStringBuilder.append(azi + " ");
					} else if (propModal != null) {
						if (propModal.getPiecewiseLinear() == null || propModal.getLinear() != 0.0) {
							n1 = String.valueOf(propModal.getLinear());
							propStringBuilder.append(n1 + " ");
						} else if (propModal.getPiecewiseLinear() == null) {
							n1 = String.valueOf(propModal.getPiecewiseLinear().getFirstExponent());
							dist = String.valueOf(propModal.getPiecewiseLinear().getBreakpoint());
							n2 = String.valueOf(propModal.getPiecewiseLinear().getSecondExponent());
							propStringBuilder.append(n1 + " " + dist + " " + n2 + " ");
						}

					}

//					if(propMapValue.get(i).getElevation()!=prevElevation && 
//							propMapValue.get(i).getElevation()!=0.0){
//						ele = 360 + " " + String.valueOf(propMapValue.get(i).getElevation()) + " ";
//					}else{
//						ele = "";
//					}
//					azi = String.valueOf(propMapValue.get(i).getAzimuth());
//					
//					propStringBuilder.append(ele);
//					if(propMapValue.get(i).getAzimuth()!=0.0){
//						propStringBuilder.append(azi + " ");
//					}
//					
//					if(propMapValue.get(i).getPropagationModel().getPiecewiseLinear()==null ||
//							propMapValue.get(i).getPropagationModel().getLinear()!=0.0){
//						n1 = String.valueOf(propMapValue.get(i).getPropagationModel().getLinear());
//						propStringBuilder.append(n1+" ");
//					}else{
//						
//						n1 = String.valueOf(propMapValue.get(i).getPropagationModel().
//								getPiecewiseLinear().getFirstExponent());					
//						dist = String.valueOf(propMapValue.get(i).getPropagationModel().
//								getPiecewiseLinear().getBreakpoint());
//						n2 = String.valueOf(propMapValue.get(i).getPropagationModel().
//								getPiecewiseLinear().getSecondExponent());
//						propStringBuilder.append(n1+" "+dist+" "+n2+" ");
//					}
//					
//					prevElevation = propMapValue.get(i).getElevation();
				}
//				propStringBuilder.append(0.0);
				propPrintData = propStringBuilder.toString();
				String propTest = propPrintData.replaceAll(" ", "");
				if (propTest.equals("") || propTest.equals("0")) {
					propPrintData = "0 0";
				}
			} catch (Exception e) {
				warningMessage = warningMessage + "\n In the " + device
						+ " model, Inconsistent Data, the data for power map is inconsistent or necessary";

				// new Warn().setWarn("Inconsistent Data", "the data for power map is
				// inconsistent or necessary");
				propPrintData = "0 0";
			}

			printfile.println("# name: " + device + "_PropMap");
			printfile.println("# type: matrix");
			printfile.println("# rows: 1");
			String[] dataList = propPrintData.split(" ");
			printfile.println("# columns: " + dataList.length);
			printfile.println(propPrintData);
			printfile.println("");
			printfile.println("");
		}
		return warningMessage;
	}

	public void printLocation(SCMLocation loc, PrintWriter printfile, String device) {

		if (loc.getLocation().getPoint() != null) {

			printfile.println("# name: " + device + "_Alt");
			printfile.println("# type: scalar");
			printfile.println(loc.getLocation().getPoint().getAltitude());
			printfile.println("");
			printfile.println("");

			printfile.println("# name: " + device + "_Long");
			printfile.println("# type: scalar");
			printfile.println(loc.getLocation().getPoint().getLongitude());
			printfile.println("");
			printfile.println("");

			printfile.println("# name: " + device + "_Lat");
			printfile.println("# type: scalar");
			printfile.println(loc.getLocation().getPoint().getLatitude());
			printfile.println("");
			printfile.println("");
		}

	}

	public void printTime(SCMSchedule sched, PrintWriter printfile, String device) {

	}

}
