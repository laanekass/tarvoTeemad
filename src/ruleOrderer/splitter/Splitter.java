/**
 * 
 */
package ruleOrderer.splitter;

import java.util.ArrayList;

/**
 * @author ehelmai
 * 
 */
public interface Splitter {

	void writeSplittedFiles(ArrayList<String> firstF,
			ArrayList<String> secondF, String firstFName, String secondFName);

}
