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
 * Home.java
 * Creates GUI panel for the main window 
*/

package SCM_home;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import Execute.MethodAnalysis;


public class Home {

    private JPanel CreatePanel;
    private JPanel panel;
    private JPanel MainPanel;
	private JPanel ExecPanel;
	private JPanel OpenPanel;
    private Create create = new Create();
	private Exec exec = new Exec();
    public Open open = new Open(); 
    
   
    final Logger LOGGER = Logger.getLogger(Home.class);
	static String LOG_FILE_NAME= "./resources/logging.properties";
	public static RollingFileAppender appender =null;
	
    
    
	public Home(){
		initializeLogger();
		design();
	}
    public void design() {
    	
    	MainPanel=new JPanel(new CardLayout());
    	CreatePanel=create.getPanel();
    	ExecPanel = exec.getPanel();
    	OpenPanel = open.getPanel();
    	
    	final JFrame frame = new JFrame("Spectrum Consumption Model Builder and Analysis Tool");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
        final Insets insets = frame.getInsets();
        frame.setSize(550 + insets.left + insets.right,
                      430 + insets.top + insets.bottom);
        frame.setVisible(true);

        panel = new JPanel();
        panel.setLayout(null);
        
        /*  Creating and placing operation buttons for: 
         *  1. Creating a new Model
         *  2. Opening an existing Model
         *  3. Executing compatibility tests between Transmitter and Receiver models.
         */
        
        final JButton Create = new JButton("New Spectrum Consumption Model");
        final JButton Open = new JButton("Open Spectrum Consumption Model");
        final JButton ExecuteBtn = new JButton("Execute Compatibility Test");
        
        JButton Exit = new JButton("Exit");
        
        Dimension size = Open.getPreferredSize();
        
        Create.setBounds(125, 50, size.width, size.height);
        Open.setBounds(125, 100, size.width, size.height);
        ExecuteBtn.setBounds(125, 150, size.width, size.height);

        panel.add(Create);
        panel.add(Open);
        panel.add(ExecuteBtn);
        
        Dimension ExitSize = Exit.getPreferredSize();
        
        Exit.setBounds(450, 350, ExitSize.width, ExitSize.height);
        panel.add(Exit);
        
        Create.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				CardLayout cardLayout = (CardLayout) MainPanel.getLayout();
				cardLayout.show(MainPanel, "Card 2");
				
			}		
		});
        
        // Defining Execute button operation.
        
        ExecuteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		            	try{
		            		
		            		CardLayout cardLayout = (CardLayout) MainPanel.getLayout();
		            		cardLayout.show(MainPanel, "Card 3");
		            	
		            	}catch(Exception e){
		                    e.printStackTrace();
		                }
		            }
		        });	
			}
		});
        
        // Defining Open Button operation.
        
        Open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		            	try{
		            		
		            		CardLayout cardLayout = (CardLayout) MainPanel.getLayout();
		            		cardLayout.show(MainPanel, "Card 4");
		            	
		            	}catch(Exception e){
		                    e.printStackTrace();
		                }
		            }
		        });	
			}
		});
        
        // All Exit Operations
        
        ActionListener exitOp = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			}
		};
        
        Exit.addActionListener(exitOp);        
        create.Exit.addActionListener(exitOp);        
        exec.Exit.addActionListener(exitOp);
        open.Exit.addActionListener(exitOp);
        
        // All Back Operations
        
        ActionListener backOp = new ActionListener(){
        	public void actionPerformed(ActionEvent arg0) {
        		
        		CardLayout cardLayout = (CardLayout) MainPanel.getLayout();
				cardLayout.show(MainPanel, "Card 1");
        		
        	}
        };
        
        create.Back.addActionListener(backOp);        
        exec.Back.addActionListener(backOp);
        open.Back.addActionListener(backOp);
        
        MainPanel.add(panel, "Card 1");
        MainPanel.add(CreatePanel, "Card 2");
        MainPanel.add(ExecPanel, "Card 3");
        MainPanel.add(OpenPanel, "Card 4");
        frame.getContentPane().add(MainPanel);
    }
    //Initializing the logs
    private void initializeLogger()
    {
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
    	Date date = new Date();
    	String curDate = dateFormat.format(date);
    	MethodAnalysis meth = new MethodAnalysis();

    	String filename = meth.getFilePath()+"logs/SCM_logFile_"+curDate+".log";//+"_"+log_number+".log";
        try
        {
        	 PatternLayout layout = new PatternLayout(); 
        	 layout.setConversionPattern("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
             appender =  new RollingFileAppender();
             appender.setAppend(true);
             appender.setMaxFileSize("10MB");
             appender.setMaxBackupIndex(2);
             appender.setFile(filename);
             appender.setLayout(layout);
             appender.activateOptions(); 
             LOGGER.addAppender(appender);
            
        }
        catch(Exception e)
        {
        	
        	System.out.println("Exception in loading the logger in the Home");
        }
    }
    
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try{
            		
            		Home fr = new Home();
                    fr.setVisible(true);
                    
            	}catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    protected void setVisible(boolean b) {
		// TODO Auto-generated method stub		
	}
    
}
