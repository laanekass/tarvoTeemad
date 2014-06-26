package io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileRoutine {
	public int[][] readFromFile(String fileName) {
		try { 
			BufferedReader in = new BufferedReader( new FileReader( fileName ) );  
			int rowCount = Integer.parseInt(in.readLine());
			int columnCount = Integer.parseInt(in.readLine());
			int[][] table = new int[rowCount][columnCount];
			String textLine = in.readLine(); 
			int i = 0;//row index
			int j; //column index
			while ( textLine != null && i < rowCount) { 
				j = 0;				
				/*if (textLine.length() != (2*columnCount-1)) {
					System.out.println("Veergude arv failis ei klapi oodatuga!");
					return null;
				}*/	
				String[] temp = textLine.split(" ");
				while (j < columnCount) {
					table[i][j]= (byte)Integer.parseInt(temp[j]);
					j++;
				}	
				i++;
				textLine = in.readLine(); 
			} 
			in.close();
			if (i != rowCount) {
				System.out.println("Ridade arv ei klapi oodatuga!");
				return null;
			}	
			else return table;
		} catch ( IOException error ) { 
			System.err.println( "Error reading from file:" ); 
			System.err.println( "\t" + error ); 
			return null; 
		}
		
	}
	
	public int[][] readUniqueFromFile(String fileName) {
		try { 
			BufferedReader in = new BufferedReader( new FileReader( fileName ) );  
			int rowCount = Integer.parseInt(in.readLine());
			int columnCount = Integer.parseInt(in.readLine());
			int[][] table = new int[rowCount][columnCount];
			String textLine = in.readLine(); 
			int fileRowIndex = 0;
			int i = 0;//row index
			int j; //column index
			while ( textLine != null && fileRowIndex < rowCount) { 
				j = 0;				
				String[] temp = textLine.split(" ");
				boolean isFound = false;
				for (int k = 0; k < i; k++) {
					boolean isDuplicate = true;
					for (int l=0; l < columnCount; l++)
					{
						if ((byte)Integer.parseInt(temp[l]) != table[k][l])
						{
							isDuplicate = false;
						}
					}
					if ( isDuplicate == true )
					{
						isFound = true;
						break;
					}
				}
				if (!isFound) {//duplikaat
					while (j < columnCount) {
						table[i][j]= (byte)Integer.parseInt(temp[j]);
						j++;
					}
					i++;
				}
				fileRowIndex++;
				textLine = in.readLine(); 
			} 
			in.close();
			if (fileRowIndex != rowCount) {
				System.out.println("Ridade arv ei klapi oodatuga!");
				return null;
			}	
			else  {
				if (fileRowIndex != i) {
					int[][] copyOftable = new int[i][columnCount];
					for (int k = 0; k < i; k++) {
						copyOftable[k] = table[k];
					}
					table = copyOftable;
				}
				return table;
			}
		} catch ( IOException error ) { 
			System.err.println( "Error reading from file:" ); 
			System.err.println( "\t" + error ); 
			return null; 
		}
		
	}
	

	public void writeRulesToFile (String fileName, ArrayList rules, boolean cont) {
		try { 
			PrintWriter out = new PrintWriter( new FileWriter( fileName, cont ) );
			byte[] rule = null;
			String ruleStr;
			int j = 0;
			if (rules == null) return;
			for(int i = 0; i < rules.size(); i++){
				ruleStr ="";
				rule = (byte[])rules.get(i);
				j = 0;
				while (j < rule.length - 1) {
					if (rule[j] != -1 ) {
						if (ruleStr.length() != 0) {
							ruleStr += "&";
						}
						ruleStr += (j + 1) + "." + rule[j];	
					}	
					j++;
				}
				out.println(ruleStr + "->" + (rule.length) + "." + rule[rule.length - 1]);
			}
			out.close(); 
		} catch ( IOException error ) { 
			System.err.println( "Error making file:" ); 
			System.err.println( "\t" + error ); 
		} 
	}
}
