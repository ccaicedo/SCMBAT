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
 * Schedule.java
 * Creates the GUI panel for the Schedule construct
*/

package SCM_gui;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Schedule {

	public JPanel panel = new JPanel();
	
	public JButton NewSched = new JButton("Add new schedule");
	public JButton Previous = new JButton("Previous");
	public JButton Next = new JButton("Next");
	
	public JButton b1 = new JButton("Save");
	public JButton b2 = new JButton("Save & Exit");
	public JButton b3 = new JButton("Cancel");
	
	public JLabel SchedLabel = new JLabel("Schedule: (yyyy-mm-dd hh:mm:ss)");
	public JLabel TimeZoneLabel = new JLabel("TimeZone Offset (from GMT): hours:minutes");
	
	Object rowData1[][] = { { "Start Time","","","","","",""}, { "End Time","","","","","",""} };
    Object columnNames1[] = {"", "Year", "Month", "Day", "Hours", "Minutes", "Seconds"};
    TableModel table_model1 = new DefaultTableModel(rowData1, columnNames1) {
    	
		private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column)
            {
                // make read only column
    			if(column ==0 )
    			{
    				return false;
    			}
    			else
    			{
    				return true;
    			}
            }
        };   
    public JTable table1 = new JTable(table_model1);
	JScrollPane tableContainer1 = new JScrollPane(table1);
	
	Object rowData2[][] = { { "Start Time","",""}, { "End Time","",""} };
    Object columnNames2[] = {"", "Hours", "Minutes"};
    TableModel table_model2 = new DefaultTableModel(rowData2, columnNames2) {
    	
		private static final long serialVersionUID = 1L;

		@Override
        public boolean isCellEditable(int row, int column)
        {
            // make read only column
			if(column ==0 )
			{
				return false;
			}
			else
			{
				return true;
			}
        }
    };
    
    public JTable table2 = new JTable(table_model2);
	JScrollPane tableContainer2 = new JScrollPane(table2);
	
	public JLabel periodLabel = new JLabel("Period (optional)");
	Object rowData3[][] = { { "","",""} };
    Object columnNames3[] = {"Wait Until On", "Duration On", "Duration Off"};
    TableModel table_model3 = new DefaultTableModel(rowData3, columnNames3);    
    public JTable table3 = new JTable(table_model3);
	JScrollPane tableContainer3 = new JScrollPane(table3);
	
	
	public JTextField LocationField = new JTextField();

	public int index = 0;
	
	//Location index combo box
		Vector<String> comboBoxItems = new Vector<String>();
		DefaultComboBoxModel<String> combomodel = new DefaultComboBoxModel<String>(comboBoxItems);
		public JComboBox<String> comboBox = new JComboBox<String>(combomodel);
	
	public JPanel getPanel(){
		
		panel.setLayout(null);
		
		// Panel for Confidence
        
        JLabel ConfText = new JLabel("This construct supports confidence values. If you need to specify a confidence");
        JLabel ConfText2 = new JLabel("value different than 1 for the values of this construct please press this button");
        JButton ConfBtn = new JButton("Define Confidence Values");
        
        Dimension ConfBtnSize = ConfBtn.getPreferredSize();
        Dimension ConfTextSize = ConfText2.getPreferredSize();
        
        ConfBtn.setBounds(25, 20, ConfBtnSize.width, ConfBtnSize.height);
        ConfText.setBounds(25, 20, ConfTextSize.width, ConfTextSize.height);
        ConfText2.setBounds(25, 40, ConfTextSize.width, ConfTextSize.height);
        
        panel.add(ConfBtn);
        
        // New construct 
        
	    Dimension NewSchedSize = NewSched.getPreferredSize();
	    NewSched.setBounds(430 - 5, 70, NewSchedSize.width, NewSchedSize.height);
	    //Set Enabled only when more than one location index is present. Hence, disabled by default
	    NewSched.setEnabled(false);
	    
	    Dimension PreviousSize = Previous.getPreferredSize();
	    Previous.setBounds(600 - 5, 70, PreviousSize.width, PreviousSize.height);
	    
	    Dimension NextSize = Next.getPreferredSize();
	    Next.setBounds(705 - 5, 70, NextSize.width, NextSize.height);
		
	    panel.add(NewSched);
	    panel.add(Previous);
	    panel.add(Next);
	    
	    // Location Index
        
        JLabel LocationLabel = new JLabel("Location Index (Optional)");
        
        Dimension LocationLabelSize = LocationLabel.getPreferredSize();
        Dimension LocationFieldSize = LocationField.getPreferredSize();
        
        LocationLabel.setBounds(25, 80, LocationLabelSize.width, LocationLabelSize.height);
       // LocationField.setBounds(225, 80, LocationFieldSize.width + 50, LocationFieldSize.height);
        comboBox.setBounds(225, 80, LocationFieldSize.width + 50, LocationFieldSize.height);
        
       // panel.add(LocationField);
        panel.add(LocationLabel);
        panel.add(comboBox);
	    
        // Schedule Tables:
        
        //Schedule Table
        Dimension SchedLabelSize = SchedLabel.getPreferredSize();
        SchedLabel.setBounds(25, 110, 
        		SchedLabelSize.width, SchedLabelSize.height);
        
      //To allow the element on the last edit to be saved
        table1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        table2.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        table3.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        
        Dimension table1Size = tableContainer1.getPreferredSize();
        tableContainer1.setBounds(25, 150, 
        		table1Size.width + 120, table1Size.height - 340);
        
        //TimeZone Table
        Dimension TimeZoneLabelSize = TimeZoneLabel.getPreferredSize();
        TimeZoneLabel.setBounds(25, 130+120, 
        		TimeZoneLabelSize.width, TimeZoneLabelSize.height);
        
        
        Dimension table2Size = tableContainer2.getPreferredSize();
        tableContainer2.setBounds(25, 150+120, 
        		table2Size.width + 20, table2Size.height - 340);
        
        
        //Period Table
        Dimension periodLabelSize = periodLabel.getPreferredSize();
        periodLabel.setBounds(25, 130 + 220, 
        		periodLabelSize.width, periodLabelSize.height);
        
        Dimension table3Size = tableContainer3.getPreferredSize();
        tableContainer3.setBounds(25, 150 + 220, 
        		table3Size.width - 150, table3Size.height - 300);
        
        panel.add(periodLabel);
        panel.add(tableContainer3);
        panel.add(TimeZoneLabel);
        panel.add(tableContainer2);
        panel.add(SchedLabel);
        panel.add(tableContainer1);
        
        // Button placement
        
        Dimension btnSize = b2.getPreferredSize();
        b1.setBounds(620, 150, btnSize.width, btnSize.height);
        b2.setBounds(620, 200, btnSize.width, btnSize.height);
        b3.setBounds(620, 250, btnSize.width, btnSize.height);
        
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        
		return panel;
	}
}
