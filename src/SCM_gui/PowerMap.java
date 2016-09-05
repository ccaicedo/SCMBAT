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
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class PowerMap {

	JPanel PowerPanel = new JPanel();
	
	JButton b3 = new JButton("Save Data");
    JButton b4 = new JButton("Exit");

    int count;
    
    int count1 = 2;
	Object rowData1[][] = { { "1","","",""} };
    Object columnNames1[] = {"#","Elevation Angle","Azimuth Angle", "Gain (dB)"};
    TableModel table_model1 = new DefaultTableModel(rowData1, columnNames1);    
    public JTable table1 = new JTable(table_model1);
    
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
    JButton Remove = new JButton("Remove Row");
	public JTable table;
    
    public JRadioButton surface = new JRadioButton("Surface");
    public JRadioButton relative = new JRadioButton("Relative to platform");
    public JRadioButton reference = new JRadioButton("Towards reference point");
    public JRadioButton scanYes = new JRadioButton("Yes");
    public JRadioButton scanNo = new JRadioButton("No");
    
    JTextField locationField = new JTextField();
    
    
    JLabel ScanningLabel = new JLabel("Scanning Region");
    JLabel AntennaBeamLabel = new JLabel("Antenna beam width");
    JTextField AntennaBeamField = new JTextField();
    Object ScanRowData[][] = {{"Azimuth limits","",""},{"Elevation limits","",""}};
    Object ScanColumnName[] = {"","Start","End"};
    TableModel ScanTableModel = new DefaultTableModel(ScanRowData,ScanColumnName);
    public JTable ScanTable = new JTable(ScanTableModel);
    JScrollPane ScanTableContainer = new JScrollPane(ScanTable);
    
    
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
        locationField.setBounds(205, 120, locationFieldSize.width + 50, locationFieldSize.height);
        
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
        PowerPanel.add(locationField);
        PowerPanel.add(scanLabel);
        PowerPanel.add(scanYes);
        PowerPanel.add(scanNo);
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(scanYes);
        group2.add(scanNo);
        
        scanNo.setSelected(true);
        
        //placing buttons for the panel
	    
	    JButton b1 = new JButton("Add Row");
	    JButton b2 = new JButton("Remove Row");
	    
        Dimension size2 = b3.getPreferredSize();
        b1.setBounds(400 + 0, 130 + 30 + 100,
                size2.width + 30, size2.height);        
        b2.setBounds(400 + 0, 180 + 30 + 100,
                size2.width + 30, size2.height);
        b3.setBounds(550, 130 + 30 + 100,
                size2.width + 30, size2.height);
        b4.setBounds(550, 180 + 30 + 100, 
        		size2.width + 30, size2.height);
                
	    PowerPanel.add(b1);
	    PowerPanel.add(b2);
	    PowerPanel.add(b3);
	    PowerPanel.add(b4);

	    // Creating table
	    
	    JLabel gainMap = new JLabel("Gain Map");
	    Dimension gainMapSize = gainMap.getPreferredSize();
	    gainMap.setBounds(25, 40 + 200, gainMapSize.width, gainMapSize.height);
	    
	    table1.getColumnModel().getColumn(0).setPreferredWidth(10);
	    table1.getColumnModel().getColumn(3).setPreferredWidth(30);
	    
	    table = table1;
	    count = count1;
	    tableContainer1 = new JScrollPane(table1);
	    Dimension sizeContainer1 = tableContainer1.getPreferredSize();
	    tableContainer1.setBounds(25 + 0, 60 + 200,
	            sizeContainer1.width - 100, sizeContainer1.height - 300);
	    
	    
	    PowerPanel.add(tableContainer1, BorderLayout.CENTER);
	    PowerPanel.add(gainMap);
	    
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
			
				count = table.getRowCount();
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{count+1, "", "",""});
			}
		});
		
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.removeRow(model.getRowCount() - 1);
				count = count - 1;
			}
		});
	    
	    return PowerPanel;
	}
	
}
