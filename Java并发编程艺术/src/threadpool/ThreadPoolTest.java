package threadpool;

import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	public static void main(String[] args) {
		//�����̳߳أ���ʼ���߳���Ϊ2�������߳���Ϊ4������߳���Ϊ6����������������1000������
		final ThreadPool threadPool = new BasicThreadPool(2, 6, 4, 1000);
		//����20��������̳߳�
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
