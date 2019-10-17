package threadpool;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BasicThreadPool extends Thread implements ThreadPool{
	//��ʼ���߳�����
	private final int initSize;
	//�̳߳�����߳�����
	private final int maxSize;
	//�̳߳غ����߳�����
	private final int coreSize;
	//��ǰ��Ծ���߳�����
	private int activeCount;
	//�����߳�����Ĺ���
	private final ThreadFactory threadFactory;
	//�������
	private final RunnableQueue runnableQueue;
	//�̳߳��Ƿ��Ѿ���shtudown
	private volatile boolean isShutdown = false;
	//�����̶߳���
	private final Queue<ThreadTask> threadQueue = new ArrayDeque<>();
	
	private final static DenyPolicy DEFAULT_DENY_POLICY = new DenyPolicy.DiscardDenyPolicy();
	
	private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory(); 
	
	private final long KeepAliveTime;
	private final TimeUnit timeUnit;
	
	public BasicThreadPool(int initSize, int maxSize, int coreSize, int queueSize) {
		this(initSize, maxSize, coreSize, DEFAULT_THREAD_FACTORY, queueSize, DEFAULT_DENY_POLICY, 10, TimeUnit.SECONDS);
	}
	
	public BasicThreadPool(int initSize, int maxSize, int coreSize, ThreadFactory threadFactory,
			int queueSize, DenyPolicy denyPolicy, long keepAliveTime, TimeUnit timeUnit) {
		this.initSize = initSize;
		this.maxSize = maxSize;
		this.coreSize = coreSize;
		this.threadFactory = threadFactory;
		this.runnableQueue = new LinkedRunnableQueue(queueSize, denyPolicy, this);
		this.KeepAliveTime = keepAliveTime;
		this.timeUnit = timeUnit;
		this.init();
	}
	//��ʼ��ʱ���ȴ���initSize���߳�
	private void init() {
		start();
		for(int i = 0; i < initSize; i++) {
			newThread();
		}
	}
	@Override
	public void run() {
		//����ά���߳��������������ݡ����յȹ���
		while(!isShutdown && isInterrupted()) {
			try {
				timeUnit.sleep(KeepAliveTime);
			}catch(InterruptedException e) {
				isShutdown = true;
				break;
			}
			synchronized(this) {
				if(isShutdown)
					break;
				//��ǰ��������������δ��������activeCount < coreSize���������
				if(runnableQueue.size() > 0 && activeCount < coreSize) {
					for (int i = initSize; i < coreSize; i++) {
						newThread();
					}
					//continue��Ŀ�����ڲ������̵߳�����ֱ�Ӵﵽmaxsize
					continue;
				}
				//��ǰ�Ķ�������������δ��������activeCount < maxSize���������
				if (runnableQueue.size() > 0 && activeCount < maxSize) {
					for (int i = coreSize; i < maxSize; i++) {
						newThread();
					}
				}
				//������������û����������Ҫ���գ�������coreSize����
				if(runnableQueue.size() == 0 && activeCount > coreSize) {
					for (int i = coreSize; i < activeCount; i++) {
						removeThread();
					}
				}
			}
		}
	}
	private void newThread() {
		//���������߳�,��������
		InternalTask internalTask = new InternalTask(runnableQueue);
		Thread thread = this.threadFactory.createThread(internalTask);
		ThreadTask threadTask = new ThreadTask(thread, internalTask);
		threadQueue.offer(threadTask);
		this.activeCount++;
		thread.start();
	}
	
	private void removeThread() {
		//���̳߳���һ��ĳ���߳�
		ThreadTask threadTask = threadQueue.remove();
		threadTask.internalTask.stop();
		this.activeCount--;
	}
	@Override	
	public void exectute(Runnable runnable) {
		// TODO Auto-generated method stub
		if (this.isShutdown)
			throw new IllegalStateException("Thre thread pool is destroy");
		this.runnableQueue.offer(runnable);
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		synchronized(this) {
			if (isShutdown)
				return;
			isShutdown = true;
			threadQueue.forEach(threadTask ->{
				threadTask.internalTask.stop();
				threadTask.thread.interrupt();
			});
			this.interrupt();
		}
	}

	@Override
	public int getMaxSize() {
		// TODO Auto-generated method stub
		if (this.isShutdown)
			throw new IllegalStateException("Thre thread pool is destroy");
		return this.maxSize;
	}

	@Override
	public int getInitSize() {
		// TODO Auto-generated method stub
		if (this.isShutdown)
			throw new IllegalStateException("Thre thread pool is destroy");
		return this.initSize;
	}

	@Override
	public int getCoreSize() {
		// TODO Auto-generated method stub
		if (this.isShutdown)
			throw new IllegalStateException("Thre thread pool is destroy");
		return this.coreSize;
	}

	@Override
	public int getQueueSize() {
		// TODO Auto-generated method stub
		if (this.isShutdown)
			throw new IllegalStateException("Thre thread pool is destroy");
		return runnableQueue.size();
	}

	@Override
	public int getActiveCount() {
		// TODO Auto-generated method stub
		synchronized(this) {
			return this.activeCount;
		}
	}

	@Override
	public boolean isShutdown() {
		// TODO Auto-generated method stub
		return this.isShutdown;
	}
	
	private static class ThreadTask{
		Thread thread;
		InternalTask internalTask;
		public ThreadTask(Thread thread, InternalTask internalTask) {
			this.thread = thread;
			this.internalTask = internalTask;
		}
	}
	
	private static class DefaultThreadFactory implements ThreadFactory{
		private static final AtomicInteger GROUP_CUONTER = new AtomicInteger(1);
		private static final ThreadGroup group = new ThreadGroup("MyThreadPool-" + GROUP_CUONTER.getAndDecrement());
		private static final AtomicInteger COUNTER = new AtomicInteger(0);
		@Override
		public Thread createThread(Runnable runnable) {
			// TODO Auto-generated method stub
			return new Thread(group, runnable, "thread-pool-" + COUNTER.getAndDecrement());
		}
		
	}

}
