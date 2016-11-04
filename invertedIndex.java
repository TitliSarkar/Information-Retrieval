/**
 * This file makes inverted index from the collection. 
 */
package informationRetrievalAssignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;

public class invertedIndex {

	public String deDup(String s) {
	    return new LinkedHashSet<String>(Arrays.asList(s.split(" "))).toString().replaceAll("(^\\[|\\]$)", "").replaceAll("," ,"");
	}

	public void makeInvIndx(String inFile, String outFile) throws IOException{
		
		long startTime = System.nanoTime();

		HashMap<Integer, String> map = new HashMap<Integer, String>();
	    String line;
	    BufferedReader reader = new BufferedReader(new FileReader(inFile));
	    FileWriter fstream = new FileWriter(outFile);
		BufferedWriter out = new BufferedWriter(fstream);
		
	    while ((line = reader.readLine()) != null)
	    {
	        String[] parts = line.split(",", 2);
	        if (parts.length >= 2)
	        {
	            Integer key = Integer.parseInt(parts[0]);
	            String value = parts[1];
	            String un1 =  deDup(value);
	            map.put(key, un1);
	        } 
	    } //end while
	    
	    //doing tokenization 
		Map<Integer, String> treeMap = new TreeMap<Integer, String>(map);

	    for (Integer key : treeMap.keySet())
	    {	
	    	String[] tempValue = treeMap.get(key).trim().split(" ");
	    	String tmpvalstr = null;
	    	for(int i=0;i<tempValue.length;i++){
	    		if(tempValue[i] == null){i++;}
	    		tmpvalstr = tmpvalstr + tempValue[i]+" "; 		
	    	}
	    	String rplc = tmpvalstr.replaceAll("null", "");
	    	//for(int i=0;i<tempValue.length;i++){
	    		//if(tempValue[i] == null){i++;}
	    		//out.write(key + "," + tempValue[i]+ "\n");
    		      out.write(key + "," + rplc + "\n");

	    	//}    
	    }
	    reader.close();
	    long endTime = System.nanoTime();
	    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
	    System.out.println("Time taken for building inverted index is : "+ duration + " nanoseconds");
	} //end of makeInvIndx()

}//end of class 
