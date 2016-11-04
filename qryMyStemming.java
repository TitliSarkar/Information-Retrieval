/**
 * This file stems the tokenized words in the collection.
 * We have used porter stemming algorithm in our code for stemming. 
 *
 */
package informationRetrievalAssignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class qryMyStemming {
	
	public void stemStopWords(String inputFile, String stopWordRemovedFile) throws IOException{
		long startTime = System.nanoTime();

	    String currentLine;
	    BufferedReader reader1 = new BufferedReader(new FileReader(inputFile));
	    FileWriter fstream = new FileWriter(stopWordRemovedFile);
		BufferedWriter out = new BufferedWriter(fstream); 
        String u = null;
	    while ((currentLine = reader1.readLine()) != null)
	    {
	        String[] parts = currentLine.split(",", 2);
	        if (parts.length >= 2)
	        {	        	
	            stemming s = new stemming();
	            char[] w = new char[parts[1].length()];
	            for(int i=0; i<parts[1].length(); i++){
	            	s.add(Character.toLowerCase(parts[1].charAt(i)));
	            }	            
	            s.stem();
                u = s.toString();
	        }
	        String newval = u.replace("(", "").replace("'", "").replace(",", "");
            out.write(parts[0] + "," + newval + "\n");
	    } //end of while
	    out.close();
	    long endTime = System.nanoTime();
	    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
	    System.out.println("Time taken for stemming stopwords is : "+ duration + " nanoseconds");
	}//end of stemStopWords()
	
	//This function removes all whitespaes from the entries
	public void whieSpaceRemove(String file, String opFile) throws NumberFormatException, IOException{
		
		String line;
	    BufferedReader reader = new BufferedReader(new FileReader(file));
		FileWriter fstream = new FileWriter(opFile);
		BufferedWriter out = new BufferedWriter(fstream);
		while ((line = reader.readLine()) != null)
	    {
	        String[] parts = line.split(",", 2);
	        if (parts.length >= 2)
	        {
	            Integer key = Integer.parseInt(parts[0]);
	            String value = parts[1];
	            String seq = key.toString()+",";
	            if(line.equals(seq))continue;
	            out.write(line + System.getProperty("line.separator"));
	        } 
	    }//end while
		reader.close();
	    out.close();
	    
	} //end of function whieSpaceRemove()
	public static void main(String[] args) throws InterruptedException, IOException{
		
		String  qryfileTokenized= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-tokenized.txt";
		String  qryfileStopWordRemoved= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-stopWord-removed.txt";
		String  qryfileSpaceRemoved= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-whitespace-removed.txt";
		qryMyStemming qmst =  new qryMyStemming();
		qmst.stemStopWords(qryfileTokenized,qryfileStopWordRemoved);
		qmst.whieSpaceRemove(qryfileStopWordRemoved,qryfileSpaceRemoved);
		
	}
} //end of class myStemming
