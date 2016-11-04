/**
 * This file creates a hashmap of terms and their corresponding documents where they are contained along with tf given by formula.
 * The hashmap is of the form hashmap<term, hashmap<docId,tf>> . 
 * A term-document-matrix is formed from the hashmap.
 */
package informationRetrievalAssignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class tfMatFormationNew {
		
//this function makes  a hashmap of terms and corresponding to documents
public void tfm(String termDocMatrix, String tfm) throws IOException, ClassNotFoundException, SQLException{
		
	long startTime = System.nanoTime();
		
		Map<String, HashMap<Integer,Integer>> tdmap = new HashMap<String, HashMap<Integer,Integer>>();
		String line;
	    BufferedReader reader = new BufferedReader(new FileReader(termDocMatrix));
	    FileWriter fstream1 = new FileWriter(tfm);
		BufferedWriter out1 = new BufferedWriter(fstream1);
		
	    List<Float> intparts = null;
	    List<Float> intparts1 = null;
		int fsum = 0;
		
	    while ((line = reader.readLine()) != null)
	    {
	    	intparts = new ArrayList<Float>();
	    	intparts1 = new ArrayList<Float>();
	    	String[] parts = line.split("\\s+");
	       for (int i=0; i < parts.length; i++) {
	        	intparts.add(Float.parseFloat((parts[i])));
	        }
	        float max = Collections.max(intparts);
	        //System.out.println(max);
	        
	        for (int i=0; i < intparts.size(); i++) {
	        	float num = (float) Math.round((float)intparts.get(i)/max * 100) / 100 ;
	        	intparts1.add(num);
	        }
	        StringBuilder sb = new StringBuilder();
	        for (float number : intparts1) {
	          sb.append(Float.toString(number)+ " ");
	        }
	        out1.write(sb.toString()+ "\n");
	    }//end of while
	    out1.close();
	    
	   long endTime = System.nanoTime();
	    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
	    System.out.println("Time taken for term-document-matrix-newFreqs formation is : "+ duration + " nanoseconds");
	    
	} //end of buildTermDocMatNew()

//******Building document-term-matrix with new frequencies************************//	
  public void transpose(String original, String dst) throws IOException {
	  
	 Scanner input = new Scanner (original);
	// pre-read in the number of rows/columns
	int rows = 0;
	int columns = 0;
	float[][] a;
	List<String> lines = Files.readAllLines(Paths.get(original), StandardCharsets.UTF_8);
	rows = lines.size();
	//System.out.println(rows);
	//String[] tmp =lines.get(0).split(" ");
	//columns=tmp.length+1;
	//System.out.println(columns);
	a = new float[rows][]; 

	for(int i=0; i<rows; i++){
		String[] dparts = lines.get(i).split(" ");
		columns=dparts.length+1;
	    float[] dnumbers = new float[dparts.length+1];
	    columns = dparts.length+1;
		System.out.println(columns);
	    for (int j = 0; j < dparts.length; ++j) {
	        float number = Float.parseFloat(dparts[j]);
	        float rounded = (int) Math.round(number * 1000) / 1000f;
	        dnumbers[j] = rounded;
	    }
		a[i] = dnumbers;	
		System.out.println(a[i]);
	}
	
	//transposing the matrix
	/*int m = a.length;
    int n = a[0].length;
	System.out.println(n+" "+m);
	float[][] b = new float[n][m];
	
	for(int x=0; x<m; x++)
    {
        for(int y=0; y<n; y++)
        {
            b[y][x] = a[x][y];
            //System.out.println("b["+y+"]["+x+"]= "+b[y][x] +" "+m+ " "+ n);
        }
    }
	FileWriter writer = new FileWriter(dst, true);
	for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
              writer.write(b[i][j]+ " ");
        }
        writer.write("\n");   // write new line
    }
    writer.close();*/
  } // end transpose()
  
	public static void main(String[] args) throws IOException, ReflectiveOperationException, SQLException {
		
		String  termDocMatrixFormed= "C://Users/Titli Sarkar/workspace/IRAssignment/term-document-matrix-formed.txt";
		String  termDocMatrixFormedNew= "C://Users/Titli Sarkar/workspace/IRAssignment/term-document-matrix-newFreqs.txt";
		String  docTermMatrixFormedNew= "C://Users/Titli Sarkar/workspace/IRAssignment/doc-term-matrix-newFreqs.txt";
		tfMatFormationNew tfmn =  new tfMatFormationNew();
		tfmn.tfm(termDocMatrixFormed, termDocMatrixFormedNew);
		tfmn.transpose(termDocMatrixFormedNew, docTermMatrixFormedNew);
		
	}//end main()
	
}//end of class termDocumentMatrix 

