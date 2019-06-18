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
 *  Save.java
 *  Defines the functions to save information entered in the GUI 
*/

package SCM_gui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Execute.Warn;
import SaveXML.Save_XML;

public class Save extends Save_XML{
	
	
	public String device = "Default";
	public String purpose = "CONSUMPTION";
	public String SaveName = "NewFile";
	private JFrame Newframe;	
	public JTextField SField;
	public SpecMask_Hop SpecHop = new SpecMask_Hop();
		
	
	public void SaveTransmitter(){
		device="Tx";	
	}
	
	public void SaveReceiver(){
		device="Rx";
	}
	
	// Passing all the information from the GUI to save.
	
	public void SaveFrame(final ArrayList<SpecMask> specArray,
			final ArrayList<UnderlayMask> underlayArray,
			final ArrayList<PowerMap> powerArray,
			final ArrayList<PropMap> propArray,
			final ArrayList<IMC> imcArray,
			final ArrayList<IMA> imaArray,
			final ArrayList<Location> locationArray,
			final ArrayList<Platform> platformArray,
			final ArrayList<Schedule> scheduleArray,
			final JTextField TotPower,
			final String SaveName,
			SCM_MainWindow scmMainObj){
		
		Newframe = new JFrame("Save");
        Newframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Newframe.setVisible(true);
        Newframe.setLayout(null);
        
        Insets insetsFrame = Newframe.getInsets();
        Newframe.setSize(500 + insetsFrame.left + insetsFrame.right,
                      100 + insetsFrame.top + insetsFrame.bottom);
        
        JLabel lblName = new JLabel("Do you want to save Model " + SaveName + " as device " + device);
		Dimension Fsize1 = lblName.getPreferredSize();
        lblName.setBounds(25 + insetsFrame.left, 3 + insetsFrame.top,
                 Fsize1.width, Fsize1.height);
        
        JButton SButton = new JButton("Save");
        Dimension ButtonSize = SButton.getPreferredSize();
        SButton.setBounds(300 + insetsFrame.left, 20 + insetsFrame.top,
                ButtonSize.width, ButtonSize.height);       
        
        SField = new JTextField();
        SField.setBounds(25 + insetsFrame.left, 20 + insetsFrame.top,
                ButtonSize.width + 150, ButtonSize.height);
        
        Newframe.add(lblName);
        Newframe.add(SButton);
        
        // Save Operations
        
        SButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				SpecMask spec = specArray.get(0);
		        UnderlayMask underlay = underlayArray.get(0);
		        PowerMap power = powerArray.get(0);
		        /*for(int i=0;i<propArray.size();i++)
		        {
		        	 PropMap prop = propArray.get(i);
		        	 addPropMap(prop);
		        }
		        PropMap prop = propArray.get(0);
		        */
		      //  IMC imc = imcArray.get(0); /*IMC not implemented in the current version*/
		        //IMA ima = imaArray.get(0); /*IMA not implemented in the current version*/
		     //   Location location = locationArray.get(0);
		        // Platform platform = platformArray.get(0); *platform not implemented in the current version
		     //   Schedule schedule = scheduleArray.get(0);
				
		        // Saving data to XML file based on the XML Schema 
				createXML(SaveName, device, purpose);
				// Saving Transmitter data and Receiver data based on the device type
				if(device.equals("Tx")){
				
			    /* Saving Reference Power,
			     *  Spectrum Mask, 
			     *  Underlay Mask 
			     *  and Propagation Map for Transmitter
			     */
				addReferencePower(TotPower, device);				
				addSpec(spec); 
				addUnderlay(spec,device);				
				for(int i=0;i<propArray.size();i++)
		        {
		        	 PropMap prop = propArray.get(i);
		        	 addPropMap(prop, device);
		        }
				for(int i=0;i<imcArray.size();i++)
				{
					addIMC(imcArray.get(i));	
				}
				
				
				}else{
					if(device.equals("Rx")){
				/* Saving Reference Power,
				*  Underlay Mask 
				*  and Propagation Map for Transmitter
				*/
				addUnderlay(underlay, device);
				addReferencePower(TotPower, device);
				for(int i=0;i<propArray.size();i++)
		        {
		        	 PropMap prop = propArray.get(i);
		        	 addPropMap(prop, device);
		        }
				
					}
				}
				
				/* Saving Power Map, 
				 * Location 
				 * and Schedule Constructs
				 */
				addPowerMap(power, device);
				for(int i=0;i<locationArray.size();i++)
		        {
		        	 Location loc = locationArray.get(i);
		        	 addLocation(loc,device);
		        }
				for(int i=0;i<scheduleArray.size();i++)
		        {
		        	 Schedule sched = scheduleArray.get(i);
		        	 addTime(sched,device);
		        }
				
				
				if(warningFlag)
				{
					new Warn().showWarnings("Warnings",warningMessage, scmMainObj);
					//Setting it back to false
					warningFlag = false;
					
					//Resetting the warning message
					warningMessage = "\n";
				}
				
				
				// Conclude saving all construct information and saving the XML file.
				concludeXML(SaveName, device); 
				
				// Moving the XML file to the Models directory.
				Process p;
				Process p2;
				try {
				//	String Command1 = "mkdir Models/"+SaveName;
					//Create the Directory Models only if does not exists
					String Command1 = "mkdir -p Models";
					String Command2 ="";
					if(SaveName.endsWith(".xml"))
					{
						 Command2 = "mv -f "
									+SaveName
									+" Models";
					}
					else
					{
						 Command2 = "mv -f "
									+SaveName+".xml "
									+"Models";
					}
					
					
					p = Runtime.getRuntime().exec(Command1);
					p.waitFor();
					
					p2 = Runtime.getRuntime().exec(Command2);
			        p2.waitFor();
			        
			        
				} catch (Exception e) {
			        e.printStackTrace();
			    }
				Newframe.dispatchEvent(new WindowEvent(Newframe, WindowEvent.WINDOW_CLOSING));
				Newframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
				//close the main window as well
				if(SCM_MainWindow.saveAndExit) {
					scmMainObj.exitWindow();
				}
			}
		});
        
	}
	
}
