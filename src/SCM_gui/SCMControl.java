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

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
	
	public class LocIndexListener implements FocusListener {
	    
	    public void focusGained(FocusEvent e) {
	      };
	      public void focusLost(FocusEvent e) {
	        if (!e.isTemporary()) {
	        	addLocIndexFunc();
	        }
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
    					
    					int propIndex = pane.indexOfTab("Propagation Map");
    					pane.removeTabAt(propIndex);
    					pane.insertTab("Propagation Map",null, propArray.get(currentIndex).getPanel(),null,propIndex);
    					
    					//To add the location indices in the new PropMap
    					addIndices(propArray.get(currentIndex));
    					
    					pane.setSelectedIndex(propIndex);
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
			
			int imIndex = pane.indexOfTab("Intermodulation Mask");
			pane.removeTabAt(imIndex);
			pane.insertTab("Intermodulation Mask",null, 
					imcArray.get(imcIndex).getPanel(),null,imIndex);
			pane.setSelectedIndex(imIndex);
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
			
    	case "location": 
    		/*
    		 * First check if the current location has location index before allowing the addition of new location
    		 */
    			String curLocIndexVal = locationArray.get(locationArray.size()-1).LocationField.getText();
    			if(curLocIndexVal.equals(""))
    			{
    				showMessageDialog(null, "Enter a value for location index");
    				break;
    			}
    			Location newLoc = new Location();
//    	newProp.b4.addActionListener(scm.exitAction);
    			newLoc.save.addActionListener(saveAction);
    			newLoc.NewMap.addActionListener(new createListener("location",pane));
    			newLoc.Next.addActionListener(new NextListener("location",pane));
    			newLoc.Previous.addActionListener(new PrevListener("location",pane));
    			locationArray.add(newLoc);
    			int locIndex = locationArray.size()-1;
    			locationArray.get(locIndex).index = locIndex;
    			tempLocation=locationArray.get(locIndex);
    			tempLocation.LocationField.addFocusListener(new LocIndexListener());
    			tempLocation.index = locIndex;
    		int tabIndex = pane.indexOfTab("Location");
			pane.removeTabAt(tabIndex);
			pane.insertTab("Location",null, 
					locationArray.get(locIndex).getPanel(),null,tabIndex);
			
			pane.setSelectedIndex(tabIndex);
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
			tempSchedule.index = schedIndex;
			
			
			int scIndex = pane.indexOfTab("Schedule");
			pane.removeTabAt(scIndex);
			pane.insertTab("Schedule",null, 
					scheduleArray.get(schedIndex).getPanel(),null,scIndex);
			
			//To add the location indices in the new PropMap
			addIndices(scheduleArray.get(schedIndex));
			
			pane.setSelectedIndex(scIndex);
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
    					int propIndex = pane.indexOfTab("Propagation Map");
    					 pane.removeTabAt(propIndex);
    					 
    					
    					 pane.insertTab("Propagation Map",null, propArray.get(index).PropPanel,null,propIndex);
    					 pane.setSelectedIndex(propIndex);
    					// PropMap prop = propArray.get(index);
    					// prop.comboBox.setSelectedItem(prop.currentSelectedItem);
    					 
    					 pane.repaint();
     					 pane.revalidate();
    					 tempPropMap = propArray.get(index);
    					 tempPropMap.index = index;
    				 }
    				 
    					 break;
    					 
    	case "imc": int imcIndex = tempIMC.index-1;
		 if(imcIndex>=0){
			 int imIndex = pane.indexOfTab("Intermodulation Mask");
			 pane.removeTabAt(imIndex);
			 pane.insertTab("Intermodulation Mask",null,
					 imcArray.get(imcIndex).mainPanel,null,imIndex);
			 pane.setSelectedIndex(imIndex);
			 pane.repaint();
			 pane.revalidate();
			 tempIMC = imcArray.get(imcIndex);
			 tempIMC.index = imcIndex;
					 
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
			 int tabIndex = pane.indexOfTab("Location");
			 pane.removeTabAt(tabIndex);
			 pane.insertTab("Location",null,
					 locationArray.get(locIndex).panel,null,tabIndex);
			// int tabIndex = pane.indexOfTab("Location");
			
			 pane.setSelectedIndex(tabIndex);
			 pane.repaint();
			 pane.revalidate();
			 tempLocation = locationArray.get(locIndex);
			 tempLocation.index = locIndex;
		 }
		 
		 break;
		 
    	case "schedule": int schedIndex = tempSchedule.index-1;
		 if(schedIndex>=0){
			 int scIndex = pane.indexOfTab("Schedule");
			 pane.removeTabAt(scIndex);
			 pane.insertTab("Schedule",null,
					 scheduleArray.get(schedIndex).panel,null,scIndex);
			 pane.setSelectedIndex(scIndex);
			 pane.repaint();
			 pane.revalidate();
			 tempSchedule = scheduleArray.get(schedIndex);
			 tempSchedule.index = schedIndex;
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
    					 int propIndex = pane.indexOfTab("Propagation Map");
    					 pane.removeTabAt(propIndex);
    					 PropMap prop = propArray.get(index);
    					 pane.insertTab("Propagation Map",null,
    							 prop.PropPanel,null,propIndex);
    					 
    					
    				//	 prop.comboBox.setSelectedItem(prop.currentSelectedItem);
    					 pane.setSelectedIndex(propIndex);
    					 pane.repaint();
     					 pane.revalidate();
    					 tempPropMap = propArray.get(index);
    					 tempPropMap.index = index;
    					
    				 }
    				 break;

    	case "imc": int IMCindex = tempIMC.index + 1;
		 if(IMCindex<imcArray.size()){
			 int imcIndex = pane.indexOfTab("Intermodulation Mask");
			 pane.removeTabAt(imcIndex);
			 pane.insertTab("Intermodulation Mask",null, 
					 imcArray.get(IMCindex).mainPanel,null,imcIndex);
			 pane.setSelectedIndex(imcIndex);
			 pane.repaint();
			 pane.revalidate();
			 tempIMC = imcArray.get(IMCindex);
			 tempIMC.index = IMCindex;
			
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
			 int tabIndex = pane.indexOfTab("Location");
			 pane.removeTabAt(tabIndex);
			 Location curLoc = locationArray.get(locIndex);
			 pane.insertTab("Location",null, 
					 curLoc.panel,null,tabIndex);
			
			// int tabIndex = pane.indexOfTab("Location");
			 pane.setSelectedIndex(tabIndex);
			 pane.repaint();
			 pane.revalidate();
			 tempLocation = locationArray.get(locIndex);
			 tempLocation.index = locIndex;
			
		 }
		 
		 break;
		 
    	case "schedule": int schedIndex = tempSchedule.index + 1;
		 if(schedIndex<scheduleArray.size()){
			 int tabIndex = pane.indexOfTab("Schedule");
			 pane.removeTabAt(tabIndex);
			 pane.insertTab("Schedule",null, 
					 scheduleArray.get(schedIndex).panel,null,tabIndex);
			 pane.setSelectedIndex(tabIndex);
			 pane.repaint();
			 pane.revalidate();
			 tempSchedule = scheduleArray.get(schedIndex);
			 tempSchedule.index = schedIndex;
		 }
		 
		 break;
		 
    	default: break;
    	
    	}
    }
   
    //Focus Listener for the Location Index field
    public void addLocIndexFunc(){
    	int locsize = locationArray.size();
    	PropMap prop;
    	PowerMap power;
    	Schedule schedule;
    	
    	
     	//Add the location Indices to respective Combo boxes
   		for(int i =0; i<locsize;i++)
    	{
   			Location loc = locationArray.get(i);
        	String locText = loc.LocationField.getText();
        	for(int x=0;x<powerArray.size();x++)
        	{
        		power = powerArray.get(x);
        		if(power.combomodel.getElementAt(i) != null)
        		{
        			power.combomodel.removeElementAt(i);
        		}
        		power.combomodel.insertElementAt(locText, i);
        		power.PowerPanel.repaint();
        		power.PowerPanel.revalidate();
        	}
        	for(int y=0;y<propArray.size();y++)
        	{
        		prop = propArray.get(y);
        		prop.NewMap.setEnabled(true);
        		if(prop.combomodel.getElementAt(i) != null)
        		{
        			prop.combomodel.removeElementAt(i);
            		
        		}
        		prop.combomodel.insertElementAt(locText, i);
        		prop.PropPanel.repaint();
        		prop.PropPanel.revalidate();
        		
        	}
        	for(int z=0;z<scheduleArray.size();z++)
        	{
        		schedule = scheduleArray.get(z);
        		schedule.NewSched.setEnabled(true);
        		if(schedule.combomodel.getElementAt(i) != null)
        		{
        			schedule.combomodel.removeElementAt(i);
            		
        		}
        		schedule.combomodel.insertElementAt(locText, i);
        		schedule.panel.repaint();
        		schedule.panel.revalidate();
        		
        	}
        			
    	}  		
    		
    	}
   public void addIndices(PropMap prop)
   {
	   int locsize = locationArray.size();
   
    	/*Add the location Indices to respective Combo boxes
    	 * Make the Add New Map enabled only if there are more than one location indices
    	 * 
    	 * */
  		for(int i =0; i<locsize;i++)
  		{
  			prop.NewMap.setEnabled(true);
  			Location loc = locationArray.get(i);
  			String locText = loc.LocationField.getText();
       
       		prop.combomodel.insertElementAt(locText, i);
       		prop.PropPanel.repaint();
       		prop.PropPanel.revalidate();     	
  		}  		
   		
   }
   public void addIndices(PowerMap power)
   {
	   int locsize = locationArray.size();
   
    	//Add the location Indices to respective Combo boxes
  		for(int i =0; i<locsize;i++)
  		{
  			Location loc = locationArray.get(i);
  			String locText = loc.LocationField.getText();
       
  			//power.NewMap.setEnabled(true);
       		power.combomodel.insertElementAt(locText, i);
       		power.PowerPanel.repaint();
       		power.PowerPanel.revalidate();     	
  		}  		
   		
   }
   public void addIndices(Schedule schedule)
   {
	   int locsize = locationArray.size();
   
    	//Add the location Indices to respective Combo boxes
  		for(int i =0; i<locsize;i++)
  		{
  			Location loc = locationArray.get(i);
  			String locText = loc.LocationField.getText();
       
  			schedule.NewSched.setEnabled(true);
  			schedule.combomodel.insertElementAt(locText, i);
  			schedule.panel.repaint();
  			schedule.panel.revalidate();     	
  		}  		
   		
   }
  /* private void addListeners(Location curLoc, JTabbedPane pane)
   {
	   	curLoc.save.addActionListener(saveAction);
	   	curLoc.Next.addActionListener(new NextListener("location",pane));
		// curLoc.exit.addActionListener(exitAction);
		 curLoc.NewMap.addActionListener(new createListener("location",pane));
		 curLoc.Previous.addActionListener(new PrevListener("location",pane));
		 curLoc.LocationField.addFocusListener(new LocIndexListener());		 
   }*/
   
}

