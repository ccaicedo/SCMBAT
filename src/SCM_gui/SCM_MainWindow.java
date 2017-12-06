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
 * SCM_MainWindow.java
 * Sets up and creates main GUI window and tabs for the SCM constructs
*/

package SCM_gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

//import org.apache.log4j.Logger;


public class SCM_MainWindow {

   
	
	public JTextField SaveField;
	public JTextField textField_1_conf;
	public JTextField minPSFDfield = new JTextField();
	public JTextField polNameField = new JTextField();
	public JTextField indexField = new JTextField();
	public JTextField paramField = new JTextField();
	
	public Save file;

	public String SaveName;
	public String device;
	
	//private String RefFreq;
	public JTextField RefPowerField = new JTextField();
    
	
	public Boolean mode = true;	
	public Action Tx_e;
		
    private JPanel panel2; 
    private JPanel UnderlayPanel;
    private String MaskType = "Spectrum Mask";
    public JPanel propCard = new JPanel(new CardLayout());
    
    public SpecMask spec = new SpecMask();
    public UnderlayMask underlay = new UnderlayMask();
	public PowerMap power = new PowerMap();
	public PropMap prop = new PropMap();
	public IMA ima = new IMA();
	public IMC imc = new IMC();
	public Schedule schedule = new Schedule();
	public Location location = new Location();
	public Platform platform = new Platform();
    
	public SCMControl control = new SCMControl();
	
	//Maintain a static variable to hold the tabbedPane
	public  JTabbedPane tabbedPane = new JTabbedPane();  
	
	
	//OpenedModels HahhMap
	HashMap<String, Boolean> openedModels = new HashMap<String, Boolean>();
	final JFrame frame = new JFrame();
	
	public SCM_MainWindow()
	{
		
	}
	public SCM_MainWindow(HashMap<String, Boolean> openedModels)
	{
		this.openedModels = openedModels;
	}
	
    public void design(int Index) {
    	frame.setTitle("Spectrum Consumption Model Builder - "+SaveName);
    	
    	frame.addWindowListener(new WindowAdapter() {
    		   public void windowClosing(WindowEvent evt) {
    			   //If the create operation is trying to open the window
    			   if(Index!=-1)
    			   {
    	    		     openedModels.put("Current", false);  
    			   }
    		   }
    		  });
    	final ActionListener exitAction = new ActionListener(){
    		public void actionPerformed(ActionEvent e) {
    			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    			
    		}        	
        }; 
    	
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        final Insets insets = frame.getInsets();
        frame.setSize(1200 + insets.left + insets.right,
                      900 + insets.top + insets.bottom);
        frame.setVisible(true);
    	
       // final JTabbedPane tabbedPane = new JTabbedPane();  
        
        
        // Creating Tabs for various SCM constructs
        
        final JPanel panel = new JPanel();
        panel.setLayout(null);
        
        if(tabbedPane.indexOfTab("Reference Power")==-1)
        tabbedPane.addTab("Reference Power", panel);
        
        panel2 = spec.getPanel();
        
        UnderlayPanel = underlay.getPanel();
        
        
        
        JPanel panel3 = power.getPanel();
        if(tabbedPane.indexOfTab("Power Map")==-1)
        tabbedPane.addTab("Power Map", panel3);
        
        final JPanel panel4 = prop.getPanel();
        control.initializeCard("prop", panel4);
        
        //Changing the Panel title to Intermodulation  Mask from IMC
        JPanel panel5 = imc.getPanel();
        if(tabbedPane.indexOfTab("Intermodulation Mask")==-1)
        tabbedPane.addTab("Intermodulation Mask",panel5);
       /******************************************************/
        
        
       /* JPanel panel6 = ima.getPanel();
        tabbedPane.addTab("IMA Mask", panel6);
        
        
        int tabIndex = tabbedPane.indexOfTab("IMA Mask");

      //Only if the IMA is enabled allow the edit and save  
        if(imc.IMAYes.isSelected())
        {
        	tabbedPane.setSelectedIndex(tabIndex);
        	tabbedPane.setEnabledAt(tabIndex,true);
			
        }
        else
        {
        	tabbedPane.setEnabledAt(tabIndex,false);
        }
        */
        JPanel panel7 = platform.getPanel();
        if(tabbedPane.indexOfTab("Platform")==-1)
        tabbedPane.addTab("Platform",panel7);
        
        JPanel panel8 = location.getPanel();
        if(tabbedPane.indexOfTab("Location")==-1)
        tabbedPane.add("Location", panel8);
        
        JPanel panel9 = schedule.getPanel();
        if(tabbedPane.indexOfTab("Schedule")==-1)
        tabbedPane.add("Schedule", panel9);
        
        frame.getContentPane().add(tabbedPane);
      
        
        //Content for the home page
        
        JButton Save = new JButton("Save Data");
        JButton Exit = new JButton("Exit");
              
        Exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			}
		});
        
        // GUI Font
        
        Font font = new Font("Arial", Font.PLAIN, 12);
		
        /* Defining other Confidence, 
         * Reference Power,
         * on the Home Page
         */

        JLabel ConfText = new JLabel("This construct supports confidence values. If you need to specify a confidence");
        JLabel ConfText2 = new JLabel("value different than 1 for the values of this construct please press this button");
        JButton ConfBtn = new JButton("Define Confidence Values");
        
        Dimension ConfBtnSize = ConfBtn.getPreferredSize();
        Dimension ConfTextSize = ConfText2.getPreferredSize();
        
        ConfBtn.setBounds(25, 20, ConfBtnSize.width, ConfBtnSize.height);
        ConfText.setBounds(25, 20, ConfTextSize.width, ConfTextSize.height);
        ConfText2.setBounds(25, 40, ConfTextSize.width, ConfTextSize.height);
        
        panel.add(ConfBtn);
        
        JLabel RefPowerLabel = new JLabel("Reference Power: ");
        JLabel dB = new JLabel("dBW");
        dB.setFont(font);
        
        Dimension LabelSize = RefPowerLabel.getPreferredSize();
        RefPowerLabel.setBounds(25, 80, LabelSize.width, LabelSize.height);
        
        Dimension dBSize = dB.getPreferredSize();
        dB.setBounds(240, 80, dBSize.width, dBSize.height);
        
        Dimension FieldSize = RefPowerField.getPreferredSize();
        RefPowerField.setBounds(170, 78, FieldSize.width+30, FieldSize.height);
        
        panel.add(RefPowerField);
        panel.add(RefPowerLabel);
        panel.add(dB);
        
        //Default operations and radio button operations
        if(tabbedPane.indexOfTab("Spectrum Mask")==-1)
        tabbedPane.insertTab("Spectrum Mask",null , panel2, null, 1);
        
        if(tabbedPane.indexOfTab("Underlay Mask")==-1)
        tabbedPane.insertTab("Underlay Mask",null , UnderlayPanel, null, 2);
		
        // setting up Propagation Map for Transmitter option
        if(tabbedPane.indexOfTab("Propagation Map")==-1)
		tabbedPane.insertTab("Propagation Map",null, panel4,null,3);// Setting up the Propagation Map Tab
        

				if(mode==true){
				
				control.file.SaveTransmitter();
				
				JLabel minPSFD = new JLabel("Minimum PSFD");
				JLabel minPSFD2 = new JLabel ("Minimum PSFD (dBW/Hz/m2)");
				minPSFD2.setFont(font);
				
				Dimension minLabelSize = minPSFD.getPreferredSize();
				Dimension minLabel2Size = minPSFD2.getPreferredSize();
				Dimension minFieldSize = minPSFDfield.getPreferredSize();
				
				minPSFD.setBounds(25,200,minLabelSize.width,minLabelSize.height);
				minPSFD2.setBounds(25, 230, minLabel2Size.width, minLabel2Size.height);
				minPSFDfield.setBounds(250, 225, minFieldSize.width + 50, minFieldSize.height);
				
				panel.add(minPSFD2);
				panel.add(minPSFD);
				panel.add(minPSFDfield);
				
				if(tabbedPane.indexOfTab("Propagation Map")==-1)
				tabbedPane.insertTab("Propagation Map",null, panel4,null,3);// Setting up the Propagation Map Tab			   
				
				}
				/* Deleting Propagation Map, 
				 * Underlay Mask, 
				 * IMA Mask and 
				 * IMC Mask for Receiver option.
				 */
				if(mode==false){
					
				control.file.SaveReceiver();
				tabbedPane.removeTabAt(1);
				MaskType = "Underlay Mask";
				if(tabbedPane.indexOfTab("Underlay Mask")==-1)
				tabbedPane.insertTab(MaskType,null , UnderlayPanel, null, 1);				
				
				tabbedPane.removeTabAt(tabbedPane.indexOfTab("Propagation Map"));
				tabbedPane.removeTabAt(tabbedPane.indexOfTab("Intermodulation Mask"));
				if(tabbedPane.indexOfTab("IMA")!=-1)
				tabbedPane.removeTabAt(tabbedPane.indexOfTab("IMA"));
				 
			}
		
		    /* Setting up Policy and Protocol 
		     * buttons and fields.
		     */
			JLabel polLabel0 = new JLabel("Do you want to include 'Protocol or Policy': ");
			final JLabel polLabel = new JLabel("Protocol or Policy name:");
			final JLabel indLabel = new JLabel("Index value: ");
			final JLabel paramLabel = new JLabel("Parameters: ");
			JRadioButton polNo = new JRadioButton("No");
			JRadioButton polYes = new JRadioButton("Yes");
			
			polLabel.setFont(font);
			indLabel.setFont(font);
			paramLabel.setFont(font);
			
			Dimension polNoSize = polNo.getPreferredSize();
			Dimension polYesSize = polYes.getPreferredSize();
			Dimension polLabel0Size = polLabel0.getPreferredSize();
			Dimension polLabelSize = polLabel.getPreferredSize();
			Dimension indLabelSize = indLabel.getPreferredSize();
			Dimension paramLabelSize = paramLabel.getPreferredSize();
			Dimension polFieldSize = polNameField.getPreferredSize();
			Dimension indFieldSize = indexField.getPreferredSize();
			Dimension paramFieldSize = paramField.getPreferredSize();
			
			polLabel0.setBounds(25, 300, polLabel0Size.width, polLabel0Size.height);
			polLabel.setBounds(25, 350,polLabelSize. width, polLabelSize.height);
			indLabel.setBounds(25, 380, indLabelSize.width, indLabelSize.height);
			paramLabel.setBounds(25, 410, paramLabelSize.width, paramLabelSize.height);
			polNameField.setBounds(225, 345, polFieldSize.width + 50, polFieldSize.height);
			indexField.setBounds(225, 375, indFieldSize.width + 50, indFieldSize.height);
			paramField.setBounds(225, 405, paramFieldSize.width + 50, paramFieldSize.height);
			polNo.setBounds(400, 295, polNoSize.width, polNoSize.height);
			polYes.setBounds(350, 295, polYesSize.width, polYesSize.height);
		
			ButtonGroup polGroup = new ButtonGroup();
			polGroup.add(polYes);
			polGroup.add(polNo);
			polNo.setSelected(true);
			
			panel.add(polLabel0);
			panel.add(polYes);
			panel.add(polNo);
			
			// Setting up radio button operation for policy and protocol
			polYes.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					panel.add(polLabel);
					panel.add(indLabel);
					panel.add(paramLabel);
					panel.add(polNameField);
					panel.add(indexField);
					panel.add(paramField);
					
					panel.revalidate();
					panel.repaint();
				}
			});
			
			polNo.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
					panel.remove(polLabel);
					panel.remove(indLabel);
					panel.remove(paramLabel);
					panel.remove(polNameField);
					panel.remove(indexField);
					panel.remove(paramField);
					
					panel.revalidate();
					panel.repaint();
				}
			});
			
		// Placing the buttons
        
        Dimension size2 = Save.getPreferredSize();
        Save.setBounds(300, 80,
                size2.width, size2.height);
        Exit.setBounds(300 , 130 ,
        		size2.width, size2.height);
        
        panel.add(Save);
        panel.add(Exit);

        // Adding the constructs to the information array. 
        
        control.specArray.add(spec);
        control.underlayArray.add(underlay);
        control.platformArray.add(platform);
        control.powerArray.add(power);
        control.propArray.add(prop);
        control.imcArray.add(imc);
        control.imaArray.add(ima);
        control.locationArray.add(location);
        control.scheduleArray.add(schedule);

        // Save Operations
        
        control.getRefPower(RefPowerField);
        control.getName(SaveName);
        
        Save.addActionListener(control.saveAction);
        spec.b3.addActionListener(control.saveAction);
        underlay.b3.addActionListener(control.saveAction);
        power.b3.addActionListener(control.saveAction);        
        prop.b3.addActionListener(control.saveAction);        
        imc.b3.addActionListener(control.saveAction);  
        imc.imab3.addActionListener(control.saveAction);
    //    ima.b3.addActionListener(control.saveAction);
        location.save.addActionListener(control.saveAction);
        platform.b1.addActionListener(control.saveAction);
        schedule.b1.addActionListener(control.saveAction);
        
        //Exit Operations
        
        spec.b4.addActionListener(exitAction);        
        underlay.b4.addActionListener(exitAction);
        power.b4.addActionListener(exitAction);
        prop.b4.addActionListener(exitAction);
        imc.b4.addActionListener(exitAction);
        imc.imab4.addActionListener(exitAction);
       // ima.b4.addActionListener(exitAction);
        location.exit.addActionListener(exitAction);
        platform.b2.addActionListener(exitAction);
        schedule.b3.addActionListener(exitAction);
        
        //All create new mask Operations
        
        prop.NewMap.addActionListener(control.new createListener("prop",tabbedPane));
        
        imc.NewMap.addActionListener(control.new createListener("imc",tabbedPane));
        //ima.NewMap.addActionListener(control.new createListener("ima",tabbedPane));
        
        location.NewMap.addActionListener(control.new createListener("location",tabbedPane));
        schedule.NewSched.addActionListener(control.new createListener("schedule",tabbedPane));
        
        prop.Next.addActionListener(control.new NextListener("prop",tabbedPane));
        imc.Next.addActionListener(control.new NextListener("imc",tabbedPane));
        imc.imaNext.addActionListener(control.new NextListener("ima",tabbedPane));
      //  ima.Next.addActionListener(control.new NextListener("ima",tabbedPane));
        location.Next.addActionListener(control.new NextListener("location",tabbedPane));
        schedule.Next.addActionListener(control.new NextListener("schedule",tabbedPane));
        
        prop.Previous.addActionListener(control.new PrevListener("prop",tabbedPane));
        imc.Previous.addActionListener(control.new PrevListener("imc",tabbedPane));
        imc.imaPrevious.addActionListener(control.new PrevListener("ima",tabbedPane));
       // ima.Previous.addActionListener(control.new PrevListener("ima",tabbedPane));
        location.Previous.addActionListener(control.new PrevListener("location",tabbedPane));
        schedule.Previous.addActionListener(control.new PrevListener("schedule",tabbedPane));
        
    }
    
    public JTabbedPane getTabbedPane()
    {
    	return tabbedPane;
    }
   
}
