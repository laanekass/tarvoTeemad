/**
 * 
 */
package ruleOrderer.splitter.dataFile;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ruleOrderer.splitter.Splitter;
import ruleOrderer.writer.dataFile.WriteToFile;


/**
 * 
 * Divides input file into two files, size of files is defined, user can define
 * own names for output files
 * 
 * @author ehelmai
 */
public class SplitIntoTwoFiles implements Splitter {

	ArrayList<String> header = new ArrayList<String>();
	ArrayList<String> data = new ArrayList<String>();
	ArrayList<String> firstFile = new ArrayList<String>();
	ArrayList<String> secondFile = new ArrayList<String>();

	/**
	 * With default file names
	 * 
	 * @param inputFileName
	 *            - String - name of the file which is going to be splitted
	 * @param oneFileSize
	 *            - double
	 */

	public void splitIntoTwo(String inputFileName, double oneFileSize) {
		String firstFileName = "outFirst.arff";
		String secondFileName = "outSecond.arff";
		readInputFile(inputFileName);
		splitFiles(header, data, oneFileSize);
		writeSplittedFiles(firstFile, secondFile, firstFileName, secondFileName);
	}

	/**
	 * With userdefined filenames
	 * 
	 * @param inputFileName
	 *            - String - name of the file which is going to be splitted
	 * @param oneFileSize
	 *            - double
	 * @param firstFileName
	 *            - String
	 * @param secondFileName
	 *            -String
	 */
	public void splitIntoTwo(String inputFileName, double oneFileSize,
			String firstFileName, String secondFileName) {
		readInputFile(inputFileName);
		splitFiles(header, data, oneFileSize);
		writeSplittedFiles(firstFile, secondFile, firstFileName, secondFileName);
	}

	/**
	 * Method reads input file and separates it into header part (realation
	 * name, attributes, comments and empty lines) and data
	 * 
	 * @param inputFileName
	 *            - String - name of the file which is going to be splitted
	 */
	private void readInputFile(String inputFileName) {
		String line;
		try {
			FileInputStream fis = new FileInputStream(inputFileName);
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			int noLines = 0;
			while ((line = br.readLine()) != null) {
				/**
				 * until the line contains letter "@" or "%" or if it empty, the
				 * line is saved as header
				 */
				if ((line.length() == 0) || (line.indexOf("%") != -1)
						|| (line.indexOf("@") != -1)) {
					header.add(line);
				} else if (line.length() > 0) {
					data.add(line);
				}
				noLines++;
			}
			br.close();
			System.out.println("Number of Lines: " + noLines);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Method calculates, how many rows from data array belong to the first
	 * outputfile. After that, header part is copied into both output arrays,
	 * correct number of rows from data array are copied into first output file,
	 * rest of the records from data array are copied into second output file
	 * 
	 * @param head
	 *            - ArrayList<String>- header part from input file
	 * @param dataArray
	 *            - ArrayList<String> - data part from input file
	 * @param oneFileSize
	 *            - double- Size of one output file, value is in %
	 */
	private void splitFiles(ArrayList<String> head,
			ArrayList<String> dataArray, double oneFileSize) {
		int dataFileSize = data.size();
		int firstFileSize = (int) Math
				.round((dataFileSize * oneFileSize) / 100);
		/**
		 * add headers to both files
		 */
		for (int k = 0; k < head.size(); k++) {
			firstFile.add(head.get(k));
			secondFile.add(head.get(k));
		}

		for (int i = 0; i < data.size(); i++) {
			if (i < firstFileSize) {
				/**
				 * add data to first file
				 */
				firstFile.add(data.get(i));
			} else {
				/**
				 * add data to second file
				 */
				secondFile.add(data.get(i));
			}
		}
		// TODO
		System.out.println("SplitIntoTwoFiles||splitFiles|| firstFile: "
				+ firstFile.size());
		System.out.println("SplitIntoTwoFiles||splitFiles|| secondFile: "
				+ secondFile.size());
	}

	/**
	 * 
	 * @param firstF
	 *            - ArrayList<String> - contains records for first output file
	 * @param secondF
	 *            - ArratList<String> - contains records for second output file
	 * @param firstFName
	 *            - String - name for first file, if user hasn't defined it,
	 *            default names "outFirst.arff" is used
	 * @param secondFName
	 *            - String - name for second file, if user hasn't defined it,
	 *            default names "outSecond.arff" is used
	 */
	public void writeSplittedFiles(ArrayList<String> firstF,
			ArrayList<String> secondF, String firstFName, String secondFName) {
		/**
		 * write first file
		 */
		WriteToFile write1 = new WriteToFile();
		write1.creatOutput(firstFName, firstF);
		/**
		 * write second file
		 */
		WriteToFile write2 = new WriteToFile();
		write2.creatOutput(secondFName, secondF);

	}
}
