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
 * UnderlayMask.java
 * Creates the GUI panel for the Underlay Mask construct
*/

package SCM_gui;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

 
public class UnderlayMask {
		
	/*public JTextField ResTextField = new JTextField();
	
	//Global Buttons
	JButton b3 = new JButton("Save Data");
    JButton b4 = new JButton("Exit");
    
    String column_names[] = {"#","Frequency (MHz)", "Power (dB)"};
    Object rowData[][] = { { "1","",""} };
    TableModel table_model = new DefaultTableModel(rowData,column_names) {
    	
		private static final long serialVersionUID = 6582367260452529797L;

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
    
    //Get Panel
    public JPanel SpecPanel;
    public SpecMask_Hop SpecHop = new SpecMask_Hop();
    public JLabel RefFreq;
    public JLabel ResBW;
    public Object[] RatList = {"Bandwidth Rated", 
    		"Bandwidth Rated List", 
    		"BTP Rated",
    		"BTP Rated List",
    		"Duty Cycle Rated List",
    		"Policy or Protocol Index"};
    public JComboBox<Object> box = new JComboBox<Object>(RatList);
    
    public UnderlayRated underlayRated = new UnderlayRated();
    
    public JRadioButton yes = new JRadioButton("Yes");
    public JRadioButton no =  new JRadioButton("No");
    public JRadioButton TotPowerBtn = new JRadioButton("Total Power");
    public JRadioButton MaxPowBtn= new JRadioButton("Max. Power Density");
    
    public boolean noInitialState=true;
    public boolean TotPowerInitialState=true;*/
    
	public void defineElements(SpecMask spec)
	{
		// Rated Underlay Mask Option
        
        
        
        Dimension maskTypeSize = spec.maskType.getPreferredSize();
        spec.maskType.setBounds(490, 540, maskTypeSize.width, maskTypeSize.height);
        
        Dimension boxSize = spec.box.getPreferredSize();
        spec.box.setBounds(620, 538, boxSize.width + 50, boxSize.height);
        
        Dimension ratedLabelSize = spec.ratedLabel.getPreferredSize();
        spec.ratedLabel.setBounds(25, 540, ratedLabelSize.width, ratedLabelSize.height);
        
        Dimension noSize = spec.underlayno.getPreferredSize();
        Dimension yesSize = spec.underlayyes.getPreferredSize();
        
        spec.underlayno.setBounds(380, 536, noSize.width, noSize.height);
        spec.underlayyes.setBounds(430, 536, yesSize.width, yesSize.height);
        
        // Calculation Method
        
               
        Dimension PowerSize = spec.PowerMarginLabel.getPreferredSize();
        spec.PowerMarginLabel.setBounds(25, 770, PowerSize.width, PowerSize.height);
        
        Dimension TotPowSize = spec.TotPowerBtn.getPreferredSize();
        Dimension MaxPowSize = spec.MaxPowBtn.getPreferredSize();
        
        spec.TotPowerBtn.setBounds(250, 770, TotPowSize.width, TotPowSize.height);
        spec.MaxPowBtn.setBounds(250, 790, MaxPowSize.width, MaxPowSize.height);
        
        // Positioning Underlay Table
        
        Dimension size3 = spec.underlaytableContainer.getPreferredSize();
        
      //To allow the element on the last edit to be saved
        spec.underlayTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
  
        
        spec.underlayTable.getTableHeader().setReorderingAllowed(true);
        spec.underlayTable.getColumnModel().getColumn(0).setPreferredWidth(15);
        spec.underlayTable.getColumnModel().getColumn(0);
        spec.underlaytableContainer.setBounds(25, 860,
                size3.width - 100, size3.height - 250);
          
	}
	public void addElements(SpecMask spec)
	{
		
		 spec.SpecPanel.add(spec.ratedLabel);
		 spec.SpecPanel.add(spec.underlayno);
		 spec.SpecPanel.add(spec.underlayyes);
	        
	        ButtonGroup group1 = new ButtonGroup();
	        group1.add(spec.underlayno);
	        group1.add(spec.underlayyes);
		 
	        ButtonGroup group2 = new ButtonGroup();
	        group2.add(spec.TotPowerBtn);
	        group2.add(spec.MaxPowBtn);
	        
	        spec.SpecPanel.add(spec.PowerMarginLabel);
	        spec.SpecPanel.add(spec.TotPowerBtn);
	        spec.SpecPanel.add(spec.MaxPowBtn);
		 // Combo Box Actions for rated Underlay Masks

        final ActionListener boxAction = new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				int index = spec.box.getSelectedIndex();
				
				switch(index){
				
				case 0: spec.underlayRated.getBR(spec.SpecPanel);
				spec.underlayRated.removeBRList(spec.SpecPanel);
				spec.underlayRated.removeBTPRating(spec.SpecPanel);
				spec.underlayRated.removeBTPList(spec.SpecPanel);
				spec.underlayRated.removeDutyCycle(spec.SpecPanel);
				spec.underlayRated.removePolicy(spec.SpecPanel);
				spec.MaxPowBtn.setSelected(true);
				spec.TotPowerBtn.setSelected(false);
				spec.TotPowerBtn.setEnabled(false);
				spec.MaxPowBtn.setEnabled(false);	

				spec.SpecPanel.revalidate();
				spec.SpecPanel.repaint();
						
						break;
						
				case 1: spec.underlayRated.getBRList(spec.SpecPanel);
				spec.underlayRated.removeBR(spec.SpecPanel);
				spec.underlayRated.removeBTPRating(spec.SpecPanel);
				spec.underlayRated.removeBTPList(spec.SpecPanel);
				spec.underlayRated.removeDutyCycle(spec.SpecPanel);
				spec.underlayRated.removePolicy(spec.SpecPanel);
				spec.MaxPowBtn.setSelected(true);
				spec.TotPowerBtn.setSelected(false);
				spec.TotPowerBtn.setEnabled(false);
				spec.MaxPowBtn.setEnabled(false);	

				spec.SpecPanel.revalidate();
				spec.SpecPanel.repaint();
						
						break;
						
				case 2: spec.underlayRated.getBTPRating(spec.SpecPanel);
				spec.underlayRated.removeBR(spec.SpecPanel);
				spec.underlayRated.removeBRList(spec.SpecPanel);
				spec.underlayRated.removeBTPList(spec.SpecPanel);
				spec.underlayRated.removeDutyCycle(spec.SpecPanel);
				spec.underlayRated.removePolicy(spec.SpecPanel);
				spec.MaxPowBtn.setSelected(true);
				spec.TotPowerBtn.setSelected(false);
				spec.TotPowerBtn.setEnabled(false);
				spec.MaxPowBtn.setEnabled(false);	

				spec.SpecPanel.revalidate();
				spec.SpecPanel.repaint();
						
						break;
						
				case 3: spec.underlayRated.removeBTPRating(spec.SpecPanel);
				spec.underlayRated.removeBR(spec.SpecPanel);
				spec.underlayRated.removeBRList(spec.SpecPanel);
				spec.underlayRated.getBTPList(spec.SpecPanel);
				spec.underlayRated.removeDutyCycle(spec.SpecPanel);
				spec.underlayRated.removePolicy(spec.SpecPanel);
				spec.MaxPowBtn.setSelected(true);
				spec.TotPowerBtn.setSelected(false);
				spec.TotPowerBtn.setEnabled(false);
				spec.MaxPowBtn.setEnabled(false);	

				spec.SpecPanel.revalidate();
				spec.SpecPanel.repaint();
						
						break;
				
				case 4: spec.underlayRated.removeBTPRating(spec.SpecPanel);
				spec.underlayRated.removeBR(spec.SpecPanel);
				spec.underlayRated.removeBRList(spec.SpecPanel);
				spec.underlayRated.removeBTPList(spec.SpecPanel);
				spec.underlayRated.getDutyCycle(spec.SpecPanel);
				spec.underlayRated.removePolicy(spec.SpecPanel);
				spec.MaxPowBtn.setEnabled(true);
				spec.TotPowerBtn.setEnabled(true);
						
				spec.SpecPanel.revalidate();
				spec.SpecPanel.repaint();
						
						break;
						
				case 5: spec.underlayRated.removeBTPRating(spec.SpecPanel);
				spec.underlayRated.removeBR(spec.SpecPanel);
				spec.underlayRated.removeBRList(spec.SpecPanel);
				spec.underlayRated.removeBTPList(spec.SpecPanel);
				spec.underlayRated.removeDutyCycle(spec.SpecPanel);
				spec.underlayRated.getPolicy(spec.SpecPanel);
				spec.MaxPowBtn.setEnabled(true);
				spec.TotPowerBtn.setEnabled(true);
				spec.SpecPanel.revalidate();
				spec.SpecPanel.repaint();
						
						break;
						
						
				default:spec.SpecPanel.revalidate();
				spec.SpecPanel.repaint();
						
						break;
				}
				
			}
        	
        };
        
        /* Setting initial states for Total Power Method vs 
         * Maximum Power Density Method radio buttons
         */
        
        spec.underlayno.setSelected(spec.noInitialState);
        spec.underlayno.setEnabled(true);
        
        spec.TotPowerBtn.setSelected(spec.TotPowerInitialState);
        spec.TotPowerBtn.setEnabled(true);
        
        // Radio Button Operations for Rated Underlay (Yes/No) 
        
        spec.underlayno.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					if(spec.underlayno.isSelected()==true){
						spec.TotPowerBtn.setEnabled(true);
				        
						spec.MaxPowBtn.setEnabled(true);
				        
						spec.SpecPanel.remove(spec.maskType);
						spec.SpecPanel.remove(spec.box);
				        
						spec.underlayRated.removeBTPRating(spec.SpecPanel);
						spec.underlayRated.removeBR(spec.SpecPanel);
						spec.underlayRated.removeBRList(spec.SpecPanel);
						spec.underlayRated.removeBTPList(spec.SpecPanel);
						spec.underlayRated.removeDutyCycle(spec.SpecPanel);
						spec.underlayRated.removePolicy(spec.SpecPanel);
						
						spec.SpecPanel.revalidate();
						spec.SpecPanel.repaint();
					}
			}
        	
        });
        
        spec.underlayyes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					if(spec.underlayyes.isSelected()==true){
						
						
						spec.SpecPanel.add(spec.maskType);
						spec.SpecPanel.add(spec.box);
						
						boxAction.actionPerformed(e);
						
						spec.SpecPanel.revalidate();
						spec.SpecPanel.repaint();
					
						if(spec.box.getSelectedIndex()==4 || spec.box.getSelectedIndex()==5){
							spec.TotPowerBtn.setEnabled(true);
							spec.MaxPowBtn.setEnabled(true);
						}else{
							spec.TotPowerBtn.setEnabled(false);
							spec.MaxPowBtn.setEnabled(false);	
						}						
					}
			}
        	
        });
        
        ActionEvent e = null;
        
        if(spec.underlayyes.isSelected()==true){
			
			
        	spec.SpecPanel.add(spec.maskType);
        	spec.SpecPanel.add(spec.box);
			
			boxAction.actionPerformed(e);
			
			spec.SpecPanel.revalidate();
			spec.SpecPanel.repaint();
		
			spec.TotPowerBtn.setEnabled(false);
			spec.MaxPowBtn.setEnabled(false);
		}
                
        spec.box.addActionListener(boxAction);
        
        // Creating Resolution Bandwidth Label and Text Field

        Dimension sizeBW = spec.underlayResBW.getPreferredSize();
        spec.underlayResBW.setBounds(25, 820, sizeBW.width, sizeBW.height);
        spec.SpecPanel.add(spec.underlayResBW);
     
        spec.underlayResTextField.setColumns(1);
        spec.underlayResTextField.setBounds(235, 820, sizeBW.width - 150, 5 + sizeBW.height);
        spec.SpecPanel.add(spec.underlayResTextField);

        spec.SpecPanel.add(spec.underlaytableContainer, BorderLayout.CENTER);
        

        // Underlay Buttons
             
        
        Dimension size2 = spec.underlayb3.getPreferredSize();
        spec.underlayb1.setBounds(400 + 0, 900,
                     size2.width + 30, size2.height);        
        spec.underlayb2.setBounds(400 + 0, 950,
                size2.width + 30, size2.height);
        spec.underlayb3.setBounds(550, 950,
                size2.width + 30, size2.height);
        spec.underlayb4.setBounds(550, 900, 
        		size2.width + 30, size2.height);
               
        spec.underlayb1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				DefaultTableModel model = (DefaultTableModel) spec.underlayTable.getModel();
				model.addRow(new Object[]{spec.underlayTable.getRowCount()+1, "", ""});	
			}
		});
		
		
		
		spec.underlayb2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				DefaultTableModel model = (DefaultTableModel) spec.underlayTable.getModel();
				//model.removeRow(model.getRowCount() - 1);
				/*
				 * Allowing the deletion of selected rows
				 */
				int[] selectedRows = spec.underlayTable.getSelectedRows();
				   for(int row=selectedRows.length-1;row>=0;row--){
					int rowNum = selectedRows[row];
				     model.removeRow(rowNum);
				     //Updating the index column - count variable appropriately
				     if(rowNum!=spec.underlayTable.getRowCount())
				     {
				    	 spec.underlayTable.getModel().setValueAt(rowNum+1,rowNum ,0 );
				     }
				     
				   }
			//	model.removeRow(model.getRowCount() - 1);		 
				   for(int i=spec.underlayTable.getRowCount()-1;i>=0;i--)
				   {
					   int curVal = Integer.parseInt(spec.underlayTable.getModel().getValueAt(i, 0).toString());
					   if(curVal!= i+1)
					   {
						   spec.underlayTable.getModel().setValueAt(i+1, i, 0);
					   }
				   }
			}
		});

		spec.SpecPanel.add(spec.underlayb1);
		spec.SpecPanel.add(spec.underlayb2);
		spec.SpecPanel.add(spec.underlayb3);
		spec.SpecPanel.add(spec.underlayb4); 
        
	}
    
	/*public JPanel getPanel(){
		
        SpecPanel = new JPanel();
        SpecPanel.setLayout(null);
        
        // Confidence Values
        JLabel ConfText = new JLabel("This construct supports confidence values. If you need to specify a confidence");
        JLabel ConfText2 = new JLabel("value different than 1 for the values of this construct please press this button");
        JButton ConfBtn = new JButton("Define Confidence Values");
        
        Dimension ConfBtnSize = ConfBtn.getPreferredSize();
        Dimension ConfTextSize = ConfText2.getPreferredSize();
        
        ConfBtn.setBounds(25, 20, ConfBtnSize.width, ConfBtnSize.height);
        ConfText.setBounds(25,20,ConfTextSize.width,ConfTextSize.height);
        ConfText2.setBounds(25,40,ConfTextSize.width,ConfTextSize.height);
        
        SpecPanel.add(ConfBtn);
        
		// Rated Underlay Mask Option
        
        JLabel ratedLabel = new JLabel("This is a rated underlay mask");        
        final JLabel maskType = new JLabel("Rated Mask Type: ");
        
        
        Dimension maskTypeSize = maskType.getPreferredSize();
        maskType.setBounds(400, 80, maskTypeSize.width, maskTypeSize.height);
        
        Dimension boxSize = box.getPreferredSize();
        box.setBounds(550, 80, boxSize.width + 50, boxSize.height);
        
        Dimension ratedLabelSize = ratedLabel.getPreferredSize();
        ratedLabel.setBounds(25, 80, ratedLabelSize.width, ratedLabelSize.height);
        
        Dimension noSize = no.getPreferredSize();
        Dimension yesSize = yes.getPreferredSize();
        
        no.setBounds(250, 75, noSize.width, noSize.height);
        yes.setBounds(300, 75, yesSize.width, yesSize.height);
        
        SpecPanel.add(ratedLabel);
        SpecPanel.add(no);
        SpecPanel.add(yes);
        
        ButtonGroup group1 = new ButtonGroup();
        group1.add(no);
        group1.add(yes);
        
                
        // Calculation Method
        
        JLabel PowerMarginLabel = new JLabel("Power Margin method to use: ");
        
        Dimension PowerSize = PowerMarginLabel.getPreferredSize();
        PowerMarginLabel.setBounds(25, 120, PowerSize.width, PowerSize.height);
        
        Dimension TotPowSize = TotPowerBtn.getPreferredSize();
        Dimension MaxPowSize = MaxPowBtn.getPreferredSize();
        
        TotPowerBtn.setBounds(250, 115, TotPowSize.width, TotPowSize.height);
        MaxPowBtn.setBounds(250, 135, MaxPowSize.width, MaxPowSize.height);
        
        ButtonGroup group2 = new ButtonGroup();
        group2.add(TotPowerBtn);
        group2.add(MaxPowBtn);
        
        SpecPanel.add(PowerMarginLabel);
        SpecPanel.add(TotPowerBtn);
        SpecPanel.add(MaxPowBtn);
        
        // Combo Box Actions for rated Underlay Masks

        final ActionListener boxAction = new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				int index = box.getSelectedIndex();
				
				switch(index){
				
				case 0: underlayRated.getBR(SpecPanel);
						underlayRated.removeBRList(SpecPanel);
						underlayRated.removeBTPRating(SpecPanel);
						underlayRated.removeBTPList(SpecPanel);
						underlayRated.removeDutyCycle(SpecPanel);
						underlayRated.removePolicy(SpecPanel);
						MaxPowBtn.setSelected(true);
						TotPowerBtn.setSelected(false);
						TotPowerBtn.setEnabled(false);
				        MaxPowBtn.setEnabled(false);	

						SpecPanel.revalidate();
						SpecPanel.repaint();
						
						break;
						
				case 1: underlayRated.getBRList(SpecPanel);
						underlayRated.removeBR(SpecPanel);
						underlayRated.removeBTPRating(SpecPanel);
						underlayRated.removeBTPList(SpecPanel);
						underlayRated.removeDutyCycle(SpecPanel);
						underlayRated.removePolicy(SpecPanel);
						MaxPowBtn.setSelected(true);
						TotPowerBtn.setSelected(false);
						TotPowerBtn.setEnabled(false);
				        MaxPowBtn.setEnabled(false);	

						SpecPanel.revalidate();
						SpecPanel.repaint();
						
						break;
						
				case 2: underlayRated.getBTPRating(SpecPanel);
						underlayRated.removeBR(SpecPanel);
						underlayRated.removeBRList(SpecPanel);
						underlayRated.removeBTPList(SpecPanel);
						underlayRated.removeDutyCycle(SpecPanel);
						underlayRated.removePolicy(SpecPanel);
						MaxPowBtn.setSelected(true);
						TotPowerBtn.setSelected(false);
						TotPowerBtn.setEnabled(false);
				        MaxPowBtn.setEnabled(false);	

						SpecPanel.revalidate();
						SpecPanel.repaint();
						
						break;
						
				case 3: underlayRated.removeBTPRating(SpecPanel);
						underlayRated.removeBR(SpecPanel);
						underlayRated.removeBRList(SpecPanel);
						underlayRated.getBTPList(SpecPanel);
						underlayRated.removeDutyCycle(SpecPanel);
						underlayRated.removePolicy(SpecPanel);
						MaxPowBtn.setSelected(true);
						TotPowerBtn.setSelected(false);
						TotPowerBtn.setEnabled(false);
				        MaxPowBtn.setEnabled(false);	

						SpecPanel.revalidate();
						SpecPanel.repaint();
						
						break;
				
				case 4: underlayRated.removeBTPRating(SpecPanel);
						underlayRated.removeBR(SpecPanel);
						underlayRated.removeBRList(SpecPanel);
						underlayRated.removeBTPList(SpecPanel);
						underlayRated.getDutyCycle(SpecPanel);
						underlayRated.removePolicy(SpecPanel);
						MaxPowBtn.setEnabled(true);
						TotPowerBtn.setEnabled(true);
						
						SpecPanel.revalidate();
						SpecPanel.repaint();
						
						break;
						
				case 5: underlayRated.removeBTPRating(SpecPanel);
						underlayRated.removeBR(SpecPanel);
						underlayRated.removeBRList(SpecPanel);
						underlayRated.removeBTPList(SpecPanel);
						underlayRated.removeDutyCycle(SpecPanel);
						underlayRated.getPolicy(SpecPanel);
						MaxPowBtn.setEnabled(true);
						TotPowerBtn.setEnabled(true);
						SpecPanel.revalidate();
						SpecPanel.repaint();
						
						break;
						
						
				default:SpecPanel.revalidate();
						SpecPanel.repaint();
						
						break;
				}
				
			}
        	
        };
        
         Setting initial states for Total Power Method vs 
         * Maximum Power Density Method radio buttons
         
        
        no.setSelected(noInitialState);
        no.setEnabled(true);
        
        TotPowerBtn.setSelected(TotPowerInitialState);
        TotPowerBtn.setEnabled(true);
        
        // Radio Button Operations for Rated Underlay (Yes/No) 
        
        no.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					if(no.isSelected()==true){
					TotPowerBtn.setEnabled(true);
				        
				        MaxPowBtn.setEnabled(true);
				        
						SpecPanel.remove(maskType);
						SpecPanel.remove(box);
				        
						underlayRated.removeBTPRating(SpecPanel);
						underlayRated.removeBR(SpecPanel);
						underlayRated.removeBRList(SpecPanel);
						underlayRated.removeBTPList(SpecPanel);
						underlayRated.removeDutyCycle(SpecPanel);
						underlayRated.removePolicy(SpecPanel);
						
						SpecPanel.revalidate();
						SpecPanel.repaint();
					}
			}
        	
        });
        
        yes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					if(yes.isSelected()==true){
						
						
						SpecPanel.add(maskType);
						SpecPanel.add(box);
						
						boxAction.actionPerformed(e);
						
						SpecPanel.revalidate();
						SpecPanel.repaint();
					
						if(box.getSelectedIndex()==4 || box.getSelectedIndex()==5){
							TotPowerBtn.setEnabled(true);
					        MaxPowBtn.setEnabled(true);
						}else{
							TotPowerBtn.setEnabled(false);
					        MaxPowBtn.setEnabled(false);	
						}						
					}
			}
        	
        });
        
        ActionEvent e = null;
        
        if(yes.isSelected()==true){
			
			
			SpecPanel.add(maskType);
			SpecPanel.add(box);
			
			boxAction.actionPerformed(e);
			
			SpecPanel.revalidate();
			SpecPanel.repaint();
		
			TotPowerBtn.setEnabled(false);
	        MaxPowBtn.setEnabled(false);
		}
                
        box.addActionListener(boxAction);
        
       // Creating Resolution Bandwidth Label and Text Field

        ResBW = new JLabel("Resolution Bandwidth (Mhz)");
        Dimension sizeBW = ResBW.getPreferredSize();
        ResBW.setBounds(25, 100 + 80, sizeBW.width, sizeBW.height);
        SpecPanel.add(ResBW);
     
        ResTextField.setColumns(1);
        ResTextField.setBounds(235, 100 + 80, sizeBW.width - 150, 5 + sizeBW.height);
        SpecPanel.add(ResTextField);

        // Positioning Underlay Table
        
        Dimension size3 = tableContainer.getPreferredSize();
        
      //To allow the element on the last edit to be saved
      table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
  
        
        table.getTableHeader().setReorderingAllowed(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(15);
        table.getColumnModel().getColumn(0);
        tableContainer.setBounds(25, 130 + 80,
                size3.width - 100, size3.height - 250);
        
        SpecPanel.add(tableContainer, BorderLayout.CENTER);

        
        // Underlay Buttons
        
        JButton b1 = new JButton("Add Row");
        JButton b2 = new JButton("Remove Row");        
        
        Dimension size2 = b3.getPreferredSize();
        b1.setBounds(400 + 0, 130 + 80,
                     size2.width + 30, size2.height);        
        b2.setBounds(400 + 0, 180 + 80,
                size2.width + 30, size2.height);
        b3.setBounds(550, 130 + 80,
                size2.width + 30, size2.height);
        b4.setBounds(550, 180 + 80, 
        		size2.width + 30, size2.height);
               
        
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
				
				 * Allowing the deletion of selected rows
				 
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
        
        SpecPanel.add(b1);
        SpecPanel.add(b2);
        SpecPanel.add(b3);
        SpecPanel.add(b4);     
        
        return SpecPanel;
	
	}*/
}