/**
 * This file seperates/tokenizes each word from the collection.
 */
package informationRetrievalAssignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class tokenization {

public void tokenize(String inFile, String outFile) throws IOException{
	long startTime = System.nanoTime();

	HashMap<Integer, String> map = new HashMap<Integer, String>();
    String line;
    BufferedReader reader = new BufferedReader(new FileReader(inFile));
    FileWriter fstream = new FileWriter(outFile);
	BufferedWriter out = new BufferedWriter(fstream);
	int no_of_words = 0;
	
    while ((line = reader.readLine()) != null)
    {
        String[] parts = line.split(",", 2);
        if (parts.length >= 2)
        {
            Integer key = Integer.parseInt(parts[0]);
            String value = parts[1];
            no_of_words += wordCount(value);
            map.put(key, value);
        } 
    } //end while
    System.out.println("Total no. of words in the collection: " + no_of_words);

    //doing tokenization 
	Map<Integer, String> treeMap = new TreeMap<Integer, String>(map);

    for (Integer key : treeMap.keySet())
    {	
    	String[] tempValue = treeMap.get(key).trim().split(" ");
    	for(int i=0;i<tempValue.length;i++){
    		if(tempValue[i] == null){i++;}
    		out.write(key + "," + tempValue[i] + "\n");
    	}    
    }
    reader.close();
    long endTime = System.nanoTime();
    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
    System.out.println("Time taken for tokenization is : "+ duration + " nanoseconds");
}//end of tokenize()

//this function gives count of words in a string
public int wordCount(String str){
	
	String trimmed = str.trim();
	int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
	return words;
} //end of wordCount

	public static void main(String[] args) throws IOException{
		String  fileTokenized= "C://Users/Titli Sarkar/workspace/IRAssignment/tokenized.txt";
		String fileKyValuePared = "C://Users/Titli Sarkar/workspace/IRAssignment/key-value-paired.txt";
		tokenization tk = new tokenization();
		tk.tokenize(fileKyValuePared, fileTokenized);
	} //end of main()
} //end of class tokenization
