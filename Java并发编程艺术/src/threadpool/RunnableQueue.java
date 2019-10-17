package threadpool;

public interface RunnableQueue {
	void offer(Runnable runnable);
	Runnable take();
	int size();
}
