package chapter5;

public class SynchronizedThread extends Thread{
	public void get() {
		synchronized(SynchronizedThread.class) {
			while(true);
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		get();
	}
}
