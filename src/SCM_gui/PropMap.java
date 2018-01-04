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
 * PropMap.java
 * Creates the GUI panel for the Propagation Map construct
*/

package SCM_gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PropMap {

	public int index = 0;
	
	public JTextField DistanceText;
	public JPanel PropPanel = new JPanel();
	public JLabel DistanceLabel;
	
	public JRadioButton antHeightYes = new JRadioButton("Yes");
	public JRadioButton antHeightNo = new JRadioButton("No");
	public JRadioButton AGL = new JRadioButton("AGL (Above Ground Level)");
	public JRadioButton HAAT = new JRadioButton("HAAT (Height Above Average Terrain)");
	public JLabel HeightLabel = new JLabel("Height (meters)");
	public JTextField HeightField = new JTextField();
	public JLabel ReferenceLabel = new JLabel("Reference: ");
	
	
	JButton b3 = new JButton("Save Values");    //Declaring Save Button
    JButton b4 = new JButton("Save & Exit");	 //Declaring Exit Button
	JButton NewMap = new JButton("Add new map");
	JButton Previous = new JButton("Previous");
	JButton Next = new JButton("Next");
    
	Object rowData[][] = { { "1","","","","",""} };
    Object columnNames[] = {"#","Elevation Angle","Azimuth Angle", "n1", "BreakPoint (m)", "n2"};
    TableModel table_model = new DefaultTableModel(rowData, columnNames) {
    	
		private static final long serialVersionUID = 4471013790258970066L;

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
    public JTable table = new JTable(table_model);
	JScrollPane tableContainer = new JScrollPane(table);
    
	public JTable createProp(String column_names[]){
		DefaultTableModel table_model = new DefaultTableModel(column_names, 0);
        JTable table = new JTable(table_model);
        return table;
	}
    
	public JPanel getPanel(){
		
		PropPanel.setLayout(null);
		// Basic Font
        Font font = new Font("Arial", Font.PLAIN, 12);
        
        // Panel for Confidence
        
        JLabel ConfText = new JLabel("This construct supports confidence values. If you need to specify a confidence");
        JLabel ConfText2 = new JLabel("value different than 1 for the values of this construct please press this button");
        JButton ConfBtn = new JButton("Define Confidence Values");
        
        Dimension ConfBtnSize = ConfBtn.getPreferredSize();
        Dimension ConfTextSize = ConfText2.getPreferredSize();
        
        ConfBtn.setBounds(25, 20, ConfBtnSize.width, ConfBtnSize.height);
        ConfText.setBounds(25, 20, ConfTextSize.width, ConfTextSize.height);
        ConfText2.setBounds(25, 40, ConfTextSize.width, ConfTextSize.height);
        
        PropPanel.add(ConfBtn);

        // Location Index
        
        JLabel LocationLabel = new JLabel("Location Index (Optional)");
        JTextField LocationField = new JTextField();

        Dimension LocationLabelSize = LocationLabel.getPreferredSize();
        Dimension LocationFieldSize = LocationField.getPreferredSize();
        
        LocationLabel.setBounds(25, 70, LocationLabelSize.width, LocationLabelSize.height);
        LocationField.setBounds(225, 70, LocationFieldSize.width + 50, LocationFieldSize.height);
        
        PropPanel.add(LocationField);
        PropPanel.add(LocationLabel);
        
        // Antenna Height Option
        
        JLabel AntennaHeightLabel = new JLabel("Associate model with a specific distant height");
        Dimension AntennaHeightSize = AntennaHeightLabel.getPreferredSize();
        AntennaHeightLabel.setBounds(25, 100, AntennaHeightSize.width, AntennaHeightSize.height);
        
        antHeightYes.setFont(font);
        Dimension YesSize = antHeightYes.getPreferredSize();
        antHeightYes.setBounds(360, 95, YesSize.width, YesSize.height);
        
        antHeightNo.setFont(font);
        Dimension NoSize = antHeightNo.getPreferredSize();
        antHeightNo.setBounds(410, 95, NoSize.width, NoSize.height);
        
        HeightLabel.setFont(font);
        Dimension HeightSize = HeightLabel.getPreferredSize();
        HeightLabel.setBounds(25, 130, HeightSize.width, HeightSize.height);
        
        HeightField.setFont(font);
        Dimension HeightFieldSize = HeightField.getPreferredSize();
        HeightField.setBounds(160, 130, HeightFieldSize.width + 50, HeightFieldSize.height);
        
        ReferenceLabel.setFont(font);
        Dimension ReferenceSize = ReferenceLabel.getPreferredSize();
        ReferenceLabel.setBounds(250, 130, ReferenceSize.width, ReferenceSize.height);
        
        AGL.setFont(font);
        Dimension AGLSize = AGL.getPreferredSize();
        AGL.setBounds(350, 125, AGLSize.width, AGLSize.height);
        
        HAAT.setFont(font);
        Dimension HAATSize = HAAT.getPreferredSize();
        HAAT.setBounds(350, 150, HAATSize.width, HAATSize.height);
        
        antHeightNo.setSelected(true);
        
        ButtonGroup group1 = new ButtonGroup();
        group1.add(antHeightNo);
        group1.add(antHeightYes);
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(AGL);
        group2.add(HAAT);
        
        antHeightYes.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				PropPanel.add(HeightField);
				PropPanel.add(HeightLabel);
				PropPanel.add(ReferenceLabel);
				PropPanel.add(AGL);
				PropPanel.add(HAAT);
				PropPanel.repaint();
				PropPanel.revalidate();
				
			}        	
        });
        
        antHeightNo.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
			
				PropPanel.remove(HeightField);
				PropPanel.remove(HeightLabel);
				PropPanel.remove(ReferenceLabel);
				PropPanel.remove(AGL);
				PropPanel.remove(HAAT);
				PropPanel.repaint();
				PropPanel.revalidate();
			}
        });
        
        PropPanel.add(AntennaHeightLabel);
        PropPanel.add(antHeightYes);
        PropPanel.add(antHeightNo);
     
        //To allow the element on the last edit to be saved
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        
        // Table Content
        
        table.getColumnModel().getColumn(0).setWidth(5);
        table.getColumnModel().getColumn(1).setWidth(20);
        table.getColumnModel().getColumn(2).setWidth(20);
        table.getColumnModel().getColumn(3).setWidth(5);
        table.getColumnModel().getColumn(4).setWidth(20);
        table.getColumnModel().getColumn(5).setWidth(5);
        
        JLabel TableLabel = new JLabel("Propagation Map");
        Dimension TableLabelSize = TableLabel.getPreferredSize();
        TableLabel.setBounds(25, 120 + 100, TableLabelSize.width, TableLabelSize.height);    
        
        Dimension tableSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 160 + 100, tableSize.width + 160, tableSize.height - 200);
		
        PropPanel.add(tableContainer);
        PropPanel.add(TableLabel);
        
        // Creating and placing buttons for the panel.
        
		JButton b1 = new JButton("Add Row");
        JButton b2 = new JButton("Delete Row");
        
        Dimension size2 = b3.getPreferredSize();
	    b1.setBounds(650 + 10, 160 + 100,
	                 size2.width, size2.height);
	    b2.setBounds(650 + 10, 210 + 100,
	                 size2.width, size2.height);
	    b3.setBounds(650 + 10, 260 + 100,
	                 size2.width, size2.height);
	    b4.setBounds(650 + 10, 310 + 100,
	                 size2.width, size2.height);
	    
	    Dimension NewMapSize = NewMap.getPreferredSize();
	    NewMap.setBounds(450 + 10, 110 + 100, NewMapSize.width, NewMapSize.height);
	    
	    Dimension PreviousSize = Previous.getPreferredSize();
	    Previous.setBounds(600 + 10, 110 + 100, PreviousSize.width, PreviousSize.height);
	    
	    Dimension NextSize = Next.getPreferredSize();
	    Next.setBounds(705 + 10, 110 + 100, NextSize.width, NextSize.height);
	    
        PropPanel.add(b1);
        PropPanel.add(b2);
        PropPanel.add(b3);
        PropPanel.add(b4);
        PropPanel.add(NewMap);
        PropPanel.add(Previous);
        PropPanel.add(Next);
        
        // Button Actions       		
        
        b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int count = model.getRowCount() +1;
				model.addRow(new Object[]{count, "", ""});	
			}
		});
		
		
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				/*
				 * Allowing the deletion of selected rows
				 */
				int[] selectedRows = table.getSelectedRows();
				   for(int row=selectedRows.length-1;row>=0;row--){
					int rowNum = selectedRows[row];
				     model.removeRow(rowNum);
				     //Updating the index column - count variable appropriately
				     if(rowNum!=table.getRowCount())
				     {
				    	 table.getModel().setValueAt(rowNum+1,rowNum ,0 );
				     }
				     
				   }
			//	model.removeRow(model.getRowCount() - 1);
				   
				  
				   for(int i=table.getRowCount()-1;i>=0;i--)
				   {
					   int curVal = Integer.parseInt(table.getModel().getValueAt(i, 0).toString());
					   if(curVal!= i+1)
					   {
						   table.getModel().setValueAt(i+1, i, 0);
					   }
				   }
				}
		});
        

		return PropPanel;
	}
}
