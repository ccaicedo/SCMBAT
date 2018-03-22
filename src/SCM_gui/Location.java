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
 *  Location.java
 *  Creates the GUI panel for the Location construct
*/

package SCM_gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import SCM_home.Home;

public class Location {
	
public JPanel panel = new JPanel();

final Logger logger = Logger.getLogger(Location.class);
public JButton NewMap = new JButton("Add new location");
public JButton Previous = new JButton("Previous");
public JButton Next = new JButton("Next");
public JTextField LocationField = new JTextField();

Object comboData[] = {"Point", "Point Surface", 
		"Circular Surface", "Polygon Surface",
		"Cylinder", "Polyhedron", "Path"};

public JComboBox<Object> LocCombo = new JComboBox<Object>(comboData);

public JLabel head = new JLabel();

//Point Data

Object pointRow[][] = { { "","",""} };
Object pointColumn[] = {"Longitude", "Latitude", "Altitude (m)"};
TableModel pointModel = new DefaultTableModel(pointRow, pointColumn);    
public JTable pointTable = new JTable(pointModel) {
	
	private static final long serialVersionUID = -356523719488233990L;

		//Add the cell tool tip text           
    	public String getToolTipText(MouseEvent event)
    	{
    		return "Enter a decimal value";
    	}
	
	
};


//Point Surface Data

Object pointSurfaceRow[][] = { { "","",""} };
Object pointSurfaceColumn[] = {"Longitude", "Latitude", "Altitude (m)"};
TableModel pointSurfaceModel = new DefaultTableModel(pointSurfaceRow, pointSurfaceColumn);    
public JTable pointSurfaceTable = new JTable(pointSurfaceModel);

//Circular Surface Data 

Object circularRow[][] = { { "","","","",""} };
Object circularColumn[] = {"Longitude", "Latitude", 
		"Altitude (m)","Radius (m)", "Perimeter Attenuation (dB)"};
TableModel circularModel = new DefaultTableModel(circularRow, circularColumn);    
public JTable circularTable = new JTable(circularModel) {
	
	private static final long serialVersionUID = 1L;
	
	//Add the cell tool tip text           
	public String getToolTipText(MouseEvent event)
	{
		return "Enter a decimal value";
	}
};

//Polygon Surface
Object polygonRow[][] = { {"1","","","",""} };
Object polygonColumn[] = {"#","Longitude", "Latitude", 
		"Altitude (m)", "Side attenuation (dB)"};
TableModel polygonModel = new DefaultTableModel(polygonRow, polygonColumn)
{
	
	private static final long serialVersionUID = -1630465887803391371L;

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
public JTable polygonTable = new JTable(polygonModel) {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Add the cell tool tip text           
	public String getToolTipText(MouseEvent event)
	{
		return "Enter a decimal value";
	}
};

//Cylinder

Object cylinderRow[][] = { { "","","","","","",""} };
Object cylinderColumn[] = {"Longitude", "Latitude", 
		"Altitude (m)","Radius (m)", "<html> Perimeter <br> Attenuation (dB)", "Height (m)", "<html> Top Surface <br> Attenuation (dB)"};
TableModel cylinderModel = new DefaultTableModel(cylinderRow, cylinderColumn);    
public JTable cylinderTable = new JTable(cylinderModel) {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//Add the cell tool tip text           
	public String getToolTipText(MouseEvent event)
	{
		return "Enter a decimal value";
	}
};

//Polyhedron

public JLabel polySideLabel = new JLabel("Polyhedron sides definition");
public JLabel polyHeightLabel = new JLabel("Polyhedron height, top and bottom definition"); 

Object polyhedronRow[][] = { {"1","","","","",""} };
Object polyhedronColumn[] = {"#","Longitude", "Latitude", 
		"Altitude (m)","Side Attenuation (dB)"};
TableModel polyhedronModel = new DefaultTableModel(polyhedronRow, polyhedronColumn)
{
	private static final long serialVersionUID = -7687121837709455910L;

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
public JTable polyhedronTable = new JTable(polyhedronModel) {
	
	private static final long serialVersionUID = 1L;

	//Add the cell tool tip text           
	public String getToolTipText(MouseEvent event)
	{
		return "Enter a decimal value";
	}
};

Object heightRow[][] = { { "","",""} };
Object heightColumn[] = {"Height (m)", 
		"Bottom Surface attenuation (dB)", "Top Surface attenuation (dB)"};
TableModel heightModel = new DefaultTableModel(heightRow, heightColumn);    
public JTable heightTable = new JTable(heightModel) {
	
	private static final long serialVersionUID = 1L;

	//Add the cell tool tip text           
	public String getToolTipText(MouseEvent event)
	{
		return "Enter a decimal value";
	}
};

public JRadioButton AGL = new JRadioButton("AGL (Abouve Ground Level)");
public JRadioButton HAAT = new JRadioButton("HAAT (Height Above Average Terrain)");
public JLabel HeightLabel = new JLabel("Height (meters)");
public JTextField HeightField = new JTextField();
public JLabel ReferenceLabel = new JLabel("Reference: ");


public JLabel transmitterLabel = new JLabel("Transmitter Density");
public JTextField transmitterField = new JTextField();

//Path 

Object pathRow[][] = { { "1","","","",""} };
Object pathColumn[] = {"#","Longitude", "Latitude", 
		"Altitude (m)","Time"};
TableModel pathModel = new DefaultTableModel(pathRow, pathColumn)
{
	
	private static final long serialVersionUID = 8038079987043919167L;

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
public JTable pathTable = new JTable(pathModel) {
	
	private static final long serialVersionUID = 1L;

	//Add the cell tool tip text           
	public String getToolTipText(MouseEvent event)
	{
		return "Enter a decimal value";
	}
};

public JTable table = new JTable();
public JScrollPane tableContainer = new JScrollPane(pointTable);
public JScrollPane heightContainer = new JScrollPane(heightTable);

public JLabel periodLabel = new JLabel("Period (Optional)");
public JTextField periodField = new JTextField();

//Buttons

public JButton add = new JButton("Add Row");
public JButton del = new JButton("Delete Row");
public JButton save = new JButton("Save");
public JButton saveExit = new JButton("Save & Exit");
public JButton exit = new JButton("Exit");

public int index =0;

	public JPanel getPanel(){
	
		logger.addAppender(Home.appender);
		panel.setLayout(null);
		
// Panel for Confidence
        
        JLabel ConfText = new JLabel("This construct support confidence values. If you need to specify a confidence");
        JLabel ConfText2 = new JLabel("value different than 1 for the values of this construct please press this button");
        JButton ConfBtn = new JButton("Define Confidence Values");
        
        Dimension ConfBtnSize = ConfBtn.getPreferredSize();
        Dimension ConfTextSize = ConfText2.getPreferredSize();
        
        ConfBtn.setBounds(25, 20, ConfBtnSize.width, ConfBtnSize.height);
        ConfText.setBounds(25, 20, ConfTextSize.width, ConfTextSize.height);
        ConfText2.setBounds(25, 40, ConfTextSize.width, ConfTextSize.height);
        
        panel.add(ConfBtn);
        
// Add new Map buttons
        
        Dimension NewMapSize = NewMap.getPreferredSize();
	    NewMap.setBounds(420 , 80, NewMapSize.width, NewMapSize.height);
	    
	    Dimension PreviousSize = Previous.getPreferredSize();
	    Previous.setBounds(600 , 80, PreviousSize.width, PreviousSize.height);
	    
	    Dimension NextSize = Next.getPreferredSize();
	    Next.setBounds(705 , 80, NextSize.width, NextSize.height);
	    
	    panel.add(NewMap);
	    panel.add(Previous);
	    panel.add(Next);
	    
	   // Next.addActionListener(.new NextListener("location",tabbedPane));
// Location Index
        
        JLabel LocationLabel = new JLabel("Location Index (Optional)");

        Dimension LocationLabelSize = LocationLabel.getPreferredSize();
        Dimension LocationFieldSize = LocationField.getPreferredSize();
        
        LocationLabel.setBounds(25, 120, LocationLabelSize.width, LocationLabelSize.height);
        LocationField.setBounds(225, 120, LocationFieldSize.width + 50, LocationFieldSize.height);
        
        panel.add(LocationField);
        panel.add(LocationLabel);
		
// Location Type
        
        JLabel LocTypeLabel = new JLabel("Location Type: ");
        
        Dimension LocTypeLabelSize = LocTypeLabel.getPreferredSize();
        LocTypeLabel.setBounds(25, 160,
        		LocTypeLabelSize.width, LocTypeLabelSize.height);
        
        Dimension comboSize = LocCombo.getPreferredSize();
        LocCombo.setBounds(150, 155, comboSize.width, comboSize.height);
		
        panel.add(LocTypeLabel);
        panel.add(LocCombo);
        
        panel.add(head);
        
// Adding table
        
        panel.add(tableContainer);
        panel.add(heightContainer);
        panel.add(polyHeightLabel);
        
// Combo Box Actions
     
        final ActionListener boxAction = new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
					
				String selectedItem = (String) LocCombo.getSelectedItem();
		        
				System.out.println(selectedItem);
				logger.info(selectedItem);
		        switch(selectedItem){
		        
		        case "Point": clearAll(); 
		        			getPoint();
		        			break;
		        			
		        case "Point Surface": clearAll();
		        					getPointSurface();
		        					break;
		        					
		        case "Circular Surface": clearAll();
		        					getCircularSurface();
		        					break;
		        					
		        case "Polygon Surface": clearAll();
		        					getPolygonSurface();
		        					break;
		        					
		        case "Cylinder": clearAll();
		        				getCylinder();
		        				break;
		        				
		        case "Polyhedron": clearAll();
		        				getPolyhedron();
		        				break;
		        				
		        case "Path": clearAll();
		        			getPath();
		        			break;
		        				
		        default: break;
		        
		        }
			}
		};
		
		if(LocCombo.isEnabled()==true){
			
			boxAction.actionPerformed(null);
			
		}
        LocCombo.addActionListener(boxAction);
        
        add.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int count = model.getRowCount() +1;
				model.addRow(new Object[]{count, "", ""});
			}
        	
        });
        
        del.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				//model.removeRow(model.getRowCount() - 1);
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
        
        return panel;
	}
	
	// Functions for placing the right location construct.
	public void clearAll(){
		
		panel.remove(head);
		panel.remove(tableContainer);
		panel.remove(heightContainer);
		panel.remove(polySideLabel);
		panel.remove(polyHeightLabel);
		panel.remove(ReferenceLabel);
		panel.remove(AGL);
		panel.remove(HAAT);
		panel.remove(HeightLabel);
		panel.remove(HeightField);
		panel.remove(periodField);
		panel.remove(periodLabel);
		panel.remove(transmitterLabel);
		panel.remove(transmitterField);
		panel.remove(add);
		panel.remove(del);
		panel.remove(save);
		panel.remove(saveExit);
		panel.remove(exit);
		
		panel.repaint();
		panel.revalidate();
	}
	
	public void getPoint(){
		
		head = new JLabel("Location - Point");
		Dimension headSize = head.getPreferredSize();
		head.setBounds(25, 200, headSize.width, headSize.height);
		pointTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		pointTable.setCellSelectionEnabled(true);	

		
		table=pointTable;
		tableContainer = new JScrollPane(table);
		Dimension containerSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 240, 
        		containerSize.width, containerSize.height-250);
		
        Dimension btnSize = saveExit.getPreferredSize();
        save.setBounds(500, 240, btnSize.width, btnSize.height);
        saveExit.setBounds(500, 240 + 50, btnSize.width, btnSize.height);
        exit.setBounds(500, 240+100, btnSize.width, btnSize.height);
        
		panel.add(tableContainer);
		
		panel.add(save);
		panel.add(saveExit);
		panel.add(exit);
		
		panel.add(head);
		panel.repaint();
		panel.revalidate();
	
	}
	
	public void getPointSurface(){
		
		head = new JLabel("Location - Point Surface");
		Dimension headSize = head.getPreferredSize();
		head.setBounds(25, 200, headSize.width, headSize.height);
		
		table=pointSurfaceTable;
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		tableContainer = new JScrollPane(table);
		Dimension containerSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 240, 
        		containerSize.width, containerSize.height-250);
		
		panel.add(tableContainer);
		
		Dimension ReferenceSize = ReferenceLabel.getPreferredSize();
        ReferenceLabel.setBounds(250, 130 +280, ReferenceSize.width, ReferenceSize.height);
        
        Dimension HeightSize = HeightLabel.getPreferredSize();
        HeightLabel.setBounds(25, 130 + 280, HeightSize.width, HeightSize.height);
        
        Dimension HeightFieldSize = HeightField.getPreferredSize();
        HeightField.setBounds(160, 130 + 280, HeightFieldSize.width + 50, HeightFieldSize.height);
        
        Dimension AGLSize = AGL.getPreferredSize();
        AGL.setBounds(350, 125 + 280, AGLSize.width, AGLSize.height);
        
        Dimension HAATSize = HAAT.getPreferredSize();
        HAAT.setBounds(350, 150 + 280, HAATSize.width, HAATSize.height);
        
        Dimension btnSize = saveExit.getPreferredSize();
        save.setBounds(500, 240, btnSize.width, btnSize.height);
        saveExit.setBounds(500, 240 + 50, btnSize.width, btnSize.height);
        exit.setBounds(500, 240+100, btnSize.width, btnSize.height);
        
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(AGL);
        group2.add(HAAT);
		
        panel.add(save);
        panel.add(saveExit);
        panel.add(exit);
        
        panel.add(AGL);
        panel.add(HAAT);
        panel.add(HeightLabel);
        panel.add(HeightField);
        panel.add(ReferenceLabel);
		
		panel.add(head);
		panel.repaint();
		panel.revalidate();
	}
	
	public void getCircularSurface(){
		
		head = new JLabel("Location - Circular Surface");
		Dimension headSize = head.getPreferredSize();
		head.setBounds(25, 200, headSize.width, headSize.height);
		
		table=circularTable;
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		tableContainer = new JScrollPane(table);
		Dimension containerSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 240, 
        		containerSize.width + 250, containerSize.height-250);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(5);
        table.getColumnModel().getColumn(1).setPreferredWidth(5);
        table.getColumnModel().getColumn(2).setPreferredWidth(5);
        table.getColumnModel().getColumn(2).setPreferredWidth(7);
        
		panel.add(tableContainer);
		
		Dimension btnSize = saveExit.getPreferredSize();
        save.setBounds(750, 240, btnSize.width, btnSize.height);
        saveExit.setBounds(750, 240 + 50, btnSize.width, btnSize.height);
        exit.setBounds(750, 240+100, btnSize.width, btnSize.height);
		
		Dimension ReferenceSize = ReferenceLabel.getPreferredSize();
        ReferenceLabel.setBounds(250, 130 +280, ReferenceSize.width, ReferenceSize.height);
        
        Dimension HeightSize = HeightLabel.getPreferredSize();
        HeightLabel.setBounds(25, 130 + 280, HeightSize.width, HeightSize.height);
        
        Dimension HeightFieldSize = HeightField.getPreferredSize();
        HeightField.setBounds(160, 130 + 280, HeightFieldSize.width + 50, HeightFieldSize.height);
        
        Dimension AGLSize = AGL.getPreferredSize();
        AGL.setBounds(350, 125 + 280, AGLSize.width, AGLSize.height);
        
        Dimension HAATSize = HAAT.getPreferredSize();
        HAAT.setBounds(350, 150 + 280, HAATSize.width, HAATSize.height);
        
        Dimension transmitterSize = transmitterLabel.getPreferredSize();
        transmitterLabel.setBounds(25, 130 + 320, transmitterSize.width + 20, transmitterSize.height);
        
        Dimension transmitterFieldSize = transmitterField.getPreferredSize();
        transmitterField.setBounds(180, 130 + 320, transmitterFieldSize.width + 50, transmitterFieldSize.height);
        
        
        panel.add(save);
        panel.add(saveExit);
        panel.add(exit);
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(AGL);
        group2.add(HAAT);
		
        panel.add(AGL);
        panel.add(HAAT);
        panel.add(HeightLabel);
        panel.add(HeightField);
        panel.add(ReferenceLabel);
		
		
        panel.add(transmitterLabel);
        panel.add(transmitterField);
		panel.add(head);
		panel.repaint();
		panel.revalidate();
	}
	
	public void getPolygonSurface(){
		
		head = new JLabel("Location - Polygon Surface");
		Dimension headSize = head.getPreferredSize();
		head.setBounds(25, 200, headSize.width, headSize.height);
		
		table=polygonTable;
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		tableContainer = new JScrollPane(table);
		Dimension containerSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 240, 
        		containerSize.width + 250, containerSize.height-250);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(5);
        
		panel.add(tableContainer);
		
		Dimension btnSize = del.getPreferredSize();
        save.setBounds(870, 240, btnSize.width, btnSize.height);
        saveExit.setBounds(870, 240 + 50, btnSize.width, btnSize.height);
        exit.setBounds(870, 240+100, btnSize.width, btnSize.height);
		add.setBounds(740, 240, btnSize.width, btnSize.height);
		del.setBounds(740, 240+50, btnSize.width, btnSize.height);
        
		Dimension ReferenceSize = ReferenceLabel.getPreferredSize();
        ReferenceLabel.setBounds(250, 130 +280, ReferenceSize.width, ReferenceSize.height);
        
        Dimension HeightSize = HeightLabel.getPreferredSize();
        HeightLabel.setBounds(25, 130 + 280, HeightSize.width, HeightSize.height);
        
        Dimension HeightFieldSize = HeightField.getPreferredSize();
        HeightField.setBounds(160, 130 + 280, HeightFieldSize.width + 50, HeightFieldSize.height);
        
        Dimension AGLSize = AGL.getPreferredSize();
        AGL.setBounds(350, 125 + 280, AGLSize.width, AGLSize.height);
        
        Dimension HAATSize = HAAT.getPreferredSize();
        HAAT.setBounds(350, 150 + 280, HAATSize.width, HAATSize.height);
        
        Dimension transmitterSize = transmitterLabel.getPreferredSize();
        transmitterLabel.setBounds(25, 130 + 320, transmitterSize.width + 20, transmitterSize.height);
        
        Dimension transmitterFieldSize = transmitterField.getPreferredSize();
        transmitterField.setBounds(180, 130 + 320, transmitterFieldSize.width + 50, transmitterFieldSize.height);
        
        
        
        panel.add(save);
        panel.add(saveExit);
        panel.add(exit);
        panel.add(add);
        panel.add(del);
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(AGL);
        group2.add(HAAT);
		
        panel.add(AGL);
        panel.add(HAAT);
        panel.add(HeightLabel);
        panel.add(HeightField);
        panel.add(ReferenceLabel);
		
		panel.add(transmitterLabel);
		panel.add(transmitterField);
        
		panel.add(head);
		panel.repaint();
		panel.revalidate();
	}
	
	public void getCylinder(){
		
		head = new JLabel("Location - Cylinder");
		Dimension headSize = head.getPreferredSize();
		head.setBounds(25, 200, headSize.width, headSize.height);
		
		table=cylinderTable;
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		tableContainer = new JScrollPane(table);
		Dimension containerSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 240, 
        		containerSize.width + 250, containerSize.height-250);
        
        Dimension headerSize = table.getTableHeader().getPreferredSize();
        table.getTableHeader().setPreferredSize(new Dimension(headerSize.width,headerSize.height*3 + 10));
     //   table.getColumnModel().getColumn(0).setPreferredWidth(5);
        
		panel.add(tableContainer);
		
		Dimension btnSize = saveExit.getPreferredSize();
        save.setBounds(750, 240, btnSize.width, btnSize.height);
        saveExit.setBounds(750, 240 + 50, btnSize.width, btnSize.height);
        exit.setBounds(750, 240+100, btnSize.width, btnSize.height);
		
		Dimension ReferenceSize = ReferenceLabel.getPreferredSize();
        ReferenceLabel.setBounds(250, 130 +280, ReferenceSize.width, ReferenceSize.height);
        
        Dimension HeightSize = HeightLabel.getPreferredSize();
        HeightLabel.setBounds(25, 130 + 280, HeightSize.width, HeightSize.height);
        
        Dimension HeightFieldSize = HeightField.getPreferredSize();
        HeightField.setBounds(160, 130 + 280, HeightFieldSize.width + 50, HeightFieldSize.height);
        
        Dimension AGLSize = AGL.getPreferredSize();
        AGL.setBounds(350, 125 + 280, AGLSize.width, AGLSize.height);
        
        Dimension HAATSize = HAAT.getPreferredSize();
        HAAT.setBounds(350, 150 + 280, HAATSize.width, HAATSize.height);
        
        Dimension transmitterSize = transmitterLabel.getPreferredSize();
        transmitterLabel.setBounds(25, 130 + 320, transmitterSize.width + 20, transmitterSize.height);
        
        Dimension transmitterFieldSize = transmitterField.getPreferredSize();
        transmitterField.setBounds(180, 130 + 320, transmitterFieldSize.width + 50, transmitterFieldSize.height);
        
        
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(AGL);
        group2.add(HAAT);
		
        panel.add(save);
        panel.add(saveExit);
        panel.add(exit);
        
        panel.add(AGL);
        panel.add(HAAT);
        panel.add(HeightLabel);
        panel.add(HeightField);
        panel.add(ReferenceLabel);
		
		panel.add(transmitterLabel);
		panel.add(transmitterField);
        
		panel.add(head);
		panel.repaint();
		panel.revalidate();
	}
	
	public void getPolyhedron(){
		
		head = new JLabel("Location - Polyhedron");
		Dimension headSize = head.getPreferredSize();
		head.setBounds(25, 200, headSize.width, headSize.height);
		
		table=polyhedronTable;
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		heightTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		tableContainer = new JScrollPane(table);
		Dimension containerSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 270, 
        		containerSize.width + 250, containerSize.height-250);        
		panel.add(tableContainer);
		table.getColumnModel().getColumn(0).setPreferredWidth(5);
		
		Dimension btnSize = del.getPreferredSize();
        save.setBounds(870, 270, btnSize.width, btnSize.height);
        saveExit.setBounds(870, 270 + 50, btnSize.width, btnSize.height);
        exit.setBounds(870, 270+100, btnSize.width, btnSize.height);
		add.setBounds(740, 270, btnSize.width, btnSize.height);
		del.setBounds(740, 270+50, btnSize.width, btnSize.height);
		
		Dimension polySideLabelSize = polySideLabel.getPreferredSize();
		polySideLabel.setBounds(25, 240, 
				polySideLabelSize.width, polySideLabelSize.height);
		
		Dimension polyHeightLabelSize = polyHeightLabel.getPreferredSize();
		polyHeightLabel.setBounds(25, 450, polyHeightLabelSize.width, polyHeightLabelSize.height);
		
		Dimension heightContainerSize = heightContainer.getPreferredSize();
        heightContainer.setBounds(25, 480, 
        		heightContainerSize.width + 200, heightContainerSize.height-300);
        panel.add(heightContainer);
		heightTable.getColumnModel().getColumn(0).setWidth(10);
        
        Dimension ReferenceSize = ReferenceLabel.getPreferredSize();
        ReferenceLabel.setBounds(250, 130 +500, ReferenceSize.width, ReferenceSize.height);
        
        Dimension HeightSize = HeightLabel.getPreferredSize();
        HeightLabel.setBounds(25, 130 + 500, HeightSize.width, HeightSize.height);
        
        Dimension HeightFieldSize = HeightField.getPreferredSize();
        HeightField.setBounds(160, 130 + 500, HeightFieldSize.width + 50, HeightFieldSize.height);
        
        Dimension AGLSize = AGL.getPreferredSize();
        AGL.setBounds(350, 125 + 500, AGLSize.width, AGLSize.height);
        
        Dimension HAATSize = HAAT.getPreferredSize();
        HAAT.setBounds(350, 150 + 500, HAATSize.width, HAATSize.height);
        
        Dimension transmitterSize = transmitterLabel.getPreferredSize();
        transmitterLabel.setBounds(25, 130 + 530, transmitterSize.width, transmitterSize.height);
        
        Dimension transmitterFieldSize = HeightField.getPreferredSize();
        transmitterField.setBounds(180, 130 + 530, transmitterFieldSize.width + 50, transmitterFieldSize.height);
        
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(AGL);
        group2.add(HAAT);
        
        panel.add(add);
        panel.add(del);
        panel.add(save);
        panel.add(saveExit);
        panel.add(exit);
        
        panel.add(polySideLabel);
        panel.add(polyHeightLabel);
		panel.add(head);
		panel.add(AGL);
		panel.add(HAAT);
		panel.add(ReferenceLabel);
		panel.add(HeightLabel);
		panel.add(HeightField);
		panel.add(transmitterLabel);
		panel.add(transmitterField);
		
		panel.repaint();
		panel.revalidate();
	}
	
	public void getPath(){
		
		head = new JLabel("Location - Path");
		Dimension headSize = head.getPreferredSize();
		head.setBounds(25, 200, headSize.width, headSize.height);
		
		table=pathTable;
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		tableContainer = new JScrollPane(table);
		Dimension containerSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 240, 
        		containerSize.width, containerSize.height-250);        
		panel.add(tableContainer);
		
		Dimension btnSize = del.getPreferredSize();
        save.setBounds(620, 240, btnSize.width, btnSize.height);
        saveExit.setBounds(620, 240 + 50, btnSize.width, btnSize.height);
        exit.setBounds(620, 240+100, btnSize.width, btnSize.height);
		add.setBounds(490, 240, btnSize.width, btnSize.height);
		del.setBounds(490, 240+50, btnSize.width, btnSize.height);
		
		Dimension periodLabelSize = periodLabel.getPreferredSize();
		periodLabel.setBounds(25, 130 + 280,
				periodLabelSize.width, periodLabelSize.height);
		
		Dimension periodFieldSize = periodField.getPreferredSize();
		periodField.setBounds(180, 130 + 280, 
				periodFieldSize.width + 50, periodFieldSize.height);
		
		Dimension ReferenceSize = ReferenceLabel.getPreferredSize();
        ReferenceLabel.setBounds(250, 130 +320, ReferenceSize.width, ReferenceSize.height);
        
        Dimension HeightSize = HeightLabel.getPreferredSize();
        HeightLabel.setBounds(25, 130 + 320, HeightSize.width, HeightSize.height);
        
        Dimension HeightFieldSize = HeightField.getPreferredSize();
        HeightField.setBounds(160, 130 + 320, HeightFieldSize.width + 50, HeightFieldSize.height);
        
        Dimension AGLSize = AGL.getPreferredSize();
        AGL.setBounds(350, 125 + 320, AGLSize.width, AGLSize.height);
        
        Dimension HAATSize = HAAT.getPreferredSize();
        HAAT.setBounds(350, 150 + 320, HAATSize.width, HAATSize.height);
        
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(AGL);
        group2.add(HAAT);
		
        panel.add(add);
        panel.add(del);
        panel.add(save);
        panel.add(saveExit);
        panel.add(exit);
        
        panel.add(periodField);
        panel.add(periodLabel);
        
        panel.add(AGL);
        panel.add(HAAT);
        panel.add(HeightLabel);
        panel.add(HeightField);
        panel.add(ReferenceLabel);
		
		panel.add(head);
		panel.repaint();
		panel.revalidate();
	}
}
