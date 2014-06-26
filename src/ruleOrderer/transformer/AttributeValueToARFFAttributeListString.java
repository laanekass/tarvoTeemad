/**
 * 
 */
package ruleOrderer.transformer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @author ehelmai
 * 
 */
public class AttributeValueToARFFAttributeListString {

	public HashMap<String, ArrayList<String>> addToAttributeList(
			String attributeName, String attributeValue,
			HashMap<String, ArrayList<String>> attributeValueList) {
		ArrayList<String> attributeList = new ArrayList<String>();
		/**
		 * check if key exists in the table already
		 */
		boolean key = attributeValueList.containsKey(attributeName);

		/**
		 * If key exists
		 */
		if (key == true) {
			/**
			 * Get list from hashmap on the key-position
			 */
			attributeList = attributeValueList.get(attributeName);
			/**
			 * find if value which we want to add already exists in the list
			 */
			// TODO
			// System.out
			// .println("StudyExamplesToArffData||addToAttributeList|| attributeValue: "
			// + attributeValue);
			boolean exists = false;
			int listSize = attributeList.size();
			if (!(listSize == 1 && attributeList.get(0) == "real")) {
				for (int i = 0; i < listSize; i++) {
					// TODO
					// System.out
					// .println("StudyExamplesToArffData||addToAttributeList|| attributeList.get(i): "
					// + attributeList.get(i));
					if (attributeList.get(i).trim().equalsIgnoreCase(
							attributeValue.trim())) {
						exists = true;
					}
				}
				// TODO
				// System.out
				// .println("StudyExamplesToArffData||addToAttributeList|| exists: "
				// + exists);
				/**
				 * if the value doesn't exists in the list jet, continue,
				 * otherwise don't do anything
				 */
				if (exists == false) {
					/**
					 * Check If new Attribute is digit
					 */
					boolean newAttributeIsDigit = true;
					for (int i = 0; i < attributeValue.length(); i++) {
						// If we find a non-digit character we return false.
						if (!Character.isDigit(attributeValue.charAt(i))
								&& attributeValue.charAt(i) != '.')
							newAttributeIsDigit = false;
					}
					/**
					 * Check, if all elements in the list are Digits
					 */
					int numberOfDigitalAttributes = 0;
					for (int i = 0; i < listSize; i++) {
						boolean elementInListIsDigit = true;
						for (int j = 0; j < attributeList.get(i).length(); j++) {
							// If we find a non-digit character we return false.
							if (!Character.isDigit(attributeList.get(i).charAt(
									j))
									&& attributeList.get(i).charAt(j) != '.')
								elementInListIsDigit = false;
						}
						if (elementInListIsDigit == true) {
							numberOfDigitalAttributes++;
						}
					}
					if (newAttributeIsDigit == true
							&& Math.floor(Double.valueOf(attributeValue)) != Double
									.parseDouble(attributeValue)) {

						// // TODO
						// System.out
						// .println("StudyExamplesToArffData||addToAttributeList|| attributeValue: "
						// + attributeValue
						// + ", "
						// + Double.parseDouble(attributeValue)
						// + ", "
						// + Math.floor(Double
						// .valueOf(attributeValue)));
						/**
						 * remove already existing values
						 */
						attributeList.clear();
						/**
						 * and define the value to be equal to "real"
						 */
						attributeList.add("real");

					}
					/**
					 * If all elements in the list are digits and new value is
					 * also digit, order elements as numbers
					 */
					else if (numberOfDigitalAttributes == listSize
							&& newAttributeIsDigit == true) {

						/**
						 * Find, if the last element in the list is already
						 * smaller, then add value to the end of the list
						 */
						if (Double.valueOf(attributeList.get(listSize - 1)) < Double
								.valueOf(attributeValue)) {
							attributeList.add(attributeValue);
						}
						/**
						 * otherwise find last element which is smaller than
						 * value we are looking at and add this value after this
						 * one
						 */
						else {
							int j = 0;
							while (Double.valueOf(attributeList.get(j)) < Double
									.valueOf(attributeValue)) {
								j++;
							}
							attributeList.add(j, attributeValue);
						}
					}

					/**
					 * Otherwise order elements alphabetically
					 */
					else {
						/**
						 * add element to the list and sort it alphabetically,
						 * note that numbers list 1, 5, 11 is ordered as
						 * letters: 1, 11, 5
						 */
						attributeList.add(attributeValue);
						Collections.sort(attributeList);
					}
				}
			}
		}
		/**
		 * If key doesn't exist, add this value to clean list and add pair of
		 * key and list to the hashMap
		 */
		else {
			attributeList.add(attributeValue);
			attributeValueList.put(attributeName, attributeList);
		}
		return attributeValueList;
	}
}
