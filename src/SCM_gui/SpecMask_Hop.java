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
 * SpecMask_Hop.java
 * Creates components for entering Spectrum Hopping information in the Spectrum Mask panel
*/

package SCM_gui;


import java.awt.Dimension;
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



public class SpecMask_Hop {
	
	
	public String device = "Default";
	public String SaveName = "NewFile";

	JLabel RevisitLabel;
	JLabel Dwell;
	public JTextField DwellField = new JTextField();
	JTextField ResTextField;
	public JTextField RevisitField = new JTextField();
	JTextField StartTextField;
	
	private int count2=2;
	private int count3=2;
	JScrollPane tableContainer2;
	JScrollPane tableContainer3;
	JScrollPane tableContainer4;
	
	Object rowData2[][] = { { "1",""} };
    Object columnNames2[] = { "#", "Frequency List"};
    TableModel table_model2 = new DefaultTableModel(rowData2, columnNames2) {
    	
		private static final long serialVersionUID = -4822847901842739752L;

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
    
    Object rowData3[][] = { { "1","",""} };
    Object columnNames3[] = {"#","Start Freq", "End Freq"};
    TableModel table_model3 = new DefaultTableModel(rowData3, columnNames3) {
    	
		private static final long serialVersionUID = 5751769122720240901L;

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
	public JTable table3 = new JTable(table_model3);
	
	Object rowData4[][] = { { "","",""} };
    Object columnNames4[] = { "Start Freq", "End Freq","Interval"};
    TableModel table_model4 = new DefaultTableModel(rowData4, columnNames4);
    public JTable table4 = new JTable(table_model4);
	
    public JRadioButton freqListBtn1 = new JRadioButton("Declare List");
    public JRadioButton freqListBtn2 = new JRadioButton("Declare List with interval");
    
	public JRadioButton rdbtnFreqList = new JRadioButton("Frequency List");
	public JRadioButton rdbtnBandList = new JRadioButton("Band List");
	
	JButton Add2; 
    JButton Remove2;
    
    JButton Add3; 
    JButton Remove3;
	JButton Test = new JButton("Test");
    
	JLabel BandListLabel;
	JLabel FreqListLabel;
	
	ButtonGroup group = new ButtonGroup();
	
	public void SpecHop(){
		
		// Dwell Time
        Dwell = new JLabel("Dwell Time (ms)");
		Dimension DwellLabelSize = Dwell.getPreferredSize();
        Dwell.setBounds(700 - 225, 80 + 420,
                 DwellLabelSize.width, DwellLabelSize.height);
        
        DwellField.setColumns(10);
        DwellField.setBounds(800 - 180, 80 + 420, DwellLabelSize.width + 10, DwellLabelSize.height + 5);
        
        // Revisit Period
        RevisitLabel = new JLabel("Revisit Period (ms)");
		Dimension RevisitLabelSize = RevisitLabel.getPreferredSize();
        RevisitLabel.setBounds(700- 225, 120 + 420,
                 RevisitLabelSize.width, RevisitLabelSize.height);
        
        RevisitField.setColumns(10); 
        RevisitField.setBounds(800 - 180, 120 + 420, DwellLabelSize.width + 10, DwellLabelSize.height +5);
        
        // FreqList Table
		FreqListLabel = new JLabel("Center frequency list definition");
		Dimension FreqLabSize = FreqListLabel.getPreferredSize();
		FreqListLabel.setBounds(25, 450, FreqLabSize.width, FreqLabSize.height);
        
		//To allow the element on the last edit to be saved
        table2.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        table3.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        table4.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);


        tableContainer2 = new JScrollPane(table2);
        Dimension size4 = tableContainer2.getPreferredSize();
        
        table2.getTableHeader().setReorderingAllowed(true);
        table2.getColumnModel().getColumn(0).setPreferredWidth(15);
        table2.getColumnModel().getColumn(0);
        tableContainer2.setBounds(700 - 675, 500,
                size4.width - 300 , size4.height - 250);
		
        // Frequency List Buttons
        Add2 = new JButton("Add Row");
        Remove2 = new JButton("Remove Row");
        
        Dimension sizeBtn = Add2.getPreferredSize();
        Add2.setBounds(900 - 675, 500,
                     sizeBtn.width + 50, sizeBtn.height);
        Remove2.setBounds(900 - 675, 550,
                sizeBtn.width + 50, sizeBtn.height);
        
        Add2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				((DefaultTableModel) table_model2).addRow(new Object[]{count2, ""});	
			    count2 = count2 + 1;
			}
		});
			
		Remove2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
			//	((DefaultTableModel) table_model2).removeRow(table_model2.getRowCount() - 1);
				DefaultTableModel model = (DefaultTableModel) table2.getModel();

				/*
				 * Allowing the deletion of selected rows
				 */
				int[] selectedRows =  table2.getSelectedRows();
				int numberOfRows = selectedRows.length;
				   for(int row=selectedRows.length-1;row>=0;row--){
					int rowNum = selectedRows[row];
					model.removeRow(rowNum);
				     //Updating the index column - count variable appropriately
				     if(rowNum!=((DefaultTableModel) table_model2).getRowCount())
				     {
				    	 model.setValueAt(rowNum+1,rowNum ,0 );
				     }
				     
				   }
			//	model.removeRow(model.getRowCount() - 1);
				   
				   count2 = count2 - numberOfRows;
				   for(int i=count2;i>=0;i--)
				   {
					   int curVal = Integer.parseInt(model.getValueAt(i, 0).toString());
					   if(curVal!= i+1)
					   {
						   model.setValueAt(i+1, i, 0);
					   }
				   }
			
				
				//count2 = count2 - 1;
			}
		});
	
		table_model2 = (DefaultTableModel) table2.getModel();
		
		//Band List Data
		BandListLabel = new JLabel("Band list definition");
		Dimension BandLabSize = BandListLabel.getPreferredSize();
		BandListLabel.setBounds(25, 450, BandLabSize.width, BandLabSize.height);
		
        tableContainer3 = new JScrollPane(table3);
        Dimension size6 = tableContainer3.getPreferredSize();
        
        table3.getTableHeader().setReorderingAllowed(true);
        table3.getColumnModel().getColumn(0).setPreferredWidth(15);
        table3.getColumnModel().getColumn(0);
        tableContainer3.setBounds(700 - 675, 500,
                size6.width - 220 , size6.height - 250);
        
        tableContainer4 = new JScrollPane(table4);
        Dimension size7 = tableContainer4.getPreferredSize();
        
        table4.getTableHeader().setReorderingAllowed(true);
        tableContainer4.setBounds(700 - 675, 500,
                size7.width - 220 , size7.height - 250);
        
        // Band List Add and Remove Buttons
        Add3 = new JButton("Add Row");
        Remove3 = new JButton("Remove Row");

        Add3.setBounds(900 - 625, 500,
                     sizeBtn.width + 50, sizeBtn.height);
        Remove3.setBounds(900 - 625, 550 + 0,
                sizeBtn.width + 50, sizeBtn.height);
        
        
        Add3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				((DefaultTableModel) table_model3).addRow(new Object[]{count3, ""});	
			    count3 = count3 + 1;
			}
		});
			
		Remove3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				//((DefaultTableModel) table_model3).removeRow(table_model3.getRowCount() - 1);
				DefaultTableModel model = (DefaultTableModel) table3.getModel();

				/*
				 * Allowing the deletion of selected rows
				 */
				int[] selectedRows =  table3.getSelectedRows();
				int numberOfRows = selectedRows.length;
				   for(int row=selectedRows.length-1;row>=0;row--){
					int rowNum = selectedRows[row];
					model.removeRow(rowNum);
				     //Updating the index column - count variable appropriately
				     if(rowNum!=((DefaultTableModel) table_model3).getRowCount())
				     {
				    	 model.setValueAt(rowNum+1,rowNum ,0 );
				     }
				     
				   }
			//	model.removeRow(model.getRowCount() - 1);
				   
				   count3 = count3 - numberOfRows;
				   for(int i=count3;i>=0;i--)
				   {
					   int curVal = Integer.parseInt(model.getValueAt(i, 0).toString());
					   if(curVal!= i+1)
					   {
						   model.setValueAt(i+1, i, 0);
					   }
				   }
			
			
			//	count3 = count3 - 1;
			}
		});		
		
		Dimension btn1Size = freqListBtn1.getPreferredSize();
		freqListBtn1.setBounds(900-625, 600, btn1Size.width, btn1Size.height);
		freqListBtn1.setSelected(true);
		
		Dimension btn2Size = freqListBtn2.getPreferredSize();
		freqListBtn2.setBounds(900-625, 630, btn2Size.width, btn2Size.height);
	
		ButtonGroup freqListgroup = new ButtonGroup();
		freqListgroup.add(freqListBtn1);
		freqListgroup.add(freqListBtn2);
	}
	
	// Functions for placing the right frequency hopping components.
	
	public void getFreqList(final JPanel panel){
		
        panel.add(Add2);
        panel.add(Remove2);  
        panel.add(tableContainer2);
        panel.add(Dwell);
        panel.add(DwellField);
        panel.add(RevisitLabel);
        panel.add(RevisitField);
        panel.add(FreqListLabel);
        panel.add(freqListBtn1);
        panel.add(freqListBtn2);
        
        freqListBtn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				panel.add(tableContainer2);
				panel.add(Add2);
				panel.add(Remove2);
				panel.remove(tableContainer4);
				
				panel.repaint();
				panel.revalidate();
			}
			
		});
		
		freqListBtn2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				panel.remove(tableContainer2);
				panel.remove(Add2);
				panel.remove(Remove2);
				panel.add(tableContainer4);
				
				panel.repaint();
				panel.revalidate();
			}
			
		});
        
        panel.revalidate();
		panel.repaint();
	}
	
	public void removeFreqList(JPanel panel){
		
		panel.remove(Add2);
        panel.remove(Remove2);  
        panel.remove(tableContainer2);
        panel.remove(Dwell);
        panel.remove(DwellField);
        panel.remove(RevisitLabel);
        panel.remove(RevisitField);
        panel.remove(FreqListLabel);
        panel.remove(freqListBtn1);
        panel.remove(freqListBtn2);
        
        panel.revalidate();
		panel.repaint();
		
	}
		
	public void getBandList(JPanel panel){
		
        panel.add(Add3);
        panel.add(Remove3);  
        panel.add(tableContainer3);
        panel.add(Dwell);
        panel.add(DwellField);
        panel.add(RevisitLabel);
        panel.add(RevisitField);
        panel.add(BandListLabel);
        
        panel.revalidate();
		panel.repaint();
        
	}
	
	public void removeBandList(JPanel panel){
		
		panel.remove(Add3);
        panel.remove(Remove3);  
        panel.remove(tableContainer3);
        panel.remove(Dwell);
        panel.remove(DwellField);
        panel.remove(RevisitLabel);
        panel.remove(RevisitField);
        panel.remove(BandListLabel);
        
        panel.revalidate();
		panel.repaint();
	}
		
}
