package alp.multithreading.racecondition;

import java.util.concurrent.locks.ReentrantLock;

public class Counter3 {

	private ReentrantLock lock = new ReentrantLock();

	public Counter3() {
	}

	public Counter3(int start) {
		count = start;
	}

	private int count = 0;

	// synchronized
	public void increment() {
		lock.lock();
		try {
			count++;
		} finally {
			lock.unlock();
		}

	}

	public int getCount() {
		lock.lock();
		try {
			return count;
		} finally {
			lock.unlock();
		}
	}

	public void changeLock() {
		//this.lock = new ReentrantLock();
	}

}
