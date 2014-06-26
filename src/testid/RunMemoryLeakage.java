package testid;

import java.io.BufferedWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.rules.ConjunctiveRule;
import weka.classifiers.rules.DTNB;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.JRip;
import weka.classifiers.rules.OneR;
import weka.classifiers.rules.PART;
import weka.classifiers.rules.Ridor;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.Id3;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.J48graft;
import weka.classifiers.trees.LADTree;
import weka.classifiers.trees.NBTree;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class RunMemoryLeakage {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void allAlgorithms(String trainingFileName,
			String testingFileName, BufferedWriter out
	// ,double[][] confusionMatrixFirst
	) throws Exception {

		ArrayList<Classifier> classifiers = new ArrayList<Classifier>();
		classifiers.add(new DecisionTable());
		classifiers.add(new JRip());
		classifiers.add(new OneR());
		classifiers.add(new PART());
		classifiers.add(new ZeroR());
		classifiers.add(new ConjunctiveRule());
		classifiers.add(new DTNB());
		// classifiers.add(new NNge());
		classifiers.add(new Ridor());
		classifiers.add(new DecisionStump());
		classifiers.add(new J48());
		classifiers.add(new RandomForest());
		classifiers.add(new RandomTree());
		classifiers.add(new REPTree());
		classifiers.add(new Id3());
		classifiers.add(new J48graft());
		classifiers.add(new LADTree());
		classifiers.add(new NBTree());
		// classifiers.add(new SimpleCart());

		Evaluation eval = null;
		double[][] confusionMatrix = null;

		out.write(trainingFileName + " " + testingFileName
				+ " \n".toUpperCase());
		System.out.println(trainingFileName);
		DataSource sourceTrain = new DataSource(trainingFileName + ".arff");
		Instances dataTrain = sourceTrain.getDataSet();
		if (dataTrain.classIndex() == -1) {
			dataTrain.setClassIndex(dataTrain.numAttributes() - 1);
		}

		DataSource sourceTest = new DataSource(testingFileName + ".arff");
		Instances dataTest = sourceTest.getDataSet();
		if (dataTest.classIndex() == -1) {
			dataTest.setClassIndex(dataTrain.numAttributes() - 1);
		}

		for (Classifier cls : classifiers) {
			out.write("timeStamp: " + new Timestamp(new Date().getTime())
					+ "\n");
			out.write("train " + cls.toString() + "\n".toUpperCase());
			cls.buildClassifier(dataTrain);

			// test
			eval = new Evaluation(dataTest);
			eval.evaluateModel(cls, dataTest);

			out.write("Correct: " + eval.correct() + "\n");
			out.write("InCorrect: " + eval.incorrect() + "\n");
			out.write("Unclassified: " + eval.unclassified() + "\n");
			confusionMatrix = eval.confusionMatrix();
			for (int i = 0; i < confusionMatrix.length; i++) {
				out.write("fMeasure(" + i + "): " + eval.fMeasure(i) + "\n");
			}
			out.write("Confusion matrix: \n");
			for (int i = 0; i < confusionMatrix.length; i++) {
				for (int j = 0; j < confusionMatrix[i].length; j++) {
					out.write(confusionMatrix[i][j] + "; ");
				}
				out.write("\n");
			}
			out.write("\n\n");

			// out.write("Summarized confusion matrix: \n");
			// double[][] summarizedConfusionMatrix = new
			// double[confusionMatrix.length][confusionMatrix[0].length];
			//
			// for (int i = 0; i < confusionMatrix.length; i++) {
			// for (int j = 0; j < confusionMatrix[i].length; j++) {
			// if (i < confusionMatrixFirst.length
			// && j < confusionMatrixFirst[i].length) {
			// summarizedConfusionMatrix[i][j] = confusionMatrix[i][j]
			// + confusionMatrixFirst[i][j];
			//
			// out.write(summarizedConfusionMatrix[i][j] + "; ");
			// } else {
			// summarizedConfusionMatrix[i][j] = confusionMatrix[i][j];
			// out.write(summarizedConfusionMatrix[i][j] + "; ");
			// }
			// }
			// out.write("\n");
			// }
			//
			// for (int i = 0; i < confusionMatrix.length; i++) {
			// out
			// .write("fMeasure("
			// + i
			// + "): "
			// + Main
			// .getFMeasure(i,
			// summarizedConfusionMatrix)
			// + "\n");
			// }

			out.write("*************************************************\n");
			out.write("\n");
		}
		out.write("timeStamp: " + new Timestamp(new Date().getTime()) + "\n");
	}
}
