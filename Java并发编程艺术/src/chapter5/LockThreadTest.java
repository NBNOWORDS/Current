package chapter5;

public class LockThreadTest {
	public static void main(String[] args) {
		LockThread t1 = new LockThread();
		LockThread t2 = new LockThread();
		t1.start();
		t2.start();
		t2.interrupt();
	}
}
