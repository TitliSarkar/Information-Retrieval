package informationRetrievalAssignment;

import java.io.IOException;
import java.sql.SQLException;

public class qryFileExecution {

	public static void main(String[] args) throws InterruptedException, IOException, NumberFormatException, ClassNotFoundException, SQLException {
		long startTime = System.nanoTime();
		String qryFileInput = "C://Users/Titli Sarkar/workspace/IRAssignment/cran.qry";
		qryFileFilterTest qfr =  new qryFileFilterTest();
		int no_of_queries = qfr.getQryFileContent(qryFileInput);
		System.out.println("No of query file is : " + no_of_queries);
		String qryFileKeyValuePaired = "C://Users/Titli Sarkar/workspace/IRAssignment/qry-key-value-paired.txt";
		qfr.putQryFileContent(qryFileKeyValuePaired);
		
		String  qryfileTokenized= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-tokenized.txt";
		qryTokenization qtk = new qryTokenization();
		qtk.tokenize(qryFileKeyValuePaired, qryfileTokenized);
		
		String  qryfileStopWordRemoved= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-stopWord-removed.txt";
		String  qryfileSpaceRemoved= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-whitespace-removed.txt";
		qryMyStemming qmst =  new qryMyStemming();
		qmst.stemStopWords(qryfileTokenized,qryfileStopWordRemoved);
		qmst.whieSpaceRemove(qryfileStopWordRemoved,qryfileSpaceRemoved);
		
		String  qrydictionary= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-dictionary.txt";
		qryTermDocumentMatrix qtdm =  new qryTermDocumentMatrix();
		qtdm.buildTermDocMatix(qryfileSpaceRemoved,qrydictionary);
		
		String  termQryMatrixFormed= "C://Users/Titli Sarkar/workspace/IRAssignment/term-qry-matrix-formed.txt";
		String  qryTermMatrixFormed= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-term-matrix-formed.txt";	
		String  termQryMatrixFormedNew= "C://Users/Titli Sarkar/workspace/IRAssignment/term-qry-matrix-newFreqs.txt";
		String  qryTermMatrixFormedNew= "C://Users/Titli Sarkar/workspace/IRAssignment/qry-term-matrix-newFreqs.txt";
		tqMatrixFormulation tqmf =  new tqMatrixFormulation();
		tqmf.TermQryMatix(qrydictionary,termQryMatrixFormed, qryTermMatrixFormed, termQryMatrixFormedNew, qryTermMatrixFormedNew);
		
		long endTime = System.nanoTime();
	    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
	    System.out.println("Time taken for execution the whole code is : "+ duration + " nanoseconds");
	    System.out.println("2nd Code ended. Thank you.");
	} //end of main()
} //end of class 
