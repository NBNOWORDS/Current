package chapter3;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class VolatileExample {
	int a = 0;
	volatile  boolean flag = false;
	 boolean isRunning = true;
	public void writer() {
		a = 1;
		flag = true;
		
		while(isRunning);
	}
	@SuppressWarnings("all")
	public void reader() {
		int cl = 0;
		cl++;
		while(true) {
			while(a ==0) {
				//System.out.println(1111);
			};
			if (flag) {
			while(a != 1) {
				
			}
				break;
		}
			//System.out.println(flag);
		}
		//System.out.println(a);
		
	}
	
	
}

