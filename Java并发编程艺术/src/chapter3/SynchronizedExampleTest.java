package chapter3;

import java.util.concurrent.TimeUnit;

public class SynchronizedExampleTest {
	public static void main(String[] args) throws InterruptedException {
		SynchronizedExample example = new SynchronizedExample();
		Thread t1 = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				example.writer();
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				example.reader();
			}
		};
		t2.start();
		TimeUnit.MILLISECONDS.sleep(10);
		t1.start();
	}
}
