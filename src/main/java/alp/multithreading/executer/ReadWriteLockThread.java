package alp.multithreading.executer;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReadWriteLockThread {
	public static void main(String[] args) {
		ReadWriteLock rwlock = new ReentrantReadWriteLock();

		final int NUM_THREADS_READ = 10;
		final int NUM_THREADS_WRITE = 4;
		final int NUM_ARGS = 3;

		var lstArg = IntStream.range(0, NUM_ARGS).toArray();
		var rand = new Random();

		var lstThRead = Stream.generate(() -> new Thread(() -> {

			int waitTime = lstArg[rand.nextInt(lstArg.length)];
			try {
				Thread.sleep(1000 * waitTime);
			} catch (InterruptedException e) {
			}

			rwlock.readLock().lock();

			System.out.println("read: " + Resource.value);

			rwlock.readLock().unlock(); // Should be protected by try...finally...

		})).limit(NUM_THREADS_READ).toList();

		var lstThWrite = Stream.generate(() -> new Thread(() -> {

			int waitTime = lstArg[rand.nextInt(lstArg.length)];
			try {
				Thread.sleep(1000 * waitTime);
			} catch (InterruptedException e) {
			}

			rwlock.writeLock().lock();

			Resource.value = rand.nextInt(100);
			System.out.println("write: " + Resource.value);

			rwlock.writeLock().unlock(); // Should be protected by try...finally...

		})).limit(NUM_THREADS_WRITE).toList();

		lstThRead.forEach(Thread::start);
		lstThWrite.forEach(Thread::start);
	}

	class Resource {
		public static volatile int value;
	}

}
/*
 
When to Use (and When to Avoid)

While it offers superior concurrency under specific scenarios, it has higher structural overhead than standard locking mechanisms.
• Ideal Scenarios: Large data collections (like custom caches or registries) where data is read hundreds of times for every single write update.
• Anti-Patterns: If the read operations are extremely short or if the write frequency is high, the overhead of managing the dual-lock wait queues can make it slower than a standard regular ReentrantLock or synchronized block.
Would you like to see a practical example of lock downgrading in action, or are you looking to resolve a specific deadlock / starvation issue you are currently encountering in your code?

 */
