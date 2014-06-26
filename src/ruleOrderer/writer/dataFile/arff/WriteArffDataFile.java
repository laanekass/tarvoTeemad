/**
 * 
 */
package ruleOrderer.writer.dataFile.arff;

import java.util.ArrayList;

import ruleOrderer.dataTypes.arff.ArffData;
import ruleOrderer.dataTypes.arff.ArffHeaderAttribute;
import ruleOrderer.dataTypes.arff.ArffValues;
import ruleOrderer.writer.dataFile.WriteToFile;

/**
 * @author ehelmai
 */
public class WriteArffDataFile extends WriteToFile {
	/**
	 * @uml.property name="arffFileData"
	 */
	ArrayList<String> arffFileData = new ArrayList<String>();
	ArrayList<String> wholeHeader = new ArrayList<String>();
	ArrayList<String> wholeData = new ArrayList<String>();
	ArrayList<String[]> arffFileDataArray = new ArrayList<String[]>();
	ArrayList<String[]> wholeHeaderArray = new ArrayList<String[]>();
	ArrayList<String[]> wholeDataArray = new ArrayList<String[]>();

	private ArrayList<String> getWholeHeader(
			ArrayList<ArffHeaderAttribute> arffAtributeList) {
		// TODO
//		System.out
//				.println("WriteArffDataFile||getWholeHeader|| arffAtributeList: "
//						+ arffAtributeList.size());
		for (int i = 0; i < arffAtributeList.size(); i++) {
			ArffHeaderAttribute tempAttribute = new ArffHeaderAttribute();
			tempAttribute = arffAtributeList.get(i);
			String tempString = "";
			String tempAttributeType = tempAttribute.getAttributeType();
			String tempAttributeName = tempAttribute.getAttributeName();
			// TODO
//			System.out
//					.println("WriteArffDataFile||getWholeHeader|| tempAttributeType: "
//							+ tempAttributeType);
//			// TODO
//			System.out
//					.println("WriteArffDataFile||getWholeHeader|| tempAttributeName: "
//							+ tempAttributeName);
//			// TODO
//			System.out
//					.println("WriteArffDataFile||getWholeHeader|| tempAttribute.getAttributeValues(): "
//							+ tempAttribute.getAttributeValues());
			if (tempAttribute.getAttributeValues() != null) {

				ArrayList<String> values = tempAttribute.getAttributeValues();
				/**
				 * add first value
				 */
				String tempValuesList = values.get(0);
				/**
				 * add rest of values if there are some
				 */
				if (values.size() > 1) {
					for (int j = 1; j < values.size(); j++) {
						tempValuesList = tempValuesList + "," + values.get(j);
					}
				}
				if (tempValuesList.equalsIgnoreCase("real")) {
					tempString = tempAttributeType + " " + tempAttributeName
							+ " " + tempValuesList;
				} else {
					tempString = tempAttributeType + " " + tempAttributeName
							+ " {" + tempValuesList + "}";
				}
			} else if (tempAttribute.getAttributeName() != null
					&& tempAttribute.getAttributeValues() == null) {
				tempString = tempAttributeType + " " + tempAttributeName;
			} else {
				tempString = tempAttributeType;
			}
			// TODO
//			System.out
//					.println("WriteArffDataFile||getWholeHeader|| tempString: "
//							+ tempString);
			wholeHeader.add(tempString);
		}
		return wholeHeader;
	}

	private ArrayList<String> getWholeData(ArrayList<ArffValues> arffValuesList) {
		ArrayList<String> wholeData = new ArrayList<String>();
		for (ArffValues arffValues : arffValuesList) {
			ArrayList<String> tempData = arffValues.getValuesList();
			String dataRow = tempData.get(0);
			for (int i = 1; i < tempData.size(); i++) {
				dataRow = dataRow + "," + tempData.get(i);
			}
			wholeData.add(dataRow);
		}
		return wholeData;
	}

	public void setArffFileData(ArffData arffData) {
		this.wholeHeader = getWholeHeader(arffData.getArffHeaderList());
		this.wholeData = getWholeData(arffData.getArffValuesList());

		this.arffFileData.addAll(this.wholeHeader);
		this.arffFileData.addAll(this.wholeData);
	}

	/**
	 * @return
	 * @uml.property name="arffFileData"
	 */
	public ArrayList<String> getArffFileData() {
		return this.arffFileData;
	}
}
