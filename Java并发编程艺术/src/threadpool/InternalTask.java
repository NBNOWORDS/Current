package threadpool;
/*
 * �����̳߳��ڲ��������ʹ�õ�RunnableQueue��
 * Ȼ�󲻶ϴ�queue��ȡ��ĳ��runnable��
 * ������runnable��run����
 */
public class InternalTask implements Runnable{
	private final RunnableQueue runnableQueue;
	private volatile boolean running = true;
	public InternalTask(RunnableQueue runnableQueue) {
		this.runnableQueue = runnableQueue;
	}
	@Override
	public void run() {
		//�����ǰ����Ϊrunning����û�б��жϣ����佫���ϵش�queue�л�ȡrunnable��Ȼ��ִ��run����
		while(running && !Thread.currentThread().isInterrupted()) {
			try {
				Runnable task = runnableQueue.take();
				task.run();
			} catch(Exception e) {
				running = false;
				break;
			}
		}
	}
	
	public void stop() {
		this.running = false;
	}
	
}
