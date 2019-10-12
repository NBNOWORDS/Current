package chapter5;

public class SynchronizedThreadTest {
	public static void main(String[] args) {
		SynchronizedThread t1 = new SynchronizedThread();
		SynchronizedThread t2 = new SynchronizedThread();
		t1.start();
		t2.start();
		t1.interrupt();
	}
}
