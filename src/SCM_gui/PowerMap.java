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
 * PowerMap.java
 * Creates the GUI panel for the Power Map construct
*/


package SCM_gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class PowerMap {

	public JPanel PowerPanel = new JPanel();
	
	public JButton b3 = new JButton("Save");
    JButton b4 = new JButton("Exit");

    int count;
    
    int count1 = 2;

	Object rowData1[][] = { { "", "Elevation Angle","-90"},  { "", "Azimuth Angle","0"}, { "1","",""}, { "", "Azimuth Angle","360"}, { "", "Elevation Angle","90"} };
    Object columnNames1[] = {"#","Value Type","Value"};
    
    TableModel table_model1 = new DefaultTableModel(rowData1, columnNames1) {
		private static final long serialVersionUID = 2580299347572016977L;  //Adding a serial version ID

			@Override
    	        public boolean isCellEditable(int row, int column)
    	        {
					return false;
    	        }
			
    };
    
    
    public JTable table1 = new JTable(table_model1) {
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
            Component comp = super.prepareRenderer(renderer, row, col);
            return comp;
        }
    	
    };
    
    
    
    JLabel relativeText = new JLabel("orientation relative to Platform");
	Object rowData2[][] = { {"","","",""} };
    Object columnNames2[] = {"\u03b1","\u03b2","\u03b3"};
    TableModel table_model2 = new DefaultTableModel(rowData2, columnNames2);
    public JTable relativeTable = new JTable(table_model2);
    JScrollPane relativeContainer = new JScrollPane(relativeTable);

    JLabel referenceText = new JLabel("Reference Point");
	Object rowData3[][] = { {"","","",""} };
    Object columnNames3[] = {"Latitude","Longitude","Altitude \n (Meters)"};
    TableModel table_model3 = new DefaultTableModel(rowData3, columnNames3);    
    public JTable referenceTable = new JTable(table_model3);
    JScrollPane referenceContainer = new JScrollPane(referenceTable);
    
    JScrollPane tableContainer1 = new JScrollPane(table1);
    JButton Add = new JButton("Add Row");
    JButton Remove = new JButton("Remove last entry");
	public JTable table;
    
	
	//radio buttons
    public JRadioButton surface = new JRadioButton("Surface");
    public JRadioButton relative = new JRadioButton("Relative to platform");
    public JRadioButton reference = new JRadioButton("Towards reference point");
    public JRadioButton scanYes = new JRadioButton("Yes");
    public JRadioButton scanNo = new JRadioButton("No");
    
    public JTextField locationField = new JTextField();
    
    
    JLabel ScanningLabel = new JLabel("Scanning Region");
    JLabel AntennaBeamLabel = new JLabel("Antenna beam width");
    JTextField AntennaBeamField = new JTextField();
    JTextField ValueTypeValue = new JTextField();
    Object ScanRowData[][] = {{"Azimuth limits","",""},{"Elevation limits","",""}};
    Object ScanColumnName[] = {"","Start","End"};

    TableModel ScanTableModel = new DefaultTableModel(ScanRowData,ScanColumnName);
    public JTable ScanTable = new JTable(ScanTableModel);
    JScrollPane ScanTableContainer = new JScrollPane(ScanTable);
    
    //Location index combo box
  	Vector<String> comboBoxItems = new Vector<String>();
  	DefaultComboBoxModel<String> combomodel = new DefaultComboBoxModel<String>(comboBoxItems);
  	public JComboBox<String> comboBox = new JComboBox<String>(combomodel);

    JLabel ValueTypeLabel = new JLabel("Value type to enter:");
    JLabel ValueTypeValueLabel = new JLabel("Enter Value:");
    String DropDownOptions[] = { "Elevation Angle", "Azimuth Angle", "Gain (dB)" };
  	public JComboBox<String> ValueTypeComboBox = new JComboBox<String>(DropDownOptions);
  	public JTextField ValueTypeRowItemValue = new JTextField();
  	
  	public static boolean isNumeric(String strNum) {
  	    try {
  	        double d = Double.parseDouble(strNum);
  	    } catch (NumberFormatException | NullPointerException nfe) {
  	        return false;
  	    }
  	    return true;
  	}
  	
	public JPanel getPanel(){
		
	    PowerPanel.setLayout(null);
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
        
        PowerPanel.add(ConfBtn);

        // Creating Orientation Panel
        
        JLabel orient = new JLabel("Orientation");
        Dimension orientSize = orient.getPreferredSize();
        orient.setBounds(25, 80, orientSize.width, orientSize.height);
        
        surface.setFont(font);
        Dimension surfaceSize = surface.getPreferredSize();
        surface.setBounds(135, 75, surfaceSize.width, surfaceSize.height);
        
        relative.setFont(font);
        Dimension relativeSize = relative.getPreferredSize();
        relative.setBounds(225, 75, relativeSize.width, relativeSize.height);
        
        reference.setFont(font);
        Dimension referenceSize = reference.getPreferredSize();
        reference.setBounds(400, 75, referenceSize.width, referenceSize.height);
        
        ButtonGroup group1 = new ButtonGroup();
        group1.add(surface);
        group1.add(relative);
        group1.add(reference);
        
        surface.setEnabled(true);
        surface.setSelected(true);
        
        PowerPanel.add(orient);
        PowerPanel.add(surface);
        PowerPanel.add(relative);
        PowerPanel.add(reference);

        // Location Index and Scanning Region
        
        JLabel locationLabel = new JLabel("Location Index (optional)");
        Dimension locationSize = locationLabel.getPreferredSize();
        locationLabel.setBounds(25, 120, locationSize.width, locationSize.height);
        
        Dimension locationFieldSize = locationField.getPreferredSize();
       // locationField.setBounds(205, 120, locationFieldSize.width + 50, locationFieldSize.height);
        comboBox.setBounds(205, 120, locationFieldSize.width + 50, locationFieldSize.height);
        
        JLabel scanLabel = new JLabel("Do you want to define a scanning region?");
        Dimension scanSize = scanLabel.getPreferredSize();
        scanLabel.setBounds(300, 120, scanSize.width, scanSize.height);
       
        /*
         * Placing the options for
         * 1. Relative 
         * 2. Reference
         * 3. Normal Gain map
         */
        Dimension relativeTextSize = relativeText.getPreferredSize();
        relativeText.setBounds(25, 60+100, 
        		relativeTextSize.width, relativeTextSize.height);
        
        Dimension relativeContSize = relativeContainer.getPreferredSize();
        relativeContainer.setBounds(25, 60+130, 
        		relativeContSize.width-150, relativeContSize.height-365);
        
        Dimension referenceContSize = referenceContainer.getPreferredSize();
        referenceContainer.setBounds(25, 60+130, 
        		referenceContSize.width-150, referenceContSize.height-365);
        
        Dimension referenceTextSize = referenceText.getPreferredSize();
        referenceText.setBounds(25, 60+100, referenceTextSize.width, referenceTextSize.height);
        
        relativeTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        referenceTable.getColumnModel().getColumn(1).setPreferredWidth(40);
        
      //To allow the element on the last edit to be saved
        relativeTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        referenceTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        ScanTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        
        
        scanYes.setFont(font);
        Dimension yesSize = scanYes.getPreferredSize();
        scanYes.setBounds(600,115, yesSize.width, yesSize.height);
        
        scanNo.setFont(font);
        Dimension noSize = scanNo.getPreferredSize();
        scanNo.setBounds(650, 115, noSize.width, noSize.height);
    
        Dimension ScanningSize = ScanningLabel.getPreferredSize();
        ScanningLabel.setBounds(25, 450, ScanningSize.width, ScanningSize.height);
        
        AntennaBeamLabel.setFont(font);
        Dimension AntennaLabelSize = AntennaBeamLabel.getPreferredSize();
        AntennaBeamLabel.setBounds(25, 480, AntennaLabelSize.width, AntennaLabelSize.height);
        
        Dimension AntennaFieldSize = AntennaBeamField.getPreferredSize();
        AntennaBeamField.setBounds(190, 480, AntennaFieldSize.width + 50, AntennaFieldSize.height);
        
        Dimension ScanningTableSize = ScanTableContainer.getPreferredSize();
        ScanTableContainer.setBounds(25, 510, ScanningTableSize.width, ScanningTableSize.height-300);

        PowerPanel.add(locationLabel);
      //  PowerPanel.add(locationField);
        PowerPanel.add(comboBox);
        PowerPanel.add(scanLabel);
        PowerPanel.add(scanYes);
        PowerPanel.add(scanNo);
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(scanYes);
        group2.add(scanNo);
        
        scanNo.setSelected(true);        
        
        //placing buttons for the panel
	    
	    JButton b1 = new JButton("Add new entry");
	    JButton b2 = new JButton("Remove last entry");
	    
        Dimension size2 = b2.getPreferredSize();
        b1.setBounds(400 + 0, 30 + 130 + 30 + 100,
                size2.width + 30, size2.height);        
        b2.setBounds(400 + 0, 30+ 180 + 30 + 100,
                size2.width + 30, size2.height);
        b3.setBounds(430 + size2.width, 30 + 130 + 30 + 100,
                size2.width + 30, size2.height);
        b4.setBounds(430 + size2.width, 30 + 180 + 30 + 100, 
        		size2.width + 30, size2.height);
                
	    PowerPanel.add(b1);
	    PowerPanel.add(b2);
	    PowerPanel.add(b3);
	    PowerPanel.add(b4);
	    

        //Value Type fields
	    Dimension ValueTypeLabelSize = ValueTypeLabel.getPreferredSize();
	    ValueTypeLabel.setBounds(25, 40 + 204, ValueTypeLabelSize.width, ValueTypeLabelSize.height);
	    
	    ValueTypeComboBox.setFont(font);
	    Dimension ValueTypeComboBoxSize = ValueTypeComboBox.getPreferredSize();
	    ValueTypeComboBox.setBounds(25 + ValueTypeLabelSize.width + 5, 40+200, ValueTypeComboBoxSize.width, ValueTypeComboBoxSize.height);
	    
	    Dimension ValueTypeValueLabelSize = ValueTypeValueLabel.getPreferredSize();
	    ValueTypeValueLabel.setBounds(25 + ValueTypeLabelSize.width + ValueTypeComboBoxSize.width + 25, 40+204, ValueTypeValueLabelSize.width, ValueTypeValueLabelSize.height);
	    
	    ValueTypeRowItemValue.setBounds(25 + ValueTypeLabelSize.width + ValueTypeComboBoxSize.width + ValueTypeValueLabelSize.width + 25, 40+200 , 100, ValueTypeRowItemValue.getPreferredSize().height);
        

	    // Creating table
	    
	    table1.getColumnModel().getColumn(0).setPreferredWidth(10);
	    table1.getColumnModel().getColumn(2).setPreferredWidth(30);
	    
	    table1.setShowGrid(true);
	    table1.setGridColor(Color.BLACK);
	    
	    table1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
	    {
	        @Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	        {
	        	int rowCount = table.getModel().getRowCount();
	            final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            if (!isSelected) {
	            	c.setBackground(row  == 0 || row == 1 || row == rowCount-1 || row == rowCount-2? Color.LIGHT_GRAY : Color.WHITE);
	            }
	            
	            return c;
	        }
	    });
	    
	    DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
	    headerRenderer.setBackground(Color.lightGray);
	    
	    
	    table1.getColumnModel().getColumn(0).setCellRenderer(headerRenderer);
	    	
	    table1.getTableHeader().setOpaque(false);
	    table1.getTableHeader().setBackground(Color.lightGray);

	    table = table1;
	  //To allow the element on the last edit to be saved
	    table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	    
	    count = count1;
	    tableContainer1 = new JScrollPane(table1);
	    Dimension sizeContainer1 = tableContainer1.getPreferredSize();
	    tableContainer1.setBounds(25 + 0, 70 + 200,
	            sizeContainer1.width - 100, sizeContainer1.height - 200);
	    
	    
	    PowerPanel.add(tableContainer1, BorderLayout.CENTER);
	    PowerPanel.add(ValueTypeComboBox);
	    PowerPanel.add(ValueTypeValueLabel);
	    PowerPanel.add(ValueTypeRowItemValue);
	    PowerPanel.add(ValueTypeLabel);
	    
	    // Table options. 
	    
	    
	    surface.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(surface.isSelected()==true){

					PowerPanel.remove(referenceContainer);
					PowerPanel.remove(relativeContainer);
					PowerPanel.remove(referenceText);
					PowerPanel.remove(relativeText);
					count = table.getRowCount();
					PowerPanel.revalidate();
					PowerPanel.repaint();
				}
			}
	    });
	    
	    relative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(relative.isSelected()==true){
					
					PowerPanel.remove(referenceContainer);
					PowerPanel.add(relativeContainer);
					PowerPanel.remove(referenceText);
					PowerPanel.add(relativeText);
					PowerPanel.revalidate();
					PowerPanel.repaint();
				}
			}
	    });
	    
	    reference.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(reference.isSelected()==true){
					
					PowerPanel.add(referenceContainer);
					PowerPanel.remove(relativeContainer);
					PowerPanel.add(referenceText);
					PowerPanel.remove(relativeText);
					PowerPanel.revalidate();
					PowerPanel.repaint();
					
				}
			}
	    });
	    
	    scanYes.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		
	    		if(scanYes.isSelected()==true){
	    			
	    			PowerPanel.add(ScanningLabel);
	    			PowerPanel.add(AntennaBeamLabel);
	    			PowerPanel.add(AntennaBeamField);
	    			PowerPanel.add(ScanTableContainer);
					PowerPanel.revalidate();
					PowerPanel.repaint();
	    		}
	    	}
	    });
	    
	    scanNo.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		
	    		if(scanNo.isSelected()==true){
	    			
	    			PowerPanel.remove(ScanningLabel);
	    			PowerPanel.remove(AntennaBeamLabel);
	    			PowerPanel.remove(AntennaBeamField);
	    			PowerPanel.remove(ScanTableContainer);
					PowerPanel.revalidate();
					PowerPanel.repaint();
	    		}
	    	}
	    });	    
	    // Button Actions
	    
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//adding only if any numeric data has been entered.
				if(isNumeric(ValueTypeRowItemValue.getText())) {
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.removeRow(model.getRowCount()-3);
					count = table.getRowCount();				
					model.insertRow(model.getRowCount()-2, new Object[]{count-3,ValueTypeComboBox.getSelectedItem().toString() , ValueTypeRowItemValue.getText()});
					model.insertRow(model.getRowCount()-2, new Object[]{table.getRowCount()-3,"" ,""});
					//resetting the data in the text field
					ValueTypeRowItemValue.setText("");
				}
			}
		});
		
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				
				//deleting only the last row
				if (table.getRowCount() == 4) {
					return;
				}
				int selectedRowIndex = table.getRowCount()-4;
				if(selectedRowIndex != 0 && selectedRowIndex != 1) {
					model.removeRow(selectedRowIndex);
					//update index of the last empty row
					model.removeRow(model.getRowCount()-3);
					model.insertRow(model.getRowCount()-2, new Object[]{table.getRowCount()-3,"" ,""});
				}

				if(table.getRowCount() == 4) {
					model.insertRow( model.getRowCount()-2, new Object[]{"1","",""});
				}
				
//				   }
				
			}
		});
	    
	    return PowerPanel;
	}
	
}
