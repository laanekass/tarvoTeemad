/**
 * 
 */
package ruleOrderer.dataTypes.arff;

import java.util.ArrayList;

/**
 * @author ehelmai
 */
public class ArffValues {

	/**
	 * @uml.property name="values"
	 */
	ArrayList<String> values = new ArrayList<String>();
	String value = "";

	/**
	 * @param data
	 * @uml.property name="values"
	 */
	public void setValuesList(ArrayList<String> values) {
		this.values = values;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return
	 * @uml.property name="data"
	 */
	public ArrayList<String> getValuesList() {
		return this.values;
	}

	public String getValue() {
		return this.value;
	}
}
