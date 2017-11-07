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
 * Exec.java
 * Creates GUI panel for Executing compatibility tests.
*/

package SCM_home;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.ieee.dyspansc._1900._5.scm.RxModelType;
import org.ieee.dyspansc._1900._5.scm.TxModelType;

import Execute.MethodAnalysis;
import Execute.Warn;


public class Exec {
		
	JPanel panel = new JPanel();
	JButton Back = new JButton("Back");
	JButton Execute = new JButton("Execute");
	JButton Exit = new JButton("Exit");
	
	
	String[] nullString = null;
    
	// Creating the list box for Transmitters
	
	DefaultListModel<String> TxListModel = new DefaultListModel<>();
	JScrollPane TxScroll = new JScrollPane();
    JList<String> TxBox = new JList<String>(TxListModel);
    JButton TxAdd = new JButton("Add");
	JFileChooser fcTx = new JFileChooser();
	ArrayList<Model> TxArray = new ArrayList<Model>();
	
	//Maintaining the variable for updating the warnings
	MethodAnalysis methAn = new MethodAnalysis();
	// Creating the list box for Receivers
	
    DefaultListModel<String> RxListModel = new DefaultListModel<>();
    JScrollPane RxScroll = new JScrollPane();
	JList<String> RxBox = new JList<String>(RxListModel);
	JButton RxAdd = new JButton("Add");
	JFileChooser fcRx = new JFileChooser();
	ArrayList<Model> RxArray = new ArrayList<Model>();
		
	String CompatStat = null;
	
		public JPanel getPanel(){
		
		panel.setBorder(new TitledBorder(null, "Execute Compatibility Test", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setLayout(null);
        
        // Positioning the Transmitter list and label        
        JLabel TxLabel = new JLabel("Transmitter Model");
        Dimension TxLabelSize = TxLabel.getPreferredSize();
        TxLabel.setBounds(25, 30, TxLabelSize.width, TxLabelSize.height);
        panel.add(TxLabel);
        
        Dimension TxBoxSize = TxBox.getPreferredSize();
        TxScroll.setBounds(25, 60, TxBoxSize.width + 200, TxBoxSize.height + 100);
        TxScroll.setViewportView(TxBox);
        panel.add(TxScroll);        
        
        Dimension TxAddSize = TxAdd.getPreferredSize();
        TxAdd.setBounds(25 + 220, 60, TxAddSize.width, TxAddSize.height);
        panel.add(TxAdd);
        
        // Operation for adding more transmitter models        
        TxAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fcTx.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fcTx.showOpenDialog(panel);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fcTx.getSelectedFile();
		            String modelName = file.getName();
		            String modelPath = file.getAbsolutePath();
		            Model model = new Model();
		            model.ModelName=modelName;
		            model.ModelPath=modelPath;
		            model.ModelMethod=null;
		            TxArray.add(model);
		            DefaultListModel<String> listModel = (DefaultListModel<String>) TxBox.getModel();
		            listModel.addElement(modelName);
		        } else {
		        }
				
			}
        });
        
        
        // Positioning the Receiver list and label.
        JLabel RxLabel = new JLabel("Receiver Model");
        Dimension RxLabelSize = RxLabel.getPreferredSize();
        RxLabel.setBounds(25, 100 + 80, RxLabelSize.width, RxLabelSize.height);
        panel.add(RxLabel);
        
        Dimension RxBoxSize = RxBox.getPreferredSize();
        RxScroll.setBounds(25, 100 + 110, RxBoxSize.width + 200, RxBoxSize.height+100);
        RxScroll.setViewportView(RxBox);
        panel.add(RxScroll);
        
        Dimension RxAddSize = RxAdd.getPreferredSize();
        RxAdd.setBounds(25 + 220,100 + 110, RxAddSize.width, RxAddSize.height);
        panel.add(RxAdd);
        
        // Setting operation for adding receiver models.
        RxAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fcRx.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fcRx.showOpenDialog(panel);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fcRx.getSelectedFile();
		            String modelName = file.getName();
		            String modelPath = file.getAbsolutePath();
		            Model model = new Model();
		            model.ModelName=modelName;
		            model.ModelPath=modelPath;
		            DefaultListModel<String> listModel = (DefaultListModel<String>) RxBox.getModel();
		            listModel.addElement(modelName);
		            RxArray.add(model);
		        } else {
		        }
			}
        });
        
        // Placing Back, Execute and Exit operations
        
        Dimension BackSize = Back.getPreferredSize();
        Back.setBounds(255, 350, BackSize.width, BackSize.height);
        panel.add(Back);
        
        Dimension ExecuteSize = Execute.getPreferredSize();
        Execute.setBounds(343, 350, ExecuteSize.width, ExecuteSize.height);
        panel.add(Execute);
        
        Dimension ExitSize = Exit.getPreferredSize();
        Exit.setBounds(450, 350, ExitSize.width, ExitSize.height);
        panel.add(Exit);
        
        // Setting operation for executing compatibility test.
        ActionListener execOp = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				int[] indices = TxBox.getSelectedIndices();
				ArrayList<TxModelType> TxData = new ArrayList<TxModelType>();
				System.out.println(indices.length);
				
				for(int i=0;i<indices.length;i++){
					
					int Index = indices[i];
					Model model = TxArray.get(Index);					
					
					String filePath = model.ModelPath+"/"+model.ModelName+".xml";
					File file = new File(filePath);
					if(file.exists() && !file.isDirectory()){
						System.out.println("File Exists");
					}
					try{
					JAXBContext TxContext = JAXBContext.newInstance(TxModelType.class);
					Unmarshaller TxUnmarshaller = TxContext.createUnmarshaller();
					JAXBElement<TxModelType> TxElement = TxUnmarshaller.unmarshal(new StreamSource(filePath),
							TxModelType.class);
					
					TxData.add(TxElement.getValue());
					System.out.println(TxData.get(i).toString());
					}catch(Exception exp){
						methAn.warningFlag = true;
						methAn.warningMessage = methAn.warningMessage + "\nIncorrect Format - File is of invalid format";
						
						//new Warn().setWarn("Incorrect Format", "File is of invalid format");
					}
				}
	
				int[] txBoxIndices = indices;
				indices = RxBox.getSelectedIndices();
				int[] rxBoxIndices = indices;
				ArrayList<RxModelType> RxData = new ArrayList<RxModelType>();
				
				for(int i=0;i<indices.length;i++){
					
					int Index = indices[i];

					Model model = RxArray.get(Index);					
					
					String filePath = model.ModelPath+"/"+model.ModelName+".xml";
					File file = new File(filePath);
					if(file.exists() && !file.isDirectory()){
						System.out.println("File Exists");
					}
					try{
					JAXBContext RxContext = JAXBContext.newInstance(RxModelType.class);
					Unmarshaller RxUnmarshaller = RxContext.createUnmarshaller();
					JAXBElement<RxModelType> RxElement = RxUnmarshaller.unmarshal(new StreamSource(filePath),
							RxModelType.class);
					
					RxData.add(RxElement.getValue());
					System.out.println(RxData.get(i).toString());
					}catch(Exception exp){
						methAn.warningFlag = true;
						methAn.warningMessage = methAn.warningMessage + "\n Incorrect Format -File is of invalid format";
						
						
						//new Warn().setWarn("Incorrect Format", "File is of invalid format");
					}
				}
				
				
				/* Using the class Method Analysis from Execute package to perform the compatibility 
				 * analysis.
				 */
				try{
					//MethodAnalysis methAn = new MethodAnalysis();
					String ratedMethod=methAn.analyseRatedMethod(TxData,RxData);
					System.out.println(ratedMethod);
					methAn.execCompat(ratedMethod, txBoxIndices, TxData, TxArray, 
							rxBoxIndices, RxData, RxArray);
					if(methAn.warningFlag)
					{
						new Warn().showWarnings("Systems Compatible", methAn.warningMessage);
						methAn.warningFlag = false;
						methAn.warningMessage = "\n";
					}
					
					
				}catch(Exception exp){
					new Warn().showWarnings("Warning", methAn.warningMessage);
					methAn.warningFlag = false;
					methAn.warningMessage = "\n";
				}				
				
			}        	
        };
        
        Execute.addActionListener(execOp);
        
        return panel;
        
		}
}
