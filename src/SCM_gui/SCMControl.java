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
 * SCMControl.java
 * Creates common operations for all constructs such as:
 * 1. Save
 * 2. Exit
 * 3. Add a new construct of the same type
 * 4. Switch to next construct of the same type
 * 5. Switch to previous construct of the same type.
*/


package SCM_gui;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class SCMControl {

	
	public class createListener implements ActionListener {
	    private String classString;
	    private JTabbedPane tabbedPane;

	    public createListener(String string, JTabbedPane pane) {
	        this.classString = string;
	        this.tabbedPane=pane;
	    }

	    public void actionPerformed(ActionEvent e) {
			getCreateAction(classString,tabbedPane);			
		}
	}
	
	public class NextListener implements ActionListener {
	    private String classString;
	    private JTabbedPane tabbedPane;

	    public NextListener(String string, JTabbedPane pane) {
	        this.classString = string;
	        this.tabbedPane=pane;
	    }

	    public void actionPerformed(ActionEvent e) {
			nextTabFunc(classString,tabbedPane);			
		}
	}
	
	public class PrevListener implements ActionListener {
	    private String classString;
	    private JTabbedPane tabbedPane;

	    public PrevListener(String string, JTabbedPane pane) {
	        this.classString = string;
	        this.tabbedPane=pane;
	    }

	    public void actionPerformed(ActionEvent e) {
			prevTabFunc(classString,tabbedPane);			
		}
	}
	
	public PropMap tempPropMap = new PropMap();
	public IMA tempIMA = new IMA();
	public IMC tempIMC = new IMC();
	public Schedule tempSchedule = new Schedule();
	public Location tempLocation = new Location();
	
	public Save file = new Save();
    public String SaveName = null;
	
    // Creating an array of constructs in case user has multiple constructs of the same type.
    public ArrayList<UnderlayMask> underlayArray = new ArrayList<UnderlayMask>();
    public ArrayList<SpecMask> specArray = new ArrayList<SpecMask>();
    public ArrayList<PowerMap> powerArray = new ArrayList<PowerMap>();
    public ArrayList<PropMap> propArray = new ArrayList<PropMap>();
    public ArrayList<IMA> imaArray = new ArrayList<IMA>();
    public ArrayList<IMC> imcArray = new ArrayList<IMC>();
    public ArrayList<Schedule> scheduleArray = new ArrayList<Schedule>();
    public ArrayList<Platform> platformArray = new ArrayList<Platform>();
    public ArrayList<Location> locationArray = new ArrayList<Location>();
	public JTextField TotPower = new JTextField();
	
	public JPanel propCard = new JPanel(new CardLayout());
	
	public void setSave(Save save){
		file = save;
	}
	
	public void getRefPower(JTextField field){
		
		TotPower = field;
	}
    
    public void getName(String string){
    	SaveName = string;
    }
    
    // Setting the operation for save.
	public ActionListener saveAction = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
		
			file.SaveFrame(specArray,
					underlayArray,
					powerArray, 
					propArray, 
					imcArray,
					imaArray,
					locationArray,
					platformArray,
					scheduleArray,
					TotPower,
					SaveName);
		}
    };

    public void initializeCard(String type, JPanel panel){
    	
    	switch(type){
    	
    	case "prop": propCard.add(panel, "0");
    	
    	
    	}
    	
    }
    
    // Operation for creating a new construct of the same type:
    public void getCreateAction(String string,JTabbedPane pane){
    	
    	switch(string){
    	
    	case "prop": 	PropMap newProp = new PropMap();
    				//	newProp.b4.addActionListener(scm.exitAction);
    					newProp.b3.addActionListener(saveAction);
    					newProp.NewMap.addActionListener(new createListener("prop",pane));
    					newProp.Next.addActionListener(new NextListener("prop",pane));
    					newProp.Previous.addActionListener(new PrevListener("prop",pane));
    					propArray.add(newProp);
    					int currentIndex = propArray.size()-1;
    					propArray.get(currentIndex).index = currentIndex;
    					tempPropMap=propArray.get(currentIndex);
    					
    					pane.removeTabAt(3);
    					pane.insertTab("Propagation Map",null, propArray.get(currentIndex).getPanel(),null,3);
    					pane.setSelectedIndex(3);
    					pane.repaint();
    					pane.revalidate();
    					
    					break;
    	
    	case "imc": 	IMC newIMC = new IMC();
		//	newProp.b4.addActionListener(scm.exitAction);
			newIMC.b3.addActionListener(saveAction);
			newIMC.NewMap.addActionListener(new createListener("imc",pane));
			newIMC.Next.addActionListener(new NextListener("imc",pane));
			newIMC.Previous.addActionListener(new PrevListener("imc",pane));
			imcArray.add(newIMC);
			int imcIndex = imcArray.size()-1;
			imcArray.get(imcIndex).index = imcIndex;
			tempIMC=imcArray.get(imcIndex);
			
			pane.removeTabAt(4);
			pane.insertTab("IMC Mask",null, 
					imcArray.get(imcIndex).getPanel(),null,4);
			pane.setSelectedIndex(4);
			pane.repaint();
			pane.revalidate();
			
			break;
    					
    	case "ima": 	IMA newIMA = new IMA();
		//	newProp.b4.addActionListener(scm.exitAction);
			newIMA.b3.addActionListener(saveAction);
			newIMA.NewMap.addActionListener(new createListener("ima",pane));
			newIMA.Next.addActionListener(new NextListener("ima",pane));
			newIMA.Previous.addActionListener(new PrevListener("ima",pane));
			imaArray.add(newIMA);
			int imaIndex = imaArray.size()-1;
			imaArray.get(imaIndex).index = imaIndex;
			tempIMA=imaArray.get(imaIndex);
			
			pane.removeTabAt(5);
			pane.insertTab("IMA Mask",null, 
					imaArray.get(imaIndex).getPanel(),null,5);
			pane.setSelectedIndex(5);
			pane.repaint();
			pane.revalidate();
			
			break;
			
    	case "location": 	Location newLoc = new Location();
		//	newProp.b4.addActionListener(scm.exitAction);
			newLoc.save.addActionListener(saveAction);
			newLoc.NewMap.addActionListener(new createListener("location",pane));
			newLoc.Next.addActionListener(new NextListener("location",pane));
			newLoc.Previous.addActionListener(new PrevListener("location",pane));
			locationArray.add(newLoc);
			int locIndex = locationArray.size()-1;
			locationArray.get(locIndex).index = locIndex;
			tempLocation=locationArray.get(locIndex);
			
			pane.removeTabAt(7);
			pane.insertTab("Location",null, 
					locationArray.get(locIndex).getPanel(),null,7);
			pane.setSelectedIndex(7);
			pane.repaint();
			pane.revalidate();
			
			break;
			
    	case "schedule": 	Schedule newSched = new Schedule();
		//	newProp.b4.addActionListener(scm.exitAction);
			newSched.b3.addActionListener(saveAction);
			newSched.NewSched.addActionListener(new createListener("schedule",pane));
			newSched.Next.addActionListener(new NextListener("schedule",pane));
			newSched.Previous.addActionListener(new PrevListener("schedule",pane));
			scheduleArray.add(newSched);
			int schedIndex = scheduleArray.size()-1;
			scheduleArray.get(schedIndex).index = schedIndex;
			tempSchedule=scheduleArray.get(schedIndex);
			
			pane.removeTabAt(8);
			pane.insertTab("Schedule",null, 
					scheduleArray.get(schedIndex).getPanel(),null,8);
			pane.setSelectedIndex(8);
			pane.repaint();
			pane.revalidate();
			
			break;
    	
    	default: break;
    	
    	}
    }
    
    // Operation for switching to the previous construct of the same type.
    public void prevTabFunc(String string, JTabbedPane pane){
    	
    	switch(string){
    	
    	case "prop": int index = tempPropMap.index-1;
    				 if(index>=0){
    					 pane.removeTabAt(3);
    					 pane.insertTab("Propagation Map",null, propArray.get(index).PropPanel,null,3);
    					 pane.setSelectedIndex(3);
    					 pane.repaint();
     					 pane.revalidate();
    					 tempPropMap = propArray.get(index);
    				 }
    				 
    					 break;
    					 
    	case "imc": int imcIndex = tempIMC.index-1;
		 if(imcIndex>=0){
			 pane.removeTabAt(4);
			 pane.insertTab("IMC Mask",null,
					 imcArray.get(imcIndex).panel,null,4);
			 pane.setSelectedIndex(4);
			 pane.repaint();
			 pane.revalidate();
			 tempIMC = imcArray.get(imcIndex);
		 }
		 
			 break;
    	
    	case "ima": int imaIndex = tempIMA.index-1;
		 if(imaIndex>=0){
			 pane.removeTabAt(5);
			 pane.insertTab("IMA Mask",null,
					 imaArray.get(imaIndex).panel,null,5);
			 pane.setSelectedIndex(5);
			 pane.repaint();
			 pane.revalidate();
			 tempIMA = imaArray.get(imaIndex);
		 }
		 
			 break;
		
    	case "location": int locIndex = tempLocation.index-1;
		 if(locIndex>=0){
			 pane.removeTabAt(7);
			 pane.insertTab("Location",null,
					 locationArray.get(locIndex).panel,null,7);
			 pane.setSelectedIndex(7);
			 pane.repaint();
			 pane.revalidate();
			 tempLocation = locationArray.get(locIndex);
		 }
		 
		 break;
		 
    	case "schedule": int schedIndex = tempSchedule.index-1;
		 if(schedIndex>=0){
			 pane.removeTabAt(8);
			 pane.insertTab("Schedule",null,
					 scheduleArray.get(schedIndex).panel,null,8);
			 pane.setSelectedIndex(8);
			 pane.repaint();
			 pane.revalidate();
			 tempSchedule = scheduleArray.get(schedIndex);
		 }
		 
			 break;
		 
			 
    	default: 	break;
    				 }
    	
    	}
    
    // Operation to switch to the next construct of the same type;
    public void nextTabFunc(String string, JTabbedPane pane){
    	
    	switch(string){
    	
    	case "prop": int index = tempPropMap.index + 1;
    				 if(index<propArray.size()){
    					 pane.removeTabAt(3);
    					 pane.insertTab("Propagation Map",null,
    							 propArray.get(index).PropPanel,null,3);
    					 pane.setSelectedIndex(3);
    					 pane.repaint();
     					 pane.revalidate();
    					 tempPropMap = propArray.get(index);
    					
    				 }
    				 break;

    	case "imc": int IMCindex = tempIMC.index + 1;
		 if(IMCindex<imcArray.size()){
			 pane.removeTabAt(4);
			 pane.insertTab("IMC Mask",null, 
					 imcArray.get(IMCindex).panel,null,4);
			 pane.setSelectedIndex(4);
			 pane.repaint();
			 pane.revalidate();
			 tempIMC = imcArray.get(IMCindex);
			
		 }
		 break;
    	
    	case "ima": int IMAindex = tempIMA.index + 1;
		 if(IMAindex<imaArray.size()){
			 pane.removeTabAt(5);
			 pane.insertTab("IMA Mask",null, 
					 imaArray.get(IMAindex).panel,null,5);
			 pane.setSelectedIndex(5);
			 pane.repaint();
			 pane.revalidate();
			 tempIMA = imaArray.get(IMAindex);
			
		 }
		 
		 break;
		
    	case "location": int locIndex = tempLocation.index + 1;
		 if(locIndex<locationArray.size()){
			 pane.removeTabAt(7);
			 pane.insertTab("Location",null, 
					 locationArray.get(locIndex).panel,null,7);
			 pane.setSelectedIndex(7);
			 pane.repaint();
			 pane.revalidate();
			 tempLocation = locationArray.get(locIndex);
			
		 }
		 
		 break;
		 
    	case "schedule": int schedIndex = tempSchedule.index + 1;
		 if(schedIndex<scheduleArray.size()){
			 pane.removeTabAt(8);
			 pane.insertTab("Schedule",null, 
					 scheduleArray.get(schedIndex).panel,null,8);
			 pane.setSelectedIndex(8);
			 pane.repaint();
			 pane.revalidate();
			 tempSchedule = scheduleArray.get(schedIndex);
			
		 }
		 
		 break;
		 
    	default: break;
    	
    	}
    }
   
   
}

