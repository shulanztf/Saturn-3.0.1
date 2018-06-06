package com.vip.saturn.job.console.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class SaturnThreadFactory implements ThreadFactory {

	protected String threadName;
	protected AtomicInteger nextId = new AtomicInteger();

	public SaturnThreadFactory(String threadName) {
		this.threadName = threadName;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r, threadName + '-' + nextId.getAndIncrement());
		thread.setDaemon(true);
		return thread;
	}
}
