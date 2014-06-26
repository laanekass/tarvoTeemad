package algorithm;

import io.FileRoutine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import model.Transaction;
import ruleOrderer.dataTypes.arff.ArffData;
import ruleOrderer.reader.dataFileReader.ArffFileReader;
import ruleOrderer.transformer.ConvertIntArray;
import ruleOrderer.writer.dataFile.WriteToFile;
import ruleOrderer.writer.dataFile.arff.WriteArffDataFile;

public class Main {

	public static int findFirstPattern(int[] test, ArrayList ruleList) {

		int result = -1;
		int[] rule;

		for (int i = 0; i < ruleList.size(); i++) {
			rule = (int[]) ruleList.get(i);

			// V�rdleme igat elementi
			int j = 0;
			boolean found = true;
			for (; j < test.length - 1; j++)// !!!!!!!!!!!-1 on ajutine h�kk
			{
				int ruleValue = rule[j];
				int testValue = test[j];
				// Kontrollime kas element on n�utud
				if (ruleValue != -1) {
					if (ruleValue != testValue) {
						found = false;
					}
				}
			}
			if (found == true) {
				result = rule[rule.length - 1];
				break;
			}
		}

		return result;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String[] datasets = { "uniquetraining2"
		// "car", "cmc", "hayes-roth", "krkopt",
		// "lenses", "letter-recognition",
		// "monk1", "monk2", "monk3",
		// "nursery" // ,
		// // "poker-hand"
		// // ,
		// // "shuttle"
		// // ,
		// // "zoo"
		// , "tae", "tic-tac-toe"
		};
		for (int f = 0; f < datasets.length; f++) {

			System.out.println(datasets[f] + "TrainingData.txt");
			FileRoutine fileRoutine = new FileRoutine();
			int[][] trainingTable = fileRoutine.readUniqueFromFile(datasets[f]
					+ "TrainingData.txt");
			// iga treeningrea kohta hoitakse siin parimat (suurima sagedusega,
			// mille p�hjal treeningandmetest v�ljav�ttes k�ik read kuuluvad
			// �hte klassi)
			// transaktsiooni (transaktsiooni ja treeningandmete rea abil saab
			// konstrueerida reegli)
			Transaction[] trainingTableTransactions = new Transaction[trainingTable.length];

			// algv��rtustame transaktsioonide massiivi
			for (int i = 0; i < trainingTableTransactions.length; i++) {
				int[] t = new int[trainingTable[0].length - 1];// transakstioonis
				// klassi
				// v��rtust pole
				for (int j = 0; j < t.length; j++) {
					t[j] = 1;
				}
				trainingTableTransactions[i] = new Transaction(t, 1, t.length,
						0);
			}

			RuleFinder ruleFinder = new RuleFinder();
			// main ts�kkel �le �ppeandmete, kus anname �he �pitava rea ette,
			// millele tahame leida parimat teda katvat reeglit.
			for (int i = 0; i < trainingTable.length; i++) {
				trainingTableTransactions = ruleFinder.ruleFinder(i,
						trainingTable, trainingTableTransactions);
				// System.out.println("rida"+i);
			}
			// teeme reeglid leitud transaktsioonide p�hjal k�igile
			// �ppen�idetele
			ArrayList ruleList = new ArrayList();
			for (int i = 0; i < trainingTableTransactions.length; i++) {
				if (trainingTableTransactions[i].frequency > 1) {
					int[] rule = new int[trainingTableTransactions[0].items.length + 1];
					for (int j = 0; j < trainingTableTransactions[0].items.length; j++) {
						if (trainingTableTransactions[i].items[j] == 1) {
							rule[j] = trainingTable[i][j];
						} else {
							rule[j] = -1;
						}
					}
					rule[rule.length - 1] = trainingTable[i][trainingTable[0].length - 1];
					ruleList.add(rule);
				}
			}
			// testime, mitu reeglit leidsime
			int[][] testingTable = fileRoutine
					.readFromFile("testing2TestingData.txt");
			int unseen = 0;
			int correct = 0;
			int incorrect = 0;
			ArrayList<int[]> unseenTests = new ArrayList<int[]>();
			TreeMap<String, Integer> testResults = new TreeMap<String, Integer>();

			for (int i = 0; i < testingTable.length; i++) {
				int t = findFirstPattern(testingTable[i], ruleList);
				if (t != -1) {
					String key = testingTable[i][testingTable[0].length - 1]
							+ ";" + t;
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

			FileWriter fileWriter = new FileWriter(datasets[f] + "TrainingData"
					+ "_BigTesting_testResults");
			BufferedWriter out = new BufferedWriter(fileWriter);

			out.write(datasets[f] + "TrainingData" + " "
					+ "Big TestingData.txt" + " \n".toUpperCase());

			out.write("timeStamp: " + new Timestamp(new Date().getTime())
					+ "\n");
			out.write("train TTClassifier\n".toUpperCase());
			out.write("Correct: " + correct + "\n");
			out.write("InCorrect: " + incorrect + "\n");
			out.write("Unclassified: " + unseen + "\n");
			double[][] confusionMatrix = getConfusionMatrix(testResults);
			for (int i = 0; i < confusionMatrix.length; i++) {
				out.write("fMeasure(" + i + "): "
						+ getFMeasure(i, confusionMatrix) + "\n");
			}
			out.write("Confusion matrix data: \n");
			for (double[] rida : confusionMatrix) {
				for (double element : rida) {
					out.write(element + "; ");
				}
				out.write("\n");
			}

			out.write("*************************************************\n");
			out.write("\n");

			System.out.println(datasets[f] + ": correct " + correct
					+ " incorrect " + incorrect + " unseen " + unseen);

			if (unseenTests.size() > 0) {
				/**
				 * �lej��nud algoritmide jaoks ette valmistumine
				 */
				ArffData unseenData = ConvertIntArray.intArrayToArffData(
						unseenTests, "uniquetraining2" + "UnseenTests.arff");
				/**
				 * kuna unseen-failis ei ole k�ikki atribuutide v�imalike
				 * v��rtusi olemas, siis kopeerin treeningfailist headeri
				 */
				ArffFileReader reader = new ArffFileReader();
				ArffData trainingData = reader.read(datasets[f]
						+ "TrainingData.arff");
				unseenData.setArffHeaderList(trainingData.getArffHeaderList());
				WriteArffDataFile arffToString = new WriteArffDataFile();
				arffToString.setArffFileData(unseenData);
				WriteToFile writer = new WriteToFile();
				writer.creatOutput(datasets[f] + "UnseenTests.arff",
						arffToString.getArffFileData());

				// RunMemoryLeakage.allAlgorithms(datasets[f] + "TrainingData",
				// datasets[f] + "UnseenTests", out, confusionMatrix);
			} else {
				out.write("No rules to test with other algorithms");
			}
			out.close();
		}

	}

	public static double getFMeasure(int classIndex, double[][] confusionMatrix) {
		double fmeasure = 0;
		double correct = confusionMatrix[classIndex][classIndex];
		double incorrectPrecision = 0;
		for (int i = 0; i < confusionMatrix.length; i++) {
			if (i != classIndex) {
				incorrectPrecision = incorrectPrecision
						+ confusionMatrix[i][classIndex];
			}
		}
		double incorrectRecall = 0;
		for (int i = 0; i < confusionMatrix[classIndex].length; i++) {
			if (i != classIndex) {
				incorrectRecall = incorrectRecall
						+ confusionMatrix[classIndex][i];
			}
		}
		double presision = correct / (correct + incorrectPrecision);
		double recall = correct / (correct + incorrectRecall);
		fmeasure = (2 * presision * recall) / (presision + recall);
		return fmeasure;
	}

	public static double[][] getConfusionMatrix(
			TreeMap<String, Integer> testResults) {
		int minRow = 1000;
		int minColumn = 1000;
		int maxRow = 0;
		int maxColumn = 0;
		for (String keyTemp : testResults.keySet()) {
			String[] key = keyTemp.split(";");
			if (minColumn > Integer.parseInt(key[0])) {
				minColumn = Integer.parseInt(key[0]);
			}
			if (maxColumn < Integer.parseInt(key[0])) {
				maxColumn = Integer.parseInt(key[0]);
			}
			if (minRow > Integer.parseInt(key[1])) {
				minRow = Integer.parseInt(key[1]);
			}
			if (maxRow < Integer.parseInt(key[1])) {
				maxRow = Integer.parseInt(key[1]);
			}
		}
		double[][] confusionMatrix = new double[maxRow - minRow + 1][maxColumn
				- minColumn + 1];
		for (String keyTemp : testResults.keySet()) {
			String[] key = keyTemp.split(";");
			confusionMatrix[Integer.parseInt(key[1]) - minRow][Integer
					.parseInt(key[0]) - minColumn] = testResults.get(keyTemp);
		}

		return confusionMatrix;
	}
}
