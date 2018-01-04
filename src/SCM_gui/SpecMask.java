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
 * SpecMask.java
 * Creates the GUI panel for theSpectrum Mask construct
*/

package SCM_gui;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

import Execute.MethodAnalysis;
import SCM_home.Home;

import java.awt.BorderLayout;
import java.awt.Color;
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

 
public class SpecMask {
	
	final Logger logger = Logger.getLogger(SpecMask.class);
	final JPanel SpecPanel = new JPanel();
	
	public JRadioButton FreqListBtn = new JRadioButton("Center frequency list");
	public JRadioButton BandListBtn = new JRadioButton("Band list");
	
	public JTextField TextField = new JTextField();
	JTextField DwellField;
	public JTextField ResTextField = new JTextField();
	JTextField RevisitField;
	JTextField StartTextField;
	public JRadioButton RelFreq = new JRadioButton("Use relative frequency values");
	public SpecMask_Hop SpecHop = new SpecMask_Hop();
    
	//Global Buttons
	JButton b3 = new JButton("Save Data");
    JButton b4 = new JButton("Exit");
    JButton Test = new JButton("Test");
    
    String column_names[] = {"#","Frequency (MHz)", "Power (dB)"};
    Object rowData[][] = { { "1","",""} };
    TableModel table_model = new DefaultTableModel(rowData,column_names) {
    	
		private static final long serialVersionUID = 8235600107489275732L;

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
    
    JTable table2;
    
    public JLabel RefFreq= new JLabel("Center Frequency (MHz)");
    public JLabel ResBW;
    
    public JRadioButton no = new JRadioButton("No");
    public JRadioButton yes = new JRadioButton("Yes");
    public boolean noInitialState = true;
    
	public JPanel getPanel(){

		logger.addAppender(Home.appender);
		SpecHop.SpecHop();
        SpecPanel.setLayout(null);
        
        // Basic Font
        Font font = new Font("Arial", Font.PLAIN, 12);
        
        // Panel for frequency hopping
        
        JLabel FreqHopLabel = new JLabel("This is a frequency hopping system");
        
        Dimension HopSize = FreqHopLabel.getPreferredSize();
        FreqHopLabel.setBounds(25,35+45,HopSize.width, HopSize.height);
        Dimension RadBtnSize = yes.getPreferredSize();
        no.setBounds(330 , 35 + 40, RadBtnSize.width, RadBtnSize.height);
        yes.setBounds(400 , 35 + 40, RadBtnSize.width, RadBtnSize.height);
        
        SpecPanel.add(FreqHopLabel);
        SpecPanel.add(no);
        SpecPanel.add(yes);

        // Controls for a frequency hopping system.
        
        final JLabel HopLabel = new JLabel("Specify frequency hopping");
        final JLabel HopLabel2 = new JLabel("characteristics via a:");
        HopLabel.setFont(font);
        HopLabel2.setFont(font);
        
        Dimension HopLabelSize = HopLabel.getPreferredSize();
        Dimension FreqListBtnSize = FreqListBtn.getPreferredSize();
        Dimension BandListBtnSize = BandListBtn.getPreferredSize();
        
        FreqListBtn.setFont(font);
        BandListBtn.setFont(font);
        
        HopLabel.setBounds(330, 35 + 80, HopLabelSize.width, HopLabelSize.height);
        HopLabel2.setBounds(330, 35 + 100, HopLabelSize.width, HopLabelSize.height);
        FreqListBtn.setBounds(550, 35 + 80, FreqListBtnSize.width, FreqListBtnSize.height);
        BandListBtn.setBounds(550, 35 + 100, BandListBtnSize.width, BandListBtnSize.height);
        
        SpecPanel.add(FreqListBtn);
        SpecPanel.add(BandListBtn);
        SpecPanel.add(HopLabel);
        SpecPanel.add(HopLabel2);
        
        ButtonGroup HopGroup = new ButtonGroup();
        HopGroup.add(BandListBtn);
        HopGroup.add(FreqListBtn);
       
        ButtonGroup group1 = new ButtonGroup();
        group1.add(no);
        group1.add(yes);
        
       no.setSelected(noInitialState);
       no.setEnabled(true);
       FreqListBtn.setEnabled(false);
	   BandListBtn.setEnabled(false);

	   // Setting Frequency List Radio Button Operation
	   
       final ActionListener FreqAction = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				FreqListBtn.setEnabled(true);
				
					if(FreqListBtn.isSelected()==true){
						System.out.println("Frequency List selected");
						logger.info("Frequency List selected");
						SpecHop.removeBandList(SpecPanel);
						SpecPanel.revalidate();
						SpecPanel.repaint();
						
						SpecHop.getFreqList(SpecPanel);
						SpecPanel.revalidate();
						SpecPanel.repaint();
					}else{
						
					}
			}        	
       };

    // Setting Band List Radio Button Operation
       
      final ActionListener BandAction = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				BandListBtn.setSelected(true);
				if(BandListBtn.isSelected()==true){
					logger.info("Band List selected");
					System.out.println("Band List selected");
					SpecHop.removeFreqList(SpecPanel);
					SpecPanel.revalidate();
					SpecPanel.repaint();
					
					SpecHop.getBandList(SpecPanel);
					SpecPanel.revalidate();
					SpecPanel.repaint();
				}
			}        	
       };
       
       no.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent e){
    		   
    		   FreqListBtn.setEnabled(false);
    		   BandListBtn.setEnabled(false);
    		   
    		   HopLabel.setEnabled(false);
    		   HopLabel2.setEnabled(false);
    		   
    		   SpecHop.removeBandList(SpecPanel);
    		   SpecHop.removeFreqList(SpecPanel);
    		   SpecPanel.revalidate();
    		   SpecPanel.repaint();
    	   } 
       }); 
       
       ActionEvent e = null;
       
       if(yes.isSelected()==true){
			
		   FreqListBtn.setEnabled(true);
		   BandListBtn.setEnabled(true);
		   
		   if(BandListBtn.isSelected()==true){
			 BandAction.actionPerformed(e);  
		   }
		   
		   if(FreqListBtn.isSelected()==true){
			  FreqAction.actionPerformed(e);
		   }	   		   
		   
	   }else{
		   if(no.isSelected()==true){
			   
    		   FreqListBtn.setEnabled(false);
    		   BandListBtn.setEnabled(false);
    		   
    		   HopLabel.setEnabled(false);
    		   HopLabel2.setEnabled(false);
			   
		   }
	   }

       // Setting Relative frequency operation
       
       if(RelFreq.isSelected()==false){
    	   
    	   RefFreq.setEnabled(false);
    	   TextField.setEnabled(false);
       }
       
       
       RelFreq.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent e){
    		  
    		   if("disabled".equals(e.getActionCommand())){
    			   if(RelFreq.isSelected()==false){
    		    	   
    		    	   RefFreq.setEnabled(false);
    		    	   TextField.setEnabled(false);
    		       }
    		   }else{
           		   RefFreq.setEnabled(true);
    	    	   TextField.setEnabled(true);
    		   }
    	   }
       });
       
       yes.addActionListener(new ActionListener(){
    	   public void actionPerformed(ActionEvent e){
    		   
    		   if(yes.isSelected()==true){
    			
    			   FreqListBtn.setEnabled(true);
        		   BandListBtn.setEnabled(true);
        		   
        		   if(BandListBtn.isSelected()==true){
        			 BandAction.actionPerformed(e);  
        		   }
        		   
        		   if(FreqListBtn.isSelected()==true){
        			  FreqAction.actionPerformed(e);
        		   }
        		   

        		   HopLabel.setForeground(Color.BLACK);
        		   HopLabel2.setForeground(Color.BLACK);
        		   
        		   HopLabel.setEnabled(true);
        		   HopLabel2.setEnabled(true);
        		   
        		   
    		   }
    		      
    	   }
       });
        
       FreqListBtn.addActionListener(FreqAction); 
       BandListBtn.addActionListener(BandAction);

       // Panel for Confidence
        
        JLabel ConfText = new JLabel("This construct supports confidence values. If you need to specify a confidence");
        JLabel ConfText2 = new JLabel("value different than 1 for the values of this construct please press this button");
        JButton ConfBtn = new JButton("Define Confidence Values");
        
        Dimension ConfBtnSize = ConfBtn.getPreferredSize();
        Dimension ConfTextSize = ConfText2.getPreferredSize();
        
        ConfBtn.setBounds(25, 20, ConfBtnSize.width, ConfBtnSize.height);
        ConfText.setBounds(25,20,ConfTextSize.width,ConfTextSize.height);
        ConfText2.setBounds(25,40,ConfTextSize.width,ConfTextSize.height);
        
        SpecPanel.add(ConfBtn);
        
        // Create Buttons
        
        JButton b1 = new JButton("Add Row");
        JButton b2 = new JButton("Remove Row");        
        
        Dimension size2 = b3.getPreferredSize();
        b1.setBounds(400 + 0, 130 + 150,
                     size2.width + 30, size2.height);        
        b2.setBounds(400 + 0, 180 + 150,
                size2.width + 30, size2.height);
        b3.setBounds(550, 180 + 150,
                size2.width + 30, size2.height);
        b4.setBounds(550, 130 + 150, 
        		size2.width + 30, size2.height);
        
        Test.setBounds(550, 500, size2.width, size2.height);
                
        
        SpecPanel.add(b1);
        SpecPanel.add(b2);
        SpecPanel.add(b3);
        SpecPanel.add(b4);     
        
        // Creating Resolution Bandwidth Label and Text Field

        ResBW = new JLabel("Resolution Bandwidth (Mhz)");
        Dimension sizeBW = ResBW.getPreferredSize();
        ResBW.setBounds(25 + 300, 35 + 150, sizeBW.width, sizeBW.height);
        SpecPanel.add(ResBW);
     
        ResTextField.setColumns(1);
        ResTextField.setBounds(235 + 300, 35 + 150, sizeBW.width - 150, 5 + sizeBW.height);
        SpecPanel.add(ResTextField);

        Dimension RelFreqSize = RelFreq.getPreferredSize();
        RelFreq.setBounds(25, 30 + 150,
                 RelFreqSize.width, RelFreqSize.height);
        SpecPanel.add(RelFreq);
        
        // Spec Mask Data

        
        // Creating Reference Frequency label
        RefFreq.setFont(font);
        Dimension sizeLabel = RefFreq.getPreferredSize();
        RefFreq.setBounds(75, 80 + 150, sizeLabel.width, sizeLabel.height);
        SpecPanel.add(RefFreq);

        // Creating reference frequency text field
        TextField.setColumns(1);
        TextField.setBounds(255 + 0, 80 + 150, sizeLabel.width - 120, 5 + sizeLabel.height);
        SpecPanel.add(TextField);
        
        // Positioning table
                
        Dimension size3 = tableContainer.getPreferredSize();
        
        //To allow the element on the last edit to be saved
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
  
        
        
        table.getTableHeader().setReorderingAllowed(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        table.getColumnModel().getColumn(0);
        tableContainer.setBounds(25, 130 + 150,
                size3.width - 100, size3.height - 300);
        
        SpecPanel.add(tableContainer, BorderLayout.CENTER);

        // Button Actions        
      
        b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[]{table.getRowCount()+1, "", ""});
			}
		});
		
		
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
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
        
        return SpecPanel;
	
	}
}