package alp.multithreading.racecondition;

public class RaceCondition {
	public static void main(String[] args) {
		RaceCondition raceCondition = new RaceCondition();

		try {
			// raceCondition.RaceConditionProblem();
			// raceCondition.RaceConditionSolutionsBySynchronized();
			// raceCondition.RaceConditionSolutionsBySynchronized1();
			raceCondition.RaceConditionSolutionsBySynchronized3();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void RaceConditionProblem() throws InterruptedException {

		Counter counter = new Counter();
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++)
				counter.increment();
		});

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++)
				counter.increment();
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		System.out.println("Final Count: " + counter.getCount());
		// Final Count: 1490
		// t1 1000 + + t2 1000 = it should be 2000
		// Why 1490 -> its mean lower number (510) due to race condition
	}

	public void RaceConditionSolutionsBySynchronized() throws InterruptedException {
		Counter2 counter = new Counter2();
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++)
				counter.increment();
		});

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++)
				counter.increment();
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();

		System.out.println("Final Count: " + counter.getCount());
		// Final Count: 2000
	}

	public void RaceConditionSolutionsBySynchronized1() throws InterruptedException {

		Counter2 obj1 = new Counter2();
		Counter2 obj2 = new Counter2();

		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				obj2.changeLock();
				obj2.increment();
			}
		});

		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				obj2.changeLock();
				obj2.increment();
			}
		});

		Thread thread3 = new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				obj1.changeLock();
				obj1.increment();
			}
		});

		thread2.start();
		thread1.start();
		thread3.start();
		thread1.join();
		thread3.join();
		thread2.join();

		System.out.println("Expected combined count: 30000");
		System.out.println("Obj1 final count:        " + obj1.getCount());
		System.out.println("Obj2 final count:10000+10000=20000        " + obj2.getCount());
		System.out.println("Actual combined total:   " + (obj1.getCount() + 2 * obj2.getCount()));
	}

	// Proper Solutions for Same
	public void RaceConditionSolutionsBySynchronized3() throws InterruptedException {

		Counter3 obj1 = new Counter3();
		Counter3 obj2 = new Counter3();

		Thread thread1 = new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				obj2.changeLock();
				obj2.increment();
			}
		});

		Thread thread2 = new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				obj2.changeLock();
				obj2.increment();
			}
		});

		Thread thread3 = new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				obj1.changeLock();
				obj1.increment();
			}
		});

		thread2.start();
		thread1.start();
		thread3.start();
		thread1.join();
		thread3.join();
		thread2.join();

		System.out.println("Expected combined count: 30000");
		System.out.println("Obj1 final count:        " + obj1.getCount());
		System.out.println("Obj2 final count:10000+10000=20000        " + obj2.getCount());
		System.out.println("Actual combined total:   " + (obj1.getCount() + 2 * obj2.getCount()));

	}

	//  private final AtomicReference<CounterState> stateRef = 
    //new AtomicReference<>(new CounterState(0));
}
