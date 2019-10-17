package threadpool;

import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	public static void main(String[] args) {
		//定义线程池，初始化线程数为2，核心线程数为4，最大线程数为6，任务队列最多允许1000个任务
		final ThreadPool threadPool = new BasicThreadPool(2, 6, 4, 1000);
		//定义20个任务给线程池
		for (int i = 0; i < 20; i++)
			threadPool.exectute(() -> {
				try {
					TimeUnit.SECONDS.sleep(10);
					System.out.println(Thread.currentThread().getName());
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			});

	}
}
