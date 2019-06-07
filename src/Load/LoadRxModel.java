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
 * LoadRxModel.java
 * Sets load operations for receiver models
*/

package Load;

import org.ieee.dyspansc._1900._5.scm.IntermodulationMask;
import org.ieee.dyspansc._1900._5.scm.RxModel;
import org.ieee.dyspansc._1900._5.scm.TxModel;

import SCM_gui.SCM_MainWindow;

public class LoadRxModel extends LoadGUI{
	
	//final Logger logger = Logger.getLogger(LoadRxModel.class);
	@Override
	public void setData(SCM_MainWindow scm, TxModel txModel) {
		
	}

	@Override
	public void setData(SCM_MainWindow scm, RxModel rxModel) {
	//	logger.info("This is a Receiver");
		System.out.println("This is a Receiver");
		
		int o=0;
		
		setUnderlay(scm,rxModel.getUnderlayMask().get(o), "Rx");
		setReferencePower(scm,rxModel.getReferencePower().get(o));
		for(int i =0;i<rxModel.getScmLocation().size();i++)
		{
			setLocation(scm,rxModel.getScmLocation().get(i),i);
		}
		scm.location = scm.control.locationArray.get(0);
		for(int i =0;i<rxModel.getScmPowerMap().size();i++)
		{
			setPowerMap(scm,rxModel.getScmPowerMap().get(i));
		}
		scm.power = scm.control.powerArray.get(0);
		
		for(int i =0;i<rxModel.getScmSchedule().size();i++)
		{
			setSchedule(scm,rxModel.getScmSchedule().get(i));
		}
		scm.schedule = scm.control.scheduleArray.get(0);
		for(int i =0;i<rxModel.getIntermodulationMask().size();i++)
		{
			
		}
		//TODO Abhatt need inputs here as i can't see any UI in reciever modal to load data 
		if (rxModel.getIntermodulationMask().size() == 0) {
			setIntermodulationMask(scm, new IntermodulationMask());
		}
		if (scm.control.imcArray.size() > 0) {
			scm.imc = scm.control.imcArray.get(0);
		}
		
		
	}	
}
