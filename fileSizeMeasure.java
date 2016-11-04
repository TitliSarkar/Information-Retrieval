/**
 * This file gives size of the each text file generated .
 */
package informationRetrievalAssignment;

import java.io.File;

public class fileSizeMeasure {

	public void getFileSize(){
		File f1 = new File("C://Users/Titli Sarkar/workspace/testProject/key-value-sorted.txt");
		System.out.println("Size of the 'key-value-sorted.txt' file is: " + f1.getTotalSpace() + " bytes");
		
		File f2 = new File("C://Users/Titli Sarkar/workspace/testProject/tokenized.txt");
		System.out.println("Size of the 'tokenized.txt' file is: " + f2.getTotalSpace() + " bytes");
		
		File f3 = new File("C://Users/Titli Sarkar/workspace/testProject/stopWord-removed.txt");
		System.out.println("Size of the 'stopWord-removed.txt' file is: " + f3.getTotalSpace() + " bytes");
		
		File f4 = new File("C://Users/Titli Sarkar/workspace/testProject/whitespace-removed.txt");
		System.out.println("Size of the 'whitespace-removed.txt' file is: " + f4.getTotalSpace() + " bytes");
		
		File f5 = new File("C://Users/Titli Sarkar/workspace/testProject/dictionary.txt");
		System.out.println("Size of the 'dictionary.txt' file is: " + f5.getTotalSpace() + " bytes");
		
		File f6 = new File("C://Users/Titli Sarkar/workspace/testProject/term-document-matrix-formed.txt");
		System.out.println("Size of the 'term-document-matrix-formed.txt' file is: " + f6.getTotalSpace() + " bytes");
		
		File f7 = new File("C://Users/Titli Sarkar/workspace/testProject/document-term-matrix-formed.txt");
		System.out.println("Size of the 'document-term-matrix-formed' file is: " + f7.getTotalSpace() + " bytes");
		
		File f8 = new File("C://Users/Titli Sarkar/workspace/testProject/inverted-index-formed.txt");
		System.out.println("Size of the 'inverted-index-formed' file is: " + f8.getTotalSpace() + " bytes");
	}
}
