/**
 * 
 */
package com.vip.saturn.job.trigger;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.vip.saturn.job.basic.AbstractElasticJob;

/**
 * @author chembo.huang
 *
 */
public class SaturnScheduler {

	private static final String SATURN_QUARTZ_WORKER = "-saturnQuartz-worker";
	private final AbstractElasticJob job;

	private Trigger trigger;
	private final ExecutorService executor;
	private SaturnWorker saturnQuartzWorker;

	public SaturnScheduler(final AbstractElasticJob job, final Trigger trigger) {
		this.job = job;
		this.trigger = trigger;
		executor = Executors.newSingleThreadExecutor(new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r,
						job.getExecutorName() + "_" + job.getConfigService().getJobName() + SATURN_QUARTZ_WORKER);
				if (t.isDaemon()) {
					t.setDaemon(false);
				}
				if (t.getPriority() != Thread.NORM_PRIORITY) {
					t.setPriority(Thread.NORM_PRIORITY);
				}
				return t;
			}
		});
	}

	public void start() throws SchedulerException {
		saturnQuartzWorker = new SaturnWorker(job, trigger);
		executor.submit(saturnQuartzWorker);
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void shutdown() {
		saturnQuartzWorker.halt();
		executor.shutdown();
	}

	public void triggerJob() {
		saturnQuartzWorker.trigger();
	}

	public boolean isShutdown() {
		return saturnQuartzWorker.isShutDown();
	}

	public void rescheduleJob(Trigger createTrigger) throws SchedulerException {
		this.trigger = createTrigger;
		saturnQuartzWorker.reInitTrigger(createTrigger);
	}
}
