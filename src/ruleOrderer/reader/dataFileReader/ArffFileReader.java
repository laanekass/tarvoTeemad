/**
 * 
 */
package ruleOrderer.reader.dataFileReader;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ruleOrderer.dataTypes.arff.ArffData;
import ruleOrderer.dataTypes.arff.ArffHeaderAttribute;
import ruleOrderer.dataTypes.arff.ArffValues;

/**
 * @author ehelmai
 * 
 */
public class ArffFileReader implements FileReader {
	@Override
	public ArffData read(String fileName) {
		ArffData arffData = new ArffData();
		ArrayList<ArffHeaderAttribute> arffHeaderAttributesList = new ArrayList<ArffHeaderAttribute>();
		ArrayList<ArffValues> arffValuesList = new ArrayList<ArffValues>();

		arffHeaderAttributesList = getAttributeListFromFile(fileName);
		arffData.setArffHeaderList(arffHeaderAttributesList);
		arffValuesList = getAttributeValuesFromFile(fileName);
		arffData.setArffValuesList(arffValuesList);
		return arffData;
	}

	@Override
	public ArrayList<ArffHeaderAttribute> getAttributeListFromFile(
			String fileName) {
		String line = null;
		ArrayList<ArffHeaderAttribute> headerList = new ArrayList<ArffHeaderAttribute>();
		ArffHeaderAttribute header;

		try {
			FileInputStream fStream = new FileInputStream(fileName);
			DataInputStream dStream = new DataInputStream(fStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					dStream));
			while ((line = br.readLine()) != null) {
				if ((line.indexOf(" ") != -1) && (line.indexOf("@") != -1)) {
					if ((line.substring(0, line.indexOf(" ")).toLowerCase())
							.equals("@relation")) {
						header = new ArffHeaderAttribute();
						String[] tempHeader = line.split(" ");
						/**
						 * tempHeader: on position 0 is "@relaton", on position
						 * 1 is relation name
						 */
						/**
						 * Set header's attribute type
						 */
						header.setAttributeType("@relation");
						/**
						 * Set header's attribute name
						 */
						header.setAttributeName(tempHeader[1].trim());
						/**
						 * Set header's attribute values
						 */
						header.setAttributeValues(null);
						/**
						 * Add header to the list
						 */
						headerList.add(header);
					} else if ((line.substring(0, line.indexOf(" "))
							.toLowerCase()).equals("@attribute")) {
						header = new ArffHeaderAttribute();
						String[] tempHeader = line.split(" ");
						/**
						 * tempHeader: on position 0 is "@attribute", on
						 * position 1 is attribute name, on position 2 is
						 * {attribute values list}
						 */
						/**
						 * Set header's attribute type
						 */
						header.setAttributeType("@attribute");
						/**
						 * Set header's attribute name
						 */
						header.setAttributeName(tempHeader[1].trim());
						ArrayList<String> attributeValues = new ArrayList<String>();
						/**
						 * remove "{" and "}" characters from string and after
						 * that split it into separate header's attribute values
						 */
						String[] tempValues = tempHeader[2].replace("{", "")
								.replace("}", "").split("\\,");
						/**
						 * Add header's attribute values
						 */
						for (int i = 0; i < tempValues.length; i++) {
							attributeValues.add(tempValues[i]);
						}
						header.setAttributeValues(attributeValues);

						/**
						 * Add header to the list
						 */
						headerList.add(header);
					}
					System.out.println(line.substring(0, line.indexOf(" "))
							.toLowerCase());
					if ((line.substring(0, line.indexOf(" ")).toLowerCase())
							.equals("@data")) {

						header = new ArffHeaderAttribute();
						/**
						 * Set header's attribute type
						 */
						header.setAttributeType("@data");
						System.out
								.println("header" + header.getAttributeType());
						/**
						 * Set header's attribute name
						 */
						header.setAttributeName(null);
						/**
						 * Set header's attribute values
						 */
						header.setAttributeValues(null);
						/**
						 * Add header to the list
						 */
						headerList.add(header);
					}
				} else {
					continue;
				}
			}

			br.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return headerList;
	}

	private static long countCharToSkip(BufferedReader reader) {
		String line = "";
		long charCount = 0;

		try {
			while ((line = reader.readLine()) != null) {
				if (line.indexOf("@") != -1) {
					if (line.trim().toLowerCase().equals("@data")) {
						return charCount;
					} else {
						charCount += line.length() * 0.015625;
					}
				} else {
					charCount += line.length() * 0.015625;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return charCount;
	}

	@Override
	public ArrayList<ArffValues> getAttributeValuesFromFile(String fileName) {
		String line = null;
		int count;
		long charToSkip = 0;
		String[] lineValues;
		ArrayList<ArffValues> valuesList = new ArrayList<ArffValues>();
		ArrayList<String> arffValuesList;
		ArffValues arffValues;
		try {
			FileInputStream fStream = new FileInputStream(fileName);
			DataInputStream dStream = new DataInputStream(fStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					dStream));

			charToSkip = countCharToSkip(br);
			br.skip(charToSkip);

			count = 0;

			while ((line = br.readLine()) != null) {
				if (line.charAt(0) != '%' && line.indexOf(",") != -1) {
					lineValues = line.split(",");
					arffValuesList = new ArrayList<String>();
					arffValues = new ArffValues();
					for (int i = 0; i < lineValues.length; i++) {
						arffValuesList.add(lineValues[i]);
					}
					arffValues.setValuesList(arffValuesList);
					valuesList.add(arffValues);
					lineValues = null;
					count++;
				} else {
					continue;
				}
			}

			br.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return valuesList;
	}
}
