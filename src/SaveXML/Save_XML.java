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

import org.ieee.dyspansc._1900._5.scm.AntennaHeightType;
import org.ieee.dyspansc._1900._5.scm.BTPRatedListType;
import org.ieee.dyspansc._1900._5.scm.BTPRatingType;
import org.ieee.dyspansc._1900._5.scm.BWRatedListType;
import org.ieee.dyspansc._1900._5.scm.BWRatingType;
import org.ieee.dyspansc._1900._5.scm.BandListType;
import org.ieee.dyspansc._1900._5.scm.BandType;
import org.ieee.dyspansc._1900._5.scm.CircularSurfaceType;
import org.ieee.dyspansc._1900._5.scm.CylinderType;
import org.ieee.dyspansc._1900._5.scm.DCRatedListType;
import org.ieee.dyspansc._1900._5.scm.DCRatingType;
import org.ieee.dyspansc._1900._5.scm.FrequencyListType;
import org.ieee.dyspansc._1900._5.scm.GainMapType;
import org.ieee.dyspansc._1900._5.scm.GainMapValueType;
import org.ieee.dyspansc._1900._5.scm.HoppingDataType;
import org.ieee.dyspansc._1900._5.scm.InflectionPointType;
import org.ieee.dyspansc._1900._5.scm.IntermodulationMaskType;
import org.ieee.dyspansc._1900._5.scm.LocationType;
import org.ieee.dyspansc._1900._5.scm.ObjectFactory;
import org.ieee.dyspansc._1900._5.scm.OrientationType;
import org.ieee.dyspansc._1900._5.scm.PathPointType;
import org.ieee.dyspansc._1900._5.scm.PathType;
import org.ieee.dyspansc._1900._5.scm.PiecewiseLinearType;
import org.ieee.dyspansc._1900._5.scm.PointSurfaceType;
import org.ieee.dyspansc._1900._5.scm.PointType;
import org.ieee.dyspansc._1900._5.scm.PolygonSurfaceType;
import org.ieee.dyspansc._1900._5.scm.PolyhedronType;
import org.ieee.dyspansc._1900._5.scm.PropMapType;
import org.ieee.dyspansc._1900._5.scm.PropMapValueType;
import org.ieee.dyspansc._1900._5.scm.PropagationModelType;
import org.ieee.dyspansc._1900._5.scm.RatingType;
import org.ieee.dyspansc._1900._5.scm.ReferencePowerType;
import org.ieee.dyspansc._1900._5.scm.RelativeToPlatformType;
import org.ieee.dyspansc._1900._5.scm.RxModelType;
import org.ieee.dyspansc._1900._5.scm.SCMLocationType;
import org.ieee.dyspansc._1900._5.scm.SCMMaskType;
import org.ieee.dyspansc._1900._5.scm.SCMPolygonType;
import org.ieee.dyspansc._1900._5.scm.SCMPowerMapType;
import org.ieee.dyspansc._1900._5.scm.SCMPropagationMapType;
import org.ieee.dyspansc._1900._5.scm.SCMScheduleType;
import org.ieee.dyspansc._1900._5.scm.SideType;
import org.ieee.dyspansc._1900._5.scm.SpectrumMaskType;
import org.ieee.dyspansc._1900._5.scm.TowardReferencePointType;
import org.ieee.dyspansc._1900._5.scm.TxModelType;
import org.ieee.dyspansc._1900._5.scm.UnderlayMaskType;
import org.w3c.dom.Document;

import SCM_gui.IMC;
import SCM_gui.Location;
import SCM_gui.PowerMap;
import SCM_gui.PropMap;
import SCM_gui.Schedule;
import SCM_gui.SpecMask;


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
	public void addUnderlay(SpecMask spec, String device) {
		
		UnderlayMaskType under = new UnderlayMaskType();
		//First check if the Underlay Mask was set from Spec Mask tab (Sometimes, both may not be enabled). Otherwise return
		if(spec.underlayNoButton.isSelected() ||( !spec.underlayYesButton.isSelected() && !spec.underlayNoButton.isSelected()))
		{
			return;
		}
		try{
		under.setResolutionBW(Double.parseDouble(spec.underlayResTextField.getText()));}
		catch(Exception e){
			warningMessage= warningMessage + "\nThe entry in the Resolution BW field should be numeric";
			warningFlag = true;
		}
		under.setScmMask(new SCMMaskType());
		
		Double data = 0.0;
		TableModel Sdata = spec.underlayTable.getModel();
		for (int i=0; i<Sdata.getRowCount(); i++){
			InflectionPointType ifPoint = new InflectionPointType();
			try{
			if(Sdata.getValueAt(i, 1).toString().equals("")&& Sdata.getValueAt(i, 2).toString().equals(""))
			{
				break;
			}
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
		
		
		if(spec.MaxPowBtn.isSelected()==true){
			under.setMaskPowerMarginMethod("MaximumPowerDensity");
		}else{
			under.setMaskPowerMarginMethod("TotalPower");
		}
		
		// If Underlay Mask is rated		
		if(spec.underlayyes.isSelected()==true){
			   
			   under.setRating(new RatingType());
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
		       
		       case 1: BWRatedListType bwRatedList = new BWRatedListType(); 
		    	data = 0.0;		       
				Sdata = spec.underlayRated.table2.getModel();

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
		       	setRatedBTP(Double.parseDouble(spec.underlayRated.BTPRatingField.getText().toString()));
		       }catch(Exception e){
		    	   
		    	 warningMessage = warningMessage  + "\nThe entry in BTP Rated field should be numerical";
		    	 warningFlag = true;
		    //	new Warn().setWarn("Warning", "The entry in BTP Rated field should be numerical");   
		       }
		       break;
		       
		       case 3: BTPRatedListType btpRatedList = new BTPRatedListType();
		       data = 0.0;
		       Sdata = spec.underlayRated.table3.getModel();
		       
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
		       Sdata = spec.underlayRated.table4.getModel();
		       
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
		        setPorpIndex(Integer.parseInt(spec.underlayRated.PolicyField.getText().toString()));
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
				
				//Set the linear value to default value if the piecewiselineartype is set
				prop.getPropMap().getPropMapValue().get(i).getPropagationModel().setLinear(0.0);
				
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
		
		else if(locData.LocCombo.getItemAt(locData.LocCombo.getSelectedIndex()).equals("Point Surface")) {
			
			TableModel pointSurfaceModel = locData.pointSurfaceTable.getModel();
			loc.setPointSurface(new PointSurfaceType());
			loc.getPointSurface().setPoint( new PointType());
			loc.getPointSurface().setAntennaHeight(new AntennaHeightType());	
			
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
			loc.setCircularSurface(new CircularSurfaceType());
			loc.getCircularSurface().setPoint(new PointType());
			loc.getCircularSurface().setAntennaHeight(new AntennaHeightType());	
			
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
			loc.setPolygonSurface(new PolygonSurfaceType());
			loc.getPolygonSurface().setScmPolygon(new SCMPolygonType());
			
			
			loc.getPolygonSurface().setAntennaHeight(new AntennaHeightType());	
			
			for (int i=0; i<polygonTable.getRowCount(); i++){
				
				try{
				Double longitude = Double.parseDouble(polygonTable.getValueAt(i, 1).toString());
				Double latitude = Double.parseDouble(polygonTable.getValueAt(i, 2).toString());
				Double altitude = Double.parseDouble(polygonTable.getValueAt(i, 3).toString());
				Double sideatten = Double.parseDouble(polygonTable.getValueAt(i, 4).toString());
				
				SideType sideType = new SideType();
				sideType.setPoint(new PointType());
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
			loc.setCylinder(new CylinderType());
			loc.getCylinder().setPoint(new PointType());
				
			
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
			
			loc.setPolyhedron(new PolyhedronType());
			loc.getPolyhedron().setScmPolygon(new SCMPolygonType());
			
				
			
			for (int i=0; i<polyhedronTable.getRowCount(); i++){
				
				try{
					
				SideType sideType = new SideType();
				PointType pointType = new PointType();
				
					
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
			loc.setPath(new PathType());
			
			
			PathPointType pathPointType = new PathPointType();
			pathPointType.setPoint(new PointType());
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
					String locindex = locData.LocationField.getText();
					int finallocindex;
					if(locindex=="" || locindex == null)
					{
						finallocindex = 0;
					}
					else
					{
						finallocindex = Integer.parseInt(locindex);
					}
					
				scmLoc.setLocationIndex(finallocindex);
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
	public void addIMA(IMC imc, IntermodulationMaskType imcMask)
	{
		//First check if the IMA was set from Intermodulation Mask tab (Sometimes, both may not be enabled). Otherwise return
		if(imc.IMANo.isSelected() ||( !imc.IMAYes.isSelected() && !imc.IMANo.isSelected()))
		{
			return;
		}
		SCMMaskType imaMask = new SCMMaskType();
		imcMask.setImAmplificationMask(imaMask);
		
		try{
			imcMask.getImAmplificationMask().setRefFrequency(Double.parseDouble(imc.RelFreqField.getText()));
		}catch(Exception e){
			imcMask.getImAmplificationMask().setRefFrequency(0.0);
		}
		
		TableModel Sdata = imc.imatable.getModel();
		for (int i = 0; i < Sdata.getRowCount(); i++) {
			try{
			InflectionPointType ifPoint = new InflectionPointType();
			imcMask.getImAmplificationMask().getInflectionPoint().add(ifPoint);
			
			Double data = Double.parseDouble(Sdata.getValueAt(i, 1).toString());
			imcMask.getImAmplificationMask().getInflectionPoint().get(i).setFrequency(data);
			data = Double.parseDouble(Sdata.getValueAt(i, 2).toString());
			imcMask.getImAmplificationMask().getInflectionPoint().get(i).setRelativePower(data);
		
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe entry at row: " +(i+1)+" in the IMA table should be numerical";
				warningFlag = true;
			}
		}	
		
	}
	
	
	/* Adding Intermodulation Mask information to the XML document*/
	
	public void addIMC(IMC imc){
				
			
		IntermodulationMaskType imask = new IntermodulationMaskType();
		
		imask.setImCombiningMask(new SCMMaskType());
		//Set the Center frequency for the intermediate frequency
		try {
			imask.setIntermediateFrequency(Double.parseDouble(imc.IFField.getText()));
		}catch(Exception e)
		{
			imask.setIntermediateFrequency(0.0);

		}
		
		try{
			imask.getImCombiningMask().setRefFrequency(Double.parseDouble(imc.RelFreqField.getText()));
		}catch(Exception e){
			imask.getImCombiningMask().setRefFrequency(0.0);
		}
		
		TableModel Sdata = imc.table.getModel();
		for (int i = 0; i < Sdata.getRowCount(); i++) {
			try{
			InflectionPointType ifPoint = new InflectionPointType();
			imask.getImCombiningMask().getInflectionPoint().add(ifPoint);
			
			if(Sdata.getValueAt(i, 1).toString().equals("")&& Sdata.getValueAt(i, 2).toString().equals(""))
			{
				break;
			}
			
			Double data = Double.parseDouble(Sdata.getValueAt(i, 1).toString());
			imask.getImCombiningMask().getInflectionPoint().get(i).setFrequency(data);
			data = Double.parseDouble(Sdata.getValueAt(i, 2).toString());
			imask.getImCombiningMask().getInflectionPoint().get(i).setRelativePower(data);
		
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe entry at row: " +(i+1)+" in the IM Combining Mask table should be numerical";
				warningFlag = true;
			}
		}    	
		try{
			imask.setOrder(imc.imOrderField.getText());
		}catch(Exception e){
			warningMessage = warningMessage + "\nThe entry in IM Order field in the Intermodulation mask should be numeric";
			warningFlag = true;
			
		//	new Warn().setWarn("Warning", "The entry in Resolution BW field should be numeric");
		}
		// If there is highSideInjection to be stored
		try{
			imask.setHighSideInjection(imc.IFYes.isSelected());
			}catch(Exception e){
				warningMessage = warningMessage + "\nThe entry for highSideInjection field in the Intermodulation mask must be set properly";
				warningFlag = true;
				
			//	new Warn().setWarn("Warning", "The entry in Resolution BW field should be numeric");
			}
		
		addIMA(imc,imask);
		TxModel.getIntermodulationMask().add(imask);
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
				JAXBElement<TxModelType> element = createTxModel(TxModel);
				Marshaller marshaller = context.createMarshaller();
		        marshaller.setProperty("jaxb.formatted.output",Boolean.TRUE);
		        marshaller.marshal(element,System.out);
		      
		        OutputStream os = new FileOutputStream(filename);
		        marshaller.marshal(element, os );
		        os.close();
			}else{
				JAXBElement<RxModelType> element = createRxModel(RxModel);
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
