package threadpool;
@FunctionalInterface
public interface ThreadFactory {
	
	Thread createThread(Runnable runnable);
}
