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

import SCM_gui.SCM_MainWindow;
import SCM_home.Home;

import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.ieee.dyspansc._1900._5.scm.*;

import Execute.MethodAnalysis;

public abstract class LoadGUI {
	
	final Logger logger = Logger.getLogger(MethodAnalysis.class);
	public abstract void setData(SCM_MainWindow scm, TxModelType txModel);
	public void setData(SCM_MainWindow scm, RxModelType rxModel){};
	
	public void setReferencePower(SCM_MainWindow scm, ReferencePowerType refPow){
		scm.RefPowerField.setText(String.valueOf(refPow.getValue()));
	}

// Loading Underlay mask construct	
public void setUnderlay(SCM_MainWindow scm, UnderlayMaskType underlay){
		
		
		
		//Loading Underlay Mask Data 
		
		if(underlay!=null){
			scm.spec.underlayResTextField.setText(String.valueOf(underlay.getResolutionBW()));
			
			List<InflectionPointType> infP = underlay.getScmMask().getInflectionPoint();
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
				
				RatingType ratedMask = underlay.getRating();
				if(ratedMask.getRatedBW()!=0.0){
					scm.spec.box.setSelectedIndex(0);
					scm.spec.underlayRated.BandRatField.setText(String.valueOf(
							ratedMask.getRatedBW()));
				}
				if(ratedMask.getBwRatedList()!=null){
					scm.spec.box.setSelectedIndex(1);
					BWRatedListType bwRatedList = ratedMask.getBwRatedList();
					List<BWRatingType> bwRating = bwRatedList.getBwRating();
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
					BTPRatedListType btpRatedList = ratedMask.getBtpRatedList();
					List<BTPRatingType> btpRating = btpRatedList.getBtpRating();
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
					DCRatedListType dcRatedList = ratedMask.getDcRatedList();
					List<DCRatingType> dcRating = dcRatedList.getDcRating();
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
				if(ratedMask.getPorpIndex()!=0){
					scm.spec.box.setSelectedIndex(5);
					scm.spec.underlayRated.PolicyField.setText(String.valueOf(
							ratedMask.getPorpIndex()));
				}
			
			}
			
		}
	}
	
// Loading Power Map construct
	public void setPowerMap(SCM_MainWindow scm, SCMPowerMapType powerMap){
		
		if(powerMap.getOrientation().getRelativeToPlatform()==null && 
				powerMap.getOrientation().getTowardReferencePoint()==null &&
				powerMap.getGainMap()!=null){
				
			scm.power.surface.setSelected(true);
			List<GainMapValueType> gainMapValue = powerMap.getGainMap().getGainMapValue();
			
			DefaultTableModel model = (DefaultTableModel) scm.power.table1.getModel();
		  	model.setRowCount(0);
			double prevElevation=361.0;						
			String ele = "";
			String azi = "";
			String gain = "";
			
			for(int i=0; i<gainMapValue.size(); i++){
				
				if(gainMapValue.get(i).getElevation()!=prevElevation){
					ele = String.valueOf(gainMapValue.get(i).getElevation());
				}else{
					ele = "";
				}
				
				azi = String.valueOf(gainMapValue.get(i).getAzimuth());
				gain = String.valueOf(gainMapValue.get(i).getGain());
				Object[] rowData = {scm.power.table1.getRowCount()+1,ele,azi,gain};
				model.addRow(rowData);
				
				prevElevation = gainMapValue.get(i).getElevation();
			}
			
			
		}else{
			if(powerMap.getOrientation().getRelativeToPlatform()!=null){
				scm.power.relative.setSelected(true);
			}else{
				scm.power.reference.setSelected(true);
			}			
		}		
	}
	
// Loading Propagation Map construct.
	public void setPropMap(SCM_MainWindow scm, SCMPropagationMapType propMap){
		
		if(propMap.getPropMap().getPropMapValue()!=null){
			
			List<PropMapValueType> propMapValue = propMap.getPropMap().getPropMapValue();
			
			DefaultTableModel model = (DefaultTableModel) scm.prop.table.getModel();
			model.setRowCount(0);
			double prevElevation=361.0;
			String ele = "";
			String azi = "";
			String n1 = "";
			String dist = "";
			String n2 = "";
			
			for(int i=0; i<propMapValue.size(); i++){
								
				if(propMapValue.get(i).getElevation()!=prevElevation){
					ele = String.valueOf(propMapValue.get(i).getElevation());
				}else{
					ele = "";
				}
				
				azi = String.valueOf(propMapValue.get(i).getAzimuth());
				
				if(propMapValue.get(i).getPropagationModel().getPiecewiseLinear()==null ||
						propMapValue.get(i).getPropagationModel().getLinear()!=0.0){
					n1 = String.valueOf(propMapValue.get(i).getPropagationModel().getLinear());
					Object[] rowData = {scm.prop.table.getRowCount()+1,ele,azi,n1,"",""};
					model.addRow(rowData);
				}else{
					
					n1 = String.valueOf(propMapValue.get(i).getPropagationModel().
							getPiecewiseLinear().getFirstExponent());					
					dist = String.valueOf(propMapValue.get(i).getPropagationModel().
							getPiecewiseLinear().getBreakpoint());
					n2 = String.valueOf(propMapValue.get(i).getPropagationModel().
							getPiecewiseLinear().getSecondExponent());
					Object[] rowData = {scm.prop.table.getRowCount()+1,ele,azi,n1,dist,n2};
					model.addRow(rowData);
				}
					
				prevElevation = propMapValue.get(i).getElevation();
			}
		}
	}

// Loading Location construct
	public void setLocation(SCM_MainWindow scm, SCMLocationType loc){
		
		if(loc.getLocation().getPoint()!=null){
			
			scm.location.LocCombo.setSelectedIndex(0);
			String longitude = String.valueOf(loc.getLocation().getPoint().getLongitude());
			String latitude = String.valueOf(loc.getLocation().getPoint().getLatitude());
			String Altitude = String.valueOf(loc.getLocation().getPoint().getAltitude());
			
			DefaultTableModel model = (DefaultTableModel) scm.location.pointTable.getModel();
		  	model.setRowCount(0);			
		  	Object[] rowData = {longitude, latitude, Altitude};
		  	model.addRow(rowData);
		}
	}

// Loading Schedule construct.
	public void setSchedule(SCM_MainWindow scm, SCMScheduleType sched){
		logger.addAppender(Home.appender);
		XMLGregorianCalendar time = sched.getStartTime();
		DefaultTableModel model = (DefaultTableModel) scm.schedule.table1.getModel();
		model.setRowCount(0);
		
		DefaultTableModel tzTableModel = (DefaultTableModel) scm.schedule.table2.getModel();
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
		
	}

}
