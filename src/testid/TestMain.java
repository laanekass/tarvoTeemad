/**
 * 
 */
package testid;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author ehelmai
 * 
 */
public class TestMain {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// ArffFileReader reader = new ArffFileReader();
		// ArffData arffData = reader.read("krkoptTrainingData.arff");
		// SplitIntoTwoArffData splitter = new SplitIntoTwoArffData();
		// splitter.splitIntoTwo(arffData, 100);
		// ArffData trainingArffData = splitter.getFirstArffData();
		//
		// WriteArffDataFile writer = new WriteArffDataFile();
		// writer.setArffFileData(trainingArffData);
		//
		// WriteToFile write = new WriteToFile();
		// write.creatOutput("krkoptTrainingDataRandom.arff",
		// writer.getArffFileData());

		FileWriter fileWriter = new FileWriter("MemoryLeakage3");
		BufferedWriter out = new BufferedWriter(fileWriter);
		// double[][] confusionMatrixFirst = null;
		// // TODO Auto-generated method stub
		RunMemoryLeakage.allAlgorithms("training3a", "testing3a",
		// "testing2",
				out
		// , confusionMatrixFirst
				);
		out.close();
	}
}
