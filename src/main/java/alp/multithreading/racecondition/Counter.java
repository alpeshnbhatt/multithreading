package alp.multithreading.racecondition;

public class Counter {

	public Counter() {
	}

	public Counter(int start) {
		count = start;
	}

	private int count = 0;

	public void increment() {
		count++;
	}

	public int getCount() {
		return count;
	}

}
