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
* PrintRxText.java
* Outputs receiver data in text format for Octave functions to process it
*/

package Execute;

import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.ieee.dyspansc._1900._5.scm.BTPRatedList;
import org.ieee.dyspansc._1900._5.scm.BTPRating;
import org.ieee.dyspansc._1900._5.scm.BWRatedList;
import org.ieee.dyspansc._1900._5.scm.BWRating;
import org.ieee.dyspansc._1900._5.scm.DCRatedList;
import org.ieee.dyspansc._1900._5.scm.DCRating;
import org.ieee.dyspansc._1900._5.scm.InflectionPnt;
import org.ieee.dyspansc._1900._5.scm.Rating;
import org.ieee.dyspansc._1900._5.scm.RxModel;

import SCM_home.Home;

public class PrintRxText extends PrintText {

	final Logger logger = Logger.getLogger(PrintRxText.class);

	public String printText(RxModel model, String SaveName) {

		logger.addAppender(Home.appender);
		String warningMessage = "\n";
		PrintWriter printfile;
		try {
			System.out.println("Rx Printing");
			logger.info("Rx Printing");

			// MethodAnalysis meth = new MethodAnalysis();
			// printfile = new PrintWriter (meth.getFilePath()+"Octave/" + SaveName);
			printfile = new PrintWriter(SaveName);

			// Create the file name
			// String savefileName = SaveName_getCurrentDate;

			// Adding the new Directory for adding the output files
			// printfile = new PrintWriter ("CompatabilityAnalysis/" + SaveName);
			int o = 0;

			if (model.getUnderlayMask().get(o) != null) {
				System.out.println("Underlay Mask Printing");
				logger.info("Underlay Mask Printing");
				printfile.println("# name: Rx_UnderlayMask");
				printfile.println("# type: matrix");
				printfile.println("# rows: 1");
				List<InflectionPnt> infPoint = model.getUnderlayMask().get(o).getScmMask().getInflectionPnt();
				String data = "";
				for (int i = 0; i < infPoint.size(); i++) {
					data = data + infPoint.get(i).getFrequency() + " ";
					data = data + infPoint.get(i).getRelativePower() + " ";
				}
				printfile.println("# columns: " + infPoint.size() * 2);
				printfile.println(data);
				printfile.println("");
				printfile.println("");

				printfile.println("# name: Rx_ResBW");
				printfile.println("# type: scalar");
				if (!String.valueOf(model.getUnderlayMask().get(o).getResolutionBW()).equals(null)) {
					printfile.println(model.getUnderlayMask().get(o).getResolutionBW());
				}
				printfile.println("");
				printfile.println("");

				if (model.getUnderlayMask().get(o).getRating() != null) {

					Rating ratedMask = model.getUnderlayMask().get(o).getRating();

					if (ratedMask.getRatedBW() != 0.0) {

						printfile.println("# name: Rx_BWRatedList");
						printfile.println("# type: matrix");
						printfile.println("# rows: 1");
						printfile.println("# columns: " + 2);
						printfile.println(ratedMask.getRatedBW() + " " + 0.0);
						printfile.println("");
						printfile.println("");
					}

					if (ratedMask.getBwRatedList() != null) {
						BWRatedList bwRatedList = ratedMask.getBwRatedList();
						List<BWRating> bwRating = bwRatedList.getBwRating();
						String bwData = "";

						for (int i = 0; i < bwRating.size(); i++) {
							String data1 = String.valueOf(bwRating.get(i).getRatedBW());
							String data2 = String.valueOf(bwRating.get(i).getAdjustment());
							bwData = bwData + data1 + " " + data2 + " ";
						}

						printfile.println("# name: Rx_BWRatedList");
						printfile.println("# type: matrix");
						printfile.println("# rows: 1");
						printfile.println("# columns: " + bwRating.size() * 2);
						printfile.println(bwData);
						printfile.println("");
						printfile.println("");
					}

					if (ratedMask.getRatedBTP() != 0.0) {

						printfile.println("# name: Rx_BTPRatedList");
						printfile.println("# type: matrix");
						printfile.println("# rows: 1");
						printfile.println("# columns: " + 2);
						printfile.println(ratedMask.getRatedBTP() + " " + 0.0);
						printfile.println("");
						printfile.println("");
					}

					if (ratedMask.getBtpRatedList() != null) {
						BTPRatedList btpRatedList = ratedMask.getBtpRatedList();
						List<BTPRating> btpRating = btpRatedList.getBtpRating();
						String btpData = "";

						for (int i = 0; i < btpRating.size(); i++) {
							String data1 = String.valueOf(btpRating.get(i).getBtp());
							String data2 = String.valueOf(btpRating.get(i).getAdjustment());
							btpData = btpData + data1 + " " + data2 + " ";
						}

						printfile.println("# name: Rx_BTPRatedList");
						printfile.println("# type: matrix");
						printfile.println("# rows: 1");
						printfile.println("# columns: " + btpRating.size() * 2);
						printfile.println(btpData);
						printfile.println("");
						printfile.println("");
					}

					if (ratedMask.getDcRatedList() != null) {

						DCRatedList dcRatedList = ratedMask.getDcRatedList();
						List<DCRating> dcRating = dcRatedList.getDcRating();
						String dcData = "";

						for (int i = 0; i < dcRating.size(); i++) {
							String data1 = String.valueOf(dcRating.get(i).getDc());
							String data2 = String.valueOf(dcRating.get(i).getMaxDwellTime());
							String data3 = String.valueOf(dcRating.get(i).getAdjustment());
							dcData = dcData + data1 + " " + data2 + " " + data3 + " ";
						}

						printfile.println("# name: Rx_DutyList");
						printfile.println("# type: matrix");
						printfile.println("# rows: 1");
						printfile.println("# columns: " + dcRating.size() * 3);
						printfile.println(dcData);
						printfile.println("");
						printfile.println("");

					}

					if (ratedMask.getPorPIndex() != 0) {
						printfile.println("# name: Rx_Policy");
						printfile.println("# type: scalar");
						printfile.println(ratedMask.getPorPIndex());
						printfile.println("");
						printfile.println("");
					}

				}

			}

			warningMessage = warningMessage + printReferencePower(model.getReferencePower().get(o), printfile, "Rx");
			warningMessage = warningMessage + printPowerMap(model.getScmPowerMap().get(o), printfile, "Rx");
			printLocation(model.getScmLocation().get(o), printfile, "Rx");
			printTime(model.getScmSchedule().get(o), printfile, "Rx");

			printfile.close();
		} catch (Exception e) {

			warningMessage = warningMessage + "\nERROR - Couldn't save the Receiver data correctly";
			// new Warn().setWarn("Error", "Couldn't save the Receiver data correctly");
		}
		return warningMessage;
	}
}
