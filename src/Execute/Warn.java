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
 * Warn.java
 * Builds a warning window with a standard template for heading and message.
 * This window is called every time there is an error and or when a warning
 * need to be issued to the user
*/

package Execute;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import SCM_gui.SCM_MainWindow;

public class Warn {

	private JFrame Newframe;

	public void setWarn(String title, String msg){
	
		Newframe = new JFrame(title);
        Newframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Newframe.setVisible(true);
        Newframe.setLayout(null);
        
        Insets insetsFrame = Newframe.getInsets();
        Newframe.setSize(500 + insetsFrame.left + insetsFrame.right,
                      100 + insetsFrame.top + insetsFrame.bottom);
        
        JLabel lblName2 = new JLabel(msg);
		Dimension Fsize2 = lblName2.getPreferredSize();
        lblName2.setBounds(25 + insetsFrame.left, 33 + insetsFrame.top,
                 Fsize2.width, Fsize2.height);
        
        Newframe.add(lblName2);
        
	}
	//Showing the warning messages when the creation of the model fails with incorrect values
	public void showWarnings(String title, String message)
	{
		JOptionPane.showMessageDialog(null, message,title,JOptionPane.WARNING_MESSAGE);

		if(SCM_MainWindow.saveAndExit) {
			SCM_MainWindow mainWindowObj = new SCM_MainWindow();
			mainWindowObj.exitWindow();
		}

	}
}
