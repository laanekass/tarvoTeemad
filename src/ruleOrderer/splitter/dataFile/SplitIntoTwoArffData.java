/**
 * 
 */
package ruleOrderer.splitter.dataFile;

import java.util.ArrayList;

import ruleOrderer.dataTypes.arff.ArffData;
import ruleOrderer.dataTypes.arff.ArffHeaderAttribute;
import ruleOrderer.dataTypes.arff.ArffValues;
import ruleOrderer.splitter.Splitter;

/**
 * @author ehelmai
 * 
 */
public class SplitIntoTwoArffData implements Splitter {
	ArffData firstArffData = new ArffData();
	ArffData secondArffData = new ArffData();

	public void splitIntoTwo(ArffData arffData, double sizeOfOnePart) {
		ArrayList<ArffHeaderAttribute> headerList = arffData
				.getArffHeaderList();
		ArrayList<ArffValues> dataList = arffData.getArffValuesList();
		ArrayList<ArffValues> firstDataList = new ArrayList<ArffValues>();
		ArrayList<ArffValues> secondDataList = new ArrayList<ArffValues>();
		int firstFileSize = (int) Math
				.round((dataList.size() * sizeOfOnePart) / 100);
		this.firstArffData.setArffHeaderList(headerList);
		this.secondArffData.setArffHeaderList(headerList);

		while (firstDataList.size() < firstFileSize) {
			int randomNumber = (int) (dataList.size() * Math.random());
			if (!firstDataList.contains(dataList.get(randomNumber))) {
				firstDataList.add(dataList.get(randomNumber));
			}
		}
		for (int i = 0; i < dataList.size(); i++) {
			if (!firstDataList.contains(dataList.get(i))) {
				secondDataList.add(dataList.get(i));
			}
		}

		// for (int i = 0; i < dataList.size(); i++) {
		// if (i < firstFileSize) {
		// firstDataList.add(dataList.get(i));
		// } else {
		// secondDataList.add(dataList.get(i));
		// }
		// }
		// TODO
		System.out.println("dataList.size(): " + dataList.size()
				+ ", firstDataList.size(): " + firstDataList.size()
				+ ", secondDataList.size(): " + secondDataList.size());
		this.firstArffData.setArffValuesList(firstDataList);
		this.secondArffData.setArffValuesList(secondDataList);

	}

	public ArffData getFirstArffData() {
		return this.firstArffData;
	}

	public ArffData getSecondArffData() {
		return this.secondArffData;
	}

	@Override
	public void writeSplittedFiles(ArrayList<String> firstF,
			ArrayList<String> secondF, String firstFName, String secondFName) {
		// TODO Auto-generated method stub

	}

}
