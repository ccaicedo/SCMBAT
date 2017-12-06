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
 * Open.java
 * Creates a panel for opening/loading an existing model.
*/

package SCM_home;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;
import org.ieee.dyspansc._1900._5.scm.RxModelType;
import org.ieee.dyspansc._1900._5.scm.TxModelType;
import org.w3c.dom.Document;

import Load.LoadGUI;
import Load.LoadRxModel;
import Load.LoadTxModel;
import SCM_gui.SCM_MainWindow;

public class Open {

	public JPanel panel = new JPanel();
	JComboBox<String> Box = new JComboBox<String>();
	ArrayList<Model> modelArray = new ArrayList<Model>();
	JButton Add = new JButton("Add");
	JButton openBtn = new JButton("Open");
	JButton Back = new JButton("Back");
	JButton Exit = new JButton("Exit");
	JFileChooser fc = new JFileChooser();
	protected TxModelType TxModel;
	protected RxModelType RxModel;
	
	//Maintaining the HashMap for the already opened Models
	HashMap<String,Boolean> openedModels = new HashMap<String, Boolean>();
	
	//Holding the index of the selected Model
	int Index = -1;
	
	final Logger logger = Logger.getLogger(Open.class);
	
	public JPanel getPanel(){
		logger.addAppender(Home.appender);
		panel.setBorder(new TitledBorder(null, "Open a SCM Model", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setLayout(null);
        
        // Setting an placing the label and drop down menu for accessing the added SCM Models.
        
        JLabel Label = new JLabel("Model");
        Dimension LabelSize = Label.getPreferredSize();
        Label.setBounds(25, 30, LabelSize.width, LabelSize.height);
        panel.add(Label);
        
        Dimension BoxSize = Box.getPreferredSize();
        Box.setBounds(25, 60, BoxSize.width + 200, BoxSize.height);
        panel.add(Box);
        
        Dimension AddSize = Add.getPreferredSize();
        Add.setBounds(25 + 250, 60, AddSize.width, AddSize.height);
        panel.add(Add);
                
        // Setting the add operation for SCM models.
        Add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(panel);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            String modelName = file.getName();
		            String modelPath = file.getAbsolutePath();
		            Model model = new Model();
		            model.ModelName=modelName;
		            model.ModelPath=modelPath;
		            model.ModelMethod=null;
		            modelArray.add(model);
		            Box.addItem(modelName);
		            Box.setSelectedIndex(Box.getItemCount()-1);
		            System.out.println(modelName);
		            logger.info("Model Name   "+modelName);
		            
		        } else {
		        }
				
			}
        });
        
        // Placing Back, Open and Exit buttons.        
        Dimension BackSize = Back.getPreferredSize();
        Back.setBounds(255, 350, BackSize.width, BackSize.height);
        panel.add(Back);
        
        Dimension OpenSize = openBtn.getPreferredSize();
        openBtn.setBounds(353, 350, OpenSize.width, OpenSize.height);
        panel.add(openBtn);
        
        Dimension ExitSize = Exit.getPreferredSize();
        Exit.setBounds(450, 350, ExitSize.width, ExitSize.height);
        panel.add(Exit);
        
       
        
        // Setting the operation opening a SCM model.
        ActionListener openOp = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
			    Index = Box.getSelectedIndex();
				Model model = modelArray.get(Index);
				
				// Read Operations  
				
				String device = "NULL";
				
				String filePath = model.ModelPath+"/"+model.ModelName+".xml";
				File file = new File(filePath);
				if(file.exists() && !file.isDirectory()){
					System.out.println("File Exists");
					logger.info("File Exists");
				}
				
				try{
					
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(file);
							
					doc.getDocumentElement().normalize();			
					
					
					String type = doc.getDocumentElement().getNodeName();
					
					switch(type){
					case "txModel": JAXBContext TxContext = JAXBContext.newInstance(TxModelType.class);
						Unmarshaller TxUnmarshaller = TxContext.createUnmarshaller();

						JAXBElement<TxModelType> TxElement = TxUnmarshaller.unmarshal(new StreamSource(filePath),
								TxModelType.class);
						device = "Tx";
						TxModel = TxElement.getValue();
						break;
						
					case "rxModel": JAXBContext RxContext = JAXBContext.newInstance(RxModelType.class);
				    	Unmarshaller RxUnmarshaller = RxContext.createUnmarshaller();
				    	
				    	JAXBElement<RxModelType> RxElement = RxUnmarshaller.unmarshal(new StreamSource(filePath),
				        RxModelType.class);
				    	device = "Rx";
				    	RxModel = RxElement.getValue();
				    	
				    	break;
						
					default:
						break;							
					}
					
				}catch(Exception exp){
					exp.printStackTrace();
				}
				
				
				// Load Operations
				
				SCM_MainWindow scm = new SCM_MainWindow(openedModels);
			
				if(device.equals("Tx")){
					
					LoadGUI load = new LoadTxModel();
					scm.device="Tx";
					scm.SaveName=model.ModelName;
					scm.mode=true;
					load.setData(scm, TxModel);
					
				}else{
					LoadGUI load = new LoadRxModel();
					scm.device="Rx";
					scm.SaveName=model.ModelName;
					scm.mode=false;
					load.setData(scm, RxModel);
				}	
				
			if(!openedModels.containsKey("Current") || openedModels.get("Current")==false)
			{
				scm.design(Index);
				openedModels.put("Current",true);
				
			}
			else
			{
			String message = "A Model is already open";
			JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
				        JOptionPane.ERROR_MESSAGE);
			}
			
			}        	
        };
        
        openBtn.addActionListener(openOp);
        
        
        return panel;
        
	}
}
