package chapter5;

import java.util.concurrent.TimeUnit;

public class LockThreadTest {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread() {
			public void run() {
				LockThread.get();
			}; 
		};
		t1.start();
		
		Thread t2 = new Thread() {
			public void run() {
				LockThread.signal();
			}; 
		};
		TimeUnit.SECONDS.sleep(1);
		t2.start();
		//t2.start();
	}
}
