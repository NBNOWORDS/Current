package chapter5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.junit.Test;

public class TwinsLockTest {
	@Test
	public void test() {
		final Lock lock = new TwinsLock();
		class Worker extends Thread{
			public void run() {
				while(true) {
					lock.lock();
					try {
						TimeUnit.SECONDS.sleep(1);
						System.out.println(Thread.currentThread().getName());
						TimeUnit.SECONDS.sleep(1);
					} catch(Exception e){
						e.printStackTrace();
					}finally {
						lock.unlock();
					}
				}
			}
		}
		
		for (int i = 0; i <10; i++) {
			Worker w = new Worker();
			w.setDaemon(true);
			w.start();
		}
		
		for (int i = 0; i < 10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println();
		}
	}
}

