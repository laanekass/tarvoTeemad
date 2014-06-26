/**
 * 
 */
package ruleOrderer.dataTypes;

/**
 * @author  ehelmai
 */
public class Attribute {

	/**
	 * @uml.property  name="attributeName"
	 */
	String attributeName = "";
	/**
	 * @uml.property  name="attributeValue"
	 */
	String attributeValue = "";

	/**
	 * @param attributeName
	 * @uml.property  name="attributeName"
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * @param attributeValue
	 * @uml.property  name="attributeValue"
	 */
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	/**
	 * @return
	 * @uml.property  name="attributeName"
	 */
	public String getAttributeName() {
		return this.attributeName;
	}

	/**
	 * @return
	 * @uml.property  name="attributeValue"
	 */
	public String getAttributeValue() {
		return this.attributeValue;
	}
}
