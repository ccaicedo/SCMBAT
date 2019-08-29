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
 * LoagGUI.java
 * Sets load operations for several constructs
*/

package Load;

import java.util.List;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.ieee.dyspansc._1900._5.scm.BTPRatedList;
import org.ieee.dyspansc._1900._5.scm.BTPRating;
import org.ieee.dyspansc._1900._5.scm.BWRatedList;
import org.ieee.dyspansc._1900._5.scm.BWRating;
import org.ieee.dyspansc._1900._5.scm.CircularSurface;
import org.ieee.dyspansc._1900._5.scm.DCRatedList;
import org.ieee.dyspansc._1900._5.scm.DCRating;
import org.ieee.dyspansc._1900._5.scm.GainMapValue;
import org.ieee.dyspansc._1900._5.scm.InflectionPnt;
import org.ieee.dyspansc._1900._5.scm.IntermodulationMask;
import org.ieee.dyspansc._1900._5.scm.PathPoint;
import org.ieee.dyspansc._1900._5.scm.PointSurface;
import org.ieee.dyspansc._1900._5.scm.PropMapValue;
import org.ieee.dyspansc._1900._5.scm.Rating;
import org.ieee.dyspansc._1900._5.scm.ReferencePower;
import org.ieee.dyspansc._1900._5.scm.RxModel;
import org.ieee.dyspansc._1900._5.scm.SCMLocation;
import org.ieee.dyspansc._1900._5.scm.SCMMask;
import org.ieee.dyspansc._1900._5.scm.SCMPowerMap;
import org.ieee.dyspansc._1900._5.scm.SCMPropagationMap;
import org.ieee.dyspansc._1900._5.scm.SCMSchedule;
import org.ieee.dyspansc._1900._5.scm.Side;
import org.ieee.dyspansc._1900._5.scm.TxModel;
import org.ieee.dyspansc._1900._5.scm.UnderlayMask;

import Execute.MethodAnalysis;
import SCM_gui.IMC;
import SCM_gui.Location;
import SCM_gui.PowerMap;
import SCM_gui.PropMap;
import SCM_gui.SCM_MainWindow;
import SCM_gui.Schedule;
import SCM_home.Home;

public abstract class LoadGUI {
	
	final Logger logger = Logger.getLogger(MethodAnalysis.class);
	public abstract void setData(SCM_MainWindow scm, TxModel txModel);
	public void setData(SCM_MainWindow scm, RxModel rxModel){};
	public Vector<String> locationIndices = new Vector<String>();
	
	public void setReferencePower(SCM_MainWindow scm, ReferencePower refPow){
		scm.RefPowerField.setText(String.valueOf(refPow.getValue()));
	}

// Loading Underlay mask construct	
public void setUnderlay(SCM_MainWindow scm, UnderlayMask underlay, String deviceInfo){
		
		if (deviceInfo == "Tx") {
			//Loading Underlay Mask Data 
			
			if(underlay!=null){
				scm.spec.underlayResTextField.setText(String.valueOf(underlay.getResolutionBW()));
				
				List<InflectionPnt> infP = underlay.getScmMask().getInflectionPnt();
				DefaultTableModel model = (DefaultTableModel) scm.spec.underlayTable.getModel();
			  	model.setRowCount(0);
			  	
				for(int i=0; i<infP.size(); i++){
						
						String serial = Integer.toString(i+1);
						String data1 = String.valueOf(infP.get(i).getFrequency());
						String data2 = String.valueOf(infP.get(i).getRelativePower());
				  		Object[] rowData = {serial,data1,data2};
				  		model.addRow(rowData);
				  	}
				scm.spec.underlayTable=new JTable(model);
				
				scm.spec.noInitialState=true;
				scm.spec.underlayno.setSelected(true);
				
				if(underlay.getMaskPowerMarginMethod().equals("TotalPower")){
					scm.spec.TotPowerInitialState=true;
					scm.spec.TotPowerBtn.setSelected(true);
				}else{
					scm.spec.TotPowerInitialState=false;
					scm.spec.MaxPowBtn.setSelected(true);
				}
				
				if(underlay.getRating()!=null){
					
					scm.spec.noInitialState=false;
					scm.spec.underlayyes.setSelected(true);
					scm.spec.underlayno.setSelected(false);
					
					Rating ratedMask = underlay.getRating();
					if(ratedMask.getRatedBW()!=0.0){
						scm.spec.box.setSelectedIndex(0);
						scm.spec.underlayRated.BandRatField.setText(String.valueOf(
								ratedMask.getRatedBW()));
					}
					if(ratedMask.getBwRatedList()!=null){
						scm.spec.box.setSelectedIndex(1);
						BWRatedList bwRatedList = ratedMask.getBwRatedList();
						List<BWRating> bwRating = bwRatedList.getBwRating();
						DefaultTableModel ratedModel = (DefaultTableModel) scm.spec.underlayRated.table2.getModel();
						ratedModel.setRowCount(0);
						
						for(int i=0; i<bwRating.size(); i++){
							String data1 = String.valueOf(bwRating.get(i).getRatedBW());		
							String data2 = String.valueOf(bwRating.get(i).getAdjustment());
							Object[] rowData = {ratedModel.getRowCount()+1,data1,data2};
							ratedModel.addRow(rowData);
						}
					}
					
					if(ratedMask.getRatedBTP()!=0.0){
						scm.spec.box.setSelectedIndex(2);
						scm.spec.underlayRated.BTPRatingField.setText(String.valueOf(
								ratedMask.getRatedBTP()));
					}
					if(ratedMask.getBtpRatedList()!=null){
						scm.spec.box.setSelectedIndex(3);
						BTPRatedList btpRatedList = ratedMask.getBtpRatedList();
						List<BTPRating> btpRating = btpRatedList.getBtpRating();
						DefaultTableModel ratedModel = (DefaultTableModel) scm.spec.underlayRated.table3.getModel();
						ratedModel.setRowCount(0);
						
						for(int i=0; i<btpRating.size(); i++){
							String data1 = String.valueOf(btpRating.get(i).getBtp());
							String data2 = String.valueOf(btpRating.get(i).getAdjustment());
							Object[] rowData = {ratedModel.getRowCount()+1,data1,data2};
							ratedModel.addRow(rowData);
						}
					}
					if(ratedMask.getDcRatedList()!=null){
						scm.spec.box.setSelectedIndex(4);
						DCRatedList dcRatedList = ratedMask.getDcRatedList();
						List<DCRating> dcRating = dcRatedList.getDcRating();
						DefaultTableModel ratedModel = (DefaultTableModel) scm.spec.underlayRated.table4.getModel();
						ratedModel.setRowCount(0);
						
						for(int i=0; i<dcRating.size(); i++){
							String data1 = String.valueOf(dcRating.get(i).getDc());
							String data2 = String.valueOf(dcRating.get(i).getMaxDwellTime());
							String data3 = String.valueOf(dcRating.get(i).getAdjustment());
							Object[] rowData = {ratedModel.getRowCount()+1,data1,data2,data3};
							ratedModel.addRow(rowData);
						}
					}
					if(ratedMask.getPorPIndex()!=0){
						scm.spec.box.setSelectedIndex(5);
						scm.spec.underlayRated.PolicyField.setText(String.valueOf(
								ratedMask.getPorPIndex()));
					}
				
				}
				
			}
			return;
		}
		
		//Loading Underlay Mask Data 
		
		if(underlay!=null){
			scm.underlay.ResTextField.setText(String.valueOf(underlay.getResolutionBW()));
			
			List<InflectionPnt> infP = underlay.getScmMask().getInflectionPnt();
			DefaultTableModel model = (DefaultTableModel) scm.underlay.table.getModel();
		  	model.setRowCount(0);
		  	
			for(int i=0; i<infP.size(); i++){
					
					String serial = Integer.toString(i+1);
					String data1 = String.valueOf(infP.get(i).getFrequency());
					String data2 = String.valueOf(infP.get(i).getRelativePower());
			  		Object[] rowData = {serial,data1,data2};
			  		model.addRow(rowData);
			  	}
			scm.underlay.table=new JTable(model);
			
			scm.underlay.noInitialState=true;
			scm.underlay.no.setSelected(true);
			
			if(underlay.getMaskPowerMarginMethod().equals("TotalPower")){
				scm.underlay.TotPowerInitialState=true;
				scm.underlay.TotPowerBtn.setSelected(true);
			}else{
				scm.underlay.TotPowerInitialState=false;
				scm.underlay.MaxPowBtn.setSelected(true);
			}
			
			if(underlay.getRating()!=null){
				
				scm.underlay.noInitialState=false;
				scm.underlay.yes.setSelected(true);
				scm.underlay.no.setSelected(false);
				
				Rating ratedMask = underlay.getRating();
				if(ratedMask.getRatedBW()!=null){
					scm.underlay.box.setSelectedIndex(0);
					scm.underlay.underlayRated.BandRatField.setText(String.valueOf(
							ratedMask.getRatedBW()));
				}
				if(ratedMask.getBwRatedList()!=null){
					scm.underlay.box.setSelectedIndex(1);
					BWRatedList bwRatedList = ratedMask.getBwRatedList();
					List<BWRating> bwRating = bwRatedList.getBwRating();
					DefaultTableModel ratedModel = (DefaultTableModel) scm.underlay.underlayRated.table2.getModel();
					ratedModel.setRowCount(0);
					
					for(int i=0; i<bwRating.size(); i++){
						String data1 = String.valueOf(bwRating.get(i).getRatedBW());		
						String data2 = String.valueOf(bwRating.get(i).getAdjustment());
						Object[] rowData = {ratedModel.getRowCount()+1,data1,data2};
						ratedModel.addRow(rowData);
					}
				}
				
				if(ratedMask.getRatedBTP()!=null){
					scm.underlay.box.setSelectedIndex(2);
					scm.underlay.underlayRated.BTPRatingField.setText(String.valueOf(
							ratedMask.getRatedBTP()));
				}
				if(ratedMask.getBtpRatedList()!=null){
					scm.underlay.box.setSelectedIndex(3);
					BTPRatedList btpRatedList = ratedMask.getBtpRatedList();
					List<BTPRating> btpRating = btpRatedList.getBtpRating();
					DefaultTableModel ratedModel = (DefaultTableModel) scm.underlay.underlayRated.table3.getModel();
					ratedModel.setRowCount(0);
					
					for(int i=0; i<btpRating.size(); i++){
						String data1 = String.valueOf(btpRating.get(i).getBtp());
						String data2 = String.valueOf(btpRating.get(i).getAdjustment());
						Object[] rowData = {ratedModel.getRowCount()+1,data1,data2};
						ratedModel.addRow(rowData);
					}
				}
				if(ratedMask.getDcRatedList()!=null){
					scm.underlay.box.setSelectedIndex(4);
					DCRatedList dcRatedList = ratedMask.getDcRatedList();
					List<DCRating> dcRating = dcRatedList.getDcRating();
					DefaultTableModel ratedModel = (DefaultTableModel) scm.underlay.underlayRated.table4.getModel();
					ratedModel.setRowCount(0);
					
					for(int i=0; i<dcRating.size(); i++){
						String data1 = String.valueOf(dcRating.get(i).getDc());
						String data2 = String.valueOf(dcRating.get(i).getMaxDwellTime());
						String data3 = String.valueOf(dcRating.get(i).getAdjustment());
						Object[] rowData = {ratedModel.getRowCount()+1,data1,data2,data3};
						ratedModel.addRow(rowData);
					}
				}
				if(ratedMask.getPorPIndex()!=null){
					scm.underlay.box.setSelectedIndex(5);
					scm.underlay.underlayRated.PolicyField.setText(String.valueOf(
							ratedMask.getPorPIndex()));
				}
			
			}
			
		}
	}
	
// Loading Power Map construct
	public void setPowerMap(SCM_MainWindow scm, SCMPowerMap powerMap){
	
		PowerMap currentPow = new PowerMap();
		
		currentPow.PowerPanel = currentPow.getPanel();
		//currentPow..addActionListener(scm.control.new NextListener("power",scm.tabbedPane));
		currentPow.b3.addActionListener(scm.control.saveAction);
	//	currentLoc.exit.addActionListener(scm.control.saveActionexitAction);
		//currentPow.NewMap.addActionListener(scm.control.new createListener("power",scm.tabbedPane));
	//	currentPow.Previous.addActionListener(scm.control.new PrevListener("power",scm.tabbedPane));
		
		if(locationIndices.size()>0)
		{
			DefaultComboBoxModel<String> combomodel = new DefaultComboBoxModel<String>(locationIndices);
			currentPow.comboBox.setModel(combomodel);
			if(String.valueOf(powerMap.getLocationIndex())!="null" && String.valueOf(powerMap.getLocationIndex())!="")
			{
				//scm.power.locationField.setText(powerMap.getLocationIndex().toString());
				currentPow.comboBox.setSelectedItem(String.valueOf(powerMap.getLocationIndex()));
			}
			else
			{
				currentPow.comboBox.setSelectedIndex(-1);
			}
			
		}
		
		
		if(powerMap.getOrientation().getRelativeToPlatform()==null && 
				powerMap.getOrientation().getTowardReferencePoint()==null &&
				powerMap.getGainMap()!=null){
				
			currentPow.surface.setSelected(true);
			List<GainMapValue> gainMapValue = powerMap.getGainMap().getGainMapValue();
			
			DefaultTableModel model = (DefaultTableModel) currentPow.table1.getModel();
		  	model.setRowCount(0);
			double prevElevation=361.0;						
			String valueType = "";
			String value = "";
			
			for(int i=0; i<gainMapValue.size(); i++){
				//Elevation Angle","Azimuth Angle", "Gain (dB)
				if (gainMapValue.get(i).getElevation() != null) {
					valueType = "Elevation Angle";
					value = gainMapValue.get(i).getElevation().toString();
				}
				else if (gainMapValue.get(i).getAzimuth() != null) {
					valueType = "Azimuth Angle";
					value = gainMapValue.get(i).getAzimuth().toString();
				}
				else if (gainMapValue.get(i).getGain() != null) {
					valueType = "Gain (dB)";
					value = gainMapValue.get(i).getGain().toString();
				}
				else {
					continue;
				}
				
				Object[] rowData = {currentPow.table1.getRowCount()+1, valueType, value};
				model.addRow(rowData);
				
			}
			
			
		}else{
			if(powerMap.getOrientation().getRelativeToPlatform()!=null){
				currentPow.relative.setSelected(true);
			}else{
				currentPow.reference.setSelected(true);
			}			
		}
		scm.control.powerArray.add(currentPow);
	}
	
// Loading Propagation Map construct.
	public void setPropMap(SCM_MainWindow scm, SCMPropagationMap propMap){
		
		PropMap currentProp = new PropMap();
		
		currentProp.PropPanel = currentProp.getPanel();
		currentProp.Next.addActionListener(scm.control.new NextListener("prop",scm.tabbedPane));
		currentProp.b3.addActionListener(scm.control.saveAction);
	//	currentLoc.exit.addActionListener(scm.control.saveActionexitAction);
		currentProp.NewMap.addActionListener(scm.control.new createListener("prop",scm.tabbedPane));
		currentProp.Previous.addActionListener(scm.control.new PrevListener("prop",scm.tabbedPane));
		
		if(locationIndices.size()>0)
		{
			DefaultComboBoxModel<String> combomodel = new DefaultComboBoxModel<String>(locationIndices);
			currentProp.comboBox.setModel(combomodel);
			if(String.valueOf(propMap.getLocationIndex())!="null" &&  String.valueOf(propMap.getLocationIndex())!="")
			{
				currentProp.comboBox.setSelectedItem(String.valueOf(propMap.getLocationIndex()));
			}
			else
			{
				currentProp.comboBox.setSelectedIndex(-1);
			}
			
			currentProp.NewMap.setEnabled(true);
			
		}
		
		if(propMap.getPropMap().getPropMapValue()!=null){
			
			List<PropMapValue> propMapValue = propMap.getPropMap().getPropMapValue();
			
			DefaultTableModel model = (DefaultTableModel) currentProp.table.getModel();
			model.setRowCount(0);
			double prevElevation=361.0;
			String ele = "";
			String azi = "";
			String n1 = "";
			String dist = "";
			String n2 = "";
			
			String valueType = "";
			String value = "";
			
			for(int i=0; i<propMapValue.size(); i++){				
								
				if(propMapValue.get(i).getElevation()!=null){
					valueType = "Elevation Angle";
					value = propMapValue.get(i).getElevation().toString();
				}
				else if (propMapValue.get(i).getAzimuth()!=null) {
					valueType = "Azimuth Angle";
					value = propMapValue.get(i).getAzimuth().toString();
				}
				
				else if(propMapValue.get(i).getPropagationModel() != null) {
					if (propMapValue.get(i).getPropagationModel().getPiecewiseLinear() != null) {
						n1 = String.valueOf(propMapValue.get(i).getPropagationModel().
								getPiecewiseLinear().getFirstExponent());					
						dist = String.valueOf(propMapValue.get(i).getPropagationModel().
								getPiecewiseLinear().getBreakpoint());
						n2 = String.valueOf(propMapValue.get(i).getPropagationModel().
								getPiecewiseLinear().getSecondExponent());
						
						Object[] firstExponentData = {currentProp.table.getRowCount()+1, "First Exponent", n1};
						model.addRow(firstExponentData);
						
						Object[] breakpointData = {currentProp.table.getRowCount()+1, "Breakpoint(m)", dist};
						model.addRow(breakpointData);
						
						Object[] secondExponentData = {currentProp.table.getRowCount()+1, "Second Exponent", n2};
						model.addRow(secondExponentData);
						
						continue;
					}
					else if(propMapValue.get(i).getPropagationModel().getLinear()!=0.0) {
						valueType = "Prop Exponent";
//						value = String.valueOf(propMapValue.get(i+2).getPropagationModel().getLinear());
						value = String.valueOf(propMapValue.get(i).getPropagationModel().getLinear());
						
					}
				}
				
				else {
					continue;
				}
					
				Object[] rowData = {currentProp.table.getRowCount()+1, valueType, value};
				model.addRow(rowData);
			}
			scm.control.propArray.add(currentProp);
		}
	}

// Loading Location construct
	public void setLocation(SCM_MainWindow scm, SCMLocation loc, int index){
		
		Location currentLoc = new Location();
		currentLoc.panel = currentLoc.getPanel();
		currentLoc.Next.addActionListener(scm.control.new NextListener("location",scm.tabbedPane));
		currentLoc.save.addActionListener(scm.control.saveAction);
	//	currentLoc.exit.addActionListener(scm.control.saveActionexitAction);
		currentLoc.NewMap.addActionListener(scm.control.new createListener("location",scm.tabbedPane));
		currentLoc.Previous.addActionListener(scm.control.new PrevListener("location",scm.tabbedPane));
		currentLoc.LocationField.addFocusListener(scm.control.new LocIndexListener());		 
		
		//Check with string null because the stored value shows null as string
		if(String.valueOf(loc.getLocationIndex())!="null" && String.valueOf(loc.getLocationIndex())!="" )
		{
			String locIndex = String.valueOf(loc.getLocationIndex());
			currentLoc.LocationField.setText(locIndex);
			locationIndices.add(locIndex);
		}
		
		if(loc.getLocation().getPoint()!=null){
			
			currentLoc.LocCombo.setSelectedIndex(0);
						
			String longitude = String.valueOf(loc.getLocation().getPoint().getLongitude());
			String latitude = String.valueOf(loc.getLocation().getPoint().getLatitude());
			String Altitude = String.valueOf(loc.getLocation().getPoint().getAltitude());
			
			DefaultTableModel model = (DefaultTableModel) currentLoc.pointTable.getModel();
		  	model.setRowCount(0);			
		  	Object[] rowData = {longitude, latitude, Altitude};
		  	model.addRow(rowData);
		}
		if(loc.getLocation().getCylinder()!=null){
			
			currentLoc.LocCombo.setSelectedItem("Cylinder");
			currentLoc.LocCombo.setSelectedItem(loc.getLocationIndex().toString());
			
			
			String longitude = String.valueOf(loc.getLocation().getCylinder().getPoint().getLongitude());
			String latitude = String.valueOf(loc.getLocation().getCylinder().getPoint().getLatitude());
			String Altitude = String.valueOf(loc.getLocation().getCylinder().getPoint().getAltitude());
			String radius = String.valueOf(loc.getLocation().getCylinder().getRadius());
			String height = String.valueOf(loc.getLocation().getCylinder().getHeight());
			String transDensity = String.valueOf(loc.getLocation().getCylinder().getTransmitterDensity());
			String permAtten = String.valueOf(loc.getLocation().getCylinder().getPerimeterAttenuation());
			String topAtten = String.valueOf(loc.getLocation().getCylinder().getTopAttenuation());
			//String bottomAtten = String.valueOf(loc.getLocation().getCylinder().getBottomAttenuation());
			
			DefaultTableModel model = (DefaultTableModel) currentLoc.cylinderTable.getModel();
		  	model.setRowCount(0);			
		  	Object[] rowData = {longitude, latitude, Altitude, radius,permAtten,height, topAtten};
		  	model.addRow(rowData);
		  	
		  	currentLoc.HeightField.setText(height);
		  	currentLoc.transmitterField.setText(transDensity);
		}
		if(loc.getLocation().getCircularSurface()!=null){
			
			currentLoc.LocCombo.setSelectedItem("Circular Surface");
			currentLoc.LocCombo.setSelectedItem(loc.getLocationIndex().toString());
			
			
			CircularSurface circ = loc.getLocation().getCircularSurface();
			String longitude = String.valueOf(circ.getPoint().getLongitude());
			String latitude = String.valueOf(circ.getPoint().getLatitude());
			String Altitude = String.valueOf(circ.getPoint().getAltitude());
			String radius = String.valueOf(circ.getRadius());
			String permAtten = String.valueOf(circ.getPerimeterAttenuation());
			String height = String.valueOf(circ.getAntennaHeight().getHeight());
			String heightVal = String.valueOf(circ.getAntennaHeight().getReference());
			String transDens = String.valueOf(circ.getTransmitterDensity());
			
			DefaultTableModel model = (DefaultTableModel) currentLoc.circularTable.getModel();
		  	model.setRowCount(0);			
		  	Object[] rowData = {longitude, latitude, Altitude, radius,permAtten};
			model.addRow(rowData);
			
			currentLoc.HeightField.setText(height);
			if(heightVal.equals("AGL"))
			{
				currentLoc.AGL.setSelected(true);
			}
			else
			{
				currentLoc.HAAT.setSelected(true);
			}
			currentLoc.transmitterField.setText(transDens);
		
		}
		if(loc.getLocation().getPath()!=null){
			
			currentLoc.LocCombo.setSelectedItem("Path");
			currentLoc.LocCombo.setSelectedItem(loc.getLocationIndex().toString());
			
			List<PathPoint> pathPoint = loc.getLocation().getPath().getPathPoint();
			currentLoc.periodField.setText(loc.getLocation().getPath().getPeriod().toString());
			
			DefaultTableModel model = (DefaultTableModel) currentLoc.pathTable.getModel();
			model.setRowCount(0);
			String longt = "";
			String lat = "";
			String alt = "";
			String time = "";
			
			for(int i=0; i<pathPoint.size(); i++){
								
				longt = String.valueOf(pathPoint.get(i).getPoint().getLongitude());
				lat = String.valueOf(pathPoint.get(i).getPoint().getLatitude());
				alt = String.valueOf(pathPoint.get(i).getPoint().getAltitude());
				time = String.valueOf(pathPoint.get(i).getTime());
				
				
				Object[] rowData = {currentLoc.pathTable.getRowCount()+1,longt,lat,alt,time};
				model.addRow(rowData);				
			}
			
		}
		if(loc.getLocation().getPointSurface()!=null){
			
			currentLoc.LocCombo.setSelectedItem("Point Surface");
			currentLoc.LocationField.setText(String.valueOf(loc.getLocationIndex()));
			
			
			PointSurface pointSurf = loc.getLocation().getPointSurface();
			
			String longitude = String.valueOf(pointSurf.getPoint().getLongitude());
			String latitude = String.valueOf(pointSurf.getPoint().getLatitude());
			String Altitude = String.valueOf(pointSurf.getPoint().getAltitude());
			
			String height = String.valueOf(pointSurf.getAntennaHeight().getHeight());
			String heightVal = String.valueOf(pointSurf.getAntennaHeight().getReference());
			
			DefaultTableModel model = (DefaultTableModel) currentLoc.pointSurfaceTable.getModel();
		  	model.setRowCount(0);			
		  	Object[] rowData = {longitude, latitude, Altitude};
			model.addRow(rowData);
			
			currentLoc.HeightField.setText(height);
			if(heightVal.equals("AGL"))
			{
				currentLoc.AGL.setSelected(true);
			}
			else
			{
				currentLoc.HAAT.setSelected(true);
			}
		
		}
		if(loc.getLocation().getPolygonSurface()!=null){
			
			currentLoc.LocCombo.setSelectedItem("Polygon Surface");
			currentLoc.LocCombo.setSelectedItem(loc.getLocationIndex().toString());
			
			
			
			String height = String.valueOf(loc.getLocation().getPolygonSurface().getAntennaHeight().getHeight());
			String heightVal = String.valueOf(loc.getLocation().getPolygonSurface().getAntennaHeight().getReference());
			
			List<Side> polySide = loc.getLocation().getPolygonSurface().getScmPolygon().getSide();
			
			DefaultTableModel model = (DefaultTableModel) currentLoc.polygonTable.getModel();
			model.setRowCount(0);
			String longt = "";
			String lat = "";
			String alt = "";
			String atten = "";
			
			for(int i=0; i<polySide.size(); i++){
								
				longt = String.valueOf(polySide.get(i).getPoint().getLongitude());
				lat = String.valueOf(polySide.get(i).getPoint().getLatitude());
				alt = String.valueOf(polySide.get(i).getPoint().getAltitude());
				atten = String.valueOf(polySide.get(i).getSideAttenuation());
				
				
				Object[] rowData = {currentLoc.polygonTable.getRowCount()+1,longt,lat,alt,atten};
				model.addRow(rowData);				
			}
			currentLoc.HeightField.setText(height);
			if(heightVal.equals("AGL"))
			{
				currentLoc.AGL.setSelected(true);
			}
			else
			{
				currentLoc.HAAT.setSelected(true);
			}
			String transDens = String.valueOf(loc.getLocation().getPolygonSurface().getTransmitterDensity());
			currentLoc.transmitterField.setText(transDens);
		}
		if(loc.getLocation().getPolyhedron()!=null){
			
			currentLoc.LocCombo.setSelectedItem("Polyhedron");
			currentLoc.LocCombo.setSelectedItem(loc.getLocationIndex().toString());
					
			
			List<Side> polySide = loc.getLocation().getPolyhedron().getScmPolygon().getSide();
			
			DefaultTableModel model = (DefaultTableModel) currentLoc.polyhedronTable.getModel();
			model.setRowCount(0);
			String longt = "";
			String lat = "";
			String alt = "";
			String atten = "";
			
			for(int i=0; i<polySide.size(); i++){
								
				longt = String.valueOf(polySide.get(i).getPoint().getLongitude());
				lat = String.valueOf(polySide.get(i).getPoint().getLatitude());
				alt = String.valueOf(polySide.get(i).getPoint().getAltitude());
				atten = String.valueOf(polySide.get(i).getSideAttenuation());
				
				
				Object[] rowData = {currentLoc.polyhedronTable.getRowCount()+1,longt,lat,alt,atten};
				model.addRow(rowData);				
			}
			
			DefaultTableModel model_2 = (DefaultTableModel) currentLoc.heightTable.getModel();
			model_2.setRowCount(0);
		
			String heigh = String.valueOf(loc.getLocation().getPolyhedron().getHeight());
			String top = String.valueOf(loc.getLocation().getPolyhedron().getTopAttenuation());
			String bot = String.valueOf(loc.getLocation().getPolyhedron().getBottomAttenuation());
		
				Object[] rowData = {heigh, bot, top};
				model_2.addRow(rowData);				
			
			
			currentLoc.HeightField.setText(heigh);
			String transDens = String.valueOf(loc.getLocation().getPolyhedron().getTransmitterDensity());
			currentLoc.transmitterField.setText(transDens);
		}
		
		scm.control.locationArray.add(currentLoc);
	}

// Loading Schedule construct.
	public void setSchedule(SCM_MainWindow scm, SCMSchedule sched){
		logger.addAppender(Home.appender);
		Schedule currentSched = new Schedule();
		
		currentSched.panel = currentSched.getPanel();
		currentSched.Next.addActionListener(scm.control.new NextListener("schedule",scm.tabbedPane));
		currentSched.b3.addActionListener(scm.control.saveAction);
	//	currentLoc.exit.addActionListener(scm.control.saveActionexitAction);
		currentSched.NewSched.addActionListener(scm.control.new createListener("schedule",scm.tabbedPane));
		currentSched.Previous.addActionListener(scm.control.new PrevListener("schedule",scm.tabbedPane));
		
		if(locationIndices.size()>0)
		{
		DefaultComboBoxModel<String> combomodel = new DefaultComboBoxModel<String>(locationIndices);
		currentSched.comboBox.setModel(combomodel);
		if(String.valueOf(sched.getLocationIndex())!="null" && String.valueOf(sched.getLocationIndex())!="")
		{
			//scm.power.locationField.setText(powerMap.getLocationIndex().toString());
			currentSched.comboBox.setSelectedItem(String.valueOf(sched.getLocationIndex()));
		}
		else
		{
			currentSched.comboBox.setSelectedIndex(-1);
		}
		currentSched.NewSched.setEnabled(true);
		}
			
		XMLGregorianCalendar time = sched.getStartTime();
		DefaultTableModel model = (DefaultTableModel) currentSched.table1.getModel();
		model.setRowCount(0);
		
		DefaultTableModel tzTableModel = (DefaultTableModel) currentSched.table2.getModel();
		tzTableModel.setRowCount(0);
		
		String index = "Start Time";
		
		try{
		for(int i=0;i<2;i++){
			
			String year = String.valueOf(time.getYear());
			String month = String.valueOf(time.getMonth());
			String day = String.valueOf(time.getDay());
			
			String hour = String.valueOf(time.getHour());
			String minute = String.valueOf(time.getMinute());
			String second = String.valueOf(time.getSecond());
			
			TimeZone tz = time.getTimeZone(time.getTimezone());
			
			System.out.println("TimeZone: " + tz.getDisplayName());
			
			logger.info("TimeZone: " + tz.getDisplayName());
			long tzHours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
			long tzMinutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset())
			      - TimeUnit.HOURS.toMinutes(tzHours);
			
			Object[] rowData = {index, year,month,day,hour,minute,second};
			model.addRow(rowData);
			
			Object[] rowData2 = {index,tzHours,Math.abs(tzMinutes)};
			tzTableModel.addRow(rowData2);
			
			index = "End Time";
			time = sched.getEndTime();
		}
		}catch(Exception e){
			Object[] row1 = {"Start Time","","","","","","",""};
			model.addRow(row1);
			Object[] row2 = {"End Time","","","","","","",""};
			model.addRow(row2);
			Object[] row3 = {"Start Time","",""};
			tzTableModel.addRow(row3);
			Object[] row4 = {"End Time","",""};
			tzTableModel.addRow(row4);
		}
		scm.control.scheduleArray.add(currentSched);
	}
	
	//The method to set the data of the inter-modulation mask into the UI
	public void setIntermodulationMask(SCM_MainWindow scm, IntermodulationMask interMod)
	{
		IMC currentImc = new IMC();
		currentImc.mainPanel = currentImc.getPanel();
		
		currentImc.Next.addActionListener(scm.control.new NextListener("imc",scm.tabbedPane));
		currentImc.Previous.addActionListener(scm.control.new PrevListener("imc",scm.tabbedPane));
		currentImc.b3.addActionListener(scm.control.saveAction);
		currentImc.imaNext.addActionListener(scm.control.new NextListener("ima",scm.tabbedPane));
		currentImc.imaPrevious.addActionListener(scm.control.new PrevListener("ima",scm.tabbedPane));
	//	currentLoc.exit.addActionListener(scm.control.saveActionexitAction);
		currentImc.NewMap.addActionListener(scm.control.new createListener("imc",scm.tabbedPane));

		//Check with string null because the stored value shows null as string
		if(String.valueOf(interMod.getOrder())!="null" && String.valueOf(interMod.getOrder())!="" )
		{
			String imIndex = String.valueOf(interMod.getOrder());
			currentImc.imOrderField.setText(imIndex);
		}
		
		try
		{
		if(String.valueOf(interMod.getIntermediateFrequency())!=null)
		{
			currentImc.IFField.setText(String.valueOf(interMod.getIntermediateFrequency()));
			
		}
				
		if(interMod.isHighSideInjection()!=null && interMod.isHighSideInjection())
		{
			currentImc.IFYes.setSelected(true);
			currentImc.IFNo.setSelected(false);
		}
		else
		{
			currentImc.IFYes.setSelected(false);
			currentImc.IFNo.setSelected(true);
		}
		
		SCMMask scmmask = interMod.getImCombiningMask();
		if(scmmask !=null && scmmask.getRefFrequency()!=null)
		{
			
			currentImc.RelFreqField.setText(String.valueOf(scmmask.getRefFrequency()));
			currentImc.relFreqBtn.setSelected(true);
			currentImc.RelFreq.setEnabled(true);
			currentImc.RelFreqField.setEnabled(true);
			
		
		List<InflectionPnt> infP = interMod.getImCombiningMask().getInflectionPnt();
		DefaultTableModel model = (DefaultTableModel) currentImc.table.getModel();
	  	model.setRowCount(0);
	  	
	  	if(infP!=null)
	  	{
	  		for(int i=0; i<infP.size(); i++){
				
				String serial = Integer.toString(i+1);
				String data1 = String.valueOf(infP.get(i).getFrequency());
				String data2 = String.valueOf(infP.get(i).getRelativePower());
		  		Object[] rowData = {serial,data1,data2};
		  		model.addRow(rowData);
		  	}
		currentImc.table=new JTable(model);
		
	  	}
		}
	  	
		
		//Check if the IMA is set or not
		if(scmmask!=null && scmmask.getRefFrequency()!=null)
	  	{
	  		currentImc.imaRelFreqField.setText(String.valueOf(interMod.getImCombiningMask().getRefFrequency()));
	  	}
		
		
		if(interMod.getImAmplificationMask()!=null)
		{
			List<InflectionPnt> imaInflP = interMod.getImAmplificationMask().getInflectionPnt();
			if(imaInflP.size()>0)
			{
				currentImc.IMAYes.setSelected(true);
				currentImc.addIMAPanel();
				currentImc.imarelFreqBtn.setSelected(true);
				currentImc.imaRelFreq.setEnabled(true);
				currentImc.imaRelFreqField.setEnabled(true);
				DefaultTableModel imamodel = (DefaultTableModel) currentImc.imatable.getModel();
			  	imamodel.setRowCount(0);
			  	
				for(int i=0; i<imaInflP.size(); i++){
						
						String serial = Integer.toString(i+1);
						String data1 = String.valueOf(imaInflP.get(i).getFrequency());
						String data2 = String.valueOf(imaInflP.get(i).getRelativePower());
				  		Object[] rowData = {serial,data1,data2};
				  		imamodel.addRow(rowData);
				  	}
				currentImc.imatable=new JTable(imamodel);
				
			}
			
		}
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		scm.control.imcArray.add(currentImc);
		
		
	}

}
