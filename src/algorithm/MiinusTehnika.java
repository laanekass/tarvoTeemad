package algorithm;

/*
 * Miinustehnika ainult ridadele ja koos kaaludega v�ljaviskamishetkel
 */
public class MiinusTehnika {
	private byte[][] table; // kohalik muutuja masin�ppe andmetabeli jaoks
	private short[][] frequencyTable;

	private double[] rowWeight; // rea konformus (kaal) v�ljaviskamise hetkel

	/**
	 * @param table
	 */
	public MiinusTehnika(byte[][] table) {
		this.table = table;
		rowWeight = new double[table.length];
	}

	/*
	 * leiame sagedustabeli ridadele alates etteantud tabeli reast.
	 */
	public void createRowFrequencyTable(int startRow) {
		frequencyTable = new short[50][table[0].length - 1];
		System.out.println(frequencyTable.length + "x"
				+ frequencyTable[0].length);
		int i = startRow;
		int j;
		System.out.println("table[0].length - 1: " + (table[0].length - 1));
		while (i < table.length) {
			j = 0;
			while (j < table[0].length - 1) {
				System.out.println(i + ";" + j);
				System.out.println("frequencyTable[table[i][j]][j]++: "
						+ (frequencyTable[table[i][j]][j]++));
				frequencyTable[table[i][j]][j]++;
				j++;
			}
			i++;
		}
	}

	/*
	 * leiame k�ige v�iksema konformsusega rea alates etteantud reast ja
	 * kasutades eelnevalt leitud sagedustabelit
	 */
	public void findMinFrequencyRow(int startRow) {
		int minFrequencyRowId = -1;
		double minFrequencySq = Math.pow((table[0].length - 1) * table.length,
				2) + 1;
		double rowFrequencySq;
		double minFrequency = 0;
		double rowFrequency;

		int i = startRow;
		int j;
		while (i < table.length) {
			j = 0;
			rowFrequencySq = 0;
			rowFrequency = 0;
			while (j < table[0].length - 1 && minFrequencySq > rowFrequencySq) {
				rowFrequencySq = rowFrequencySq
						+ Math.pow(frequencyTable[table[i][j]][j], 2);
				rowFrequency = rowFrequency + frequencyTable[table[i][j]][j];
				j++;
			}
			if (rowFrequencySq < minFrequencySq) {
				minFrequencyRowId = i;
				minFrequencySq = rowFrequencySq;
				minFrequency = rowFrequency;
			}
			i++;
		}
		if (minFrequencyRowId != startRow) {
			byte[] temp = new byte[table[0].length];
			temp = table[startRow];
			table[startRow] = table[minFrequencyRowId];
			table[minFrequencyRowId] = temp;
			// t�stame reanumbrid ka �mber
			/*
			 * short tempRowNumber = rowNumber[minFrequencyRowId];
			 * rowNumber[minFrequencyRowId] = rowNumber[startRow];
			 * rowNumber[startRow] = tempRowNumber;
			 */
		}
		// Lisame rea kaalu v�ljaviskamishetkel vastavasse tabelisse
		rowWeight[startRow] = minFrequency;
	}

	public void doMiinustehnika() {

		int i = 0;
		/*
		 * teeme miinustehnikat ridadele iga ts�kli sammuga leiame �he
		 * v�ljavisatava rea. V�ljavisatavad read paneme esialgse tabeli
		 * algusesse. St esimesena v�ljavisatud rida l�heb esialgses tabelis
		 * esimeseks, teisena v�ljavisatud rida teiseks jne. V�ljavisatud
		 * ridadest esialgses tabelis me end segada ei lase, vaid vaatame
		 * esialgset tabelit alates reast, mis pole veel v�lja visatud
		 */
		while (i < table.length) {
			createRowFrequencyTable(i);
			findMinFrequencyRow(i);
			i++;
		}
		/*
		 * veerud j�tame seekord reastamata
		 */
	}

	public short[][] getFrequencyTable() {
		return frequencyTable;
	}

	public byte[][] getTable() {
		return table;
	}

	/**
	 * @return Returns the rowWeight.
	 */
	public double[] getRowWeight() {
		return rowWeight;
	}
}
