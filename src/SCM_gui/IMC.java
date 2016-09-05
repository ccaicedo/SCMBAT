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
    TableModel table_model = new DefaultTableModel(rowData, columnNames);    
    public JTable table = new JTable(table_model);
	JScrollPane tableContainer = new JScrollPane(table);
    
	JLabel heteroLabel = new JLabel("If this is a superheterodyne receiver, do you want to evaluate image");
    JLabel heteroLabel2 = new JLabel("frequencies produced in it?");
     
    JRadioButton no = new JRadioButton("No");
    JRadioButton yes = new JRadioButton("Yes");
	
    JLabel IFTitle = new JLabel("IF and local oscillator details");
    JLabel IFText1 = new JLabel("Center frequency of the intermediate"
    		+ "frequency (IF) Filter (MHz): ");
    JLabel IFText2 = new JLabel("The local"
    		+ " oscillator frequency is above the received signal frequency");
    public JRadioButton IFYes = new JRadioButton("Yes");
    public JRadioButton IFNo = new JRadioButton("No");
    
    public JTextField IFField = new JTextField();
    
	JPanel panel = new JPanel();


	public int index =0;
    
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
        
        heteroLabel.setBounds(25, 80, heteroSize1.width, heteroSize1.height);
        heteroLabel2.setBounds(25, 100, heteroSize2.width, heteroSize2.height);
        
        no.setBounds(550, 80, noSize.width, yesSize.height);
        yes.setBounds(600, 80, yesSize.width, yesSize.height);
        
        ButtonGroup group = new ButtonGroup();
        group.add(yes);
        group.add(no);
        
        no.setSelected(true);
        
        panel.add(heteroLabel);
        panel.add(heteroLabel2);
        panel.add(no);
        panel.add(yes);

        
        // IMC Mask Table content
        
        JLabel TableLabel = new JLabel("IM Mask");
        Dimension TableLabelSize = TableLabel.getPreferredSize();
        TableLabel.setBounds(25, 140 + 100, TableLabelSize.width, TableLabelSize.height);    
        
        TableColumn col = table.getColumnModel().getColumn(0);
        col.setPreferredWidth(5);
        
        Dimension tableSize = tableContainer.getPreferredSize();
        tableContainer.setBounds(25, 160 + 40 +200, 
        		tableSize.width , tableSize.height - 200);
        
        JLabel imOrderLabel = new JLabel("IM order for the mask: ");
        JTextField imOrderField = new JTextField();
        
        imOrderLabel.setFont(font);
        Dimension imOrderLabelSize = imOrderLabel.getPreferredSize();
        Dimension imOrderFieldSize = imOrderField.getPreferredSize();
        
        imOrderLabel.setBounds(25, 160 + 100,
        		imOrderLabelSize.width, imOrderLabelSize.height);
        imOrderField.setBounds(200, 160 + 100,
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
	    NewMap.setBounds(450 + 10, 110 + 30 + 100, NewMapSize.width, NewMapSize.height);
	    
	    Dimension PreviousSize = Previous.getPreferredSize();
	    Previous.setBounds(600 + 10, 110 + 30 + 100, PreviousSize.width, PreviousSize.height);
	    
	    Dimension NextSize = Next.getPreferredSize();
	    Next.setBounds(705 + 10, 110 + 30 + 100, NextSize.width, NextSize.height);
	    
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
				model.removeRow(model.getRowCount() - 1);
			}
		});
	
		// IF Frequency area
		
		Dimension IFTitleSize = IFTitle.getPreferredSize();
		IFTitle.setBounds(25, 140, IFTitleSize.width, IFTitleSize.height);
		
		IFText1.setFont(font);
		Dimension IFText1Size = IFText1.getPreferredSize();
		IFText1.setBounds(25, 160, IFText1Size.width, IFText1Size.height);
		
		IFText2.setFont(font);
		Dimension IFText2Size = IFText2.getPreferredSize();
		IFText2.setBounds(25, 180, IFText2Size.width, IFText2Size.height);
		
		Dimension IFFieldSize = IFField.getPreferredSize();
		IFField.setBounds(480, 157, IFFieldSize.width + 50, IFFieldSize.height);
		
		IFYes.setFont(font);
		Dimension IFYesSize = IFYes.getPreferredSize();
		IFYes.setBounds(580, 177, IFYesSize.width, IFYesSize.height);
		
		IFNo.setFont(font);
		Dimension IFNoSize = IFNo.getPreferredSize();
		IFNo.setBounds(530, 177, IFNoSize.width, IFNoSize.height);
		
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
		
		return panel;
	}
}
