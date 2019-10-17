package threadpool;
//饱和策略
@FunctionalInterface
public interface DenyPolicy {
	void reject(Runnable runnable, ThreadPool threadPool);
	
	//该策略会直接将任务丢弃
	class DiscardDenyPolicy implements DenyPolicy{

		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	//该策略会向任务提交者抛出异常
		class AbortDenyPolicy implements DenyPolicy{

			@Override
			public void reject(Runnable runnable, ThreadPool threadPool)  {
				// TODO Auto-generated method stub
				try {
					throw new Exception("full");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		//该策略会使任务在提交者所在的线程中执行任务
		class RunnerDenyPolicy implements DenyPolicy{

			@Override
			public void reject(Runnable runnable, ThreadPool threadPool) {
				// TODO Auto-generated method stub
				if(!threadPool.isShutdown()) {
					runnable.run();
				}
			}
			
		}
}
