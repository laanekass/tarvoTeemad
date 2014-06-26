/**
 * 
 */
package ruleOrderer.dataTypes.arff;

import java.util.ArrayList;

import ruleOrderer.dataTypes.Attribute;

/**
 * @author ehelmai
 */
public class ArffHeaderAttribute extends Attribute {
	/**
	 * @uml.property name="attributeValues"
	 */
	public ArrayList<String> attributeValues = new ArrayList<String>();
	public String attributeType;

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	/**
	 * @param attributeValues
	 * @uml.property name="attributeValues"
	 */
	public void setAttributeValues(ArrayList<String> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public String getAttributeType() {
		return this.attributeType;
	}

	/**
	 * @return
	 * @uml.property name="attributeValues"
	 */
	public ArrayList<String> getAttributeValues() {
		return this.attributeValues;
	}
}
