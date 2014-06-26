package ruleOrderer.writer;

import java.util.ArrayList;

public interface OutputWriterAdapter {

	public void creatOutput(String fileName, ArrayList<String> dataList);

	public void creatOutputArray(String fileName, ArrayList<String[]> dataList);
}
