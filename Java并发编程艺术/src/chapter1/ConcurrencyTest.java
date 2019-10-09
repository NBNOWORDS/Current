package chapter1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrencyTest {
	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		lock.lock();
	}
}
