package informationRetrievalAssignment;
/**
 * This file extracts the query ids and their corrresponding queries contents from the givem file "cran.qry" 
 * and put them in a hashmap as (qId,qContent) pair after doing preprocessing on it.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class qryFileFilterTest{

	private static HashMap<Integer,String> filterMap=new HashMap<Integer,String>();
	
	//This function gets the raw file, extracts query Ids as keys and corresponding query content as values of a hashmap 
	public int getQryFileContent(String qryFile) throws InterruptedException {
		
		long startTime = System.nanoTime();

		int count =0;
		String key="";
        boolean flag=false;
        String key1="";
        String array[];
		try {
	        File file1 = getInstance(qryFile);
            BufferedReader br = new BufferedReader(new FileReader(qryFile));
            String s = null;
            String temp="";
            String temp1="";
            StringBuffer sb = new StringBuffer();
            while ((s = br.readLine()) != null) {
            	if(s!= null && s.charAt(0)=='.' && s.charAt(1)=='I' && s.charAt(2)==' '){
            		key=s;
            	}
            	if(s.charAt(0)=='.' && s.charAt(1)=='W'){
            		while ((s = br.readLine()) != null) {
            			if((s.charAt(0)!='.' && s.charAt(1)!='I' && s.charAt(2)!=' ')){
            				array=s.split(" ");
            				for(int i=0;i<array.length;i++){
            					temp1=array[i];
            					if(array[i].contains(".")){
            						temp1=array[i].replace(".", " ");
            					}
            					if(array[i].contains("-")){
            						temp1=array[i].replace("-", " ");
            					}
            					if(array[i].contains("/")){
            						temp1=array[i].replace("/", " ");
            					}
            					if(array[i].contains(",")){
            						temp1=array[i].replace(",", " ");
            					}
            					if(array[i].contains("(")){
            						temp1=array[i].replace("(", " ");
            					}
            					if(array[i].contains(")")){
            						temp1=array[i].replace(")", " ");
            					}
            					if(array[i].contains("\\s+")){
            						temp1=array[i].replace("\\s+", " ");
            					}
            					String temp2 = temp1.replaceAll("\\s+", " ").trim();
            					
            					temp=temp.concat(temp2.trim());
            					temp=temp.concat(" ");
            				}
            				sb.append(temp);
            				temp="";
            				flag=true;
                    	}
            			else if((s.charAt(0)=='.' && s.charAt(1)=='I' && s.charAt(2)==' ')){
            				key1=s;
            				break;
            			}
            		}
            	}
            	if(flag || s==null){
            		filterMap.put(Integer.parseInt(key.replaceAll(".I ", "").trim()), sb.toString());
            		sb = new StringBuffer();
            		flag=false;
            		key=key1;
            		count++;
            	}
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Exception:"+e.toString());
        }
		
		long endTime = System.nanoTime();
	    long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
	    System.out.println("Time taken for parsing the queryFile is : "+ duration + " nanoseconds");
		return (count);
		
    } //end of getQryFileContent()
	
	/* This function gets the desired input file */
	public static File getInstance(String qryFile) {
        return new File(qryFile);
    } // end of getInstance()
	
	
	/*this function puts the hashmap value to an output file, after removing all symbols like "," , ". " ";", "/" etc. from query content, 
	 * leaving the query separated by only whitespace */ 
	public  void putQryFileContent(String qFileOut) throws IOException{
		
		FileWriter fstream = new FileWriter(qFileOut);
		BufferedWriter out = new BufferedWriter(fstream);
		
		//Sorting the hashmap before putting the output file
		Map<Integer, String> treeMap = new TreeMap<Integer, String>(filterMap);
		
		for (java.util.Map.Entry<Integer, String> entry : treeMap.entrySet()) {
			int key = entry.getKey();
			String value = entry.getValue();
			out.write(key + "," + value + "\n");
		}
	    out.close();
	} //end of putQryFileContent()
	
	public static void main(String[] args) throws Exception{
		String qryFileInput = "C://Users/Titli Sarkar/workspace/IRAssignment/cran.qry";
		qryFileFilterTest qfr =  new qryFileFilterTest();
		int no_of_queries = qfr.getQryFileContent(qryFileInput);
		System.out.println("\n No of query file is : " + no_of_queries);
		String qryFileKeyValuePaired = "C://Users/Titli Sarkar/workspace/testProject/qry-key-value-paired.txt";
		qfr.putQryFileContent(qryFileKeyValuePaired);
	}//end of main()
	
}  // end of class qryFileRead

