/****THIS IS THE JAVA FILE YOU NEED TO EXECUTE. IT CALLS ALL OTHER CLASSES KEPT IN DIFFERENT JAVA FILES UNDER SAME PACKAGE****/

package informationRetrievalAssignment;

import java.io.IOException;
import java.sql.SQLException;

public class progExecution {

	public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException, SQLException{
		long startTime = System.nanoTime();

		String fileInput = "C://Users/Titli Sarkar/workspace/IRAssignment/cran.all.1400";
		fileFilterTest ft = new fileFilterTest();
		int no_of_docs = ft.getFileContent(fileInput);
		System.out.println("No of docs is : " + no_of_docs);
		String fileKyValuePared = "C://Users/Titli Sarkar/workspace/IRAssignment/key-value-paired.txt";
		ft.putFileContent(fileKyValuePared);
		
		String  fileTokenized= "C://Users/Titli Sarkar/workspace/IRAssignment/tokenized.txt";
		tokenization tk = new tokenization();
		tk.tokenize(fileKyValuePared, fileTokenized);
		
		String  fileStopWordRemoved= "C://Users/Titli Sarkar/workspace/IRAssignment/stopWord-removed.txt";
		String  fileSpaceRemoved= "C://Users/Titli Sarkar/workspace/IRAssignment/whitespace-removed.txt";
		myStemming mst =  new myStemming();
		mst.stemStopWords(fileTokenized,fileStopWordRemoved);
		mst.whieSpaceRemove(fileStopWordRemoved,fileSpaceRemoved);
		
		String  dictionary= "C://Users/Titli Sarkar/workspace/IRAssignment/dictionary.txt";
		termDocumentMatrix tdm =  new termDocumentMatrix();
		tdm.buildTermDocMatix(fileSpaceRemoved,dictionary);
		
		String  termDocMatrixFormed= "C://Users/Titli Sarkar/workspace/IRAssignment/term-document-matrix-formed.txt";
		String  docTermMatrixFormed= "C://Users/Titli Sarkar/workspace/IRAssignment/document-term-matrix-formed.txt";
		String  termDocMatrixFormedNew= "C://Users/Titli Sarkar/workspace/IRAssignment/term-document-matrix-newFreqs.txt";
		String  docTermMatrixFormedNew= "C://Users/Titli Sarkar/workspace/IRAssignment/doc-term-matrix-newFreqs.txt";
		tdMatrixFormulation tdmf =  new tdMatrixFormulation();
		tdmf.TermDocMatix(dictionary,termDocMatrixFormed, docTermMatrixFormed, termDocMatrixFormedNew, docTermMatrixFormedNew);
		
		String  invertedIndexFormed= "C://Users/Titli Sarkar/workspace/IRAssignment/inverted-index-formed.txt";
		invertedIndex inv = new invertedIndex();
		inv.makeInvIndx(fileKyValuePared, invertedIndexFormed);
		
		fileSizeMeasure fm = new fileSizeMeasure();
		fm.getFileSize();
		
		long endTime = System.nanoTime();
	    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
	    System.out.println("Time taken for execution the whole code is : "+ duration + " nanoseconds");
	    System.out.println("Code ended. Thank you.");
	} //end of main()

} //end of class progExecution
