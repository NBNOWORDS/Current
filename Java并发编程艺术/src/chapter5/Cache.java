package chapter5;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Test;

public class Cache {
	static Map<String, Object> map = new HashMap<>();
	static ReentrantReadWriteLock rw1 = new ReentrantReadWriteLock();
	static Lock r = rw1.readLock();
	static Lock w = rw1.writeLock();
	
	public static final void get() {
		r.lock();
		try {
			while(true);
		}finally {
			r.unlock();
		}
	}
	
	public static final void put() {
		w.lock();
		try {
			System.out.println("fuck");
		}finally {
			w.unlock();
		}
	}
	
	public static void main(String[] args) {
		Thread t1 = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Cache.get();
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				Cache.put();
			};
		};
		t1.start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t2.start();
	}
}
