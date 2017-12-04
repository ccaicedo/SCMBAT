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
 *  IMC.java
 *  Creates panel to input data for an IMC Mask
*/

package SCM_gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * IMC.java
 * Creates GUI panel to input information for IMC mask
*/

public class IMC {	
	
	public JTextField IntFreqField;
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
    
    //Adding the order field to save into the XML
    public JTextField imOrderField = new JTextField();
    
    TableModel table_model = new DefaultTableModel(rowData, columnNames) {
    	
		private static final long serialVersionUID = 1848549027172860034L;

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
    
	/*
	 *Adding the IMA UI Controls
	 */
	public JTextField imaRelFreqField = new JTextField();
	JLabel imaRelFreq = new JLabel("Center Frequency (MHz)");
	JRadioButton imarelFreqBtn = new JRadioButton("Use relative frequency values");

    JButton imab3 = new JButton("Save Data");
    JButton imab4 = new JButton("Exit");
 
    JButton imab1 = new JButton("Add Row");
    JButton imab2 = new JButton("Delete Row");

    JButton imaNewMap = new JButton("Add new mask");
    JButton imaPrevious = new JButton("Previous");
    JButton imaNext = new JButton("Next");
    
    Object imarowData[][] = { { "1","",""} };
    Object imacolumnNames[] = {"#","Frequency (MHz)","Relative Power (dB)"};
    
    
    TableModel imatable_model = new DefaultTableModel(imarowData, imacolumnNames) {
    	
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
    public JTable imatable = new JTable(imatable_model);
	JScrollPane imatableContainer = new JScrollPane(imatable);
	
	
	////////////////////
	
	
	
	JLabel heteroLabel = new JLabel("If this is a superheterodyne receiver, do you want to evaluate image");
    JLabel heteroLabel2 = new JLabel("frequencies produced in it?");
     
    JRadioButton no = new JRadioButton("No");
    public JRadioButton yes = new JRadioButton("Yes");
	
    JLabel IFTitle = new JLabel("IF and local oscillator details");
    JLabel IFText1 = new JLabel("Center frequency of the intermediate"
    		+ "frequency (IF) Filter (MHz): ");
    JLabel IFText2 = new JLabel("Is the local oscillator frequency above the receive frequency? (High Side Injection)");
    public JRadioButton IFYes = new JRadioButton("Yes");
    public JRadioButton IFNo = new JRadioButton("No");
    
    public JTextField IFField = new JTextField();
    
	JPanel panel = new JPanel();

	//Adding the panel for defining the IMA
	JPanel imapanel = new IMA().getPanel();
    JFrame imaframe = new JFrame();
    
    
	public int index =0;
	//Add the IMA button for defining IMA
	public JRadioButton IMAYes = new JRadioButton("Yes");
    public JRadioButton IMANo = new JRadioButton("No");
    public JLabel IMAText  = new JLabel("Define IMA");
    		
	public JPanel getPanel(){
		
		
        panel.setLayout(null);
        
        // Basic Font
        Font font = new Font("Arial", Font.PLAIN, 12);
        
        // Panel for Confidence
        
        JLabel confText = new JLabel("This construct support confidence values. If you need to specify a confidence");
        JLabel confText2 = new JLabel("value different than 1 for the values of this construct please press this button");
        JButton confBtn = new JButton("Define Confidence Values");
        
        Dimension confBtnSize = confBtn.getPreferredSize();
        Dimension confTextSize = confText2.getPreferredSize();
        
        confBtn.setBounds(25, 20, confBtnSize.width, confBtnSize.height);
        confText.setBounds(25, 20, confTextSize.width, confTextSize.height);
        confText2.setBounds(25, 40, confTextSize.width, confTextSize.height);
        
        panel.add(confBtn);


        // Panel for super heterodyne
        
        
        Dimension heteroSize1 = heteroLabel.getPreferredSize();
        Dimension heteroSize2 = heteroLabel2.getPreferredSize();
        Dimension noSize = no.getPreferredSize();
        Dimension yesSize = yes.getPreferredSize();
        
        heteroLabel.setBounds(25, 160, heteroSize1.width, heteroSize1.height);
        heteroLabel2.setBounds(25, 180, heteroSize2.width, heteroSize2.height);
        
        no.setBounds(550, 160, noSize.width, yesSize.height);
        yes.setBounds(600, 160, yesSize.width, yesSize.height);
        
        ButtonGroup group = new ButtonGroup();
        group.add(yes);
        group.add(no);
        
        no.setSelected(true);
        
        /*panel.add(heteroLabel);
        panel.add(heteroLabel2);
        panel.add(no);
        panel.add(yes);
*/
        
        // IMC Mask Table content
        
        JLabel TableLabel = new JLabel("IM Mask");
        Dimension TableLabelSize = TableLabel.getPreferredSize();
        TableLabel.setBounds(25, 60, TableLabelSize.width, TableLabelSize.height);    
        
        TableColumn col = table.getColumnModel().getColumn(0);
        col.setPreferredWidth(5);
        
        //Setting the property for the table to save the fields when out of focus
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        
        Dimension tableSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 160 + 40 +200, 
        		tableSize.width , tableSize.height - 200);
        
        JLabel imOrderLabel = new JLabel("IM order for the mask: ");
    //    JTextField imOrderField = new JTextField();
        
        imOrderLabel.setFont(font);
        Dimension imOrderLabelSize = imOrderLabel.getPreferredSize();
        Dimension imOrderFieldSize = imOrderField.getPreferredSize();
        
        imOrderLabel.setBounds(25, 80,
        		imOrderLabelSize.width, imOrderLabelSize.height);
        imOrderField.setBounds(200, 80,
        		imOrderFieldSize.width + 50, imOrderFieldSize.height);
        
        panel.add(tableContainer, BorderLayout.CENTER);
        panel.add(TableLabel);
        panel.add(imOrderLabel);
        panel.add(imOrderField);

        // Center Frequency data
        
        Dimension relBtnSize = relFreqBtn.getPreferredSize();
        relFreqBtn.setBounds(25, 210+100, relBtnSize.width, relBtnSize.height);
        panel.add(relFreqBtn);
        
        RelFreq.setFont(font);
        Dimension sizeLabel = RelFreq.getPreferredSize();
        RelFreq.setBounds(75, 210 + 150, sizeLabel.width, sizeLabel.height);
        panel.add(RelFreq);

        // Creating reference frequency text field
        RelFreqField.setColumns(1);
        RelFreqField.setBounds(255 + 0, 210 + 150, sizeLabel.width - 120, 5 + sizeLabel.height);
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
	    b1.setBounds(500 + 10, 160 + 40 + 200,
	                 size2.width, size2.height);
	    b2.setBounds(500 + 10, 210 + 40 + 200,
	                 size2.width, size2.height);
	    b3.setBounds(630 + 10, 160 + 40 + 200,
	                 size2.width, size2.height);
	    b4.setBounds(630 + 10, 210 + 40 + 200,
	                 size2.width, size2.height);       
        
	    Dimension NewMapSize = NewMap.getPreferredSize();
	    NewMap.setBounds(450 + 10, 80, NewMapSize.width, NewMapSize.height);
	    
	    Dimension PreviousSize = Previous.getPreferredSize();
	    Previous.setBounds(600 + 10,80, PreviousSize.width, PreviousSize.height);
	    
	    Dimension NextSize = Next.getPreferredSize();
	    Next.setBounds(705 + 10, 80, NextSize.width, NextSize.height);
	    
        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);        
        panel.add(NewMap);
        panel.add(Previous);
        panel.add(Next);
        
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
			//	model.removeRow(model.getRowCount() - 1);
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
	
		// IF Frequency area
		
		Dimension IFTitleSize = IFTitle.getPreferredSize();
		IFTitle.setBounds(25, 210, IFTitleSize.width, IFTitleSize.height);
		
		IFText1.setFont(font);
		Dimension IFText1Size = IFText1.getPreferredSize();
		IFText1.setBounds(25, 230, IFText1Size.width, IFText1Size.height);
		
		IFText2.setFont(font);
		Dimension IFText2Size = IFText2.getPreferredSize();
		IFText2.setBounds(22, 250, IFText2Size.width, IFText2Size.height);
		
		Dimension IFFieldSize = IFField.getPreferredSize();
		IFField.setBounds(450, 225, IFFieldSize.width + 60, IFFieldSize.height);
		
		IFYes.setFont(font);
		Dimension IFYesSize = IFYes.getPreferredSize();
		IFYes.setBounds(590, 248, IFYesSize.width, IFYesSize.height);
		
		IFNo.setFont(font);
		Dimension IFNoSize = IFNo.getPreferredSize();
		IFNo.setBounds(540, 248, IFNoSize.width, IFNoSize.height);
		
		ButtonGroup group2 = new ButtonGroup();
		group2.add(IFNo);
		group2.add(IFYes);
		
		yes.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {

				if(yes.isSelected()==true){
					
					panel.add(IFTitle);
					panel.add(IFText1);
					panel.add(IFText2);
					panel.add(IFField);
					panel.add(IFNo);
					panel.add(IFYes);
					panel.repaint();
					panel.revalidate();
					
				}
			}
			
		});
		
		no.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
			
				if(no.isSelected()==true){
					
					panel.remove(IFTitle);
					panel.remove(IFText1);
					panel.remove(IFText2);
					panel.remove(IFField);
					panel.remove(IFNo);
					panel.remove(IFYes);
					panel.repaint();
					panel.revalidate();
				}
				
			}
			
		});
		//IMA panel area
		//Define the action listener for toggle button
		 
		 
		 
		 IMAText.setFont(new Font("Arial", Font.BOLD, 14));
		 IMAYes.setFont(new Font("Arial", Font.BOLD, 14));
		 IMANo.setFont(new Font("Arial", Font.BOLD, 14));
		 IMAText.setBounds(100 + 10,200+440,size2.width, size2.height);
		 IMAYes.setBounds(210,200+440,size2.width, size2.height);
		 IMANo.setBounds(250 + 90,200+440,size2.width, size2.height);
		 
		 panel.add(IMAText);
		 panel.add(IMAYes);
		 panel.add(IMANo);
		 imaframe.setTitle("IMA");
		 
		// JTabbedPane tabPane = new SCM_MainWindow().getTabbedPane();
		 
		 IMAYes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
				/*SCM_MainWindow.tabbedPane.add("IMA", imapanel);
				int tabIndex = SCM_MainWindow.tabbedPane.indexOfTab("IMA");
				SCM_MainWindow.tabbedPane.setSelectedIndex(tabIndex);
				*/
				addIMAPanel();
                IMAYes.setSelected(true);
                IMANo.setSelected(false);
                
			}
		});
		IMANo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				/*tabPane.setEnabledAt(tabPane.indexOfTab("IMA Mask"),false);
				int tabIndex = tabPane.indexOfTab("Intermodulation Mask");				
				tabPane.setSelectedIndex(tabIndex);*/
				panel.remove(imaRelFreq);
				panel.remove(imarelFreqBtn);
				panel.remove(imaRelFreqField);
				panel.remove(imatable);
				panel.remove(imatableContainer);
				panel.remove(imab1);
				panel.remove(imab2);
				panel.remove(imab3);
				panel.remove(imab4);
				panel.repaint();
				panel.revalidate();
                IMANo.setSelected(true);
                IMAYes.setSelected(false);
                
			}
		});
	
		
		//Checking if the IM order=1, and displaying the adjusting view for intermediate frequency values
		imOrderField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			//do nothing	
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				if(imOrderField.getText().equals("1"))
				{
					panel.add(heteroLabel);
			        panel.add(heteroLabel2);
			        panel.add(no);
			        panel.add(yes);
			        if(yes.isSelected()==true)
			        {
			        panel.add(IFTitle);
					panel.add(IFText1);
					panel.add(IFText2);
					panel.add(IFField);
					panel.add(IFNo);
					panel.add(IFYes);
			        }
					panel.repaint();
					panel.revalidate();

				}
				else
				{
					panel.remove(heteroLabel);
			        panel.remove(heteroLabel2);
			        panel.remove(no);
			        panel.remove(yes);
			        panel.remove(IFTitle);
					panel.remove(IFText1);
					panel.remove(IFText2);
					panel.remove(IFField);
					panel.remove(IFNo);
					panel.remove(IFYes);
					panel.repaint();
					panel.revalidate();

					
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		panel.setAutoscrolls(true);
		panel.setPreferredSize(new Dimension(1180, 1000));
		panel.repaint();
		panel.revalidate();
			
		
		JScrollPane scrollPane=new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,  
				   ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.setWheelScrollingEnabled(true); 
		scrollPane.getViewport().setPreferredSize(new Dimension(1180, 600));
		scrollPane.repaint();
		scrollPane.revalidate();
		
		
		JPanel mainPanel = new JPanel();
				
		
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		mainPanel.repaint();
		mainPanel.revalidate();
		
		
		return mainPanel;
	}
	
	private void addIMAPanel()
	{
		// Basic Font
        Font font = new Font("Arial", Font.PLAIN, 12);
		JLabel TableLabel = new JLabel("IM Mask");
        Dimension TableLabelSize = TableLabel.getPreferredSize();
        TableLabel.setBounds(25, 760 , TableLabelSize.width, TableLabelSize.height);    
        
        TableColumn col = imatable.getColumnModel().getColumn(0);
        col.setPreferredWidth(5);
        
        imatable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        
        Dimension tableSize = imatableContainer.getPreferredSize();
        imatableContainer.setBounds(25, 60 + 700, 
        		tableSize.width , tableSize.height - 200);
        panel.add(imatableContainer, BorderLayout.CENTER);
        
        Dimension relBtnSize = imarelFreqBtn.getPreferredSize();
        imarelFreqBtn.setBounds(25,660 , relBtnSize.width, relBtnSize.height);
     
        panel.add(imarelFreqBtn);
        
        imaRelFreq.setFont(font);
        Dimension sizeLabel = imaRelFreq.getPreferredSize();
        imaRelFreq.setBounds(75, 700, sizeLabel.width, sizeLabel.height);
     

        panel.add(imaRelFreq);

        // Creating reference frequency text field

        imaRelFreqField.setColumns(1);
        imaRelFreqField.setBounds(255 + 0, 710, sizeLabel.width - 120, 5 + sizeLabel.height);
       

        panel.add(imaRelFreqField);          
        
        if(imarelFreqBtn.isSelected()==false){
	    	   
        	imaRelFreq.setEnabled(false);
        	imaRelFreqField.setEnabled(false);
	       }else{
	    	   imaRelFreq.setEnabled(true);
	    	   imaRelFreqField.setEnabled(true);
	   }
        
        imarelFreqBtn.addActionListener(new ActionListener(){
     	   public void actionPerformed(ActionEvent e){
     		  
     		   if("disabled".equals(e.getActionCommand())){
     			   if(imarelFreqBtn.isSelected()==false){
     		    	   
     				  imaRelFreq.setEnabled(false);
     				 imaRelFreqField.setEnabled(false);
     		       }
     		   }else{
     			  imaRelFreq.setEnabled(true);
     			 imaRelFreqField.setEnabled(true);
     		   }
     	   }
        });
        
        // Placing buttons for the panel
        
        Dimension size2 = imab2.getPreferredSize();
        imab1.setBounds(500 + 10, 160 + 720,
	                 size2.width, size2.height);
        imab2.setBounds(500 + 10, 210 + 720,
	                 size2.width, size2.height);
        imab3.setBounds(630 + 10, 160 + 720,
	                 size2.width, size2.height);
        imab4.setBounds(630 + 10, 210 + 720,
	                 size2.width, size2.height);       
        
        panel.add(imab1);
        panel.add(imab2);
        panel.add(imab3);
        panel.add(imab4);    
        
        /*Considering that the relativeFrequency is inherited from the Intermodulation Mask tab removing it from this panel*/
        //panel.add(NewMap);
        //panel.add(Previous);
        //panel.add(Next);
        
        // Add and Remove Rows actions
        
        imab1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				DefaultTableModel model = (DefaultTableModel) imatable.getModel();
				int count = model.getRowCount();
				model.addRow(new Object[]{count+1, "", ""});	

			}
		});
		
		
		
        imab2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				DefaultTableModel model = (DefaultTableModel) imatable.getModel();
				
				/*
				 * Allowing the deletion of selected rows
				 */
				int[] selectedRows = imatable.getSelectedRows();
				   for(int row=selectedRows.length-1;row>=0;row--){
					int rowNum = selectedRows[row];
				     model.removeRow(rowNum);
				     //Updating the index column - count variable appropriately
				     if(rowNum!=imatable.getRowCount())
				     {
				    	 imatable.getModel().setValueAt(rowNum+1,rowNum ,0 );
				     }
				     
				   }
			//	model.removeRow(model.getRowCount() - 1);
				   
				   
				   for(int i=imatable.getRowCount()-1;i>=0;i--)
				   {
					   int curVal = Integer.parseInt(imatable.getModel().getValueAt(i, 0).toString());
					   if(curVal!= i+1)
					   {
						   imatable.getModel().setValueAt(i+1, i, 0);
					   }
				   }
			}
		});
        
	}
}
