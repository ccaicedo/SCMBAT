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

import org.ieee.dyspansc._1900._5.scm.BTPRatedListType;
import org.ieee.dyspansc._1900._5.scm.BTPRatingType;
import org.ieee.dyspansc._1900._5.scm.BWRatedListType;
import org.ieee.dyspansc._1900._5.scm.BWRatingType;
import org.ieee.dyspansc._1900._5.scm.BandListType;
import org.ieee.dyspansc._1900._5.scm.BandType;
import org.ieee.dyspansc._1900._5.scm.DCRatedListType;
import org.ieee.dyspansc._1900._5.scm.DCRatingType;
import org.ieee.dyspansc._1900._5.scm.FrequencyListType;
import org.ieee.dyspansc._1900._5.scm.GainMapType;
import org.ieee.dyspansc._1900._5.scm.GainMapValueType;
import org.ieee.dyspansc._1900._5.scm.HoppingDataType;
import org.ieee.dyspansc._1900._5.scm.InflectionPointType;
import org.ieee.dyspansc._1900._5.scm.LocationType;
import org.ieee.dyspansc._1900._5.scm.ObjectFactory;
import org.ieee.dyspansc._1900._5.scm.OrientationType;
import org.ieee.dyspansc._1900._5.scm.PiecewiseLinearType;
import org.ieee.dyspansc._1900._5.scm.PointType;
import org.ieee.dyspansc._1900._5.scm.PropMapType;
import org.ieee.dyspansc._1900._5.scm.PropMapValueType;
import org.ieee.dyspansc._1900._5.scm.PropagationModelType;
import org.ieee.dyspansc._1900._5.scm.RatingType;
import org.ieee.dyspansc._1900._5.scm.ReferencePowerType;
import org.ieee.dyspansc._1900._5.scm.RelativeToPlatformType;
import org.ieee.dyspansc._1900._5.scm.RxModelType;
import org.ieee.dyspansc._1900._5.scm.SCMLocationType;
import org.ieee.dyspansc._1900._5.scm.SCMMaskType;
import org.ieee.dyspansc._1900._5.scm.SCMPowerMapType;
import org.ieee.dyspansc._1900._5.scm.SCMPropagationMapType;
import org.ieee.dyspansc._1900._5.scm.SCMScheduleType;
import org.ieee.dyspansc._1900._5.scm.SpectrumMaskType;
import org.ieee.dyspansc._1900._5.scm.TowardReferencePointType;
import org.ieee.dyspansc._1900._5.scm.TxModelType;
import org.ieee.dyspansc._1900._5.scm.UnderlayMaskType;
import org.w3c.dom.Document;

import SCM_gui.Location;
import SCM_gui.PowerMap;
import SCM_gui.PropMap;
import SCM_gui.Schedule;
import SCM_gui.SpecMask;
import SCM_gui.UnderlayMask;


public class Save_XML extends ObjectFactory {
	
	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder;
	Document doc;
		
	TxModelType TxModel;
	RxModelType RxModel;
	Object Model;
	
	/*
	 * Maintain the global warning message and flag to display the warnings
	 */
	public String warningMessage = "\n";
	public boolean warningFlag = false;
	
	//Creating a XML document.
	public void createXML(String SystemID, String device){
		
		try {
			
			docBuilder = docFactory.newDocumentBuilder();
			doc=docBuilder.newDocument();
			
			if(device.equals("Tx")){
				TxModel = createTxModelType();
				TxModel.setSystemID(SystemID);
				Model = (Object) TxModel;
			}else{
				RxModel = createRxModelType();
				RxModel.setSystemID(SystemID);
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
		
		SpectrumMaskType spec = new SpectrumMaskType();
		TxModel.getSpectrumMask().add(spec);
		TxModel.getSpectrumMask().get(o).setScmMask(new SCMMaskType());
		
		try{
			TxModel.getSpectrumMask().get(o).getScmMask().setRefFrequency(Double.parseDouble(specMask.TextField.getText()));
		}catch(Exception e){
			TxModel.getSpectrumMask().get(o).getScmMask().setRefFrequency(0.0);
		}
		
		TableModel Sdata = specMask.table.getModel();
		for (int i = 0; i < Sdata.getRowCount(); i++) {
			try{
			InflectionPointType ifPoint = new InflectionPointType();
			TxModel.getSpectrumMask().get(o).getScmMask().getInflectionPoint().add(ifPoint);
			
			Double data = Double.parseDouble(Sdata.getValueAt(i, 1).toString());
			TxModel.getSpectrumMask().get(o).getScmMask().getInflectionPoint().get(i).setFrequency(data);
			data = Double.parseDouble(Sdata.getValueAt(i, 2).toString());
			TxModel.getSpectrumMask().get(o).getScmMask().getInflectionPoint().get(i).setRelativePower(data);
		
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
			
			TxModel.getSpectrumMask().get(o).setHoppingData(new HoppingDataType());
			
			TxModel.getSpectrumMask().get(o).getHoppingData().setDwellTime(
					Double.parseDouble(specMask.SpecHop.DwellField.getText()));
			TxModel.getSpectrumMask().get(o).getHoppingData().setRevisitPeriod(
					Double.parseDouble(specMask.SpecHop.RevisitField.getText()));
			
			if(specMask.FreqListBtn.isSelected()==true && specMask.SpecHop.freqListBtn1.isSelected()==true){
				TxModel.getSpectrumMask().get(o).getHoppingData().setFrequencyList(new FrequencyListType());
				
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
				TxModel.getSpectrumMask().get(o).getHoppingData().setBandList(new BandListType());
				
				TableModel bandTable = specMask.SpecHop.table3.getModel();
				
				for (int i = 0; i < bandTable.getRowCount(); i++) {
					try{
					TxModel.getSpectrumMask().get(o).getHoppingData().getBandList().getBand().add(new BandType());
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
	public void addUnderlay(UnderlayMask underlayMask, String device) {
		
		UnderlayMaskType under = new UnderlayMaskType();
		try{
		under.setResolutionBW(Double.parseDouble(underlayMask.ResTextField.getText()));}
		catch(Exception e){
			warningMessage= warningMessage + "\nThe entry in the Resolution BW field should be numeric";
			warningFlag = true;
		}
		under.setScmMask(new SCMMaskType());
		
		Double data = 0.0;
		TableModel Sdata = underlayMask.table.getModel();
		for (int i=0; i<Sdata.getRowCount(); i++){
			InflectionPointType ifPoint = new InflectionPointType();
			try{
			data = Double.parseDouble(Sdata.getValueAt(i, 1).toString());
			ifPoint.setFrequency(data);
			data = Double.parseDouble(Sdata.getValueAt(i, 2).toString());
			ifPoint.setRelativePower(data);
			under.getScmMask().getInflectionPoint().add(ifPoint);
			}catch(Exception e){
				
				warningMessage = warningMessage + "\nThe entry at row: "+(i+1)+ "in the Underlay Mask table should be numeric";
				warningFlag = true;
				/*
				new Warn().setWarn("warning", "The entry at row: "+(i+1)+
						" in the Underlay Mask table should be numeric");*/
			}
		}
		
		
		if(underlayMask.MaxPowBtn.isSelected()==true){
			under.setMaskPowerMarginMethod("MaximumPowerDensity");
		}else{
			under.setMaskPowerMarginMethod("TotalPower");
		}
		
		// If Underlay Mask is rated		
		if(underlayMask.yes.isSelected()==true){
			   
			   under.setRating(new RatingType());
		       int index = underlayMask.box.getSelectedIndex();
		       
		       switch(index){
		       
		       case 0: try{under.getRating().
		       	setRatedBW(Double.parseDouble(underlayMask.underlayRated.BandRatField.getText().toString()));
		       }catch(Exception e){
		    	   
		    	   warningMessage = warningMessage + "\nBW Rating number should be a number";
		    	   warningFlag = true;
		    	  // new Warn().setWarn("Warning", "BW Rating number should be a number");
		       }
		       break;
		       
		       case 1: BWRatedListType bwRatedList = new BWRatedListType(); 
		    	data = 0.0;		       
				Sdata = underlayMask.underlayRated.table2.getModel();

				for (int i=0; i<Sdata.getRowCount(); i++){
					
					BWRatingType bwRating = new BWRatingType();
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
		       	setRatedBTP(Double.parseDouble(underlayMask.underlayRated.BTPRatingField.getText().toString()));
		       }catch(Exception e){
		    	   
		    	 warningMessage = warningMessage  + "\nThe entry in BTP Rated field should be numerical";
		    	 warningFlag = true;
		    //	new Warn().setWarn("Warning", "The entry in BTP Rated field should be numerical");   
		       }
		       break;
		       
		       case 3: BTPRatedListType btpRatedList = new BTPRatedListType();
		       data = 0.0;
		       Sdata = underlayMask.underlayRated.table3.getModel();
		       
		       for (int i=0; i<Sdata.getRowCount(); i++){
		    	   
		    	   BTPRatingType btpRating = new BTPRatingType();
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
		       
		       case 4: DCRatedListType dcRatedList = new DCRatedListType();
		       data = 0.0;
		       Sdata = underlayMask.underlayRated.table4.getModel();
		       
		       for(int i=0; i<Sdata.getRowCount(); i++){
		    	   
		    	   DCRatingType dcRating = new DCRatingType();
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
		        setPorpIndex(Integer.parseInt(underlayMask.underlayRated.PolicyField.getText().toString()));
		       }catch(Exception e){
		    	   warningMessage = warningMessage + "\nThe entry for PorP Index should be numerical";
	    		   warningFlag = true;
		    	  // new Warn().setWarn("Warning", "The entry for PorP Index should be numerical");
		       }
		       break;	       
		    
		       default:break;
		       }
		       
		}
		
		if(device.equals("Tx")){
			TxModel.setUnderlayMask(under);
		}else{
			RxModel.getUnderlayMask().add(under);
		}
	}
	
	/* Adding Power Map information to the XML document
	 * (Considering that we are adding a single Power Map)
	 */		
	public void addPowerMap(PowerMap powerMap, String device){
				
		SCMPowerMapType power = new SCMPowerMapType();
		power.setOrientation(new OrientationType());
		power.setGainMap(new GainMapType());
		
		if(powerMap.surface.isSelected()==true){
			power.getOrientation().setSurface(true);
			
			Double elevationData = 0.0;
			Double azimuthData = 0.0;
			Double gainData = 0.0;
			String strData = "";
			
			TableModel tableData = powerMap.table.getModel();
			for (int i = 0; i < tableData.getRowCount(); i++) {
			try{	
				
				power.getGainMap().getGainMapValue().add(new GainMapValueType());
				
				strData = tableData.getValueAt(i, 1).toString().replaceAll(" ", "");
				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
					power.getGainMap().getGainMapValue().get(i).setElevation(elevationData);
				}else{
					elevationData = Double.parseDouble(strData);
					power.getGainMap().getGainMapValue().get(i).setElevation(elevationData);
				}
				
				strData = tableData.getValueAt(i, 2).toString().replaceAll(" ", "");				
				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
					power.getGainMap().getGainMapValue().get(i).setAzimuth(azimuthData);
				}else{
					azimuthData = Double.parseDouble(strData);
					power.getGainMap().getGainMapValue().get(i).setAzimuth(azimuthData);
				}
				
				strData = tableData.getValueAt(i, 3).toString().replaceAll(" ", "");
				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
					power.getGainMap().getGainMapValue().get(i).setGain(gainData);
				}else{
					gainData = Double.parseDouble(strData);
					power.getGainMap().getGainMapValue().get(i).setGain(gainData);
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
			power.setOrientation(new OrientationType());
			power.getOrientation().setTowardReferencePoint(new TowardReferencePointType());
		}
		
		if(powerMap.relative.isSelected()==true){
			power.setOrientation(new OrientationType());
			power.getOrientation().setRelativeToPlatform(new RelativeToPlatformType());
		}
	}

	/* Adding Reference Power information to the XML document
	 */
	public void addReferencePower(JTextField powerField, String device){
		ReferencePowerType pow = new ReferencePowerType();
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
	public void addPropMap(PropMap propMap){
		
		SCMPropagationMapType prop = new SCMPropagationMapType();
		prop.setPropMap(new PropMapType());
		
		String strData = "";
		Double elevationData = 0.0;
		Double azimuthData = 0.0;
		Double n1 = 0.0;
		Double breakData = 0.0;
		Double n2 = 0.0;
		
		TableModel tableData = propMap.table.getModel();
		for (int i = 0; i < tableData.getRowCount(); i++) {
			try{
			prop.getPropMap().getPropMapValue().add(new PropMapValueType());
			
			strData = tableData.getValueAt(i, 1).toString().replaceAll(" ", "");
			if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
				prop.getPropMap().getPropMapValue().get(i).setElevation(elevationData);
			}else{
				elevationData = Double.parseDouble(strData);
				prop.getPropMap().getPropMapValue().get(i).setElevation(elevationData);
			}
			
			strData = tableData.getValueAt(i, 2).toString().replaceAll(" ", "");				
			if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
				prop.getPropMap().getPropMapValue().get(i).setAzimuth(azimuthData);
			}else{
				azimuthData = Double.parseDouble(strData);
				prop.getPropMap().getPropMapValue().get(i).setAzimuth(azimuthData);
			}
			
			prop.getPropMap().getPropMapValue().get(i).setPropagationModel(new PropagationModelType());
			
			
			strData = tableData.getValueAt(i, 4).toString().replaceAll(" ", "");
			if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
				
				strData = tableData.getValueAt(i, 3).toString().replaceAll(" ", "");
				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
					prop.getPropMap().getPropMapValue().get(i).getPropagationModel().setLinear(n1);
				}else{
					n1 = Double.parseDouble(strData);
					prop.getPropMap().getPropMapValue().get(i).getPropagationModel().setLinear(n1);
				}
				
			}else{
				
				prop.getPropMap().getPropMapValue().get(i).getPropagationModel().
				setPiecewiseLinear(new PiecewiseLinearType());
				breakData = Double.parseDouble(strData);
				prop.getPropMap().getPropMapValue().get(i).getPropagationModel()
				.getPiecewiseLinear().setBreakpoint(breakData);
				
				strData = tableData.getValueAt(i, 3).toString().replaceAll(" ", "");
				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
					prop.getPropMap().getPropMapValue().get(i).getPropagationModel()
					.getPiecewiseLinear().setFirstExponent(0.0);
				}else{
					n1 = Double.parseDouble(strData);
					prop.getPropMap().getPropMapValue().get(i).getPropagationModel()
					.getPiecewiseLinear().setFirstExponent(n1);
				}
				
				strData = tableData.getValueAt(i, 5).toString().replaceAll(" ", "");
				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
					prop.getPropMap().getPropMapValue().get(i).getPropagationModel()
					.getPiecewiseLinear().setSecondExponent(0.0);
				}else{
					n2 = Double.parseDouble(strData);
					prop.getPropMap().getPropMapValue().get(i).getPropagationModel()
					.getPiecewiseLinear().setSecondExponent(n2);
				}				
			}		
			
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe entry at row: " + (i+1) + " in the propagation map table should be numeric";
				warningFlag = true;
				//new Warn().setWarn("Warning", "The entry at row: " + (i+1) + " in the propagation map table should be numeric");
			}
		}
		
		TxModel.getScmPropagationMap().add(prop);
	}

	/* Adding Schedule information to the XML document
	 * (Considering that we are adding a single Schedule Construct)
	 */
	@SuppressWarnings("deprecation")
	public void addTime(Schedule sched, String device){
		
		DefaultTableModel timeModel = (DefaultTableModel) sched.table1.getModel();		
		DefaultTableModel timezoneModel = (DefaultTableModel) sched.table2.getModel();
		SCMScheduleType sch = new SCMScheduleType();

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
			cal.setTime(date);
			XMLGregorianCalendar start = DatatypeFactory.newInstance().
			newXMLGregorianCalendar(cal);
			
			start.setYear(cal.get(Calendar.YEAR));
			start.setMonth(cal.get(Calendar.MONTH)+1);
			start.setDay(cal.get(Calendar.DAY_OF_MONTH));
			start.setHour(startHour);
			start.setMinute(date.getMinutes());
			start.setSecond(date.getSeconds());
			try{
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
			
			cal.setTime(date);
			//System.out.println(cal.getTimeZone().LONG);
			XMLGregorianCalendar end = DatatypeFactory.newInstance().
			newXMLGregorianCalendar(cal);
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
			
			try{

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
	public void addLocation(Location locData, String device){
	
		SCMLocationType scmLoc = new SCMLocationType();
		LocationType loc = new LocationType();
				
		if(locData.LocCombo.getItemAt(locData.LocCombo.getSelectedIndex()).equals("Point")){
			loc.setPoint(new PointType());
		
			try{
			TableModel pointModel = locData.pointTable.getModel();
			loc.getPoint().setLongitude(Double.parseDouble(pointModel.getValueAt(0, 0).toString()));
			loc.getPoint().setLatitude(Double.parseDouble(pointModel.getValueAt(0, 1).toString()));
			loc.getPoint().setAltitude(Double.parseDouble(pointModel.getValueAt(0, 2).toString()));
		
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe entry in the Location Table should be Numerical";
				warningFlag =  true;
			//	new Warn().setWarn("Warning", "The entry in the Location Table should be Numerical");
			}
		}
		scmLoc.setLocation(loc);
		
		if(device.equals("Tx")){
			TxModel.getScmLocation().add(scmLoc);
		}else{
			RxModel.getScmLocation().add(scmLoc);
		}
	}
	
	
	/* Adding IMA information to the XML document
	 * 
	Requires the corresponding JAXB class for IMA and IMC
	
	public void addIMA(IMA ima, String device){
				
		SCMPowerMapType power = new SCMPowerMapType();
		power.setOrientation(new OrientationType());
		power.setGainMap(new GainMapType());
		
		if(powerMap.surface.isSelected()==true){
			power.getOrientation().setSurface(true);
			
			Double elevationData = 0.0;
			Double azimuthData = 0.0;
			Double gainData = 0.0;
			String strData = "";
			
			TableModel tableData = powerMap.table.getModel();
			for (int i = 0; i < tableData.getRowCount(); i++) {
			try{	
				
				power.getGainMap().getGainMapValue().add(new GainMapValueType());
				
				strData = tableData.getValueAt(i, 1).toString().replaceAll(" ", "");
				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
					power.getGainMap().getGainMapValue().get(i).setElevation(elevationData);
				}else{
					elevationData = Double.parseDouble(strData);
					power.getGainMap().getGainMapValue().get(i).setElevation(elevationData);
				}
				
				strData = tableData.getValueAt(i, 2).toString().replaceAll(" ", "");				
				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
					power.getGainMap().getGainMapValue().get(i).setAzimuth(azimuthData);
				}else{
					azimuthData = Double.parseDouble(strData);
					power.getGainMap().getGainMapValue().get(i).setAzimuth(azimuthData);
				}
				
				strData = tableData.getValueAt(i, 3).toString().replaceAll(" ", "");
				if(strData.equals(null)  || strData.equals("") || strData.equals(" ")){
					power.getGainMap().getGainMapValue().get(i).setGain(gainData);
				}else{
					gainData = Double.parseDouble(strData);
					power.getGainMap().getGainMapValue().get(i).setGain(gainData);
				}		
				
			}catch(Exception e){
				new Warn().setWarn("Warning", "The entry in row: "+ (i+1) 
						+ " in the Power Map table should be numeric");
			}
			}
			
			if(device.equals("Tx")){
				TxModel.getScmPowerMap().add(power);
			}else{
				RxModel.getScmPowerMap().add(power);
			}
			
		}
			
		if(powerMap.reference.isSelected()==true){
			power.setOrientation(new OrientationType());
			power.getOrientation().setTowardReferencePoint(new TowardReferencePointType());
		}
		
		if(powerMap.relative.isSelected()==true){
			power.setOrientation(new OrientationType());
			power.getOrientation().setRelativeToPlatform(new RelativeToPlatformType());
		}
	}
	
	*/
	
	
	/* Concluding all information added to the xml document and saving it, 
	 * based on the provided schema
	 */
	public void concludeXML(String saveName, String device){
		try {
			
			JAXBContext context = JAXBContext.newInstance("org.ieee.dyspansc._1900._5.scm");
			if(device.equals("Tx")){
				JAXBElement<TxModelType> element = createTxModel(TxModel);
				Marshaller marshaller = context.createMarshaller();
		        marshaller.setProperty("jaxb.formatted.output",Boolean.TRUE);
		        marshaller.marshal(element,System.out);
		      
		        OutputStream os = new FileOutputStream(saveName+".xml");
		        marshaller.marshal(element, os );
		        os.close();
			}else{
				JAXBElement<RxModelType> element = createRxModel(RxModel);
				Marshaller marshaller = context.createMarshaller();
		        marshaller.setProperty("jaxb.formatted.output",Boolean.TRUE);
		        marshaller.marshal(element,System.out);
		      
		        OutputStream os = new FileOutputStream(saveName+".xml");
		        marshaller.marshal(element, os );
		        os.close();
			}
			
	        
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
