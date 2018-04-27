package reportsGeneration;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/*
 * This class loads the template HTML file and updates it with respective values 
 */

public class LoadCompatibilityReport {
	
	private String templatePath;
	
	public LoadCompatibilityReport(String templatePath)
	{
		this.templatePath = templatePath;
	}
	
	
	//Define methods based on the type of compatibility test
	
	/*
	 * Adds the results into the HTML page and modifies display content accordingly
	 * @input CompatStat - contains the compatibility statisitics
	 * @input PowerMargin - contains the Power margin value
	 * Returns no value
	 */
	public void displayCompatibilityReport(String CompatStat,String PowerMargin, String reportPath)
	{
		String image_1 = null;
		String image_2 = null;
		String htmlFilename = this.templatePath+"/Basic_CompatTest.html";
		String stringHTML = null;
		
        try{

            //Read the entire HTML file contents 
           
        	stringHTML = new String(Files.readAllBytes(Paths.get(htmlFilename)));
        	image_1 = reportPath+"/Analysis_Figure_1.png";
        	image_2 = reportPath+"/CompatAnalysis.png";
        }catch (IOException e) {
    		System.out.println("Error in reading the HTML template contents from the file");
    	}
        catch(Exception e){
        	System.out.println("Error while reading image files");
       }
       if(stringHTML != null)
       {
    	    
           Document rootDoc = Jsoup.parse(stringHTML); 
           
           //Navigate the document and obtain the division element where the content needs to be added
           Element divElem =  rootDoc.getElementById("div02");
           
           
           //Update the image src in the img elements
           Element imgElem_1 = rootDoc.getElementById("img1");
           imgElem_1.attr("src", image_1);
           
           Element imgElem_2 = rootDoc.getElementById("img2");
           imgElem_2.attr("src", image_2);
           
           
           //Add the contents to paragraph element
           Element paraElem_1 = rootDoc.createElement("p");
           paraElem_1.append("Compatibility Result: " + CompatStat);
           
           Element paraElem_2 = rootDoc.createElement("p");
           paraElem_2.append("Power Margin: " + PowerMargin);
           
           divElem.appendChild(paraElem_1);
           divElem.appendChild(paraElem_2);
           
           //Write the contents into the file and 
           BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(htmlFilename));
			writer.write(rootDoc.html());
            writer.close();
	                     
		} catch (IOException e) {
			System.out.println("Error in writing the HTML contents into the file");
		}
		
		File htmlURL = new File(htmlFilename);
		
		try {
			Desktop.getDesktop().open(htmlURL);
		} catch (IOException e1) {
			System.out.println("Error in displaying the HTML file");
		}
           
           
       }
		
	}
	public void displayBWRatedAnalysis(final ArrayList<String> allCompatModelList, final ArrayList<String> nonCompatModelList , 
			final ArrayList<String> compatModelList, String reportPath )
	{
		String image_1 = null;
		String image_2 = null;
		String htmlFilename = this.templatePath+"/BWRatedAnalysis.html";
		String stringHTML = null;
		
        try{

            //Read the entire HTML file contents 
           
        	stringHTML = new String(Files.readAllBytes(Paths.get(htmlFilename)));
        	image_1 = reportPath+"/Analysis_Figure_1.png";
        	image_2 = reportPath+"/BWRatedAnalysis.png";
        }catch (IOException e) {
    		System.out.println("Error in reading the HTML template contents from the file");
    	}
        catch(Exception e){
        	System.out.println("Error while reading image files");
       }
       if(stringHTML != null)
       {
    	    
           Document rootDoc = Jsoup.parse(stringHTML); 
           
           //Navigate the document and obtain the division element where the content needs to be added
         
           
           //Update the image src in the img elements
           Element imgElem_1 = rootDoc.getElementById("img1");
           imgElem_1.attr("src", image_1);
           
           //Update the image src in the img elements
           Element imgElem_2 = rootDoc.getElementById("img2");
           imgElem_2.attr("src", image_2);
           
                     
           //Add the non compatible table contents
           
           Element nonCompTable = rootDoc.getElementById("notcomp");
         
           for(int i=0;i<nonCompatModelList.size();i++)
           {
        	   Element row = rootDoc.createElement("tr");
               Element cell = rootDoc.createElement("td");
               cell.appendText(nonCompatModelList.get(i));
               row.appendChild(cell);
               nonCompTable.appendChild(row);
               
           }
           
           Element allcompTable = rootDoc.getElementById("allcomp");
           
           for(int i=0;i<allCompatModelList.size();i++)
           {
        	   Element row = rootDoc.createElement("tr");
               Element cell = rootDoc.createElement("td");
               cell.appendText(allCompatModelList.get(i));
               row.appendChild(cell);
               allcompTable.appendChild(row);
               
           }
           
           
           Element compTable = rootDoc.getElementById("speccomp");
           
           for(int i=0;i<compatModelList.size();i++)
           {
        	   Element row = rootDoc.createElement("tr");
               Element cell = rootDoc.createElement("td");
               cell.appendText(compatModelList.get(i));
               row.appendChild(cell);
               compTable.appendChild(row);
               
           }
           //Add the tables to the div
          
           Element divElem_1 =  rootDoc.getElementById("div03");
           Element divElem_2 =  rootDoc.getElementById("div04");
           Element divElem_3 =  rootDoc.getElementById("div05");
           
           divElem_1.appendChild(nonCompTable);
           divElem_2.appendChild(allcompTable);
           divElem_3.appendChild(compTable);

                  
           //Write the contents into the file and 
           BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(htmlFilename));
			writer.write(rootDoc.html());
            writer.close();
	                     
		} catch (IOException e) {
			System.out.println("Error in writing the HTML contents into the file");
		}
		
		File htmlURL = new File(htmlFilename);
		
		try {
			Desktop.getDesktop().open(htmlURL);
		} catch (IOException e1) {
			System.out.println("Error in displaying the HTML file");
		}
           
           
       }
		
	}
	

}
