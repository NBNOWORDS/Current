package chapter5;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockThread extends Thread{
	public static Lock lock = new ReentrantLock();
	
	public void get()  {
		try {
			lock.lockInterruptibly();
			lock.newCondition();
			while(true) {}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		get();
	}
}
