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
 * UnderlayRated.java
 * Creates the GUI components for defining a Rated Underlay Mask in the Underlay Mask Panel.
*/

package SCM_gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class UnderlayRated {

	public JTextField TextField;
	public JTextField BTPRatingField = new JTextField();
	public JTextField PolicyField = new JTextField();
	
	JTextField StartTextField;

	//Global Buttons
	JButton b3 = new JButton("Save Data");
    JButton b4 = new JButton("Exit");
    JTable table;
    
    Object rowData2[][] = { { "1","",""} };
    Object columnNames2[] = {"#","BandWidth (MHz)","Power Adjustment (dB)"};
    TableModel table_model2 = new DefaultTableModel(rowData2, columnNames2)
    		{
				private static final long serialVersionUID = -1075393898938351635L;

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
    
    JScrollPane tableContainer2;
    JButton Add2 = new JButton("Add Row");
    JButton Remove2 = new JButton("Remove Row");
    
    Object rowData3[][] = { { "1","",""} };
    Object columnNames3[] = {"#","BTP (MHz x sec)","Power Adjustment (dB)"};
    TableModel table_model3 = new DefaultTableModel(rowData3, columnNames3)
    		{
    	
				private static final long serialVersionUID = 5632676225961854776L;

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
    
    JScrollPane tableContainer3;
    JButton Add3 = new JButton("Add Row");
    JButton Remove3 = new JButton("Remove Row");
    
    Object rowData4[][] = { { "1","","",""} };
    Object columnNames4[] = {"#","Duty Cycle", "Dwell Time(sec)", "Power Adjustment (dB)"};
    TableModel table_model4 = new DefaultTableModel(rowData4, columnNames4)
    		{
    	
				private static final long serialVersionUID = 5713786734782943722L;

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
    public JTable table4 = new JTable(table_model4);
    
    JScrollPane tableContainer4;
    JButton Add4 = new JButton("Add Row");
    JButton Remove4 = new JButton("Remove Row");
    
    JLabel BWListLabel;
    JLabel BTPRatingLabel;
    JLabel BTPListLabel;
    JLabel DutyLabel;
    JLabel PolicyLabel;
    
	//Get Panel
    public JPanel SpecPanel;
    
    public JLabel BandRatLabel;
    public JTextField BandRatField = new JTextField();
	
    
	public UnderlayRated(){
		
		// Bandwidth Rated Field
		BandRatLabel = new JLabel("Bandwidth rating for this mask (MHz)");
		Dimension BandRatSize = BandRatLabel.getPreferredSize();
		BandRatLabel.setBounds(25, 565, BandRatSize.width, BandRatSize.height);
		
		Dimension BandFieldSize = BandRatField.getPreferredSize();
		BandRatField.setBounds(300, 565, BandFieldSize.width + 40, 1 + BandFieldSize.height);
		
		// BW List Table
		BWListLabel = new JLabel("Power adjustment offsets definition:");
		Dimension BWLabelSize = BWListLabel.getPreferredSize();
		BWListLabel.setBounds(25, 565, BWLabelSize.width, BWLabelSize.height);
        
		tableContainer2 = new JScrollPane(table2);
        Dimension size4 = tableContainer2.getPreferredSize();
        
        table2.getTableHeader().setReorderingAllowed(true);
        table2.getColumnModel().getColumn(0).setPreferredWidth(5);
        table2.getColumnModel().getColumn(0);
        tableContainer2.setBounds(700 - 675, 600,
                size4.width - 50 , size4.height - 250);
		
        // BW List Buttons
        
        Dimension sizeBtn = Add2.getPreferredSize();
        Add2.setBounds(900 - 425, 600,
                     sizeBtn.width + 50, sizeBtn.height);
        Remove2.setBounds(900 - 425, 645,
                sizeBtn.width + 50, sizeBtn.height);
        
        Add2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				((DefaultTableModel) table_model2).addRow(new Object[]{table2.getRowCount()+1, "",""});
			}
		});
        
        Remove2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				//((DefaultTableModel) table_model2).removeRow(table_model2.getRowCount() - 1);
				DefaultTableModel model = (DefaultTableModel) table2.getModel();
				//model.removeRow(model.getRowCount() - 1);
				/*
				 * Allowing the deletion of selected rows
				 */
				int[] selectedRows = table2.getSelectedRows();
				   for(int row=selectedRows.length-1;row>=0;row--){
					int rowNum = selectedRows[row];
				     model.removeRow(rowNum);
				     //Updating the index column - count variable appropriately
				     if(rowNum!=table2.getRowCount())
				     {
				    	 table2.getModel().setValueAt(rowNum+1,rowNum ,0 );
				     }
				     
				   }
			//	model.removeRow(model.getRowCount() - 1);		 
				   for(int i=table2.getRowCount()-1;i>=0;i--)
				   {
					   int curVal = Integer.parseInt(table2.getModel().getValueAt(i, 0).toString());
					   if(curVal!= i+1)
					   {
						   table2.getModel().setValueAt(i+1, i, 0);
					   }
				   }
			}
		});
	
      //To allow the element on the last edit to be saved
      table2.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
      table3.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
      table4.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
    
		table_model2 = (DefaultTableModel) table2.getModel();
		
		// BTP Rated Field
		
		BTPRatingLabel = new JLabel("BTP Rating for this mask (MHz x sec)");
		Dimension BTPRatingSize = BTPRatingLabel.getPreferredSize();
		BTPRatingLabel.setBounds(25, 565, BTPRatingSize.width, BTPRatingSize.height);
		
		Dimension BTPRatingFieldSize = BTPRatingField.getPreferredSize();
		BTPRatingField.setBounds(300, 565, BTPRatingFieldSize.width + 40, 1 + BTPRatingFieldSize.height);
		
		// BTP Rated List Table
		
		BTPListLabel = new JLabel("Power adjustment offsets definition:");
		Dimension BTPListLabelSize = BTPListLabel.getPreferredSize();
		BTPListLabel.setBounds(25, 565, BTPListLabelSize.width, BTPListLabelSize.height);
		        
		tableContainer3 = new JScrollPane(table3);
		Dimension table3Size = tableContainer3.getPreferredSize();
		        
		table3.getTableHeader().setReorderingAllowed(true);
		table3.getColumnModel().getColumn(0).setPreferredWidth(5);
		table3.getColumnModel().getColumn(0);
		tableContainer3.setBounds(700 - 675, 600,
		         table3Size.width - 50 , table3Size.height - 250);
				
		// BTP Rated List Buttons
		        
		Dimension sizeBtn3 = Add3.getPreferredSize();
		Add3.setBounds(900 - 425, 600,
		             sizeBtn3.width + 50, sizeBtn3.height);
		Remove3.setBounds(900 - 425, 645,
		             sizeBtn3.width + 50, sizeBtn3.height);
		       
		Add3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				((DefaultTableModel) table_model3).addRow(new Object[]{table3.getRowCount()+1, "",""});	
				}
			});
		        
		Remove3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					DefaultTableModel model = (DefaultTableModel) table3.getModel();
					//model.removeRow(model.getRowCount() - 1);
					/*
					 * Allowing the deletion of selected rows
					 */
					int[] selectedRows = table3.getSelectedRows();
					   for(int row=selectedRows.length-1;row>=0;row--){
						int rowNum = selectedRows[row];
					     model.removeRow(rowNum);
					     //Updating the index column - count variable appropriately
					     if(rowNum!=table3.getRowCount())
					     {
					    	 table3.getModel().setValueAt(rowNum+1,rowNum ,0 );
					     }
					     
					   }
				//	model.removeRow(model.getRowCount() - 1);		 
					   for(int i=table3.getRowCount()-1;i>=0;i--)
					   {
						   int curVal = Integer.parseInt(table3.getModel().getValueAt(i, 0).toString());
						   if(curVal!= i+1)
						   {
							   table3.getModel().setValueAt(i+1, i, 0);
						   }
					   }
		
					}
				});
			
		table_model3 = (DefaultTableModel) table3.getModel();
		
		// Policy Field
				PolicyLabel = new JLabel("Policy or Protocol Index");
				Dimension PolicySize = PolicyLabel.getPreferredSize();
				PolicyLabel.setBounds(25,565, PolicySize.width, PolicySize.height);
				
				Dimension PolicyFieldSize = PolicyField.getPreferredSize();
				PolicyField.setBounds(250, 565, PolicyFieldSize.width + 40, 1 + PolicyFieldSize.height);
		
		// Duty Cycle List Table
		
				DutyLabel = new JLabel("Power adjustment offsets definition:");
				Dimension DutyLabelSize = DutyLabel.getPreferredSize();
				DutyLabel.setBounds(25, 565, DutyLabelSize.width, DutyLabelSize.height);
				        
				tableContainer4 = new JScrollPane(table4);
				Dimension table4Size = tableContainer4.getPreferredSize();
				        
				table4.getTableHeader().setReorderingAllowed(true);
				table4.getColumnModel().getColumn(0).setPreferredWidth(5);
				table4.getColumnModel().getColumn(1).setPreferredWidth(25);
				table4.getColumnModel().getColumn(2).setPreferredWidth(50);
				table4.getColumnModel().getColumn(0);
				tableContainer4.setBounds(700 - 675, 600,
				         table4Size.width + 5, table4Size.height - 250);
						
				// Duty Cycle List Buttons
				        
				Dimension sizeBtn4 = Add4.getPreferredSize();
				Add4.setBounds(900 - 370, 600,
				             sizeBtn4.width + 50, sizeBtn4.height);
				Remove4.setBounds(900 - 370, 645,
				             sizeBtn4.width + 50, sizeBtn4.height);
				       
				Add4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							
						((DefaultTableModel) table_model4).addRow(new Object[]{table4.getRowCount()+1, "", "",""});
						}
					});
				        
				Remove4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							
					//	((DefaultTableModel) table_model4).removeRow(table_model4.getRowCount() - 1);
							DefaultTableModel model = (DefaultTableModel) table4.getModel();
							//model.removeRow(model.getRowCount() - 1);
							/*
							 * Allowing the deletion of selected rows
							 */
							int[] selectedRows = table4.getSelectedRows();
							   for(int row=selectedRows.length-1;row>=0;row--){
								int rowNum = selectedRows[row];
							     model.removeRow(rowNum);
							     //Updating the index column - count variable appropriately
							     if(rowNum!=table4.getRowCount())
							     {
							    	 table4.getModel().setValueAt(rowNum+1,rowNum ,0 );
							     }
							     
							   }
						//	model.removeRow(model.getRowCount() - 1);		 
							   for(int i=table4.getRowCount()-1;i>=0;i--)
							   {
								   int curVal = Integer.parseInt(table4.getModel().getValueAt(i, 0).toString());
								   if(curVal!= i+1)
								   {
									   table4.getModel().setValueAt(i+1, i, 0);
								   }
							   }
				
						
						}
						});
					
				table_model4 = (DefaultTableModel) table4.getModel();
		

				
	}
	
	// Functions for placing the right Rated Underlay Mask components:
	
	public void getBR(JPanel panel){
		
        panel.add(BandRatField);
        panel.add(BandRatLabel);
        
        panel.revalidate();
		panel.repaint();
	}
	
	public void removeBR(JPanel panel){
		
		panel.remove(BandRatField);
        panel.remove(BandRatLabel);
        
        panel.revalidate();
		panel.repaint();
		
	}
		
	public void getBRList(JPanel panel){
		
           
        panel.revalidate();
		panel.repaint();
		panel.add(tableContainer2, BorderLayout.CENTER);
        panel.add(BWListLabel);
        panel.add(Add2);
        panel.add(Remove2);

        panel.revalidate();
		panel.repaint();
	}
	
	public void removeBRList(JPanel panel){
		
		
        panel.revalidate();
		panel.repaint();
		panel.remove(tableContainer2);
		panel.remove(BWListLabel);
		panel.remove(Add2);
		panel.remove(Remove2);
		
        panel.revalidate();
		panel.repaint();
	}
	
	public void getBTPRating(JPanel panel){
		
		panel.add(BTPRatingField);
		panel.add(BTPRatingLabel);
		
		panel.revalidate();
		panel.repaint();
		
	}
	
	
	public void removeBTPRating(JPanel panel){
	
		panel.remove(BTPRatingField);
		panel.remove(BTPRatingLabel);
		
		panel.revalidate();
		panel.repaint();
	}
	
	public void getBTPList(JPanel panel){
		
        panel.revalidate();
		panel.repaint();
		panel.add(tableContainer3, BorderLayout.CENTER);
        panel.add(BTPListLabel);
        panel.add(Add3);
        panel.add(Remove3);

        panel.revalidate();
		panel.repaint();
	}
	
	public void removeBTPList(JPanel panel){
		
        panel.revalidate();
		panel.repaint();
		panel.remove(tableContainer3);
		panel.remove(BTPListLabel);
		panel.remove(Add3);
		panel.remove(Remove3);
		
        panel.revalidate();
		panel.repaint();
	}

	public void removeDutyCycle(JPanel panel) {
        
		panel.revalidate();
		panel.repaint();
		panel.remove(tableContainer4);
		panel.remove(DutyLabel);
		panel.remove(Add4);
		panel.remove(Remove4);
		
        panel.revalidate();
		panel.repaint();
		
	}

	public void removePolicy(JPanel panel) {
		
        panel.remove(PolicyField);
        panel.remove(PolicyLabel);
        
        panel.revalidate();
		panel.repaint();	
	}

	public void getDutyCycle(JPanel panel) {

		panel.revalidate();
		panel.repaint();
		panel.add(tableContainer4, BorderLayout.CENTER);
        panel.add(DutyLabel);
        panel.add(Add4);
        panel.add(Remove4);

        panel.revalidate();
		panel.repaint();
	}

	public void getPolicy(JPanel panel) {

		panel.add(PolicyField);
        panel.add(PolicyLabel);
        
        panel.revalidate();
		panel.repaint();
		
	}
	
}
