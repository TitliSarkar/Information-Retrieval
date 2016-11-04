package informationRetrievalAssignment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RSVCalculation {
	
	public void calculateRSV(String qfile, String dfile, String rsvfile) throws IOException{
		long startTime = System.nanoTime();
		
	    BufferedReader qryreader = new BufferedReader(new FileReader(qfile));
	    FileWriter fstream = new FileWriter(rsvfile);
		BufferedWriter out = new BufferedWriter(fstream);
		String qline,dline;
		int qcount = 0;
		while ((qline = qryreader.readLine()) != null){
			qcount++;
			String[] qparts = qline.split(" ");
		    float[] qnumbers = new float[qparts.length]; // float array of each qfile row
		    for (int i = 0; i < qparts.length; ++i) {
		        float number = Float.parseFloat(qparts[i]);
		        float rounded = (int) Math.round(number * 1000) / 1000f;
		        qnumbers[i] = rounded;
		    }
		    BufferedReader docreader = new BufferedReader(new FileReader(dfile));
		    int dcount = 0 ;
			while((dline = docreader.readLine()) != null){
				dcount ++;
				float dotproduct=0, rsv=0, multi=0,qmod=0,dmod=0;
				String[] dparts = dline.split(" ");
			    float[] dnumbers = new float[dparts.length]; // float array of each dfile row
			    for (int i = 0; i < dparts.length; ++i) {
			        float number = Float.parseFloat(dparts[i]);
			        float rounded = (int) Math.round(number * 1000) / 1000f;
			        dnumbers[i] = rounded;
			    }
			    for (int var = 0; var < dnumbers.length; var++) {
			    	qmod +=qnumbers[var];
			    	dmod += dnumbers[var];
			    	dotproduct += qnumbers[var]+dnumbers[var];    
		        }
			    Float tmp = dotproduct/(qmod+dmod);
			    rsv = Math.round(tmp * 1000) / 1000f;
			    System.out.println(qcount+ " " + dcount+ " " +rsv+ "\n");
			    out.write(qcount+ " " + dcount+ " " +rsv+ "\n");
			}// end inner while
	    }// end outer while
		
		long endTime = System.nanoTime();
	    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
	    System.out.println("Time taken for RSV calculation is : "+ duration + " nanoseconds");
	}//end of calculateRSV()
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String  termDocMatrixFormedNew= "C://Users/Titli Sarkar/workspace/IRAssignment/term-document-matrix-newFreqs.txt";
		String  termQryMatrixFormed= "C://Users/Titli Sarkar/workspace/IRAssignment/term-qry-matrix-formed.txt";
		String  rsvFile= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-doc-rsv.txt";
		RSVCalculation rsv = new RSVCalculation();
		rsv.calculateRSV(termQryMatrixFormed,termDocMatrixFormedNew ,rsvFile);
	} //end main()
}//end class
