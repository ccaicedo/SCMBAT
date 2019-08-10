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

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.ieee.dyspansc._1900._5.scm.RxModel;
import org.ieee.dyspansc._1900._5.scm.TxModel;

import Execute.MethodAnalysis;
import Execute.Warn;

public class Exec {

	JPanel panel = new JPanel();
	JButton Back = new JButton("Back");
	JButton Execute = new JButton("Execute");
	JButton Exit = new JButton("Exit");

	// To view the results in HTML page
	JButton viewHTML = new JButton("View in HTML page");

	final Logger logger = Logger.getLogger(Exec.class);
	String[] nullString = null;

	// Creating the list box for Transmitters

	DefaultListModel<String> TxListModel = new DefaultListModel<>();
	JScrollPane TxScroll = new JScrollPane();
	JList<String> TxBox = new JList<String>(TxListModel);
	JButton TxAdd = new JButton("Add");
	JFileChooser fcTx = new JFileChooser();
	ArrayList<Model> TxArray = new ArrayList<Model>();

	// Maintaining the variable for updating the warnings
	MethodAnalysis methAn = new MethodAnalysis();
	// Creating the list box for Receivers

	DefaultListModel<String> RxListModel = new DefaultListModel<>();
	JScrollPane RxScroll = new JScrollPane();
	JList<String> RxBox = new JList<String>(RxListModel);
	JButton RxAdd = new JButton("Add");
	JFileChooser fcRx = new JFileChooser();
	ArrayList<Model> RxArray = new ArrayList<Model>();
	
	//Boolean variable for Octave Logging enable/disable
	Boolean OctaveLogging = false;
	//filepath for the logging file
//	public String logFilePath = null;

	String CompatStat = null;
	public String logFilePath;


	public static String ExecuteCompatiabilityTestFromCLI(List<String> txModelsFilePaths,
			List<String> rxModelsFilePaths) {

		StringBuilder errorMessage = new StringBuilder();
		final Logger logger = Logger.getLogger(Exec.class);
		ArrayList<TxModel> TxData = new ArrayList<TxModel>();
		ArrayList<RxModel> RxData = new ArrayList<RxModel>();
		MethodAnalysis methAn = new MethodAnalysis();
		int[] txBoxIndices = new int[txModelsFilePaths.size()];
		int[] rxBoxIndices = new int[rxModelsFilePaths.size()];

		ArrayList<Model> TxArray = new ArrayList<Model>();
		ArrayList<Model> RxArray = new ArrayList<Model>();

		for (int i = 0; i < txModelsFilePaths.size(); i++) {
			File file = new File(txModelsFilePaths.get(i));
			txBoxIndices[i] = i;
			if (file.exists() && !file.isDirectory()) {
				System.out.println("File Exists");
				logger.info("File Exists");
				Model model = new Model();
				model.ModelName = file.getName();
				model.ModelPath = file.getAbsolutePath();
				TxArray.add(model);
			}
			try {
				JAXBContext TxContext = JAXBContext.newInstance(TxModel.class);
				Unmarshaller TxUnmarshaller = TxContext.createUnmarshaller();
				JAXBElement<TxModel> TxElement = TxUnmarshaller.unmarshal(new StreamSource(file), TxModel.class);

				TxData.add(TxElement.getValue());
				System.out.println(TxData.get(i).toString());
				logger.debug(TxData.get(i).toString());
			} catch (Exception exp) {

				errorMessage.append("\nIncorrect Format - File is of invalid format");
			}
		}
		for (int i = 0; i < rxModelsFilePaths.size(); i++) {
			File file = new File(rxModelsFilePaths.get(i));
			txBoxIndices[i] = i;
			if (file.exists() && !file.isDirectory()) {
		System.out.println("File Exists");
				logger.info("File Exists");
				Model model = new Model();
				model.ModelName = file.getName();
				model.ModelPath = file.getAbsolutePath();
				RxArray.add(model);
			}

			try {
				JAXBContext RxContext = JAXBContext.newInstance(RxModel.class);
				Unmarshaller RxUnmarshaller = RxContext.createUnmarshaller();
				JAXBElement<RxModel> RxElement = RxUnmarshaller.unmarshal(new StreamSource(file), RxModel.class);

				RxData.add(RxElement.getValue());
				System.out.println(RxData.get(i).toString());
				logger.debug(RxData.get(i).toString());
			} catch (Exception exp) {

				errorMessage.append("\nIncorrect Format - File is of invalid format");
			}
		}
		try {

			String ratedMethod = methAn.analyseRatedMethod(TxData, RxData);
			System.out.println(ratedMethod);
			logger.debug(ratedMethod);
			methAn.execCompat(ratedMethod, txBoxIndices, TxData, TxArray, rxBoxIndices, RxData, RxArray, false);
			if (methAn.warningFlag) {
				new Warn().showWarnings("Systems Compatible", methAn.warningMessage);
				methAn.warningFlag = false;
				methAn.warningMessage = "\n";
			}

		} catch (Exception exp) {

			errorMessage.append("Warning " + methAn.warningMessage);
		}

		return errorMessage.toString();
	}
	
	public JPanel getPanel(){
		
			logger.addAppender(Home.appender);
		
		panel.setBorder(new TitledBorder(null, "Execute Compatibility Test", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension( 850, 800 ));
        
        // Positioning the Transmitter list and label        
        JLabel TxLabel = new JLabel("Transmitter Model");
        Dimension TxLabelSize = TxLabel.getPreferredSize();
        TxLabel.setBounds(25, 30, TxLabelSize.width, TxLabelSize.height);
        panel.add(TxLabel);
        
        Dimension TxBoxSize = TxBox.getPreferredSize();
        TxScroll.setBounds(25, 50, TxBoxSize.width + 200, TxBoxSize.height + 100);
        TxScroll.setViewportView(TxBox);
        panel.add(TxScroll);        
        
        Dimension TxAddSize = TxAdd.getPreferredSize();
        TxAdd.setBounds(25 + 220, 50, TxAddSize.width, TxAddSize.height);
        panel.add(TxAdd);
        
        // Operation for adding more transmitter models        
        TxAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fcTx.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
				fcTx.setFileFilter(xmlfilter);
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
        RxLabel.setBounds(25, 100 + 70, RxLabelSize.width, RxLabelSize.height);
        panel.add(RxLabel);
        
        Dimension RxBoxSize = RxBox.getPreferredSize();
        RxScroll.setBounds(25, 100 + 90, RxBoxSize.width + 200, RxBoxSize.height+100);
        RxScroll.setViewportView(RxBox);
        panel.add(RxScroll);
        
        Dimension RxAddSize = RxAdd.getPreferredSize();
        RxAdd.setBounds(25 + 220,100 + 90, RxAddSize.width, RxAddSize.height);
        panel.add(RxAdd);
        
        // Setting operation for adding receiver models.
        RxAdd.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fcRx.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter xmlfilter = new FileNameExtensionFilter("xml files (*.xml)", "xml");
				fcRx.setFileFilter(xmlfilter);
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
        //Adding the help label to select more than one model
        JLabel modelsLabel_1 = new JLabel("Please select at least one Transmitter and one Receiver model.");
        JLabel modelsLabel_2 = new JLabel("Use the Ctrl button to select more than one model(transmitter/receiver).");
              
        modelsLabel_1.setFont(new Font(modelsLabel_1.getFont().getName(), Font.ITALIC, 11));
        Dimension modelsLabelSize_1 = modelsLabel_1.getPreferredSize();
        modelsLabel_1.setBounds(25,300, modelsLabelSize_1.width, modelsLabelSize_1.height);
        panel.add(modelsLabel_1);
        
        modelsLabel_2.setFont(new Font(modelsLabel_2.getFont().getName(), Font.ITALIC, 11));
        Dimension modelsLabelSize_2 = modelsLabel_2.getPreferredSize();
        modelsLabel_2.setBounds(25,320, modelsLabelSize_2.width, modelsLabelSize_2.height);
        panel.add(modelsLabel_2);
        
        //Adding checkbox to enable/disable logging for Octave code
        JCheckBox logCheckBox = new JCheckBox("Enable Logging for Octave Code");
        
        Dimension logCheckBoxSize = logCheckBox.getPreferredSize();
        logCheckBox.setBounds(25,340, logCheckBoxSize.width, logCheckBoxSize.height);
        panel.add(logCheckBox);
        
        // Placing Back, Execute and Exit operations
        
        Dimension BackSize = Back.getPreferredSize();
        Back.setBounds(255, 370, BackSize.width, BackSize.height);
        panel.add(Back);
        
        Dimension ExecuteSize = Execute.getPreferredSize();
        Execute.setBounds(343, 370, ExecuteSize.width, ExecuteSize.height);
        panel.add(Execute);
        
        Dimension ExitSize = Exit.getPreferredSize();
        Exit.setBounds(450, 370, ExitSize.width, ExitSize.height);
        panel.add(Exit);
        
        //Placing the View button
        Dimension viewSize = viewHTML.getPreferredSize();
        viewHTML.setBounds(350, 210, viewSize.width, viewSize.height);
     //   panel.add(viewHTML);
        
        
        //Adding action listener for viewing HTML page
        ActionListener viewOp = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				String fileUrl = methAn.compatTestDirectory+"/first_Trial.html";
				File htmlURL = new File(fileUrl);
				
				try {
					Desktop.getDesktop().open(htmlURL);
				} catch (IOException e1) {
					logger.error("Error in opening the web page"+ e1.getMessage() );
				}
			}
		};

        // Setting operation for executing compatibility test.
        ActionListener execOp = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				int[] indices = TxBox.getSelectedIndices();
				ArrayList<TxModel> TxData = new ArrayList<TxModel>();
				System.out.println(indices.length);
				logger.debug(indices.length);
				
				for(int i=0;i<indices.length;i++){
					
					int Index = indices[i];
					Model model = TxArray.get(Index);					
					
					String filePath = model.ModelPath;//+"/"+model.ModelName+".xml";
					File file = new File(filePath);
					if(file.exists() && !file.isDirectory()){
						System.out.println("File Exists");
						logger.info("File Exists");
					}
					try{
					JAXBContext TxContext = JAXBContext.newInstance(TxModel.class);
					Unmarshaller TxUnmarshaller = TxContext.createUnmarshaller();
					JAXBElement<TxModel> TxElement = TxUnmarshaller.unmarshal(new StreamSource(filePath),
							TxModel.class);
					
					TxData.add(TxElement.getValue());
					System.out.println(TxData.get(i).toString());
					logger.debug(TxData.get(i).toString());
					}catch(Exception exp){
						methAn.warningFlag = true;
						methAn.warningMessage = methAn.warningMessage + "\nIncorrect Format - File is of invalid format";
						
						//new Warn().setWarn("Incorrect Format", "File is of invalid format");
					}
				}
	
				int[] txBoxIndices = indices;
				indices = RxBox.getSelectedIndices();
				int[] rxBoxIndices = indices;
				ArrayList<RxModel> RxData = new ArrayList<RxModel>();
				
				for(int i=0;i<indices.length;i++){
					
					int Index = indices[i];

					Model model = RxArray.get(Index);					
					
					String filePath = model.ModelPath;//+"/"+model.ModelName+".xml";
					File file = new File(filePath);
					if(file.exists() && !file.isDirectory()){
						System.out.println("File Exists");
						logger.info("File Exists");
						
					}
					try{
					JAXBContext RxContext = JAXBContext.newInstance(RxModel.class);
					Unmarshaller RxUnmarshaller = RxContext.createUnmarshaller();
					JAXBElement<RxModel> RxElement = RxUnmarshaller.unmarshal(new StreamSource(filePath),
							RxModel.class);
					
					RxData.add(RxElement.getValue());
					System.out.println(RxData.get(i).toString());
					logger.debug(RxData.get(i).toString());
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
					logger.debug(ratedMethod);
					methAn.execCompat(ratedMethod, txBoxIndices, TxData, TxArray, 
							rxBoxIndices, RxData, RxArray, OctaveLogging);
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
        
        logCheckBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
					OctaveLogging = true;

				if(e.getStateChange() == ItemEvent.DESELECTED)
					OctaveLogging = false;
				
			}
		});
        
        Execute.addActionListener(execOp);        
        viewHTML.addActionListener(viewOp);
        
        return panel;
        
		}
}
