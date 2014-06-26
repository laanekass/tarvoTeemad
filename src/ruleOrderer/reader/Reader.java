package ruleOrderer.reader;

import ruleOrderer.dataTypes.arff.ArffData;

public interface Reader {

	public ArffData read(String sourceName);
}
