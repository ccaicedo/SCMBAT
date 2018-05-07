package Execute;

import java.io.PrintWriter;
import java.util.ArrayList;


/*
 * The class is responsible for printing the results of each compatibility test
 */
public class PrintReportResults {
	
	
	/*
	 * Display the results of ExecuteBWRated test into the Reports file
	 */
	public void printBWRated(PrintWriter printfile, ArrayList<String> allCompatModelList, ArrayList<String> nonCompatModelList,
			ArrayList<String> compatModelList, int list)
	{
		if(list == 1)
		{
			printfile.println("Method: BW Rated List Analysis");	
		}
		else
		{
			printfile.println("Method: BW Rated  Analysis");
		}
		printfile.println("");
		
		//Print the Not Compatible results
		printfile.println("Not Compatible");
		if(nonCompatModelList.size()==0)
		{
			
			printfile.println("None");
			printfile.println("");
		}
		for(int i=0;i<nonCompatModelList.size();i++){
        	printfile.println(nonCompatModelList.get(i));
        }
		
		printfile.println("");
		
		//Print the All Compatible results
		printfile.println("Compatible with All");
		if(allCompatModelList.size()==0)
		{
			
			printfile.println("None");
			printfile.println("");
		}
		
		for(int i=0;i<allCompatModelList.size();i++){
	       	printfile.println(allCompatModelList.get(i));
		}
				
		printfile.println("");
			
		//Print the Specific Compatible results
		printfile.println("Compatible with specific masks");
		if(compatModelList.size()==0)
		{
			
			printfile.println("None");
			printfile.println("");
		}
		for(int i=0;i<compatModelList.size();i++){
			printfile.println(compatModelList.get(i));
		}
						
		printfile.println("");
		
	}
	/*
	 * Display the results of BTPRated test into the Reports file
	 */
	public void printBTPRated(PrintWriter printfile, ArrayList<String> compatList, ArrayList<String> nonCompatList,
			int list)
	
	{
		if(list == 1)
		{
			printfile.println("Method: BTP Rated List Analysis");	
		}
		else
		{
			printfile.println("Method: BTP Rated  Analysis");
		}
		printfile.println("");
		
		//Print the Not Compatible results
		printfile.println("Not Compatible");
		if(nonCompatList.size()==0)
		{
			
			printfile.println("None");
			printfile.println("");
		}
		
		for(int i=0;i<nonCompatList.size();i++){
        	printfile.println(nonCompatList.get(i));
        }
		
		printfile.println("");
		
		//Print the All Compatible results
		printfile.println("Compatible Masks");
		if(compatList.size()==0)
		{
			
			printfile.println("None");
			printfile.println("");
		}
		for(int i=0;i<compatList.size();i++){
	       	printfile.println(compatList.get(i));
		}
				
		printfile.println("");
			
		}
	
	/*
	 * Display the results of DutyCycle test into the Reports file
	 */
	public void printDuty(PrintWriter printfile, ArrayList<String> compatList, ArrayList<String> nonCompatList)
	
	{
		
		printfile.println("Method: DC Rated List Analysis ");
		printfile.println("");
		
		//Print the Not Compatible results
		printfile.println("Not Compatible");
		if(nonCompatList.size()==0)
		{
			
			printfile.println("None");
			printfile.println("");
		}
		
		for(int i=0;i<nonCompatList.size();i++){
        	printfile.println(nonCompatList.get(i));
        }
		
		printfile.println("");
		
		//Print the All Compatible results
		printfile.println("Compatible Masks");
		if(compatList.size()==0)
		{
			
			printfile.println("None");
			printfile.println("");
		}
		for(int i=0;i<compatList.size();i++){
	       	printfile.println(compatList.get(i));
		}
				
		printfile.println("");
	}

}
