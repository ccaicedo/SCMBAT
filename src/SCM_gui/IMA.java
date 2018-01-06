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
 * IMA.java
 * Creates GUI panel to input information for IMA mask.
*/

package SCM_gui;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import java.awt.BorderLayout;
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

public class IMA {

	public JTextField RelFreqField = new JTextField();
	JLabel RelFreq = new JLabel("Center Frequency (MHz)");
	JRadioButton relFreqBtn = new JRadioButton("Use relative frequency values");

    JButton b3 = new JButton("Save Data");
    JButton b4 = new JButton("Exit");
 
    JButton b1 = new JButton("Add Row");
    JButton b2 = new JButton("Delete Row");

    JButton NewMap = new JButton("Add new mask");
    JButton Previous = new JButton("Previous");
    JButton Next = new JButton("Next");
    
    Object rowData[][] = { { "1","",""} };
    Object columnNames[] = {"#","Frequency (MHz)","Relative Power (dB)"};
    
    //Adding the order field to store into the XML
    JTextField imOrderField = new JTextField();
    
    TableModel table_model = new DefaultTableModel(rowData, columnNames) {
    	
		private static final long serialVersionUID = 797660903338719428L;

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
    
	JLabel heteroLabel = new JLabel("If this is a superheterodyne receiver, do you want to evaluate image");
    JLabel heteroLabel2 = new JLabel("frequencies produced in it?");
     
    JRadioButton no = new JRadioButton("No");
    JRadioButton yes = new JRadioButton("Yes");
	public int index =0;
	

	JPanel panel = new JPanel();
	public JPanel getPanel(){
		
        panel.setLayout(null);
        
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
        
        panel.add(ConfBtn);

      //To allow the element on the last edit to be saved
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        
        // IMA Table content
        
        JLabel TableLabel = new JLabel("IM Mask");
        Dimension TableLabelSize = TableLabel.getPreferredSize();
        TableLabel.setBounds(25, 140 - 60 , TableLabelSize.width, TableLabelSize.height);    
        
        TableColumn col = table.getColumnModel().getColumn(0);
        col.setPreferredWidth(5);
        
        Dimension tableSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 160 + 140 -60, 
        		tableSize.width , tableSize.height - 200);
        
        JLabel imOrderLabel = new JLabel("IM order for the mask: ");
        imOrderLabel.setFont(font);
       
        
        Dimension imOrderLabelSize = imOrderLabel.getPreferredSize();
        Dimension imOrderFieldSize = imOrderField.getPreferredSize();
        
        imOrderLabel.setBounds(25, 160 - 60,
        		imOrderLabelSize.width, imOrderLabelSize.height);
        imOrderField.setBounds(200, 160 - 60,
        		imOrderFieldSize.width + 50, imOrderFieldSize.height);
        
        panel.add(tableContainer, BorderLayout.CENTER);
     //   panel.add(TableLabel);
   
        /*Considering that the order is inherited from the INtermodulation Mask tab removing it from this panel*/
       
        //panel.add(imOrderLabel);
       //panel.add(imOrderField);

        // Center Frequency data
        
        Dimension relBtnSize = relFreqBtn.getPreferredSize();
        relFreqBtn.setBounds(25, 210-60, relBtnSize.width, relBtnSize.height);
     
        panel.add(relFreqBtn);
        
        RelFreq.setFont(font);
        Dimension sizeLabel = RelFreq.getPreferredSize();
        RelFreq.setBounds(75, 260-60, sizeLabel.width, sizeLabel.height);
     

        panel.add(RelFreq);

        // Creating reference frequency text field

        RelFreqField.setColumns(1);
        RelFreqField.setBounds(255 + 0, 260-60, sizeLabel.width - 120, 5 + sizeLabel.height);
       

        panel.add(RelFreqField);          
        
        if(relFreqBtn.isSelected()==false){
	    	   
	    	   RelFreq.setEnabled(false);
	    	   RelFreqField.setEnabled(false);
	       }else{
 		   RelFreq.setEnabled(true);
  	       RelFreqField.setEnabled(true);
	   }
        
        relFreqBtn.addActionListener(new ActionListener(){
     	   public void actionPerformed(ActionEvent e){
     		  
     		   if("disabled".equals(e.getActionCommand())){
     			   if(relFreqBtn.isSelected()==false){
     		    	   
     		    	   RelFreq.setEnabled(false);
     		    	   RelFreqField.setEnabled(false);
     		       }
     		   }else{
            		   RelFreq.setEnabled(true);
     	    	       RelFreqField.setEnabled(true);
     		   }
     	   }
        });
        
        // Placing buttons for the panel
        
        Dimension size2 = b2.getPreferredSize();
	    b1.setBounds(500 + 10, 160 + 140 - 60,
	                 size2.width, size2.height);
	    b2.setBounds(500 + 10, 210 + 140 - 60,
	                 size2.width, size2.height);
	    b3.setBounds(630 + 10, 160 + 140 - 60,
	                 size2.width, size2.height);
	    b4.setBounds(630 + 10, 210 + 140 - 60,
	                 size2.width, size2.height);       
        
	    Dimension NewMapSize = NewMap.getPreferredSize();
	    NewMap.setBounds(450 + 10, 110 + 30 - 60, NewMapSize.width, NewMapSize.height);
	    
	    Dimension PreviousSize = Previous.getPreferredSize();
	    Previous.setBounds(600 + 10, 110 + 30 - 60, PreviousSize.width, PreviousSize.height);
	    
	    Dimension NextSize = Next.getPreferredSize();
	    Next.setBounds(705 + 10, 110 + 30 - 60, NextSize.width, NextSize.height);
	    
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);    
        
        /*Considering that the relativeFrequency is inherited from the Intermodulation Mask tab removing it from this panel*/
        //panel.add(NewMap);
        //panel.add(Previous);
        //panel.add(Next);
        
        // Add and Remove Rows actions
        
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				int count = model.getRowCount();
				model.addRow(new Object[]{count+1, "", ""});	

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
	
		return panel;
	}
}
