package threadpool;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue{
	//������е�����������ڹ���ʱ����
	private final int limit;
	//����������е��������Ѿ����ˣ�����Ҫִ�б��Ͳ���
	private final DenyPolicy denyPolicy;
	//�������Ķ���
	private final LinkedList<Runnable> runnableList = new LinkedList<>();
	private final ThreadPool threadPool;
	
	

	public LinkedRunnableQueue(int limit, DenyPolicy denyPolicy, ThreadPool threadPool) {
		this.limit = limit;
		this.denyPolicy = denyPolicy;
		this.threadPool = threadPool;
	}

	@Override
	public void offer(Runnable runnable) {
		// TODO Auto-generated method stub
		synchronized(runnableList) {
			if(runnableList.size() >= limit) {
				denyPolicy.reject(runnable, threadPool);
			}else {
				runnableList.addLast(runnable);
				runnableList.notifyAll();
			}
		}
	}

	@Override
	public Runnable take() {
		// TODO Auto-generated method stub
		synchronized(runnableList) {
			while(runnableList.isEmpty()) {
				try {
					//������������û�п�ִ��������ǰ�߳̽������wait״̬
					runnableList.wait();
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return runnableList.removeFirst();
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		synchronized(runnableList) {
			return runnableList.size();
		}
	}

}
