/**
 * 
 */
package ruleOrderer.writer.dataFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import ruleOrderer.writer.OutputWriterAdapter;

/**
 * @author ehelmai
 * 
 */
public class WriteToFile implements OutputWriterAdapter {

	/**
	 * 
	 * @param fileName
	 *            -String - name of new file
	 * @param data
	 *            - ArrayList<String> - Arraylist which contains data to be
	 *            written into new file
	 */
	public void creatOutput(String fileName, ArrayList<String> data) {
		try {
			// Create file
			FileWriter fstream = new FileWriter(fileName);
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i = 0; i < data.size(); i++) {
				out.write(data.get(i) + "\n");
			}
			// Close the output stream
			out.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}

	@Override
	public void creatOutputArray(String fileName, ArrayList<String[]> dataList) {
		try {
			// Create file
			FileWriter writer = new FileWriter(fileName);
			// BufferedWriter out = new BufferedWriter(fstream);
			for (String[] temp : dataList) {
				for (int i = 0; i < temp.length; i++) {
					if (i != (temp.length - 1)) {
						writer.append(temp[i] + ";");
					} else {
						writer.append(temp[i] + ";\r\n");
					}
				}
				writer.flush();
			}
			// Close the output stream
			writer.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
}
