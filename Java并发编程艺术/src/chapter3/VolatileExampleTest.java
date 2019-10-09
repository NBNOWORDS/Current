package chapter3;

import java.util.concurrent.TimeUnit;

public class VolatileExampleTest {
	public static void main(String[] args) throws InterruptedException {
		VolatileExample example = new VolatileExample();
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
		t1.setName("1");
		t2.setName("2");
		t2.start();
		TimeUnit.MILLISECONDS.sleep(2000);
		t1.start();
		TimeUnit.MILLISECONDS.sleep(2);
		System.out.println(t2.isAlive());
	}
}
