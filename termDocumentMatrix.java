/**
 * This file creates a hashmap of term document matrix of the form hashmap<String, List<Integer>>, which stores terms and list of their corresponding documents where  they are contained.
 */
package informationRetrievalAssignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class termDocumentMatrix {

public void buildTermDocMatix(String inputFile, String termDocMatrix) throws IOException{

	long startTime = System.nanoTime();
	
		Map<String, List<Integer>> tdmat = new HashMap<String, List<Integer>>();
		String line;
	    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
	    FileWriter fstream = new FileWriter(termDocMatrix);
		
	    while ((line = reader.readLine()) != null) //reading each line from input file
	    {
	        String[] parts = line.split(",", 2); // splitting the line in two parts: docId & Word
	        Integer key = Integer.parseInt(parts[0]);  //getting the docId          
	        String value = parts[1].replace("[","").replace("]", "").replace("\\s", ""); // getting corresponding word
	        
	        if(tdmat.containsKey(value)){ //if that word is already present in map, insert the docId in it's value list  
	        	
	        	List<Integer> docIds = new ArrayList<Integer>();
	        	docIds.add(key);
	        	if(tdmat.get(value) != null){
	        		 for(Integer id : tdmat.get(value)){
	         	    	docIds.add(id);
	         	    }	                
	        	}
		        tdmat.put(value, docIds);
	       } 
	        else{
	        	List<Integer> docIds = new ArrayList<Integer>();
        	    docIds.add(key);
    	        tdmat.put(value, docIds);
	        }
	    }//end of while
	    
	    //writing map to output file 
	    FileWriter fstream1 = new FileWriter(termDocMatrix);
		BufferedWriter out1 = new BufferedWriter(fstream1);	
		Map<String, List<Integer>> treeMap = new TreeMap<String, List<Integer>>(tdmat);
		int no_of_unique_word=0;
	    for (String key : treeMap.keySet())
	    {	
        	List<Integer> builder = new ArrayList<Integer>();
        	Collections.sort(treeMap.get(key));
	    	for(Integer id : treeMap.get(key)){ 
	        	  builder.add(id);
	    	}
        	String text = builder.toString();
	    	//System.out.println(text);
	    	out1.write(key + "," + text + "\n");
	    	no_of_unique_word++;
	    }
	    System.out.println("Number of unique words in the collection is: " + no_of_unique_word);
	    reader.close();
	    out1.close();
	    long endTime = System.nanoTime();
	    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
	    System.out.println("Time taken for dictionary formation is : "+ duration + " nanoseconds");
	} //end of buildTermDocMat()

	public static void main(String[] args) throws IOException {
		
		String  fileSpaceRemoved= "C://Users/Titli Sarkar/workspace/IRAssignment/whitespace-removed.txt";
		String  dictionary= "C://Users/Titli Sarkar/workspace/IRAssignment/dictionary.txt";
		termDocumentMatrix tdm =  new termDocumentMatrix();
		tdm.buildTermDocMatix(fileSpaceRemoved,dictionary);
		
	} //end main()
	
}//end of class termDocumentMatrix {

