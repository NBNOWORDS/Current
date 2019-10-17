package threadpool;
//���Ͳ���
@FunctionalInterface
public interface DenyPolicy {
	void reject(Runnable runnable, ThreadPool threadPool);
	
	//�ò��Ի�ֱ�ӽ�������
	class DiscardDenyPolicy implements DenyPolicy{

		@Override
		public void reject(Runnable runnable, ThreadPool threadPool) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	//�ò��Ի��������ύ���׳��쳣
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
		//�ò��Ի�ʹ�������ύ�����ڵ��߳���ִ������
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
