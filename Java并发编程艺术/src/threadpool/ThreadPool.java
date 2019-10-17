package threadpool;

public interface ThreadPool {
	//�ύ�����̳߳�
	void exectute(Runnable runnable);
	//�ر��̳߳�
	void shutdown();
	//��ȡ�̳߳������߳���
	int getMaxSize();
	//��ȡ�̳߳صĳ�ʼ����С
	int getInitSize();
	//��ȡ�̳߳صĺ����߳�����
	int getCoreSize();
	//��ȡ�̳߳������ڻ���������еĴ�С
	int getQueueSize();
	//��ȡ�̳߳��л�Ծ�̵߳�����
	int getActiveCount();
	//�鿴�̳߳��Ƿ��Ѿ���shutdown
	boolean isShutdown();
}
