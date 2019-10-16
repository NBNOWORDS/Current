package chapter8;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ������ˮ���������
 */
public class BankWaterService implements Runnable{
	/**
	 *  ����4�����ϣ�������֮��ִ�е�ǰ���run����
	 */
	private CyclicBarrier c = new CyclicBarrier(4, this);
	/**
	 *  ����ֻ��4��sheet������ֻ����4���߳�
	 */
	private Executor executor = Executors.newFixedThreadPool(4);
	
	/**
	 *  ����ÿ��sheet��������������
	 */
	private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();
	private void count() {
		for (int i = 0; i < 4; i++) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					//���㵱ǰsheet����������
					// to do
					sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
					//����������ɣ�����һ������
					try {
						c.await();
						System.out.println("haha");
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			});
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int result = 0;
		//����ÿ��sheet������Ľ��
		for(Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
			result += sheet.getValue();
		}
		//��������
		sheetBankWaterCount.put("result", result);
		System.out.println(result);
	}
	public static void main(String[] args) {
		BankWaterService bankWaterCount = new BankWaterService();
		bankWaterCount.count();
	}
}
