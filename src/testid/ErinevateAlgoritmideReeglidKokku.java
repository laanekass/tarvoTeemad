package testid;

import io.FileRoutine;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.rules.Ridor;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import algorithm.MiinusTehnika;

public class ErinevateAlgoritmideReeglidKokku {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		FileWriter fileWriter = new FileWriter("TrainingData"
				+ "_testResults09112012");
		BufferedWriter out = new BufferedWriter(fileWriter);

		DataSource sourceTrain = new DataSource(
				"uniquetraining2TrainingData.arff");
		Instances dataTrain = sourceTrain.getDataSet();
		if (dataTrain.classIndex() == -1) {
			dataTrain.setClassIndex(dataTrain.numAttributes() - 1);
		}

		DataSource sourceTest = new DataSource(
				"uniquetraining2TestingData.arff");
		Instances dataTest = sourceTest.getDataSet();
		if (dataTest.classIndex() == -1) {
			dataTest.setClassIndex(dataTrain.numAttributes() - 1);
		}

		Classifier ridor = new Ridor();
		ridor.buildClassifier(dataTrain);

		Evaluation ridorEval = new Evaluation(dataTest);
		ridorEval.evaluateModel(ridor, dataTest);
		out.write("Correct: " + ridorEval.correct() + "\n");
		out.write("InCorrect: " + ridorEval.incorrect() + "\n");
		out.write("Unclassified: " + ridorEval.unclassified() + "\n");
		double[][] confusionMatrix = ridorEval.confusionMatrix();
		for (int i = 0; i < confusionMatrix.length; i++) {
			out.write("fMeasure(" + i + "): " + ridorEval.fMeasure(i) + "\n");
		}
		out.write("Confusion matrix: \n");
		for (int i = 0; i < confusionMatrix.length; i++) {
			for (int j = 0; j < confusionMatrix[i].length; j++) {
				out.write(confusionMatrix[i][j] + "; ");
			}
			out.write("\n");
		}
		out.write("\n\n");
		out.write("\n");

		Classifier j48 = new J48();
		j48.buildClassifier(dataTrain);
		Evaluation j48Eval = new Evaluation(dataTest);
		j48Eval.evaluateModel(j48, dataTest);
		out.write("j48\n");
		out.write("Correct: " + j48Eval.correct() + "\n");
		out.write("InCorrect: " + j48Eval.incorrect() + "\n");
		out.write("Unclassified: " + j48Eval.unclassified() + "\n");
		double[][] confusionMatrix1 = j48Eval.confusionMatrix();
		for (int i = 0; i < confusionMatrix1.length; i++) {
			out.write("fMeasure(" + i + "): " + j48Eval.fMeasure(i) + "\n");
		}
		out.write("Confusion matrix: \n");
		for (int i = 0; i < confusionMatrix1.length; i++) {
			for (int j = 0; j < confusionMatrix1[i].length; j++) {
				out.write(confusionMatrix1[i][j] + "; ");
			}
			out.write("\n");
		}
		out.write("\n\n");
		out.write("\n");

		out.write("reeglid järjestatud suhte järgi");
		FileReader reader = new FileReader("rulesOrdered1.txt");
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		ArrayList<int[]> rules = new ArrayList<int[]>();
		while ((line = br.readLine()) != null) {
			String[] rule = line.split(" ");
			int[] ruleInt = new int[rule.length];
			for (int i = 0; i < rule.length; i++) {
				ruleInt[i] = Integer.parseInt(rule[i]);
			}
			rules.add(ruleInt);
		}
		br.close();
		reader.close();

		FileRoutine fileRoutine = new FileRoutine();
		int[][] testingTable = fileRoutine
				.readFromFile("uniquetraining2TestingData.txt");

		int unseen = 0;
		int correct = 0;
		int incorrect = 0;
		ArrayList<int[]> unseenTests = new ArrayList<int[]>();
		TreeMap<String, Integer> testResults = new TreeMap<String, Integer>();

		for (int i = 0; i < testingTable.length; i++) {
			int t = algorithm.Main.findFirstPattern(testingTable[i], rules);
			if (t != -1) {
				String key = testingTable[i][testingTable[0].length - 1] + ";"
						+ t;
				if (testResults.containsKey(key)) {
					int oldValue = testResults.get(key);
					testResults.put(key, oldValue + 1);
				} else {
					testResults.put(key, 1);
				}
			}
			if (t == -1) {
				unseenTests.add(testingTable[i]);
				unseen++;
			} else if (testingTable[i][testingTable[0].length - 1] == t) {
				correct++;
			} else {
				incorrect++;
			}
		}

		out.write("TrainingData " + "\n");

		out.write("timeStamp: " + new Timestamp(new Date().getTime()) + "\n");
		out.write("Correct: " + correct + "\n");
		out.write("InCorrect: " + incorrect + "\n");
		out.write("Unclassified: " + unseen + "\n");
		double[][] confusionMatrix3 = algorithm.Main
				.getConfusionMatrix(testResults);
		for (int i = 0; i < confusionMatrix3.length; i++) {
			out.write("fMeasure(" + i + "): "
					+ algorithm.Main.getFMeasure(i, confusionMatrix3) + "\n");
		}
		out.write("Confusion matrix data: \n");
		for (double[] rida : confusionMatrix3) {
			for (double element : rida) {
				out.write(element + "; ");
			}
			out.write("\n");
		}

		out.write("*************************************************\n");
		out.write("\n");

		out.write("Miinustehnikaga järjestatud reeglid");
		reader = new FileReader("rulesOrdered2");
		br = new BufferedReader(reader);
		line = null;

		rules = new ArrayList<int[]>();
		while ((line = br.readLine()) != null) {
			String[] rule = line.split(" ");
			int[] ruleInt = new int[rule.length];
			for (int i = 0; i < rule.length; i++) {
				ruleInt[i] = Integer.parseInt(rule[i]);
			}
			rules.add(ruleInt);
		}
		br.close();
		reader.close();

		byte[][] ruleM = new byte[rules.size()][rules.get(0).length];
		for (int i = 0; i < rules.size(); i++) {
			for (int j = 0; j < rules.get(i).length; j++) {
				ruleM[i][j] = (byte) (rules.get(i))[j];
			}
		}

		MiinusTehnika miinus = new MiinusTehnika(ruleM);
		miinus.doMiinustehnika();

		byte[][] jarjestatudReeglid = miinus.getTable();
		ArrayList<int[]> orderedRules = new ArrayList<int[]>();
		for (int i = 0; i < jarjestatudReeglid.length; i++) {
			int[] r = new int[jarjestatudReeglid[i].length];
			for (int j = 0; j < jarjestatudReeglid[i].length; j++) {
				if (jarjestatudReeglid[i][j] == 9) {
					r[j] = -1;
				} else {
					r[j] = jarjestatudReeglid[i][j];
				}
			}
			orderedRules.add(r);
		}

		unseen = 0;
		correct = 0;
		incorrect = 0;
		unseenTests = new ArrayList<int[]>();
		testResults = new TreeMap<String, Integer>();

		for (int i = 0; i < testingTable.length; i++) {
			int t = algorithm.Main.findFirstPattern(testingTable[i],
					orderedRules);
			if (t != -1) {
				String key = testingTable[i][testingTable[0].length - 1] + ";"
						+ t;
				if (testResults.containsKey(key)) {
					int oldValue = testResults.get(key);
					testResults.put(key, oldValue + 1);
				} else {
					testResults.put(key, 1);
				}
			}
			if (t == -1) {
				unseenTests.add(testingTable[i]);
				unseen++;
			} else if (testingTable[i][testingTable[0].length - 1] == t) {
				correct++;
			} else {
				incorrect++;
			}
		}

		out.write("TrainingData " + "\n");

		out.write("timeStamp: " + new Timestamp(new Date().getTime()) + "\n");
		out.write("Correct: " + correct + "\n");
		out.write("InCorrect: " + incorrect + "\n");
		out.write("Unclassified: " + unseen + "\n");
		double[][] confusionMatrix4 = algorithm.Main
				.getConfusionMatrix(testResults);
		for (int i = 0; i < confusionMatrix4.length; i++) {
			out.write("fMeasure(" + i + "): "
					+ algorithm.Main.getFMeasure(i, confusionMatrix4) + "\n");
		}
		out.write("Confusion matrix data: \n");
		for (double[] rida : confusionMatrix4) {
			for (double element : rida) {
				out.write(element + "; ");
			}
			out.write("\n");
		}

		out.write("*************************************************\n");
		out.write("\n");
		out.close();

	}
}
