/**
 * 
 */
package ruleOrderer.transformer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author ehelmai
 * 
 */
public class AttributeValueToARFFAttributeList {
	/**
	 * Procedure creates attribute list
	 * 
	 * @param valuePos
	 * @param attributeValue
	 * @param attributeValueList
	 * @return
	 */

	public HashMap<Integer, ArrayList<String>> addToAttributeList(int valuePos,
			String attributeValue,
			HashMap<Integer, ArrayList<String>> attributeValueList) {
		ArrayList<String> attributeList = new ArrayList<String>();
		/**
		 * check if key exists in the table already
		 */
		boolean key = attributeValueList.containsKey(valuePos);

		/**
		 * If key exists
		 */
		if (key == true) {
			/**
			 * Get list from hashmap on the key-position
			 */
			attributeList = attributeValueList.get(valuePos);
			/**
			 * find if value which we want to add already exists in the list
			 */
			boolean exists = false;
			int listSize = attributeList.size();
			for (int i = 0; i < listSize; i++) {
				if (attributeList.get(i).equalsIgnoreCase(attributeValue)) {
					exists = true;
				}
			}
			/**
			 * if the value doesn't exists in the list jet, continue, otherwise
			 * don't do anything
			 */
			if (exists == false) {
				/**
				 * Find, if the last element in the list is already smaller,
				 * then add value to the end of the list
				 */
				if (Integer.parseInt(attributeList.get(listSize - 1)) < Integer
						.parseInt(attributeValue)) {
					attributeList.add(attributeValue);
				}
				/**
				 * otherwise find last element which is smaller than value we
				 * are looking at and add this value after this one
				 */
				else {
					int j = 0;
					while (Integer.parseInt(attributeList.get(j)) < Integer
							.parseInt(attributeValue)) {
						j++;
					}
					attributeList.add(j, attributeValue);
				}
			}

		}
		/**
		 * If key doesn't exist, add this value to clean list and add pair of
		 * key and list to the hashMap
		 */
		else {
			attributeList.add(attributeValue);
			attributeValueList.put(valuePos, attributeList);
		}

		return attributeValueList;
	}
}
