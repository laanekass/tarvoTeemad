/**
 * 
 */
package ruleOrderer.transformer;

import java.util.ArrayList;
import java.util.HashMap;

import ruleOrderer.dataTypes.arff.ArffData;
import ruleOrderer.dataTypes.arff.ArffHeaderAttribute;
import ruleOrderer.dataTypes.arff.ArffValues;

/**
 * @author ehelmai
 * 
 */
public class ConvertIntArray {

	public static ArffData intArrayToArffData(ArrayList<int[]> data,
			String arffFileName) {

		ArffData arffData = new ArffData();

		ArrayList<ArffHeaderAttribute> arffHeaderAttributeList = new ArrayList<ArffHeaderAttribute>();
		ArffHeaderAttribute headerAttribute;
		AttributeValueToARFFAttributeList addAttribute = new AttributeValueToARFFAttributeList();
		HashMap<Integer, ArrayList<String>> attributeValueList = new HashMap<Integer, ArrayList<String>>();

		ArffValues arffValues = null;
		ArrayList<ArffValues> arffValuesList = new ArrayList<ArffValues>();

		/**
		 * Add @relation + filename row
		 */
		ArffHeaderAttribute attribute_x = new ArffHeaderAttribute();
		String[] fileName = arffFileName.split("\\.");
		attribute_x.setAttributeType("@relation");
		attribute_x.setAttributeName(fileName[0]);
		attribute_x.setAttributeValues(null);
		arffHeaderAttributeList.add(attribute_x);

		/**
		 * get number of elemnets on one row, by that generate header, each
		 * attribute gets name of its position in the row
		 */
		int numberOfElements = data.get(0).length;

		for (int i = 1; i <= numberOfElements; i++) {
			headerAttribute = new ArffHeaderAttribute();
			headerAttribute.setAttributeType("@attribute");
			headerAttribute.setAttributeName(Integer.toString(i));
			headerAttribute.setAttributeValues(null);
			arffHeaderAttributeList.add(headerAttribute);
		}

		for (int[] dataRow : data) {
			/**
			 * Find data
			 */
			arffValues = new ArffValues();
			ArrayList<String> values = new ArrayList<String>();
			for (int i = 0; i < dataRow.length; i++) {
				/**
				 * Add attribute value for data array;
				 */
				values.add(Integer.toString(dataRow[i]));
				/**
				 * Add attribute value into attribute values list
				 */
				attributeValueList = addAttribute.addToAttributeList((i + 1),
						Integer.toString(dataRow[i]), attributeValueList);
			}
			arffValues.setValuesList(values);
			arffValuesList.add(arffValues);
		}
		/**
		 * turn hasmap of attributeValues into arff attribute values list
		 */
		for (int i = 1; i < arffHeaderAttributeList.size(); i++) {
			Integer key = Integer.parseInt(arffHeaderAttributeList.get(i)
					.getAttributeName());
			arffHeaderAttributeList.get(i).setAttributeValues(
					attributeValueList.get(key));
		}
		/**
		 * Add @data row
		 */
		ArffHeaderAttribute attribute_y = new ArffHeaderAttribute();
		attribute_y.setAttributeType("@data ");
		attribute_y.setAttributeName(null);
		attribute_y.setAttributeValues(null);
		arffHeaderAttributeList.add(attribute_y);

		arffData.setArffHeaderList(arffHeaderAttributeList);
		arffData.setArffValuesList(arffValuesList);

		return arffData;
	}

}
