/**
 * This file creates a hashmap of terms and their corresponding documents where they are contained along with frequeny i.e. how many time that term has appeared in that document.
 * The hashmap is of the form hashmap<term, hashmap<docId,frequency>> . 
 * A term-document-matrix is formed from the hashmap.
 * A inverted index of that matrix is also formed from that matrix (document-term-matrix).
 */
package informationRetrievalAssignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class tqMatrixFormulation {
	
//this function makes  a hashmap of terms and corresponding to documents
	public List<Integer> TermQryMatix(String inputFile, String termDocMatrix, String docTermMatrix, String TDMtf, String DCMtf) throws IOException, ClassNotFoundException, SQLException, NumberFormatException, InterruptedException{
		
		long startTime = System.nanoTime();
			Map<String, HashMap<Integer,Integer>> tdmap = new HashMap<String, HashMap<Integer,Integer>>();
			String line;
		    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		    FileWriter fstream = new FileWriter(termDocMatrix);
			BufferedWriter out = new BufferedWriter(fstream);
			FileWriter fstream1 = new FileWriter(termDocMatrix);
			BufferedWriter out1 = new BufferedWriter(fstream1);	
			FileWriter fstream2 = new FileWriter(docTermMatrix);
			BufferedWriter out2 = new BufferedWriter(fstream2);	
			FileWriter fstream3 = new FileWriter(TDMtf);
			BufferedWriter outtdmtf = new BufferedWriter(fstream3);
			FileWriter fstream4 = new FileWriter(DCMtf);
			BufferedWriter outdcmtf = new BufferedWriter(fstream4);
			
			int wordcount=0;
			int row = 0, column = 0;
			List<Integer> dimension = new ArrayList<Integer>();
		    while ((line = reader.readLine()) != null)
		    {
		        wordcount++;
		    	String[] parts = line.split(",", 2);
		        String key = parts[0];    
		        String tmp1 = parts[1].replaceAll("\\[","").replaceAll("\\]", "").replaceAll("\\s", "");
		        String[] tempIds =tmp1.split(",");
		        List<Integer> tmp = new ArrayList<Integer>(); // list of all docIds (with duplicate) for one term

		        for(int i = 0 ; i<tempIds.length ; i++){
		        	String mystr = tempIds[i];
		        	int myId = Integer.parseInt(mystr);
		        	tmp.add(myId);
		        }

		        Set<Integer> uniqueIds = new HashSet<Integer>(tmp);  //list of unique integers
		        List<Integer> frequencies = new ArrayList<Integer>(); //list of frequency of these unique integers 
		        
		        Iterator iterator = uniqueIds.iterator(); 
		        while (iterator.hasNext()){
		        	int freq = Collections.frequency(tmp,iterator.next());
			        frequencies.add(freq);	         
			     }
		        
		        Map<Integer,Integer> idWithFreqMap = new HashMap<Integer,Integer>();
		        int j=0;
		        for (Integer s : uniqueIds) {
		        	Integer frq = frequencies.get(j++);
		        	idWithFreqMap.put(s,frq);
		        }
			    tdmap.put(key, (HashMap<Integer, Integer>) idWithFreqMap);	
		    }//end of while
		    //writing in a file
		    row = wordcount+1;
		    dimension.add(row);
		    fileFilterTest f = new fileFilterTest();
		    column = f.getFileContent("C://Users/Titli Sarkar/workspace/IRAssignment/cran.qry")+1;
		    dimension.add(column);
		    System.out.println(dimension);
		    Integer[][] tdmtrx = new Integer[row][column];
		    for(int m=0;m<row;m++){
		    	for(int n=0;n<column;n++){
		    		tdmtrx[m][n] = 0;
		    	}
		    }
			 //Iterating through the hashmap to get terms and corresponding documents with frequencies
			int i =0;
			Integer id = 0;
			Integer frequency = 0;
			List<String> termsarray = new ArrayList<String>();
			for (Map.Entry<String, HashMap<Integer, Integer>> termEntry : tdmap.entrySet()) {
			    String term = termEntry.getKey();
			    termsarray.add(term);
			    int j =0;
			    for (Map.Entry<Integer, Integer> idEntry : termEntry.getValue().entrySet()) {
			        id = idEntry.getKey();
			        frequency = idEntry.getValue();
			        for(j=0;j<id;j++);
					tdmtrx[i][j] = frequency;
			    }
			    i++;
			    //establishConnection(term,id,frequency);
			}
		    // writing the terms and and corresponding documents with frequencies in file
		   for(int i1=0;i1<row;i1++){
		    	for(int j1=0;j1<column;j1++){
		    		out1.write(tdmtrx[i1][j1] + " ");
		    	}
		    	out1.write("\n");
		    }
		   //forming TDM with tf weights 
		   float[][] tfm = new float[row][column];
		   for(int x=0;x<row;x++){
			   int max = 0;
		    	for(int y=0;y<column;y++){
		    		if(tdmtrx[x][y] > max){ max = tdmtrx[x][y];}
		    	}
		    	System.out.println(max);
		    	for(int z=0;z<column;z++){
		    		if(max == 0){tfm[x][z]=(float) 0.0;} 
		    		else{tfm[x][z]=tdmtrx[x][z]/max;}
		    	}
		    }
		// writing the TDM with tf weights to file 
		   for(int i1=0;i1<row;i1++){
		    	for(int j1=0;j1<column;j1++){
		    		outtdmtf.write(tfm[i1][j1] + " ");
		    	}
		    	outtdmtf.write("\n");
		    }
		   //forming document term matrix
		   int len = tdmtrx[0].length;
		   int wid = tdmtrx.length;
		   
		   int[][] temp = new int[len][wid];
	       for (int k = 0; k < wid; k++){
	           for (int l = 0; l < len; l++){
	               temp[l][k] =tdmtrx[k][l];
	           }  
	       }
	     //writing doc term matrix to file 
	       for(int tr=0;tr<termsarray.size();tr++){
	    	   out2.write(termsarray.get(tr)+ " ");
	       }
	       for (int k = 0; k < wid; k++){
	    	  out2.write("I"+k+" ");
	           for (int l = 0; l < len; l++){
	               out2.write(temp[l][k] + " ");
	           }  
		    	out2.write("\n");
	       }
		   
	     //forming document term matrix with tf weights
		   int len1 = tfm[0].length;
		   int wid1 = tfm.length;
		   
		   float[][] temp1 = new float[len][wid];
	       for (int k = 0; k < wid1; k++){
	           for (int l = 0; l < len1; l++){
	               temp1[l][k] =tfm[k][l];
	           }  
	       }
	     //writing doc term matrix with tf weights to file 
	       for(int tr=0;tr<termsarray.size();tr++){
	    	   outdcmtf.write(termsarray.get(tr)+ " ");
	       }
	       for (int k = 0; k < wid; k++){
	    	   outdcmtf.write("I"+k+" ");
	           for (int l = 0; l < len; l++){
	        	   outdcmtf.write(temp1[l][k] + " ");
	           }  
	           outdcmtf.write("\n");
	       }
	       
		   long endTime = System.nanoTime();
		    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
		    System.out.println("Time taken for term-qry-matrix formation is : "+ duration + " nanoseconds");
		    return (dimension);
		} //end of buildTermDocMat()

	public static void main(String[] args) throws IOException, ReflectiveOperationException, SQLException, NumberFormatException, InterruptedException {
		
		String  qrydictionary= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-dictionary.txt";
		String  termQryMatrixFormed= "C://Users/Titli Sarkar/workspace/IRAssignment/term-qry-matrix-formed.txt";
		String  qryTermMatrixFormed= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-term-matrix-formed.txt";
		String  termQryMatrixFormedNew= "C://Users/Titli Sarkar/workspace/IRAssignment/term-qry-matrix-newFreqs.txt";
		String  qryTermMatrixFormedNew= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-term-matrix-newFreqs.txt";
		tqMatrixFormulation tqmf =  new tqMatrixFormulation();
		tqmf.TermQryMatix(qrydictionary,termQryMatrixFormed, qryTermMatrixFormed, termQryMatrixFormedNew, qryTermMatrixFormedNew);
		
	}//end main()
	
}//end of class termDocumentMatrix 


