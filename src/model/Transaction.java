package model;

public class Transaction {
	public int[] items;
	public int frequency;
	public int itemsCount;
	public int conformity;
	
	public Transaction (int[] items, int frequency, int itemsCount, int conformity) {
		this.items = items;
		this.frequency = frequency;
		this.itemsCount = itemsCount;
		this.conformity = conformity;
	}
}
