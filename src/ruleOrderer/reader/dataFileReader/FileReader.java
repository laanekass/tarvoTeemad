/**
 * 
 */
package ruleOrderer.reader.dataFileReader;

import java.util.ArrayList;

import ruleOrderer.dataTypes.arff.ArffData;
import ruleOrderer.dataTypes.arff.ArffHeaderAttribute;
import ruleOrderer.dataTypes.arff.ArffValues;
import ruleOrderer.reader.Reader;

/**
 * @author ehelmai
 * 
 */
public interface FileReader extends Reader {

	ArffData read(String fileName);

	ArrayList<ArffHeaderAttribute> getAttributeListFromFile(String fileName);

	ArrayList<ArffValues> getAttributeValuesFromFile(String fileName);
}
