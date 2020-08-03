/**
 * Scheduler class for UM_TABLES_DATA batch load.
 */
package com.jpmorgan.gwmft.batch.scheduler;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Component
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UMTablesBatchScheduler {

	@Autowired
	Job umTablesDataSchedulerJob;

	@Autowired
	JobLauncher umTablesDataJobLauncher;

	@Scheduled(cron = "0 38 12 ? * MON-FRI")
	public void executeSchedule() throws JobExecutionAlreadyRunningException, JobRestartException,
	JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		umTablesDataJobLauncher.run(umTablesDataSchedulerJob, new JobParametersBuilder().toJobParameters());
	}

}