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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.ieee.dyspansc._1900._5.scm.PiecewiseLinear;

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
	
	
	public JButton b3 = new JButton("Save");    //Declaring Save Button
    public JButton b4 = new JButton("Exit");	 //Declaring Exit Button
	public JButton NewMap = new JButton("Add new map");
	public JButton Previous = new JButton("Previous");
	public JButton Next = new JButton("Next");

	Object rowData[][] = { { "", "Elevation Angle","-90"},  { "", "Azimuth Angle","0"}, { "1","",""}, { "", "Azimuth Angle","360"}, { "", "Elevation Angle","90"} };
	Object columnNames[] = {"#","Value Type","Value"};
  
    TableModel table_model = new DefaultTableModel(rowData, columnNames) {
    	
		private static final long serialVersionUID = 4471013790258970066L;

		@Override
        public boolean isCellEditable(int row, int column)
        {
			return false;
        }
    	
    };
    
	Object rowData_piecewise[][] = { { "", "", "" } };
	Object columnData_piecewise[] = {"First Exponent","Breakpoint(meters)","Second Exponent"};
    
	//table for piecewise linear model
    TableModel piecewise_table_model = new DefaultTableModel(rowData_piecewise, columnData_piecewise) {
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = -6638870939760639702L;

		@Override
        public boolean isCellEditable(int row, int column)
        {
			return true;
        }
    	
    };
    
    public JTable table = new JTable(table_model);
	JScrollPane tableContainer = new JScrollPane(table);
    
	public JTable createProp(String column_names[]){
		DefaultTableModel table_model = new DefaultTableModel(column_names, 0);
        JTable table = new JTable(table_model);
        return table;
	}
	
	//Location index combo box
	Vector<String> comboBoxItems = new Vector<String>();
	DefaultComboBoxModel<String> combomodel = new DefaultComboBoxModel<String>(comboBoxItems);
	public JComboBox<String> comboBox = new JComboBox<String>(combomodel);
	
	//value type items
    JLabel ValueTypeLabel = new JLabel("Value type to enter:");
    String DropDownOptions[] = { "Elevation Angle", "Azimuth Angle", "Propagation Model" };
  	public JComboBox<String> ValueTypeComboBox = new JComboBox<String>(DropDownOptions);
    JLabel ValueTypeValueLabel = new JLabel("Enter Value:");
  	public JTextField ValueTypeRowItemValue = new JTextField();
  	
  	//for propagation model
  	JLabel PropModelValueTypeLabel = new JLabel("Type:");
  	String PropTypeDropDownOptions[] = { "Linear", "Piecewise Linear" };
  	public JComboBox<String> PropTypeComboBox = new JComboBox<String>(PropTypeDropDownOptions);
  	
  	//for linear - propagation model
    JLabel PropExpTypeValueLabel = new JLabel("Prop. exponent:");
  	public JTextField PropExpTypeRowItemValue = new JTextField();
  	
  	//for piecewise linear - propagation model
  	JLabel PiecewiseTypeValueLabel = new JLabel("Values:");
  	public JTable piecewiseTable = new JTable(piecewise_table_model);
	JScrollPane piecewiseTableContainer = new JScrollPane(piecewiseTable);
  	
  	
  	public static boolean isNumeric(String strNum) {
  	    try {
  	        double d = Double.parseDouble(strNum);
  	    } catch (NumberFormatException | NullPointerException nfe) {
  	        return false;
  	    }
  	    return true;
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
       // LocationField.setBounds(225, 70, LocationFieldSize.width + 50, LocationFieldSize.height);
        comboBox.setBounds(225, 70, LocationFieldSize.width + 50, LocationFieldSize.height);
        
       // PropPanel.add(LocationField);
        PropPanel.add(LocationLabel);
        PropPanel.add(comboBox);
        
        
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
        
        table.getColumnModel().getColumn(0).setWidth(1);
        table.getColumnModel().getColumn(1).setWidth(15);
        table.getColumnModel().getColumn(2).setWidth(20);
        
	    table.setShowGrid(true);
	    table.setGridColor(Color.BLACK);
	    
	    DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
	    headerRenderer.setBackground(Color.lightGray);
	    
	    table.getColumnModel().getColumn(0).setCellRenderer(headerRenderer);
	    
	    	
	    table.getTableHeader().setOpaque(false);
	    table.getTableHeader().setBackground(Color.lightGray);
        
        
        JLabel TableLabel = new JLabel("Propagation Map");
        Dimension TableLabelSize = TableLabel.getPreferredSize();
        TableLabel.setBounds(25, 120 + 100, TableLabelSize.width, TableLabelSize.height);    
        
        Dimension tableSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 160 + 100, tableSize.width - 100, tableSize.height-200);
		
        PropPanel.add(tableContainer);
        PropPanel.add(TableLabel);
        
        
        //Value Type fields
	    Dimension ValueTypeLabelSize = ValueTypeLabel.getPreferredSize();
	    ValueTypeLabel.setBounds(400 + 25, 160+80  + 4, ValueTypeLabelSize.width, ValueTypeLabelSize.height);
	    
	    ValueTypeComboBox.setFont(font);
	    Dimension ValueTypeComboBoxSize = ValueTypeComboBox.getPreferredSize();
	    ValueTypeComboBox.setBounds(400 + ValueTypeLabelSize.width + 25, 160+80 , ValueTypeComboBoxSize.width, ValueTypeComboBoxSize.height);
	    
	    //textbox for azimuth & elevation
	    Dimension ValueTypeValueLabelSize = ValueTypeValueLabel.getPreferredSize();
	    ValueTypeValueLabel.setBounds(400+25, 160 + 80 + 50 + 10 + 4, ValueTypeValueLabelSize.width, ValueTypeValueLabelSize.height);
	    ValueTypeRowItemValue.setBounds(400+25+ValueTypeValueLabelSize.width, 160 + 80 + 10 + 50 , 100, ValueTypeRowItemValue.getPreferredSize().height);
        
	    //prop model fields - drop down selection
	    PropModelValueTypeLabel.setBounds(400+25, 160 + 80 - 20 + 50 + 13, PropModelValueTypeLabel.getPreferredSize().width, PropModelValueTypeLabel.getPreferredSize().height);
	    
	    PropTypeComboBox.setFont(font);
	    Dimension PropTypeComboBoxSize = PropTypeComboBox.getPreferredSize();
	    PropTypeComboBox.setBounds(400 + 25 + PropModelValueTypeLabel.getPreferredSize().width + 25, 160 + 80 - 20 + 50 + 10, PropTypeComboBoxSize.width, PropTypeComboBoxSize.height);
	    
	    //Linear
	    Dimension PropExpTypeValueLabelSize = PropExpTypeValueLabel.getPreferredSize();
	    PropExpTypeValueLabel.setBounds(400+25, 160 + 80 - 20 + 50 + 10 + 50, PropExpTypeValueLabelSize.width, PropExpTypeValueLabelSize.height);
	    Dimension PropExpTypeRowItemValueSize = PropExpTypeRowItemValue.getPreferredSize();
	    PropExpTypeRowItemValue.setBounds(400+25+PropExpTypeValueLabelSize.width, 160 + 80 - 25 + 50 + 10 + 50, 100, PropExpTypeRowItemValueSize.height);
	    
	    //piecewise linear
	    Dimension PiecewiseTypeValueLabelSize = PiecewiseTypeValueLabel.getPreferredSize();
	    PiecewiseTypeValueLabel.setBounds(400+25, 160 + 80 - 25 + 50 + 10 + 50, PiecewiseTypeValueLabelSize.width, PiecewiseTypeValueLabelSize.height);
	    Dimension piecewiseTableContainerSize = piecewiseTableContainer.getPreferredSize();
	    piecewiseTableContainer.setBounds(400+25+PiecewiseTypeValueLabelSize.width, 160 + 80 - 25 + 50 + 10 + 50, 350, 40);
	    
        // Creating and placing buttons for the panel.
        
		JButton b1 = new JButton("Add new entry");
        JButton b2 = new JButton("Remove Last Entry");
        
        Dimension size2 = b2.getPreferredSize();
	    b1.setBounds(400 + 30, 280 + 100,
	                 size2.width, size2.height);
	    b2.setBounds(400 + 30, 330 + 100,
	                 size2.width, size2.height);
	    b3.setBounds(400 + size2.width + 30, 280 + 100,
	                 size2.width, size2.height);
	    b4.setBounds(400 + size2.width + 30, 330 + 100,
	                 size2.width, size2.height);
	    
	    Dimension NewMapSize = NewMap.getPreferredSize();
	    NewMap.setBounds(450 + 10, 110 + 100, NewMapSize.width, NewMapSize.height);
	    //By Default keep it disabled so that only when more than 1 location index is present, we allow the addition
	    NewMap.setEnabled(false);
	    
	    Dimension PreviousSize = Previous.getPreferredSize();
	    Previous.setBounds(600 + 10, 110 + 100, PreviousSize.width, PreviousSize.height);
	    
	    Dimension NextSize = Next.getPreferredSize();
	    Next.setBounds(705 + 10, 110 + 100, NextSize.width, NextSize.height);
	   
        
	    PropPanel.add(ValueTypeComboBox);
	    
	    PropPanel.add(ValueTypeValueLabel);
	    PropPanel.add(ValueTypeRowItemValue);
	    PropPanel.add(ValueTypeLabel);
        PropPanel.add(b1);
        PropPanel.add(b2);
        PropPanel.add(b3);
        PropPanel.add(b4);
        
        
        //selection action listener for combo box
        ValueTypeComboBox.addItemListener(new ItemListener() 
        {
        	@Override
       	    public void itemStateChanged(ItemEvent event) {
            if (event.getStateChange() == ItemEvent.SELECTED) {
            	System.out.println(event.getItem().toString());
            	System.out.println(ValueTypeComboBox.getSelectedItem().toString());
            	String currentSelectedItem = event.getItem().toString();
            	if(currentSelectedItem == "Elevation Angle" || currentSelectedItem == "Azimuth Angle") {
            		PropTypeComboBox.setSelectedIndex(0);
            		PropPanel.remove(PropTypeComboBox);
            		PropPanel.remove(PropModelValueTypeLabel);    
            		PropPanel.add(ValueTypeRowItemValue);
            	    PropPanel.add(ValueTypeValueLabel);
    				PropPanel.remove(PropExpTypeValueLabel);
            		PropPanel.remove(PropExpTypeRowItemValue);
            		PropPanel.remove(PiecewiseTypeValueLabel);
            		PropPanel.remove(piecewiseTableContainer);
            	    
            	    PropPanel.invalidate();
            	    PropPanel.validate();
            		PropPanel.repaint();
            	}
            	else {
            		PropPanel.remove(ValueTypeValueLabel);
            		PropPanel.remove(ValueTypeRowItemValue);
            		PropPanel.add(PropTypeComboBox);
            		PropPanel.add(PropModelValueTypeLabel);
    				PropPanel.add(PropExpTypeValueLabel);
            		PropPanel.add(PropExpTypeRowItemValue);
            		
            	    PropPanel.invalidate();
            	    PropPanel.validate();
            		PropPanel.repaint();
            	}
            }
        	}
        }	
      );        
        
        
        PropTypeComboBox.addItemListener(new ItemListener() 
        {
        	@Override
        	public void itemStateChanged(ItemEvent event) {
        		if (event.getStateChange() == ItemEvent.SELECTED) {
        			System.out.println(event.getItem().toString());
        			System.out.println(PropTypeComboBox.getSelectedItem().toString());
        			String currentSelectedItem = event.getItem().toString();
        			if(currentSelectedItem == "Linear") {
//        				linearSelected = true;
//        				piecewiseLinearSelected = false;
        				PropPanel.add(PropExpTypeValueLabel);
                		PropPanel.add(PropExpTypeRowItemValue);
                		PropPanel.remove(PiecewiseTypeValueLabel);
                		PropPanel.remove(piecewiseTableContainer);
                		
                	    PropPanel.invalidate();
                	    PropPanel.validate();
                		PropPanel.repaint();
        			}
        			else {
//        				linearSelected = false;
//        				piecewiseLinearSelected = true;
        				PropPanel.remove(PropExpTypeValueLabel);
                		PropPanel.remove(PropExpTypeRowItemValue);
                		PropPanel.add(PiecewiseTypeValueLabel);
                		PropPanel.add(piecewiseTableContainer);
                		
                	    PropPanel.invalidate();
                	    PropPanel.validate();
                		PropPanel.repaint();
        			}
        		}
        	}
        }	
      );        
        

        
        // Button Actions       		
        
        b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int count;
				
				if (piecewiseTable.isEditing())
					piecewiseTable.getCellEditor().stopCellEditing();
				
				if(ValueTypeComboBox.getSelectedItem().toString() == "Propagation Model" && PropTypeComboBox.getSelectedItem().toString() == "Linear" && isNumeric(PropExpTypeRowItemValue.getText())) {
					model.removeRow(model.getRowCount()-3);
					count = table.getRowCount();
					model.insertRow(model.getRowCount()-2, new Object[]{count-3, "Prop Exponent" , PropExpTypeRowItemValue.getText()});
				}
				else if(ValueTypeComboBox.getSelectedItem().toString() == "Propagation Model" && PropTypeComboBox.getSelectedItem().toString() == "Piecewise Linear" && isNumeric(piecewiseTable.getModel().getValueAt(0,0).toString()) && isNumeric(piecewiseTable.getModel().getValueAt(0,1).toString()) && isNumeric(piecewiseTable.getModel().getValueAt(0,2).toString())) {
					model.removeRow(model.getRowCount()-3);
					model.insertRow(model.getRowCount()-2, new Object[]{table.getRowCount()-3, "First Exponent" , piecewiseTable.getModel().getValueAt(0,0).toString()});
					model.insertRow(model.getRowCount()-2, new Object[]{table.getRowCount()-3, "Breakpoint(meters)" , piecewiseTable.getModel().getValueAt(0,1).toString()});
					model.insertRow(model.getRowCount()-2, new Object[]{table.getRowCount()-3, "Second Exponent" , piecewiseTable.getModel().getValueAt(0,2).toString()});
				}
				
				else if(ValueTypeComboBox.getSelectedItem().toString() != "Propagation Model" && isNumeric(ValueTypeRowItemValue.getText())) {
					model.removeRow(model.getRowCount()-3);
					count = table.getRowCount();
					model.insertRow(model.getRowCount()-2, new Object[]{count-3,ValueTypeComboBox.getSelectedItem().toString() , ValueTypeRowItemValue.getText()});
				}
				model.insertRow(model.getRowCount()-2, new Object[]{table.getRowCount()-3,"" ,""});
				//firstRowInsertion = false;
			}
		});
		
		
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				   if (table.getRowCount() == 4) {
					   return;
					}
					int selectedRowIndex = table.getRowCount()-4;
					if(selectedRowIndex != 0 && selectedRowIndex != 1) {
						if(model.getValueAt(selectedRowIndex, 1)=="Second Exponent") {
							model.removeRow(selectedRowIndex);
							model.removeRow(selectedRowIndex-1);
							model.removeRow(selectedRowIndex-2);
						}
						else
							model.removeRow(selectedRowIndex);
						
						//update index of the last empty row
						model.removeRow(model.getRowCount()-3);
						model.insertRow(model.getRowCount()-2, new Object[]{table.getRowCount()-3,"" ,""});
					}

					if(table.getRowCount() == 4) {
						model.insertRow( model.getRowCount()-2, new Object[]{"1","",""});
					}
				  
				}
		});
        

		return PropPanel;
	}
}
