package alp.multithreading.racecondition;

public class Counter2 {

	private Object lock = new Object(); // Dedicated lock object

	public Counter2() {
	}

	public Counter2(int start) {
		count = start;
	}

	private int count = 0;

	// synchronized
	public void increment() {
		synchronized (lock) {
			count++;
		}

	}

	public int getCount() {
		synchronized (lock) {
			return count;
		}
	}

	public void changeLock() {
		this.lock = new Object();
	}

}
