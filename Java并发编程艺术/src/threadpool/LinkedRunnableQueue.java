package threadpool;

import java.util.LinkedList;

public class LinkedRunnableQueue implements RunnableQueue{
	//任务队列的最大容量，在构造时传入
	private final int limit;
	//若任务队列中的任务以已经满了，则需要执行饱和策略
	private final DenyPolicy denyPolicy;
	//存放任务的队列
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
					//如果任务队列中没有可执行任务，则当前线程将会进入wait状态
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
