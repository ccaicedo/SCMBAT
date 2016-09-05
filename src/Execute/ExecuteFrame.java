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
* ExecuteFrame.java
* Creates a report window to display compatibility results 
* for non-rated underlay masks (i.e for Total Power Method and Maximum Power Density Method)
*/

package Execute;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ExecuteFrame {
	
	String plotPath = "Octave/CompatAnalysis.png";
	JFrame frame;
	JLabel picLabel;
	
	public JFrame getFrame(String CompatStat,String PowerMargin){
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
        
        JLabel compatLabel = new JLabel("Compatibility Result: " + CompatStat);
        Dimension compatLabelSize = compatLabel.getPreferredSize();
        compatLabel.setBounds(700, 50, compatLabelSize.width, compatLabelSize.height);
        frame.add(compatLabel);
        
        JLabel pMarginLabel = new JLabel("Power Margin: "+PowerMargin);
        Dimension pMarginSize = pMarginLabel.getPreferredSize();
        pMarginLabel.setBounds(700, 80, pMarginSize.width, pMarginSize.height);
        frame.add(pMarginLabel);
        
        System.out.println(CompatStat);
        
		return frame;
		
	}
}
