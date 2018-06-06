package com.vip.saturn.it.impl;

import com.google.gson.Gson;
import com.vip.saturn.it.AbstractSaturnIT;
import com.vip.saturn.it.utils.HttpClientUtils;
import org.assertj.core.util.Maps;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RestApiIT extends AbstractSaturnIT {

	private final Gson gson = new Gson();
	private static String CONSOLE_HOST_URL;

	@BeforeClass
	public static void setUp() throws Exception {
		System.setProperty("ALARM_RAISED_ON_EXECUTOR_RESTART", "true");
		startSaturnConsoleList(1);
		CONSOLE_HOST_URL = saturnConsoleInstanceList.get(0).url;
	}

	@AfterClass
	public static void tearDown() throws Exception {
		stopSaturnConsoleList();
	}

	@After
	public void after() throws Exception {
	}

	@Test
	public void testCreateAndQueryJobSuccessfully() throws Exception {
		// create
		JobEntity jobEntity = constructJobEntity("job1");
		HttpClientUtils.HttpResponseEntity responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs", jobEntity.toJSON());
		assertEquals(201, responseEntity.getStatusCode());
		// query
		responseEntity = HttpClientUtils.sendGetRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/job1");
		assertEquals(200, responseEntity.getStatusCode());
		JobEntity responseJobEntity = gson.fromJson(responseEntity.getEntity(), JobEntity.class);
		// assert for details
		assertEquals("job1", responseJobEntity.getJobName());
		assertEquals("this is a description of job1", responseJobEntity.getDescription());
		assertEquals("0 */1 * * * ?", responseJobEntity.getJobConfig().get("cron"));
		assertEquals("SHELL_JOB", responseJobEntity.getJobConfig().get("jobType"));
		assertEquals(2.0, responseJobEntity.getJobConfig().get("shardingTotalCount"));
		assertEquals("0=echo 0;sleep $SLEEP_SECS,1=echo 1",
				responseJobEntity.getJobConfig().get("shardingItemParameters"));
	}

	@Test
	public void testCreateJobFailAsJobAlreadyExisted() throws Exception {
		JobEntity jobEntity = constructJobEntity("job2");

		HttpClientUtils.HttpResponseEntity responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs", jobEntity.toJSON());

		assertEquals(201, responseEntity.getStatusCode());

		responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs", jobEntity.toJSON());

		assertEquals(400, responseEntity.getStatusCode());

		Map<String, String> responseMap = gson.fromJson(responseEntity.getEntity(), Map.class);
		assertEquals("该作业(job2)已经存在", responseMap.get("message"));
	}

	@Test
	public void testCreateJobFailAsNamespaceNotExisted() throws Exception {
		JobEntity jobEntity = constructJobEntity("job3");

		HttpClientUtils.HttpResponseEntity responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/unknown/jobs", jobEntity.toJSON());

		assertEquals(404, responseEntity.getStatusCode());

		Map<String, String> responseMap = gson.fromJson(responseEntity.getEntity(), Map.class);
		assertEquals("The namespace {unknown} does not exists.", responseMap.get("message"));
	}

	@Test
	public void testCreateJobFailAsCronIsNotFilled() throws Exception {
		JobEntity jobEntity = constructJobEntity("job3");
		jobEntity.getJobConfig().remove("cron");

		HttpClientUtils.HttpResponseEntity responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs", jobEntity.toJSON());
		assertEquals(400, responseEntity.getStatusCode());
		Map<String, String> responseMap = gson.fromJson(responseEntity.getEntity(), Map.class);
		assertEquals("对于JAVA/SHELL作业，cron表达式必填", responseMap.get("message"));
	}

	@Test
	public void testQueryJobFailAsJobIsNotFound() throws IOException {
		HttpClientUtils.HttpResponseEntity responseEntity = HttpClientUtils
				.sendGetRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/not_existed");

		assertEquals(404, responseEntity.getStatusCode());
		Map<String, String> responseMap = gson.fromJson(responseEntity.getEntity(), Map.class);
		assertEquals("The job {not_existed} does not exists.", responseMap.get("message"));
	}


	@Test
	public void testDeleteJobFailAsJobIsCreatedIn2Minutes() throws IOException {
		String jobName = "jobTestDeleteJobSuccessfully";
		// create a job
		JobEntity jobEntity = constructJobEntity(jobName);

		HttpClientUtils.HttpResponseEntity responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs", jobEntity.toJSON());
		assertEquals(201, responseEntity.getStatusCode());

		// and delete it
		responseEntity = HttpClientUtils
				.sendDeleteResponseJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/" + jobName);
		assertEquals(400, responseEntity.getStatusCode());
		Map<String, String> responseMap = gson.fromJson(responseEntity.getEntity(), Map.class);
		assertEquals("不能删除该作业(jobTestDeleteJobSuccessfully)，因为该作业创建时间距离现在不超过2分钟", responseMap.get("message"));
	}

	@Test
	public void testEnableAndDisableJobSuccessfully() throws IOException, InterruptedException {
		// create
		String jobName = "testEnableJobSuccessfully";
		JobEntity jobEntity = constructJobEntity(jobName);
		HttpClientUtils.HttpResponseEntity responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs", jobEntity.toJSON());
		assertEquals(201, responseEntity.getStatusCode());
		// sleep for 10 seconds
		Thread.sleep(10100L);
		// enable
		responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/" + jobName + "/enable",
						jobEntity.toJSON());
		assertEquals(200, responseEntity.getStatusCode());
		// query for status
		responseEntity = HttpClientUtils.sendGetRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/" + jobName);
		assertEquals(200, responseEntity.getStatusCode());
		JobEntity responseJobEntity = gson.fromJson(responseEntity.getEntity(), JobEntity.class);
		assertEquals("READY", responseJobEntity.getRunningStatus());
		// enable again
		responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/" + jobName + "/enable",
						jobEntity.toJSON());
		assertEquals(201, responseEntity.getStatusCode());
		// query for status
		responseEntity = HttpClientUtils.sendGetRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/" + jobName);
		assertEquals(200, responseEntity.getStatusCode());
		responseJobEntity = gson.fromJson(responseEntity.getEntity(), JobEntity.class);
		assertEquals("READY", responseJobEntity.getRunningStatus());
		// sleep for 3 seconds
		Thread.sleep(3010L);
		// disable
		responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/" + jobName + "/disable",
						jobEntity.toJSON());
		assertEquals(200, responseEntity.getStatusCode());
		// query for status
		responseEntity = HttpClientUtils.sendGetRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/" + jobName);
		assertEquals(200, responseEntity.getStatusCode());
		responseJobEntity = gson.fromJson(responseEntity.getEntity(), JobEntity.class);
		assertEquals("STOPPED", responseJobEntity.getRunningStatus());
		// disable again
		responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/" + jobName + "/disable",
						jobEntity.toJSON());
		assertEquals(201, responseEntity.getStatusCode());
		// query for status
		responseEntity = HttpClientUtils.sendGetRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/" + jobName);
		assertEquals(200, responseEntity.getStatusCode());
		responseJobEntity = gson.fromJson(responseEntity.getEntity(), JobEntity.class);
		assertEquals("STOPPED", responseJobEntity.getRunningStatus());
	}

	@Test
	public void testRaiseExecutorRestartAlarmSuccessfully() throws IOException {
		Map<String, Object> requestBody = Maps.newHashMap();
		requestBody.put("executorName", "exec_1");
		requestBody.put("level", "Critical");
		requestBody.put("title", "Executor_Restart");
		requestBody.put("name", "Saturn Event");

		HttpClientUtils.HttpResponseEntity responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/alarms/raise", gson.toJson(requestBody));
		assertEquals(201, responseEntity.getStatusCode());
	}

	@Test
	public void testUpdateCronSuccessfully() throws IOException, InterruptedException {
		String jobName = "testUpdateCronSuccessfully";
		// create
		JobEntity jobEntity = constructJobEntity(jobName);
		HttpClientUtils.HttpResponseEntity responseEntity = HttpClientUtils
				.sendPostRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs", jobEntity.toJSON());
		assertEquals(201, responseEntity.getStatusCode());
		// sleep for a while ...
		Thread.sleep(3010L);
		// update cron
		Map<String, Object> requestBody = Maps.newHashMap();
		requestBody.put("cron", "0 0/11 * * * ?");

		responseEntity = HttpClientUtils
				.sendPutRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/" + jobName + "/cron",
						gson.toJson(requestBody));
		System.out.println(responseEntity.getEntity());
		assertEquals(200, responseEntity.getStatusCode());
		// query again
		responseEntity = HttpClientUtils.sendGetRequestJson(CONSOLE_HOST_URL + "/rest/v1/it-saturn/jobs/" + jobName);
		assertEquals(200, responseEntity.getStatusCode());
		JobEntity responseJobEntity = gson.fromJson(responseEntity.getEntity(), JobEntity.class);
		assertEquals("0 0/11 * * * ?", responseJobEntity.getJobConfig().get("cron"));
	}


	private JobEntity constructJobEntity(String job) {
		JobEntity jobEntity = new JobEntity(job);

		jobEntity.setDescription("this is a description of " + job);
		jobEntity.setConfig("cron", "0 */1 * * * ?");
		jobEntity.setConfig("jobType", "SHELL_JOB");
		jobEntity.setConfig("shardingTotalCount", 2);
		jobEntity.setConfig("shardingItemParameters", "0=echo 0;sleep $SLEEP_SECS,1=echo 1");

		return jobEntity;
	}


	public class JobEntity {
		private final Gson gson = new Gson();

		private String jobName;

		private String description;

		private String runningStatus;

		private Map<String, Object> jobConfig = Maps.newHashMap();

		public JobEntity(String jobName) {
			this.jobName = jobName;
		}

		public void setConfig(String key, Object value) {
			jobConfig.put(key, value);
		}

		public Object getConfig(String key) {
			return jobConfig.get(key);
		}

		public String toJSON() {
			return gson.toJson(this);
		}

		public Gson getGson() {
			return gson;
		}

		public String getJobName() {
			return jobName;
		}

		public void setJobName(String jobName) {
			this.jobName = jobName;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public Map<String, Object> getJobConfig() {
			return jobConfig;
		}

		public void setJobConfig(Map<String, Object> jobConfig) {
			this.jobConfig = jobConfig;
		}

		public String getRunningStatus() {
			return runningStatus;
		}

		public void setRunningStatus(String runningStatus) {
			this.runningStatus = runningStatus;
		}
	}


}
