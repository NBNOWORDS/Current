package chapter8;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 银行流水处理服务类
 */
public class BankWaterService implements Runnable{
	/**
	 *  创建4个屏障，处理完之后执行当前类的run方法
	 */
	private CyclicBarrier c = new CyclicBarrier(4, this);
	/**
	 *  假设只有4个sheet，所以只启动4个线程
	 */
	private Executor executor = Executors.newFixedThreadPool(4);
	
	/**
	 *  保存每个sheet计算出的银流结果
	 */
	private ConcurrentHashMap<String, Integer> sheetBankWaterCount = new ConcurrentHashMap<>();
	private void count() {
		for (int i = 0; i < 4; i++) {
			executor.execute(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					//计算当前sheet的银流数据
					// to do
					sheetBankWaterCount.put(Thread.currentThread().getName(), 1);
					//银流计算完成，插入一个屏障
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
		//汇总每个sheet计算出的结果
		for(Entry<String, Integer> sheet : sheetBankWaterCount.entrySet()) {
			result += sheet.getValue();
		}
		//将结果输出
		sheetBankWaterCount.put("result", result);
		System.out.println(result);
	}
	public static void main(String[] args) {
		BankWaterService bankWaterCount = new BankWaterService();
		bankWaterCount.count();
	}
}
