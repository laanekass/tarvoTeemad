/**
 * 
 */
package ruleOrderer.dataTypes.arff;

import java.util.ArrayList;

/**
 * @author ehelmai
 * 
 */
public class ArffData {

	public ArrayList<ArffHeaderAttribute> headerList;
	public ArrayList<ArffValues> valuesList;
	public ArffHeaderAttribute header;
	public ArffValues value;

	public void setArffHeaderList(ArrayList<ArffHeaderAttribute> headerList) {
		this.headerList = headerList;
	}

	public void setArffValuesList(ArrayList<ArffValues> valuesList) {
		this.valuesList = valuesList;
	}

	public void setArffHeader(ArffHeaderAttribute header) {
		this.header = header;
	}

	public void setArffValue(ArffValues value) {
		this.value = value;
	}

	public ArrayList<ArffHeaderAttribute> getArffHeaderList() {
		return this.headerList;
	}

	public ArrayList<ArffValues> getArffValuesList() {
		return this.valuesList;
	}

	public String toString() {
		String arffHeaderList = "";
		for (ArffHeaderAttribute attribute : this.headerList) {
			arffHeaderList = arffHeaderList + "; "
					+ attribute.getAttributeType();
		}
		return arffHeaderList;
	}

}
