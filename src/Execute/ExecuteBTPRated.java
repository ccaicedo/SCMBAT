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
* ExecuteBTPRated.java
* Creates a report window to display compatibility results 
* for Bandwidth-Time Product rated underlay masks
*/

package Execute;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;


public class ExecuteBTPRated {

	String plotPath = "Octave/BTPRatedAnalysis.png";
	
	JFrame frame;
	JLabel picLabel;
	JLabel statusLabel=new JLabel();
	

	DefaultListModel<String> ListModel2 = new DefaultListModel<>();
	JScrollPane nonCompatPane = new JScrollPane();
	JList<String> nonCompatList = new JList<String>(ListModel2);
	
	DefaultListModel<String> ListModel3 = new DefaultListModel<>();
	JScrollPane compatPane = new JScrollPane();
	JList<String> compatList = new JList<String>(ListModel3);
	    
// Opening a new window frame to show the results	
	public JFrame getFrame(){
		frame = new JFrame("Compatibility Analysis Report");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);
		
        final Insets insets = frame.getInsets();
        frame.setSize((2*550) + insets.left + insets.right,
                      (700) + insets.top + insets.bottom);
        frame.setVisible(true);
		
        BufferedImage img = null;
        try{
        	img = ImageIO.read(new File(plotPath));
        	
        }catch(Exception e){
        	e.printStackTrace();
        }
        int w = img.getWidth();
        int h = img.getHeight();
        
        BufferedImage NewImg = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(0.5, 0.5);
        
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        NewImg = scaleOp.filter(img,NewImg);
        
        picLabel = new JLabel(new ImageIcon(NewImg));
        
       // Graphics g = img.getGraphics();
       
        Dimension picSize = picLabel.getPreferredSize();
        picLabel.setBounds(25, 50, picSize.width, picSize.height);        
        frame.add(picLabel);
        
        JLabel resultLabel = new JLabel("Graphical result");
        Dimension resultLabelSize = resultLabel.getPreferredSize();
        resultLabel.setBounds(25,30,resultLabelSize.width,resultLabelSize.height);
        frame.add(resultLabel);
        
        JLabel nonCompatLabel = new JLabel("Not Compatible");
        Dimension labelSize = nonCompatLabel.getPreferredSize();
        nonCompatLabel.setBounds(700,50, labelSize.width, labelSize.height);
        frame.add(nonCompatLabel);
        
        Dimension listSize = nonCompatList.getPreferredSize();
        nonCompatPane.setBounds(700,80,listSize.width+200,listSize.height+100);
        nonCompatPane.setViewportView(nonCompatList);
        frame.add(nonCompatPane);
        
        JLabel compatLabel = new JLabel("Compatible masks");
        compatLabel.setBounds(700,230, labelSize.width + 200, labelSize.height);
        frame.add(compatLabel);
        
        compatPane.setBounds(700,260,listSize.width+200,listSize.height+100);
        compatPane.setViewportView(compatList);
        frame.add(compatPane);
        
        
		return frame;
		
}
	public String getPlotPath() {
		return plotPath;
	}

	public void setPlotPath(String plotPath) {
		this.plotPath = plotPath+"Octave/BTPRatedAnalysis.png";
	}

	
void buildNonCompatList(ArrayList<String> nonCompatModelList){
	DefaultListModel<String> listModel = (DefaultListModel<String>) nonCompatList.getModel();
	for(int i=0;i<nonCompatModelList.size();i++){
       	listModel.addElement(nonCompatModelList.get(i));
       }
};
	
void buildCompatList(ArrayList<String> compatModelList){
	DefaultListModel<String> listModel = (DefaultListModel<String>) compatList.getModel();
    for(int i=0;i<compatModelList.size();i++){
     	listModel.addElement(compatModelList.get(i));
       }		
};
	
}
