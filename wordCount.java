package informationRetrievalAssignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class wordCount {
	 
	public  int wordCounts(String File) {
 
		String Line;
		int totalWords = 0;
		String[] myWords = null;
		
		try (BufferedReader buff = new BufferedReader(new FileReader(File))){ 

			while ((Line = buff.readLine()) != null) {
				//System.out.println(Line);

				String temp = Line.replaceAll(".", " ");
				//System.out.println(temp);
				String temp1 = Line.replaceAll("  ", " ");
				myWords = temp.split(" ");
				System.out.println(myWords);

            }
			totalWords += myWords.length;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (totalWords);
	} // end of wordCounts()
	
	public static void main(String[] args){
	String testing = ".I 1009,free flight measurements of the static and dynamic     charts of thermodynamic properties for equilibrium air are presented with sufficient accuracy to permit the calculation of flow parameters in hypersonic nozzles operating at stagnation temperatures up to 4 950 r and pressures up to 1 000 atm    flow parameters calculated from these charts are presented for a series of stagnation temperatures between use of these parameters  it is possible to calibrate a nozzle in the conventional way    a method is also presented from which the flow parameters for conditions other than those chosen herein may be calculated    real gas effects on the calculation of a hypersonic nozzle contour are shown by an example calculation in which the nozzle contour for mach number 12 was determined by including real gas effects  and this contour was compared with one calculated by ideal gas considerations    also";
	String modi = testing.replaceAll("\\s+", " ");
	System.out.println(modi);
	}
} // end of class
