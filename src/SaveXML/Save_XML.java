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
 * Save_XML.java
 * Saves all applicable GUI information (in current version)
 * as an XML file based on the IEEE 1900.5.2 XML Schema for
 * Spectrum Consumption Models
*/

package SaveXML;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.ieee.dyspansc._1900._5.scm.AntennaHeight;
import org.ieee.dyspansc._1900._5.scm.BTPRatedList;
import org.ieee.dyspansc._1900._5.scm.BTPRating;
import org.ieee.dyspansc._1900._5.scm.BWRatedList;
import org.ieee.dyspansc._1900._5.scm.BWRating;
import org.ieee.dyspansc._1900._5.scm.Band;
import org.ieee.dyspansc._1900._5.scm.BandList;
import org.ieee.dyspansc._1900._5.scm.CircularSurface;
import org.ieee.dyspansc._1900._5.scm.Cylinder;
import org.ieee.dyspansc._1900._5.scm.DCRatedList;
import org.ieee.dyspansc._1900._5.scm.DCRating;
import org.ieee.dyspansc._1900._5.scm.FrequencyList;
import org.ieee.dyspansc._1900._5.scm.GainMap;
import org.ieee.dyspansc._1900._5.scm.GainMapValue;
import org.ieee.dyspansc._1900._5.scm.HoppingData;
import org.ieee.dyspansc._1900._5.scm.InflectionPnt;
import org.ieee.dyspansc._1900._5.scm.IntermodulationMask;
import org.ieee.dyspansc._1900._5.scm.Location;
import org.ieee.dyspansc._1900._5.scm.ObjectFactory;
import org.ieee.dyspansc._1900._5.scm.Orientation;
import org.ieee.dyspansc._1900._5.scm.PathPoint;
import org.ieee.dyspansc._1900._5.scm.Path;
import org.ieee.dyspansc._1900._5.scm.PiecewiseLinear;
import org.ieee.dyspansc._1900._5.scm.PointSurface;
import org.ieee.dyspansc._1900._5.scm.Point;
import org.ieee.dyspansc._1900._5.scm.PolygonSurface;
import org.ieee.dyspansc._1900._5.scm.Polyhedron;
import org.ieee.dyspansc._1900._5.scm.PropMap;
import org.ieee.dyspansc._1900._5.scm.PropMapValue;
import org.ieee.dyspansc._1900._5.scm.PropagationModel;
import org.ieee.dyspansc._1900._5.scm.Rating;
import org.ieee.dyspansc._1900._5.scm.ReferencePower;
import org.ieee.dyspansc._1900._5.scm.RelativeToPlatform;
import org.ieee.dyspansc._1900._5.scm.RxModel;
import org.ieee.dyspansc._1900._5.scm.SCMLocation;
import org.ieee.dyspansc._1900._5.scm.SCMMask;
import org.ieee.dyspansc._1900._5.scm.SCMPolygon;
import org.ieee.dyspansc._1900._5.scm.SCMPowerMap;
import org.ieee.dyspansc._1900._5.scm.SCMPropagationMap;
import org.ieee.dyspansc._1900._5.scm.SCMSchedule;
import org.ieee.dyspansc._1900._5.scm.Side;
import org.ieee.dyspansc._1900._5.scm.SpectrumMask;
import org.ieee.dyspansc._1900._5.scm.TowardReferencePoint;
import org.ieee.dyspansc._1900._5.scm.TxModel; 
import org.ieee.dyspansc._1900._5.scm.UnderlayMask;
import org.w3c.dom.Document;

import SCM_gui.IMC;
import SCM_gui.PowerMap;
import SCM_gui.Schedule;
import SCM_gui.SpecMask;


public class Save_XML extends ObjectFactory {
	
	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder;
	Document doc;
		
	TxModel TxModel;
	RxModel RxModel;
	Object Model;
	
	/*
	 * Maintain the global warning message and flag to display the warnings
	 */
	public String warningMessage = "\n";
	public boolean warningFlag = false;
	
	//Creating a XML document.
	public void createXML(String SystemID, String device, String purpose){
		
		try {
			
			docBuilder = docFactory.newDocumentBuilder();
			doc=docBuilder.newDocument();
			
			if(device.equals("Tx")){
				TxModel = createTxModel();
				TxModel.setSystemID(SystemID);
				TxModel.setPurpose(purpose);
				Model = (Object) TxModel;
			}else{
				RxModel = createRxModel();
				RxModel.setSystemID(SystemID);
				RxModel.setPurpose(purpose);
				Model = (Object) RxModel;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* Adding Spectrum Mask information to the XML document
	 * (Considering that we are adding a single Spectrum Mask)
	 */
	
	public void addSpec(SpecMask specMask) {
		
		int o = 0;
		
		SpectrumMask spec = new SpectrumMask();
		TxModel.getSpectrumMask().add(spec);
		TxModel.getSpectrumMask().get(o).setScmMask(new SCMMask());
		
		try{
			TxModel.getSpectrumMask().get(o).getScmMask().setRefFrequency(Double.parseDouble(specMask.TextField.getText()));
		}catch(Exception e){
			TxModel.getSpectrumMask().get(o).getScmMask().setRefFrequency(0.0);
		}
		
		TableModel Sdata = specMask.table.getModel();
		for (int i = 0; i < Sdata.getRowCount(); i++) {
			try{
			InflectionPnt ifPoint = new InflectionPnt();
			TxModel.getSpectrumMask().get(o).getScmMask().getInflectionPnt().add(ifPoint);
			
			Double data = Double.parseDouble(Sdata.getValueAt(i, 1).toString());
			TxModel.getSpectrumMask().get(o).getScmMask().getInflectionPnt().get(i).setFrequency(data);
			data = Double.parseDouble(Sdata.getValueAt(i, 2).toString());
			TxModel.getSpectrumMask().get(o).getScmMask().getInflectionPnt().get(i).setRelativePower(data);
		
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe entry at row: " +(i+1)+" in the Spectrum Mask table should be numerical";
				warningFlag = true;
			}
		}    	
		try{
		TxModel.getSpectrumMask().get(o).setResolutionBW(Double.parseDouble(specMask.ResTextField.getText()));
		}catch(Exception e){
			warningMessage = warningMessage + "\nThe entry in Resolution BW field should be numeric";
			warningFlag = true;
			
		//	new Warn().setWarn("Warning", "The entry in Resolution BW field should be numeric");
		}
		// If there is Spectrum Hopping Data
		
		if(specMask.yes.isSelected()==true){
			
			TxModel.getSpectrumMask().get(o).setHoppingData(new HoppingData());
			
			TxModel.getSpectrumMask().get(o).getHoppingData().setDwellTime(
					Double.parseDouble(specMask.SpecHop.DwellField.getText()));
			TxModel.getSpectrumMask().get(o).getHoppingData().setRevisitPeriod(
					Double.parseDouble(specMask.SpecHop.RevisitField.getText()));
			
			if(specMask.FreqListBtn.isSelected()==true && specMask.SpecHop.freqListBtn1.isSelected()==true){
				TxModel.getSpectrumMask().get(o).getHoppingData().setFrequencyList(new FrequencyList());
				
				TableModel freqTable = specMask.SpecHop.table2.getModel();
				
				for(int i=0; i<freqTable.getRowCount(); i++){
					try{
					TxModel.getSpectrumMask().get(o).getHoppingData().getFrequencyList().getReferenceFreq()
					.add(Double.parseDouble(freqTable.getValueAt(i, 1).toString()));
					}catch(Exception e){
						warningMessage = warningMessage + "\nThe entry at row: " + (i+1) + " in the Frequency list table should be numerical";
						warningFlag = true;
						
						//new Warn().setWarn("Warning", "The entry at row: " + (i+1)+ 
							//	" in the Frequency list table should be numerical");
					}
				}
			}
			
			if(specMask.BandListBtn.isSelected()==true){
				TxModel.getSpectrumMask().get(o).getHoppingData().setBandList(new BandList());
				
				TableModel bandTable = specMask.SpecHop.table3.getModel();
				
				for (int i = 0; i < bandTable.getRowCount(); i++) {
					try{
					TxModel.getSpectrumMask().get(o).getHoppingData().getBandList().getBand().add(new Band());
					TxModel.getSpectrumMask().get(o).getHoppingData().getBandList().getBand().get(i)
					.setStartFrequency(Double.parseDouble(bandTable.getValueAt(i, 1).toString()));
					TxModel.getSpectrumMask().get(o).getHoppingData().getBandList().getBand().get(i)
					.setEndFrequency(Double.parseDouble(bandTable.getValueAt(i, 2).toString()));
					}catch(Exception e){
						
						warningMessage = warningMessage + "\nThe entry at row: " + (i+1)+ " in the Band List table should be numerical";
						warningFlag = true;
						/*new Warn().setWarn("Warning", "The entry at row: " + (i+1)+ 
								" in the Band List table should be numerical");*/
					}
				}	
			}	
		}
	}
	
	/* Adding Underlay Mask information to the XML document
	 * (Considering that we are adding a single Underlay Mask)
	 */
	public void addUnderlay(SpecMask spec, String device) {
		
		UnderlayMask under = new UnderlayMask();
		//First check if the Underlay Mask was set from Spec Mask tab (Sometimes, both may not be enabled). Otherwise return
		if(device.equals("Tx") && (spec.underlayNoButton.isSelected() ||( !spec.underlayYesButton.isSelected() && !spec.underlayNoButton.isSelected())))
		{
			return;
		}
		try{
		under.setResolutionBW(Double.parseDouble(spec.underlayResTextField.getText()));}
		catch(Exception e){
			warningMessage= warningMessage + "\nThe entry in the Resolution BW field should be numeric";
			warningFlag = true;
		}
		under.setScmMask(new SCMMask());
		
		Double data = 0.0;
		TableModel Sdata = spec.underlayTable.getModel();
		for (int i=0; i<Sdata.getRowCount(); i++){
			InflectionPnt ifPoint = new InflectionPnt();
			try{
			if(Sdata.getValueAt(i, 1).toString().equals("")&& Sdata.getValueAt(i, 2).toString().equals(""))
			{
				break;
			}
			data = Double.parseDouble(Sdata.getValueAt(i, 1).toString());
			ifPoint.setFrequency(data);
			data = Double.parseDouble(Sdata.getValueAt(i, 2).toString());
			ifPoint.setRelativePower(data);
			under.getScmMask().getInflectionPnt().add(ifPoint);
			}catch(Exception e){
				
				warningMessage = warningMessage + "\nThe entry at row: "+(i+1)+ "in the Underlay Mask table should be numeric";
				warningFlag = true;
				/*
				new Warn().setWarn("warning", "The entry at row: "+(i+1)+
						" in the Underlay Mask table should be numeric");*/
			}
		}
		
		
		if(spec.MaxPowBtn.isSelected()==true){
			under.setMaskPowerMarginMethod("MaximumPowerDensity");
		}else{
			under.setMaskPowerMarginMethod("TotalPower");
		}
		
		// If Underlay Mask is rated		
		if(spec.underlayyes.isSelected()==true){
			   
			   under.setRating(new Rating());
		       int index = spec.box.getSelectedIndex();
		       
		       switch(index){
		       
		       case 0: try{under.getRating().
		       	setRatedBW(Double.parseDouble(spec.underlayRated.BandRatField.getText().toString()));
		       }catch(Exception e){
		    	   
		    	   warningMessage = warningMessage + "\nBW Rating number should be a number";
		    	   warningFlag = true;
		    	  // new Warn().setWarn("Warning", "BW Rating number should be a number");
		       }
		       break;
		       
		       case 1: BWRatedList bwRatedList = new BWRatedList(); 
		    	data = 0.0;		       
				Sdata = spec.underlayRated.table2.getModel();

				for (int i=0; i<Sdata.getRowCount(); i++){
					
					BWRating bwRating = new BWRating();
					try{
					data = Double.parseDouble(Sdata.getValueAt(i, 1).toString());
					bwRating.setRatedBW(data);
					data = Double.parseDouble(Sdata.getValueAt(i, 2).toString());
					bwRating.setAdjustment(data);
					}catch(Exception e){
						 warningMessage = warningMessage + "\nThe entry in row: " + i+1 +" in the BW list table should be numerical";
				    	 warningFlag = true;
						/*new Warn().setWarn("Warning", "The entry in row: " + i+1 + 
								" in the BW list table should be numerical");*/
					}
					bwRatedList.getBwRating().add(bwRating);
				}
		    	
				under.getRating().setBwRatedList(bwRatedList);
				break;

		       case 2: try{under.getRating().
		       	setRatedBTP(Double.parseDouble(spec.underlayRated.BTPRatingField.getText().toString()));
		       }catch(Exception e){
		    	   
		    	 warningMessage = warningMessage  + "\nThe entry in BTP Rated field should be numerical";
		    	 warningFlag = true;
		    //	new Warn().setWarn("Warning", "The entry in BTP Rated field should be numerical");   
		       }
		       break;
		       
		       case 3: BTPRatedList btpRatedList = new BTPRatedList();
		       data = 0.0;
		       Sdata = spec.underlayRated.table3.getModel();
		       
		       for (int i=0; i<Sdata.getRowCount(); i++){
		    	   
		    	   BTPRating btpRating = new BTPRating();
		    	   try{
		    	   data = Double.parseDouble(Sdata.getValueAt(i, 1).toString());
		    	   btpRating.setBtp(data);
		    	   data = Double.parseDouble(Sdata.getValueAt(i, 2).toString());
		    	   btpRating.setAdjustment(data);
		    	   }catch(Exception e){
		    		   warningMessage = warningMessage  + "\nThe entry in row: " + (i+1)+"in the BTP Rated List table should be numerical";
				       warningFlag = true;
		    		   /*new Warn().setWarn("Warning","The entry in row: " + (i+1)+
		    				   " in the BTP Rated List table should be numerical");*/
		    	   }
		    	   btpRatedList.getBtpRating().add(btpRating);
		       }
		       
		       under.getRating().setBtpRatedList(btpRatedList);
		       break;
		       
		       case 4: DCRatedList dcRatedList = new DCRatedList();
		       data = 0.0;
		       Sdata = spec.underlayRated.table4.getModel();
		       
		       for(int i=0; i<Sdata.getRowCount(); i++){
		    	   
		    	   DCRating dcRating = new DCRating();
		    	   try{
		    	   data = Double.parseDouble(Sdata.getValueAt(i, 1).toString());
		    	   dcRating.setDc(data);
		    	   data = Double.parseDouble(Sdata.getValueAt(i, 2).toString());
		    	   dcRating.setMaxDwellTime(data);
		    	   data = Double.parseDouble(Sdata.getValueAt(i, 3).toString());
		    	   dcRating.setAdjustment(data);
		    	   }catch(Exception e){
		    		   
		    		   warningMessage = warningMessage + "\nThe entry in row:" + (i+1)+"in the DC rating table should be numerical";
		    		   warningFlag = true;
		    		   /*new Warn().setWarn("Warning", "The entry in row: " + (i+1)+
		    				   " in the DC rating table should be numerical");*/
		    	   }
		    	   
		       }
		       
		       under.getRating().setDcRatedList(dcRatedList);
		       break;
		       		   
		       case 5: try{under.getRating().
		        setPorPIndex(Integer.parseInt(spec.underlayRated.PolicyField.getText().toString()));
		       }catch(Exception e){
		    	   warningMessage = warningMessage + "\nThe entry for PorP Index should be numerical";
	    		   warningFlag = true;
		    	  // new Warn().setWarn("Warning", "The entry for PorP Index should be numerical");
		       }
		       break;	       
		    
		       default:break;
		       }
		       
		}

		if (device.equals("Tx")) {
			if (!(under.getConfidence() == null && under.getRating() == null
					&& (under.getScmMask().getInflectionPnt() == null
							|| under.getScmMask().getInflectionPnt().size() == 0)
					&& under.getScmMask().getRefFrequency() == null
					&& Double.compare(under.getResolutionBW(), new Double(0.0)) == 0))
				TxModel.setUnderlayMask(under);
		} else {
			RxModel.getUnderlayMask().add(under);
		}
	}
	
	/* Adding Power Map information to the XML document
	 * (Considering that we are adding a single Power Map)
	 */		
	public void addPowerMap(PowerMap powerMap, String device){
				
		SCMPowerMap power = new SCMPowerMap();
		power.setOrientation(new Orientation());
		power.setGainMap(new GainMap());
		String locindexvalue = String.valueOf(powerMap.comboBox.getSelectedItem());
		
		//Save the location index only if it is not empty
		try
		{
		if(locindexvalue!="" && locindexvalue!="null" && locindexvalue!=null )
		{
			power.setLocationIndex(Integer.parseInt(locindexvalue));
		}
		}
		catch(NumberFormatException e)
		{
			warningFlag = true;
			warningMessage = warningMessage + "\nException in formatting the location index\n";
		}
		
		if(powerMap.surface.isSelected()==true){
			power.getOrientation().setSurface(true);
			
			
			
			TableModel tableData = powerMap.table.getModel();
			int gainMapValueIndex = -1;
			for (int i = 0; i < tableData.getRowCount(); i++) {
			try{	

				String dataValue = "";
				String strData = "";
				
				
				strData = tableData.getValueAt(i, 1).toString().replaceAll(" ", "");
				dataValue = tableData.getValueAt(i, 2).toString().replaceAll(" ", "");
				
				if(dataValue == "")
					continue;
				++gainMapValueIndex;
				//Elevation Angle","Azimuth Angle", "Gain (dB)
				if (strData.equals("ElevationAngle")) {
					power.getGainMap().getGainMapValue().add(new GainMapValue());
					power.getGainMap().getGainMapValue().get((gainMapValueIndex)).setElevation(Double.parseDouble(dataValue));
					
				}
				else if (strData.equals("AzimuthAngle")) {
					power.getGainMap().getGainMapValue().add(new GainMapValue());
					power.getGainMap().getGainMapValue().get((gainMapValueIndex)).setAzimuth(Double.parseDouble(dataValue));
					
				}
				else if (strData.equals("Gain(dB)")) {
					power.getGainMap().getGainMapValue().add(new GainMapValue());
					power.getGainMap().getGainMapValue().get((gainMapValueIndex)).setGain(Double.parseDouble(dataValue));
					
				}
				else {					
					--gainMapValueIndex;
				}
				
				
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe entry in row: "+ (i+1) +" in the Power Map table should be numeric";
				warningFlag = true;		
				/*
				new Warn().setWarn("Warning", "The entry in row: "+ (i+1) 
						+ " in the Power Map table should be numeric");*/
			}
			}
			
			if(device.equals("Tx")){
				TxModel.getScmPowerMap().add(power);
			}else{
				RxModel.getScmPowerMap().add(power);
			}
			
		}
			
		if(powerMap.reference.isSelected()==true){
			power.setOrientation(new Orientation());
			power.getOrientation().setTowardReferencePoint(new TowardReferencePoint());
		}
		
		if(powerMap.relative.isSelected()==true){
			power.setOrientation(new Orientation());
			power.getOrientation().setRelativeToPlatform(new RelativeToPlatform());
		}
	}

	/* Adding Reference Power information to the XML document
	 */
	public void addReferencePower(JTextField powerField, String device){
		ReferencePower pow = new ReferencePower();
		try{
		pow.setValue(Double.parseDouble(powerField.getText()));
		}catch(Exception e){
			warningMessage = warningMessage + "\nThe entry in the Reference Power field should be numeric";
			warningFlag = true;
			
			//new Warn().setWarn("Warning", "The entry in the Reference Power field should be numeric");
		}
		if(device.equals("Tx")){
			TxModel.getReferencePower().add(pow);
		}else{
			RxModel.getReferencePower().add(pow);
		}
	}

	/* Adding Propagation Map information to the XML document
	 * (Considering that we are adding a single Propagation Map)
	 */
	public void addPropMap(SCM_gui.PropMap propMap, String device){
		
		SCMPropagationMap prop = new SCMPropagationMap();
		prop.setPropMap(new PropMap());
		
		String strData = "";
		String dataValue = "";
//		Double elevationData = 0.0;
//		Double azimuthData = 0.0;
//		Double n1 = 0.0;
//		Double breakData = 0.0;
//		Double n2 = 0.0;
		String locindexvalue = String.valueOf(propMap.comboBox.getSelectedItem());
		
		//Save the location index only if it is not empty
		try
		{
		if(locindexvalue!="" && locindexvalue!="null" &&locindexvalue!=null)
		{
			prop.setLocationIndex(Integer.parseInt(locindexvalue));
		}
		}
		catch(NumberFormatException e)
		{
			warningFlag = true;
			warningMessage = warningMessage + "\nException in formatting the location index\n";
		}
		
		TableModel tableData = propMap.table.getModel();
		int propMapIndex = -1;
		for (int i = 0; i < tableData.getRowCount(); i++) {
			try{
			
				
			strData = tableData.getValueAt(i, 1).toString().replaceAll(" ", "");
			dataValue = tableData.getValueAt(i, 2).toString().replaceAll(" ", "");
			if(dataValue == "")
				continue;
			++propMapIndex;
			if (strData.equals("ElevationAngle")) {
				prop.getPropMap().getPropMapValue().add(new PropMapValue());
				prop.getPropMap().getPropMapValue().get((propMapIndex)).setElevation(Double.parseDouble(dataValue));
			}
			
			else if (strData.equals("AzimuthAngle")) {
				prop.getPropMap().getPropMapValue().add(new PropMapValue());
				prop.getPropMap().getPropMapValue().get((propMapIndex)).setAzimuth(Double.parseDouble(dataValue));
			}
			
			else if (strData.equals("PropExponent")) {
				prop.getPropMap().getPropMapValue().add(new PropMapValue());
				PropagationModel propModel = new PropagationModel();
				propModel.setLinear(Double.parseDouble(dataValue));
				prop.getPropMap().getPropMapValue().get((propMapIndex)).setPropagationModel(propModel);
			}
			
			else if (strData.equals("FirstExponent")) {
				prop.getPropMap().getPropMapValue().add(new PropMapValue());
				PropagationModel propModel = new PropagationModel();
				PiecewiseLinear plinear = new PiecewiseLinear();
				plinear.setFirstExponent(Double.parseDouble(dataValue));
				propModel.setPiecewiseLinear(plinear);
				prop.getPropMap().getPropMapValue().get((propMapIndex)).setPropagationModel(propModel);
			}
			
			else if (strData.equals("Breakpoint(meters)")) {
				prop.getPropMap().getPropMapValue().add(new PropMapValue());
				PropagationModel propModel = new PropagationModel();
				PiecewiseLinear plinear = new PiecewiseLinear();
				plinear.setBreakpoint(Double.parseDouble(dataValue));
				propModel.setPiecewiseLinear(plinear);
				propModel.setLinear(Double.parseDouble(dataValue));
				prop.getPropMap().getPropMapValue().get((propMapIndex)).setElevation(Double.parseDouble(dataValue));
			}
			
			else if (strData.equals("SecondExponent")) {
				prop.getPropMap().getPropMapValue().add(new PropMapValue());
				PropagationModel propModel = new PropagationModel();
				PiecewiseLinear plinear = new PiecewiseLinear();
				plinear.setSecondExponent(Double.parseDouble(dataValue));
				propModel.setPiecewiseLinear(plinear);
				propModel.setLinear(Double.parseDouble(dataValue));
				prop.getPropMap().getPropMapValue().get((propMapIndex)).setElevation(Double.parseDouble(dataValue));
			}
			else {
				--propMapIndex;
			}
//			prop.getPropMap().getPropMapValue().add(new PropMapValue());
//			prop.getPropMap().getPropMapValue().get(i*3+2).setPropagationModel(new PropagationModel());
			
			
//			strData = tableData.getValueAt(i, 4).toString().replaceAll(" ", "");
//			if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
//				
//				strData = tableData.getValueAt(i, 3).toString().replaceAll(" ", "");
//				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
//					prop.getPropMap().getPropMapValue().get(i*3+2).getPropagationModel().setLinear(n1);
//				}else{
//					n1 = Double.parseDouble(strData);
//					prop.getPropMap().getPropMapValue().get(i*3+2).getPropagationModel().setLinear(n1);
//				}
//				
//			}else{
//				
//				prop.getPropMap().getPropMapValue().get(i*3+2).getPropagationModel().
//				setPiecewiseLinear(new PiecewiseLinear());
//				breakData = Double.parseDouble(strData);
//				prop.getPropMap().getPropMapValue().get(i*3+2).getPropagationModel()
//				.getPiecewiseLinear().setBreakpoint(breakData);
//				
//				//Set the linear value to default value if the piecewiselinear is set
//				prop.getPropMap().getPropMapValue().get(i*3+2).getPropagationModel().setLinear(0.0);
//				
//				strData = tableData.getValueAt(i, 3).toString().replaceAll(" ", "");
//				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
//					prop.getPropMap().getPropMapValue().get(i*3+2).getPropagationModel()
//					.getPiecewiseLinear().setFirstExponent(0.0);
//				}else{
//					n1 = Double.parseDouble(strData);
//					prop.getPropMap().getPropMapValue().get(i*3+2).getPropagationModel()
//					.getPiecewiseLinear().setFirstExponent(n1);
//				}
//				
//				strData = tableData.getValueAt(i, 5).toString().replaceAll(" ", "");
//				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
//					prop.getPropMap().getPropMapValue().get(i*3+2).getPropagationModel()
//					.getPiecewiseLinear().setSecondExponent(0.0);
//				}else{
//					n2 = Double.parseDouble(strData);
//					prop.getPropMap().getPropMapValue().get(i*3+2).getPropagationModel()
//					.getPiecewiseLinear().setSecondExponent(n2);
//				}				
//			}		
			
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe entry at row: " + (i+1) + " in the propagation map table should be numeric";
				warningFlag = true;
				//new Warn().setWarn("Warning", "The entry at row: " + (i+1) + " in the propagation map table should be numeric");
			}
		}
		
		if(device.equals("Tx")){
			TxModel.getScmPropagationMap().add(prop);
		}else{
			RxModel.getScmPropagationMap().add(prop);
		}
	}

	/* Adding Schedule information to the XML document
	 * (Considering that we are adding a single Schedule Construct)
	 */
	@SuppressWarnings("deprecation")
	public void addTime(Schedule sched, String device){
		
		DefaultTableModel timeModel = (DefaultTableModel) sched.table1.getModel();		
		DefaultTableModel timezoneModel = (DefaultTableModel) sched.table2.getModel();
		SCMSchedule sch = new SCMSchedule();

		String locindexvalue = String.valueOf(sched.comboBox.getSelectedItem());
		
		//Save the location index only if it is not empty
		try
		{
		if(locindexvalue!="" && locindexvalue!="null" && locindexvalue!=null )
		{
			sch.setLocationIndex(Integer.parseInt(locindexvalue));
		}
		}
		catch(NumberFormatException e)
		{
			warningFlag = true;
			warningMessage = warningMessage + "\nException in formatting the location index\n";
		}
		try{
			GregorianCalendar cal = new GregorianCalendar();
			Date date = null;
			DateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String startTime = timeModel.getValueAt(0, 1).toString() +"-"+
							   timeModel.getValueAt(0, 2).toString() +"-"+ 
							   timeModel.getValueAt(0, 3).toString() +" "+ 
							   timeModel.getValueAt(0, 4).toString() +":"+
							   timeModel.getValueAt(0, 5).toString() +":"+
							   timeModel.getValueAt(0, 6).toString();
			int startHour =0;
			try{
			date=df.parse(startTime);
			startHour = Integer.parseInt(timeModel.getValueAt(0, 4).toString());
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe Start Time couldn't be parsed";
				warningFlag = true;
			//	new Warn().setWarn("Warning","The Start Time couldn't be parsed");
			}
			XMLGregorianCalendar start = DatatypeFactory.newInstance().
					newXMLGregorianCalendar(cal);
			try{
			cal.setTime(date);
			
			
			start.setYear(cal.get(Calendar.YEAR));
			start.setMonth(cal.get(Calendar.MONTH)+1);
			start.setDay(cal.get(Calendar.DAY_OF_MONTH));
			start.setHour(startHour);
			start.setMinute(date.getMinutes());
			start.setSecond(date.getSeconds());
			
				int hour = Integer.parseInt(timezoneModel.getValueAt(0, 1).toString());
				int minute = Integer.parseInt(timezoneModel.getValueAt(0, 2).toString());
				if(hour>0){
					start.setTimezone((hour*60) + minute);
				}else{
					start.setTimezone((hour*60) - minute);
				}
			}catch(Exception e){
				warningMessage = warningMessage + "\nStart Time - Time Zone format is not Correct";
				warningFlag = true;
				//new Warn().setWarn("Warning", "Start Time - Time Zone format is not Correct");
			}
			/*XMLGregorianCalendar start = DatatypeFactory.newInstance().
					newXMLGregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, 
							cal.get(Calendar.DAY_OF_MONTH), date.getHours(),date.getMinutes(),
							date.getSeconds(),DatatypeConstants.FIELD_UNDEFINED, cal.getTimeZone()
							.LONG).normalize();*/

			String endTime = timeModel.getValueAt(1, 1).toString() +"-"+
					   timeModel.getValueAt(1, 2).toString() +"-"+ 
					   timeModel.getValueAt(1, 3).toString() +" "+ 
					   timeModel.getValueAt(1, 4).toString() +":"+
					   timeModel.getValueAt(1, 5).toString() +":"+
					   timeModel.getValueAt(1, 6).toString(); 
			
			int endHour =0;
			try{
			date = df.parse(endTime);
			endHour = Integer.parseInt(timeModel.getValueAt(1, 4).toString());
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe End Time couldn't be parsed";
				warningFlag = true;
				//new Warn().setWarn("Warning","The End Time couldn't be parsed");
			}
			XMLGregorianCalendar end = DatatypeFactory.newInstance().
					newXMLGregorianCalendar(cal);
			try{
			cal.setTime(date);
			//System.out.println(cal.getTimeZone().LONG);
			
			end.setYear(cal.get(Calendar.YEAR));
			end.setMonth(cal.get(Calendar.MONTH)+1);
			end.setDay(cal.get(Calendar.DAY_OF_MONTH));
			end.setHour(endHour);
			end.setMinute(date.getMinutes());
			end.setSecond(date.getSeconds());
			/*XMLGregorianCalendar end = DatatypeFactory.newInstance().
					newXMLGregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, 
							cal.get(Calendar.DAY_OF_MONTH), date.getHours(),date.getMinutes(),
							date.getSeconds(),DatatypeConstants.FIELD_UNDEFINED, cal.getTimeZone()
			.LONG).normalize();*/
			
			

				int hour = Integer.parseInt(timezoneModel.getValueAt(1, 1).toString());
				int minute = Integer.parseInt(timezoneModel.getValueAt(1, 2).toString());
				if(hour>0){
					end.setTimezone((hour*60) + minute);
				}else{
					end.setTimezone((hour*60) - minute);
				}
				
			}catch(Exception e){
				warningMessage = warningMessage + "\nEnd Time - Time Zone format is not Correct";
				warningFlag = true;
			//	new Warn().setWarn("Warning", "End Time - Time Zone format is not Correct");
			}
			
			sch.setStartTime(start);
			sch.setEndTime(end);
			
			long durMs = end.toGregorianCalendar().getTimeInMillis()-
					start.toGregorianCalendar().getTimeInMillis();
			//Duration dur = DatatypeFactory.newDuration(durMs);
			if(durMs<=0){
				
				warningMessage = warningMessage + "\nThe duration for transmission or reception can't be negative or zero";
				warningFlag = true;
				/*new Warn().setWarn("Warning", "The duration for "
						+ "transmission or reception can't be negative or zero");*/
			}else{
				
				warningMessage = warningMessage + "\nNew Information, The time difference is" + durMs/(60*60*1000)+ " hrs";
				warningFlag = true;
				/*new Warn().setWarn("New Information", "The time difference is "
						+ durMs/(60*60*1000)+ " hrs");*/
			}
			

		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		if(device.equals("Tx")){
			TxModel.getScmSchedule().add(sch);
		}else{
			RxModel.getScmSchedule().add(sch);
		}
		
		
	}

	/* Adding Location information to the XML document
	 * (Considering that we are adding a single Location Construct)
	 */
	public void addLocation(SCM_gui.Location locData, String device){
	
		SCMLocation scmLoc = new SCMLocation();
		Location loc = new Location();
				
		if (locData.LocCombo.getItemAt(locData.LocCombo.getSelectedIndex()).equals("Point")){
			loc.setPoint(new Point());
		
			try{
			TableModel pointModel = locData.pointTable.getModel();
			
			if(pointModel.getValueAt(0, 0)!="" & pointModel.getValueAt(0, 1)!="" & pointModel.getValueAt(0, 2)!="")
			{
			Double longitude = Double.parseDouble(pointModel.getValueAt(0, 0).toString());
			Double latitude = Double.parseDouble(pointModel.getValueAt(0, 1).toString());
			Double altitude = Double.parseDouble(pointModel.getValueAt(0, 2).toString());
			loc.getPoint().setLongitude(longitude);
			loc.getPoint().setLatitude(latitude);
			loc.getPoint().setAltitude(altitude);
			}
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe entry in the Location Table should be Numerical";
				warningFlag =  true;
			//	new Warn().setWarn("Warning", "The entry in the Location Table should be Numerical");
			}
		}
		
		else if(locData.LocCombo.getItemAt(locData.LocCombo.getSelectedIndex()).equals("Point Surface")) {
			
			TableModel pointSurfaceModel = locData.pointSurfaceTable.getModel();
			loc.setPointSurface(new PointSurface());
			loc.getPointSurface().setPoint( new Point());
			loc.getPointSurface().setAntennaHeight(new AntennaHeight());	
			
			for (int i=0; i<pointSurfaceModel.getRowCount(); i++){
				
				try{
				Double longitude = Double.parseDouble(pointSurfaceModel.getValueAt(i, 0).toString());
				Double latitude = Double.parseDouble(pointSurfaceModel.getValueAt(i, 1).toString());
				Double altitude = Double.parseDouble(pointSurfaceModel.getValueAt(i, 2).toString());
				loc.getPointSurface().getPoint().setLongitude(longitude);
				loc.getPointSurface().getPoint().setLatitude(latitude);
				loc.getPointSurface().getPoint().setAltitude(altitude);
				
				}catch(Exception e){
					
					warningMessage = warningMessage + "\nThe entry at row: "+(i+1)+ "in the POint Surface table should be numeric";
					warningFlag = true;
					/*
					new Warn().setWarn("warning", "The entry at row: "+(i+1)+
							" in the Underlay Mask table should be numeric");*/
				}
			}
			loc.getPointSurface().getAntennaHeight().setHeight(Double.parseDouble(locData.HeightField.getText()));
			String val = "";
			if(locData.AGL.isSelected())
			{
				val = "AGL";
			}
			else if(locData.HAAT.isSelected())
			{
				val="HAAT";
			}
			

			loc.getPointSurface().getAntennaHeight().setReference(val);
		}
		else if(locData.LocCombo.getItemAt(locData.LocCombo.getSelectedIndex()).equals("Circular Surface")) {
			
			TableModel circularTable = locData.circularTable.getModel();
			loc.setCircularSurface(new CircularSurface());
			loc.getCircularSurface().setPoint(new Point());
			loc.getCircularSurface().setAntennaHeight(new AntennaHeight());	
			
				try{
				Double longitude = Double.parseDouble(circularTable.getValueAt(0, 0).toString());
				Double latitude = Double.parseDouble(circularTable.getValueAt(0, 1).toString());
				Double altitude = Double.parseDouble(circularTable.getValueAt(0, 2).toString());
				Double radius = Double.parseDouble(circularTable.getValueAt(0, 3).toString());
				Double perAttenuation = Double.parseDouble(circularTable.getValueAt(0,4).toString());
				
				loc.getCircularSurface().getPoint().setLongitude(longitude);
				loc.getCircularSurface().getPoint().setLatitude(latitude);
				loc.getCircularSurface().getPoint().setAltitude(altitude);
				loc.getCircularSurface().setRadius(radius);
				loc.getCircularSurface().setPerimeterAttenuation(perAttenuation);
				
				}catch(Exception e){
					
					warningMessage = warningMessage + "\nThe value stored in the Circular Surface table has to be numeric";
					warningFlag = true;
				}
			
			if(locData.HeightField.getText()!=null && locData.HeightField.getText()!="")
			{
				loc.getCircularSurface().getAntennaHeight().setHeight(Double.parseDouble(locData.HeightField.getText()));

			}
			String val = "";
			if(locData.AGL.isSelected())
			{
				val = "AGL";
			}
			else if(locData.HAAT.isSelected())
			{
				val="HAAT";
			}
			
			loc.getCircularSurface().getAntennaHeight().setReference(val);
			
			if(locData.transmitterField.getText()!=null && locData.transmitterField.getText()!="")
			{
				loc.getCircularSurface().setTransmitterDensity(Double.parseDouble(locData.transmitterField.getText()));

			}
			
		}	
		else if(locData.LocCombo.getItemAt(locData.LocCombo.getSelectedIndex()).equals("Polygon Surface")) {
			TableModel polygonTable = locData.polygonTable.getModel();
			loc.setPolygonSurface(new PolygonSurface());
			loc.getPolygonSurface().setScmPolygon(new SCMPolygon());
			
			
			loc.getPolygonSurface().setAntennaHeight(new AntennaHeight());	
			
			for (int i=0; i<polygonTable.getRowCount(); i++){
				
				try{
				Double longitude = Double.parseDouble(polygonTable.getValueAt(i, 1).toString());
				Double latitude = Double.parseDouble(polygonTable.getValueAt(i, 2).toString());
				Double altitude = Double.parseDouble(polygonTable.getValueAt(i, 3).toString());
				Double sideatten = Double.parseDouble(polygonTable.getValueAt(i, 4).toString());
				
				Side sideType = new Side();
				sideType.setPoint(new Point());
				loc.getPolygonSurface().getScmPolygon().getSide().add(sideType);
				
				sideType.getPoint().setLongitude(longitude);
				sideType.getPoint().setLatitude(latitude);
				sideType.getPoint().setAltitude(altitude);
				sideType.setSideAttenuation(sideatten);
				
				
				
				}catch(Exception e){
					
					warningMessage = warningMessage + "\nThe entry at row: "+(i+1)+ "in the Polygon Surface table should be numeric";
					warningFlag = true;
					
				}
			}
			if(locData.HeightField.getText()!=null && locData.HeightField.getText()!="")
			{
				loc.getPolygonSurface().getAntennaHeight().setHeight(Double.parseDouble(locData.HeightField.getText()));
			}
			
			String val = "";
			if(locData.AGL.isSelected())
			{
				val = "AGL";
			}
			else if(locData.HAAT.isSelected())
			{
				val="HAAT";
			}
			
			loc.getPolygonSurface().getAntennaHeight().setReference(val);
			if(locData.transmitterField.getText()!=null && locData.transmitterField.getText()!="")
			{
				loc.getPolygonSurface().setTransmitterDensity(Double.parseDouble(locData.transmitterField.getText()));
			}
			

		}
		else if(locData.LocCombo.getItemAt(locData.LocCombo.getSelectedIndex()).equals("Cylinder")) {
			TableModel cylinderTable = locData.cylinderTable.getModel();
			loc.setCylinder(new Cylinder());
			loc.getCylinder().setPoint(new Point());
				
			
			for (int i=0; i<cylinderTable.getRowCount(); i++){
				
				try{
				Double longitude = Double.parseDouble(cylinderTable.getValueAt(i, 0).toString());
				Double latitude = Double.parseDouble(cylinderTable.getValueAt(i, 1).toString());
				Double altitude = Double.parseDouble(cylinderTable.getValueAt(i, 2).toString());
				Double radius = Double.parseDouble(cylinderTable.getValueAt(i, 3).toString());
				Double permAtten = Double.parseDouble(cylinderTable.getValueAt(i, 4).toString());
			//	Double height = Double.parseDouble(cylinderTable.getValueAt(i, 5).toString());
				Double topSurf = Double.parseDouble(cylinderTable.getValueAt(i, 6).toString());
				
				loc.getCylinder().getPoint().setLongitude(longitude);
				loc.getCylinder().getPoint().setLatitude(latitude);
				loc.getCylinder().getPoint().setAltitude(altitude);
				
				loc.getCylinder().setPerimeterAttenuation(permAtten);
				loc.getCylinder().setTopAttenuation(topSurf);
				loc.getCylinder().setRadius(radius);
				//Bottom Attenuation and transmitterDensity
				
				
				}catch(Exception e){
					
					warningMessage = warningMessage + "\nThe entry at row: "+(i+1)+ "in the Cylinder table should be numeric";
					warningFlag = true;
					
				}
				String transText = locData.transmitterField.getText();
				String heightText = locData.HeightField.getText();
				if(transText!="")
				{
					loc.getCylinder().setTransmitterDensity(Double.parseDouble(transText));
				}
				if(heightText!="")
				{
					loc.getCylinder().setHeight(Double.parseDouble(heightText));
				}
				
			}			
		}
		else if(locData.LocCombo.getItemAt(locData.LocCombo.getSelectedIndex()).equals("Polyhedron")) {
			TableModel polyhedronTable = locData.polyhedronTable.getModel();
			TableModel heightTable = locData.heightTable.getModel();
			
			loc.setPolyhedron(new Polyhedron());
			loc.getPolyhedron().setScmPolygon(new SCMPolygon());
			
				
			
			for (int i=0; i<polyhedronTable.getRowCount(); i++){
				
				try{
					
				Side sideType = new Side();
				Point pointType = new Point();
				
					
				Double longitude = Double.parseDouble(polyhedronTable.getValueAt(i, 1).toString());
				Double latitude = Double.parseDouble(polyhedronTable.getValueAt(i, 2).toString());
				Double altitude = Double.parseDouble(polyhedronTable.getValueAt(i, 3).toString());
				
				pointType.setLongitude(longitude);
				pointType.setLatitude(latitude);
				pointType.setAltitude(altitude);
				
				Double sideAtten = Double.parseDouble(polyhedronTable.getValueAt(i, 4).toString());
				
				sideType.setSideAttenuation(sideAtten);
				
				sideType.setPoint(pointType);
				loc.getPolyhedron().getScmPolygon().getSide().add(sideType);
				
				//Height in the table and the one below ; transmitterDensity
				
				
				}catch(Exception e){
					
					warningMessage = warningMessage + "\nThe entry at row: "+(i+1)+ "in the Polyhedron table should be numeric";
					warningFlag = true;
					
				}
				try {
					
					
					//Since these are set only once the first row is used to retrieve the values
					Double height = Double.parseDouble(heightTable.getValueAt(0, 0).toString());
					Double bottomAtten = Double.parseDouble(heightTable.getValueAt(0, 1).toString());
					Double topAtten = Double.parseDouble(heightTable.getValueAt(0, 2).toString());
					
					loc.getPolyhedron().setHeight(height);
					loc.getPolyhedron().setTopAttenuation(topAtten);
					loc.getPolyhedron().setBottomAttenuation(bottomAtten);
					
				}
				catch(Exception e)
				{

					warningMessage = warningMessage + "\nThe entry at row: 1 in the Polyhedron table for Height should be numeric";
					warningFlag = true;
				}
			}	
			if(locData.transmitterField.getText()!="")
			{
				loc.getPolyhedron().setTransmitterDensity(Double.parseDouble(locData.transmitterField.getText()));

			}

		}
		else if(locData.LocCombo.getItemAt(locData.LocCombo.getSelectedIndex()).equals("Path")) {
			
			TableModel pathTable = locData.pathTable.getModel();
			loc.setPath(new Path());
			
			
			PathPoint pathPointType = new PathPoint();
			pathPointType.setPoint(new Point());
			loc.getPath().getPathPoint().add(pathPointType);
			
				
			
			for (int i=0; i<locData.pathTable.getRowCount(); i++){
				
				try{
				Double longitude = Double.parseDouble(pathTable.getValueAt(i, 1).toString());
				Double latitude = Double.parseDouble(pathTable.getValueAt(i, 2).toString());
				Double altitude = Double.parseDouble(pathTable.getValueAt(i, 3).toString());
				XMLGregorianCalendar time = DatatypeFactory.newInstance().newXMLGregorianCalendar(pathTable.getValueAt(i, 4).toString());
				
				
				pathPointType.getPoint().setLongitude(longitude);
				
				pathPointType.getPoint().setLatitude(latitude);
				pathPointType.getPoint().setAltitude(altitude);
				
				pathPointType.setTime(time);
				
				}catch(Exception e){
					
					warningMessage = warningMessage + "\nThe entry at row: "+(i+1)+ " in the Path table of Location tab should be numeric";
					warningFlag = true;
					
				}
			}	
			
		}
			
		//Save the location index
				try
				{
					if(!locData.LocationField.getText().equals(""))
					{
						scmLoc.setLocationIndex(Integer.parseInt(locData.LocationField.getText().toString()));

					}
									
				
				}
				catch(Exception e)
				{
					warningMessage = warningMessage + "\nThe entry in the Location Index of Location tab should be numeric";
					warningFlag = true;
					
				}
				
		
		
		scmLoc.setLocation(loc);
		
		if(device.equals("Tx")){
			TxModel.getScmLocation().add(scmLoc);
		}else{
			RxModel.getScmLocation().add(scmLoc);
		}
	}
	/*
	 * Adding the IMA information to the XML document
	 */
	public boolean addIMA(IMC imc, IntermodulationMask imcMask)
	{
		boolean isIMAProvided = false;
		//First check if the IMA was set from Intermodulation Mask tab (Sometimes, both may not be enabled). Otherwise return
		if(imc.IMANo.isSelected() ||( !imc.IMAYes.isSelected() && !imc.IMANo.isSelected()))
		{
			return isIMAProvided;
		}
		SCMMask imaMask = new SCMMask();
		imcMask.setImAmplificationMask(imaMask);
		
		try{
			imcMask.getImAmplificationMask().setRefFrequency(Double.parseDouble(imc.RelFreqField.getText()));
			isIMAProvided = true;
		}catch(Exception e){
			imcMask.getImAmplificationMask().setRefFrequency(0.0);
		}
		
		TableModel Sdata = imc.imatable.getModel();
		for (int i = 0; i < Sdata.getRowCount(); i++) {
			try{
			InflectionPnt ifPoint = new InflectionPnt();
			imcMask.getImAmplificationMask().getInflectionPnt().add(ifPoint);
			
			Double data = Double.parseDouble(Sdata.getValueAt(i, 1).toString());
			imcMask.getImAmplificationMask().getInflectionPnt().get(i).setFrequency(data);
			data = Double.parseDouble(Sdata.getValueAt(i, 2).toString());
			imcMask.getImAmplificationMask().getInflectionPnt().get(i).setRelativePower(data);
			isIMAProvided = true;
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe entry at row: " +(i+1)+" in the IMA table should be numerical";
				warningFlag = true;
			}
		}
		return isIMAProvided;
		
	}
	
	
	/* Adding Intermodulation Mask information to the XML document*/
	
	public void addIMC(IMC imc){
				
			
		IntermodulationMask imask = new IntermodulationMask();
		boolean isIntermodulationMaskProvided = false;
		
		imask.setImCombiningMask(new SCMMask());
		//Set the Center frequency for the intermediate frequency
		try {
			imask.setIntermediateFrequency(Double.parseDouble(imc.IFField.getText()));
			isIntermodulationMaskProvided = true;
		}catch(Exception e)
		{
			imask.setIntermediateFrequency(0.0);

		}
		
		try{
			imask.getImCombiningMask().setRefFrequency(Double.parseDouble(imc.RelFreqField.getText()));
			isIntermodulationMaskProvided = true;
		}catch(Exception e){
			imask.getImCombiningMask().setRefFrequency(0.0);
		}
		
		TableModel Sdata = imc.table.getModel();
		for (int i = 0; i < Sdata.getRowCount(); i++) {
			try{
			InflectionPnt ifPoint = new InflectionPnt();
			imask.getImCombiningMask().getInflectionPnt().add(ifPoint);
			
			if(Sdata.getValueAt(i, 1).toString().equals("")&& Sdata.getValueAt(i, 2).toString().equals(""))
			{
				break;
			}
			
			Double data = Double.parseDouble(Sdata.getValueAt(i, 1).toString());
			imask.getImCombiningMask().getInflectionPnt().get(i).setFrequency(data);
			data = Double.parseDouble(Sdata.getValueAt(i, 2).toString());
			imask.getImCombiningMask().getInflectionPnt().get(i).setRelativePower(data);
			isIntermodulationMaskProvided = true;
			}catch(Exception e){
				warningFlag = true;
			}
		}    	

		if (!imc.imOrderField.getText().equals("")) {
			int order = 0;
			try {
				order = Integer.parseInt(imc.imOrderField.getText());
				isIntermodulationMaskProvided = true;
			} catch (NumberFormatException exception) {
				// ABhatt eating this exception for now
				warningFlag = true;
				warningMessage = warningMessage
						+ "\nThe entry in IM Order field in the Intermodulation mask should be numeric";
			}
			imask.setOrder(order);
		}
		
		// If there is highSideInjection to be stored
		try{
			//TODO ABhatt can't see  HighSideInjection in GUI
			imask.setHighSideInjection(imc.IFYes.isSelected());
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe entry for highSideInjection field in the Intermodulation mask must be set properly";
				warningFlag = true;
				
			//	new Warn().setWarn("Warning", "The entry in Resolution BW field should be numeric");
			}
		
		isIntermodulationMaskProvided = isIntermodulationMaskProvided || addIMA(imc, imask);
		if (isIntermodulationMaskProvided) {
			TxModel.getIntermodulationMask().add(imask);
		}

	}
		
	public void concludeXML(String saveName, String device){
		try {
			
			JAXBContext context = JAXBContext.newInstance("org.ieee.dyspansc._1900._5.scm");
			String filename = "";
			if(saveName.endsWith(".xml"))
			{
				filename = saveName;
			}
			else
			{
				filename = saveName + ".xml";
			}
			
			if(device.equals("Tx")){
				JAXBElement<TxModel> element = createTxModel(TxModel);
				Marshaller marshaller = context.createMarshaller();
		        marshaller.setProperty("jaxb.formatted.output",Boolean.TRUE);
		        marshaller.marshal(element,System.out);
		      
		        OutputStream os = new FileOutputStream(filename);
		        marshaller.marshal(element, os );
		        os.close();
			}else{
				JAXBElement<RxModel> element = createRxModel(RxModel);
				Marshaller marshaller = context.createMarshaller();
		        marshaller.setProperty("jaxb.formatted.output",Boolean.TRUE);
		        marshaller.marshal(element,System.out);
		      
		        OutputStream os = new FileOutputStream(filename);
		        marshaller.marshal(element, os );
		        os.close();
			}
			
	        
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
