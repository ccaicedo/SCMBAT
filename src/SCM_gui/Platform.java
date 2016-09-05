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
 * Platform.java
 * Creates the GUI panel for the Platform construct
*/


package SCM_gui;

import javax.swing.*;

import java.awt.*;

public class Platform {

JPanel panel = new JPanel();
public JLabel platformLabel = new JLabel("Platform name: ");
public JTextArea platformTextArea = new JTextArea();
public JButton b1 = new JButton("Save & Exit");
public JButton b2 = new JButton("Cancel");


	public JPanel getPanel(){
		
		// Panel for Confidence
        
		panel.setLayout(null);
		
        JLabel ConfText = new JLabel("This construct supports confidence values. If you need to specify a confidence");
        JLabel ConfText2 = new JLabel("value different than 1 for the values of this construct please press this button");
        JButton ConfBtn = new JButton("Define Confidence Values");
        
        Dimension ConfBtnSize = ConfBtn.getPreferredSize();
        Dimension ConfTextSize = ConfText2.getPreferredSize();
        
        ConfBtn.setBounds(25, 20, ConfBtnSize.width, ConfBtnSize.height);
        ConfText.setBounds(25, 20, ConfTextSize.width, ConfTextSize.height);
        ConfText2.setBounds(25, 40, ConfTextSize.width, ConfTextSize.height);
        
        panel.add(ConfBtn);
  
        // Platform Data
        
        Dimension platformLabelSize = platformLabel.getPreferredSize();
        platformLabel.setBounds(25, 80,
        		platformLabelSize.width, platformLabelSize.height);
		
        Dimension platformTextSize = platformTextArea.getPreferredSize();
        platformTextArea.setBounds(140, 80,
        		platformTextSize.width + 400, platformTextSize.height + 100);
        
        panel.add(platformLabel);
        panel.add(platformTextArea);

        // Button Alignment 
        
        Dimension butSize = b1.getPreferredSize();
        b1.setBounds(600, 80, butSize.width, butSize.height);
        b2.setBounds(600, 130, butSize.width, butSize.height);
        
        panel.add(b1);
        panel.add(b2);
        
        return panel;
		
	}
	
}
