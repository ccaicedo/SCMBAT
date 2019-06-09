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
 * LoadTxModel.java
 * Sets load operations for transmitter models
*/


package Load;


import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.ieee.dyspansc._1900._5.scm.Band;
import org.ieee.dyspansc._1900._5.scm.InflectionPnt;
import org.ieee.dyspansc._1900._5.scm.IntermodulationMask;
import org.ieee.dyspansc._1900._5.scm.TxModel;

import SCM_gui.SCM_MainWindow;

public class LoadTxModel extends LoadGUI {
	
	//final Logger logger = Logger.getLogger(LoadTxModel.class);
	
	@Override
	public void setData(SCM_MainWindow scm, TxModel txModel) {		
	//	logger.info("This is Transmitter");
		System.out.println("This is Transmitter");
		
		//Loading Spectrum Mask Data
		int o=0;
		scm.spec.ResTextField.setText(String.valueOf(
				txModel.getSpectrumMask().get(o).getResolutionBW()));
		
		List<InflectionPnt> infP = txModel.getSpectrumMask().get(o).getScmMask().getInflectionPnt();
		int inflectionLength = infP.size();
		DefaultTableModel model = (DefaultTableModel) scm.spec.table.getModel();
	  	model.setRowCount(0);
	  	
		for(int i=0; i<inflectionLength; i++){
				
				String serial = Integer.toString(i+1);
				String data1 = String.valueOf(infP.get(i).getFrequency());
				String data2 = String.valueOf(infP.get(i).getRelativePower());
				
		  		Object[] rowData = {serial,data1,data2};
		  		model.addRow(rowData);
		  	}
		
		scm.spec.table = new JTable(model);
		
			if(txModel.getSpectrumMask().get(o).getScmMask().getRefFrequency()==null){
				scm.spec.RelFreq.setSelected(false);
			}else{
				scm.spec.RelFreq.setSelected(true);
				scm.spec.TextField.setText(String.valueOf(
						txModel.getSpectrumMask().get(o).getScmMask().getRefFrequency()));
			} 
			
			if(txModel.getSpectrumMask().get(o).getHoppingData()!=null){
				
				scm.spec.noInitialState = false;
				scm.spec.yes.setSelected(true);
				
				//Loading Spectrum Hopping Systems
				scm.spec.SpecHop.DwellField.setText(
						String.valueOf(txModel.getSpectrumMask().get(o).
								getHoppingData().getDwellTime()));
				
				scm.spec.SpecHop.RevisitField.setText(
						String.valueOf(txModel.getSpectrumMask().get(o).
								getHoppingData().getRevisitPeriod()));
				
				if(txModel.getSpectrumMask().get(o).getHoppingData().getBandList()!=null){
					
					List<Band> bandList = txModel.getSpectrumMask().get(o).
							getHoppingData().getBandList().getBand();
					model = (DefaultTableModel) scm.spec.SpecHop.table3.getModel();
				  	model.setRowCount(0);
				  	
					for(int i=0; i<bandList.size(); i++){
							
							String serial = Integer.toString(i+1);
							String data1 = String.valueOf(bandList.get(i).getStartFrequency());
							String data2 = String.valueOf(bandList.get(i).getEndFrequency());
							
					  		Object[] rowData = {serial,data1,data2};
					  		model.addRow(rowData);
					  	}
					
					scm.spec.SpecHop.table3=new JTable(model);
					scm.spec.BandListBtn.setSelected(true);
					
				}else{
					
					List<Double> freqList = txModel.getSpectrumMask().get(o).
							getHoppingData().getFrequencyList().getReferenceFreq();
					model = (DefaultTableModel) scm.spec.SpecHop.table2.getModel();
				  	model.setRowCount(0);
				  	
					for(int i=0; i<freqList.size(); i++){
							
							String serial = Integer.toString(i+1);
							String data1 = String.valueOf(freqList.get(i));
							
					  		Object[] rowData = {serial,data1};
					  		model.addRow(rowData);
					  	}
					
					scm.spec.SpecHop.table2=new JTable(model);
					scm.spec.FreqListBtn.setSelected(true);
				}
				
			}else{
				scm.spec.no.setSelected(true);
			}
			
			// Setting all other constructs.
			setUnderlay(scm,txModel.getUnderlayMask(), "Tx");
			setReferencePower(scm,txModel.getReferencePower().get(o));
			
			//Set all the location data into the model
			for(int l=0;l<txModel.getScmLocation().size();l++)
			{
				setLocation(scm,txModel.getScmLocation().get(l),l);
			}
			scm.location = scm.control.locationArray.get(0);
			for(int i =0;i<txModel.getScmPowerMap().size();i++)
			{
				setPowerMap(scm,txModel.getScmPowerMap().get(i));
			}
			scm.power = scm.control.powerArray.get(0);
			for(int i =0;i<txModel.getScmPropagationMap().size();i++)
			{
				setPropMap(scm,txModel.getScmPropagationMap().get(i));
			}
			scm.prop = scm.control.propArray.get(0);
			for(int i =0;i<txModel.getScmSchedule().size();i++)
			{
				setSchedule(scm,txModel.getScmSchedule().get(i));
			}
			scm.schedule = scm.control.scheduleArray.get(0);
			
			//TODO Abhatt need inputs here as i can't see any UI in reciever modal to load data 
			
			
			for(int i =0;i<txModel.getIntermodulationMask().size();i++)
			{
				setIntermodulationMask(scm,txModel.getIntermodulationMask().get(i));
			}
			if (txModel.getIntermodulationMask().size() == 0) {
				setIntermodulationMask(scm, new IntermodulationMask());
			}
		
			scm.imc = scm.control.imcArray.get(0);
			
	}

	
}