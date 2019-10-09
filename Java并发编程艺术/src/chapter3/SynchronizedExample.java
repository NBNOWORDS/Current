package chapter3;

import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedExample {
	int a = 0;
	ReentrantLock lock = new ReentrantLock();
	public void writer() {
		a++;
	}
	
	public void reader() {
		lock.lock();
		try {
			while(true) {
				if (a == 1)
					break;
			}
		} finally {
			lock.unlock();
		}
	}
}
