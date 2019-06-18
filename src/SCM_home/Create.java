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
 * Create.java
 * GUI panel to create a new Transmitter or Receiver Model
*/

package SCM_home;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import SCM_gui.SCM_MainWindow;

public class Create {
	
	
	public String device = "Default";
	public String SaveName = "NewFile";

	private JTextField ModelName = new JTextField();
	private JPanel panel = new JPanel();
	
	public JRadioButton rdbtnTransmitter = new JRadioButton("Transmitter");
    public JRadioButton rdbtnReceiver = new JRadioButton("Receiver");
	
	JButton Exit = new JButton("Exit");
	JButton Back = new JButton("Back");
	JButton Create = new JButton("Create");
	SCM_MainWindow BuildSCM;
	
    private String ModelText;

	
	public JPanel getPanel(){
		
		// Creating the panel for a 'Create Operation'
		
		panel.setBorder(new TitledBorder(null, "Create a Spectrum Consumption Model", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setLayout(null);
        
        Insets insetsFrame = panel.getInsets();
        
        JLabel lblName = new JLabel("Model Name");
		Dimension Fsize1 = lblName.getPreferredSize();
        lblName.setBounds(25 + insetsFrame.left, 30 + insetsFrame.top,
                 Fsize1.width, Fsize1.height);
        
        ModelName.setBounds(25, 70, 150, Fsize1.height + 5);
        
        JLabel lblName2 = new JLabel("Transmitter/Receiver?");
		Dimension Fsize2 = lblName2.getPreferredSize();
        lblName2.setBounds(25 + insetsFrame.left, 120 + insetsFrame.top,
                 Fsize2.width, Fsize2.height);

        // Setting operation buttons
        
        Dimension ExitSize = Exit.getPreferredSize();
        Exit.setBounds(450, 350, ExitSize.width, ExitSize.height);
        panel.add(Exit);
        
        Dimension CreateSize = Create.getPreferredSize();
        Create.setBounds(343, 350, CreateSize.width, CreateSize.height);
        
        Dimension BackSize = Back.getPreferredSize();
        Back.setBounds(250, 350, BackSize.width, BackSize.height);
        
        // Setting radio buttons for selecting Transmitter vs Receiver model.
        
        Dimension size5 = rdbtnReceiver.getPreferredSize();
        rdbtnReceiver.setBounds(150, 160,
                size5.width, size5.height);
        
        Dimension size6 = rdbtnTransmitter.getPreferredSize();
        rdbtnTransmitter.setBounds(25, 160,
                size6.width, size6.height);
        
        ButtonGroup group = new ButtonGroup();
		group.add(rdbtnReceiver);
		group.add(rdbtnTransmitter);
        
		rdbtnTransmitter.setSelected(true);	
		
		final JButton btn = new JButton ("But");
        
        Dimension btnSize = btn.getPreferredSize();
        btn.setBounds(100, 100, btnSize.width + 10, btnSize.height + 10);
        
//        rdbtnReceiver.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				BuildSCM.mode=false;
//			}
//        });
//        
//        rdbtnTransmitter.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				BuildSCM.mode=true;
//			}
//        });
        
        // Setting Create button operation
        
        Create.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		BuildSCM = null;
        		BuildSCM = new SCM_MainWindow();
        		
        		ModelText=ModelName.getText().replaceAll(" ", "");
        	    
        	    if(rdbtnTransmitter.isSelected()==true){
        	    	BuildSCM.mode=true;
        	    }else{
        	    	if(rdbtnReceiver.isSelected()==true){
        	    		BuildSCM.mode=false;
        	    	}else{
        	    		System.out.println("Please make a selection between Transmitter or Receiver");
        	    	}
        	    }
        	    
           		BuildSCM.SaveName=ModelText;
        	    BuildSCM.design(-1, BuildSCM);
        	    
        	}
        });
        
        panel.add(lblName2);
        panel.add(lblName);
        panel.add(ModelName);
        panel.add(Exit);
        panel.add(rdbtnReceiver);
        panel.add(rdbtnTransmitter);
        panel.add(Create);
        panel.add(Back);
        
        return panel;
        
	}	
		
}
