package algorithm;

import java.util.ArrayList;

import model.Transaction;

public class RuleFinder {
	public static ArrayList ruleList = new ArrayList();
	private Transaction[] trainingTableTransactions;
	
	/* 1. kontrollime, kas väljavõte on frequent
	 * 		kui väljavõte on frequent, kuid täpselt võrdne min suppordiga, siis tunnuste arv peab olema vähemalt 1 võrra väiksem, kui võrreldaval piiril.
	 * 2. kui 1. ei, siis lõpetame selle haru töö.
	 * 3. kui 1. jah, siis kontrollime, kas tegu on reegliga
	 * 4. kui 3. jah, siis kirjutame reegli kõigile neile ridadele maha, kus ta parem on (vähemalt 1 real on ta parem) ja lõpetame selle haru töö.
	 * 5. kui 3. ei, siis kontrollime, kas on prefixit hoidev.
	 * 6. kui 5. jah, siis teeme rekursiooni sügavamale
	 * 7. kui 5. ei, siis lõpetame selle haru töö.
	 * */
	public void bestRuleFinderForRow (int rowNo, int core, int[][] trainingTable, int[] itemFrequencies, int[] indexes, int rowStart[], int[] selectedRows, int selectedRowsCount, int[] selectedItems, int selectedItemCount) {
		int selectedItemsConformity = 0;
		for (int i = 0; i < selectedItems.length; i++) {
			if (selectedItems[i]==1) {
				selectedItemsConformity += itemFrequencies[i]*itemFrequencies[i];
			}
		}
		if (selectedRowsCount < trainingTableTransactions[rowNo].frequency) {
			return;
		} else if((selectedRowsCount == trainingTableTransactions[rowNo].frequency) && (selectedItemCount > trainingTableTransactions[rowNo].itemsCount)){
			return;
		} else if((selectedRowsCount == trainingTableTransactions[rowNo].frequency) && (selectedItemCount == trainingTableTransactions[rowNo].itemsCount) && (selectedItemsConformity >= trainingTableTransactions[rowNo].conformity)){
			return;
		}
		//kontrollime, kas tegu on reegliga
		boolean isRule = true;
		for (int i = 0; i < selectedRowsCount; i++) {
			if (trainingTable[selectedRows[0]][trainingTable[0].length-1] != trainingTable[selectedRows[i]][trainingTable[0].length-1]) {
				isRule = false;
				break;
			}
		}
		if (isRule) {
			/*kui saaks parima reegli alteratiive..
			int[] rule = new int[trainingTableTransactions[0].items.length+1];
			for (int j = 0; j < selectedItems.length; j++) {
				if (selectedItems[j] == 1) {
					rule[j] = trainingTable[rowNo][j];
				} else {
					rule[j] = -1;
				}
			}
			rule[rule.length-1] = trainingTable[rowNo][trainingTable[0].length-1];
			ruleList.add(rule);
		*/
			for (int m = 0; m < selectedRowsCount; m++) {
				if (selectedRows[m]>=rowNo &&((selectedRowsCount > trainingTableTransactions[selectedRows[m]].frequency) || ((selectedRowsCount == trainingTableTransactions[selectedRows[m]].frequency) && (selectedItemCount < trainingTableTransactions[selectedRows[m]].itemsCount)) || ((selectedRowsCount == trainingTableTransactions[selectedRows[m]].frequency) && (selectedItemCount == trainingTableTransactions[selectedRows[m]].itemsCount) && (selectedItemsConformity < trainingTableTransactions[selectedRows[m]].conformity)))) {
					System.arraycopy( selectedItems, 0, trainingTableTransactions[selectedRows[m]].items, 0, selectedItems.length );
					trainingTableTransactions[selectedRows[m]].itemsCount = selectedItemCount;
					trainingTableTransactions[selectedRows[m]].frequency = selectedRowsCount;
					trainingTableTransactions[selectedRows[m]].conformity = selectedItemsConformity;
				}
			}
			/*for (int i = 0; i < trainingTableTransactions.length; i++) {
				for (int j = 0; j < trainingTableTransactions[0].items.length; j++) {
					System.out.print(trainingTableTransactions[i].items[j] + " ");
				}
				System.out.println("");
			}
			System.out.println("");*/
			return;
		}
		//leiame veerud, mis moodustavad prefixi (seni mustris kasutamata tunnused enne core-i.) 
		int prefixCount = 0;
		for (int i = 0; i < core; i++) {
			if (selectedItems[i] == 0) {
				prefixCount++;
			}
		}
		
		if (prefixCount > 0) {
		
			int index = 0;
			int[] prefixItems = new int[prefixCount];
			for (int i = 0; i < core; i++) {
				if (selectedItems[i] == 0) {
					prefixItems[index] = i;
					index++;
				}
			}
			
			//leiame prefixi sagedused
			int[] prefixFrequencies = new int[prefixCount]; 
			for (int i = 0; i < selectedRowsCount; i++) {
				for (int j = 0; j < prefixItems.length; j++) {
					if (trainingTable[selectedRows[0]][prefixItems[j]] == trainingTable[selectedRows[i]][prefixItems[j]]) {
						prefixFrequencies[j]++;
					}
				} 
			}
			//kontrollime, kas tegu on prefixit hoidva mustriga 
			for (int i = 0; i < prefixFrequencies.length; i++) {
				if (prefixFrequencies[i] == selectedRowsCount) {
					return;
				}
			}
		}
		//leiame tunnused, mis moodustavad suffixi (peale core-i kasutamata tunnused)
		int suffixCount = 0;
		for (int i = core+1; i < selectedItems.length; i++) {
			if (selectedItems[i] == 0) {
				suffixCount++;
			}
		}
		
		if (suffixCount > 0) {
			
			int index = 0;
			int[] suffixItems = new int[suffixCount];
			for (int i = core+1; i < selectedItems.length; i++) {
				if (selectedItems[i] == 0) {
					suffixItems[index] = i;
					index++;
				}
			}
			
			//leiame suffixi sagedused
			int[] suffixFrequencies = new int[suffixCount]; 
			for (int i = 0; i < selectedRowsCount; i++) {
				for (int j = 0; j < suffixItems.length; j++) {
					if (trainingTable[selectedRows[0]][suffixItems[j]] == trainingTable[selectedRows[i]][suffixItems[j]]) {
						suffixFrequencies[j]++;
					}
				} 
			}
			//määrame mustris need read ära, mis clousure operastiooniga kaasa tulevad 
			for (int i = 0; i < suffixFrequencies.length; i++) {
				if (suffixFrequencies[i] == selectedRowsCount) {
					selectedItems[suffixItems[i]] = -1;
				}
			}
		}
		for (int j = core+1; j < selectedItems.length; j++) {
			if (selectedItems[j] == 0) {
				int[] newSelectedItems = new int[trainingTable[0].length-1];
				System.arraycopy( selectedItems, 0, newSelectedItems, 0, selectedItems.length );
				newSelectedItems[j] = 1;
				int[] newSelectedRows = new int[selectedRowsCount-1];
				int i = 0;
				int newI = 0;
				int newSelectedRowsCount = 0;
				while ((i!=selectedRowsCount) && (newI != itemFrequencies[j])) {
					if (selectedRows[i]<indexes[rowStart[j]+newI]) {
						i++;
					} else if (selectedRows[i]>indexes[rowStart[j]+newI]) {
						newI++;
					} else {//on võrdne
						newSelectedRows[newSelectedRowsCount] = selectedRows[i];
						newSelectedRowsCount++;
						newI++;
						i++;
					}
				}
				bestRuleFinderForRow(rowNo, j, trainingTable, itemFrequencies, indexes, rowStart, newSelectedRows, newSelectedRowsCount, newSelectedItems, selectedItemCount+1);
			}
		}
		
		
	}
	
	public Transaction[] ruleFinder(int rowNo, int[][] trainingTable, Transaction[] trainingTableTransactions) {
		this.trainingTableTransactions = trainingTableTransactions;
		//leiame sagedusrea
		int[] itemFrequencies = new int[trainingTable[0].length-1]; 
		int totalCount = 0;
		for (int j = 0; j < trainingTable[0].length-1; j++) {
			for (int i = 0; i < trainingTable.length; i++) {
				if (trainingTable[rowNo][j] == trainingTable[i][j]) {
					itemFrequencies[j]++;
					totalCount++;
				}
			}
		}
		
		//paneme kõikide itemite esinemissagedustele vastavate ridade indexid ühte pikka jorusse.
		int[] indexes = new int[totalCount];
		//indexes reas märgime ära, kus hakkab järgmise itemi-i sagedusele vastavad read.
		int rowStart[] = new int[trainingTable[0].length-1];
		int counter = 0;
		for (int j = 0; j < trainingTable[0].length-1; j++) {
			rowStart[j] = counter;
			for (int i = 0; i < trainingTable.length; i++) {
				if (trainingTable[rowNo][j] == trainingTable[i][j]) {
					indexes[counter] = i;
					counter++;
				}
			}
		}
		int[] selectedRows = new int[trainingTable.length];
		for (int i = 0; i < selectedRows.length; i++) {
			selectedRows[i]=i;
		}
		bestRuleFinderForRow(rowNo, -1, trainingTable, itemFrequencies, indexes, rowStart, selectedRows, selectedRows.length, new int[trainingTable[0].length-1], 0);
		return this.trainingTableTransactions;
	}
}
