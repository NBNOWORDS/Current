package chapter5;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockThread {
	public static Lock lock = new ReentrantLock();
	public static Condition condition = lock.newCondition();
	public static void get()  {
		try {
			lock.lock();
			condition.await();
			System.out.println("quit");
			System.out.println(condition);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
	
	public static void signal() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			condition.signal();
			System.out.println(condition);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			lock.unlock();
		}
	}
}
