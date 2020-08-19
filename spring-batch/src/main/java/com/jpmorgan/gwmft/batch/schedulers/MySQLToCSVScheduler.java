/**
 * Scheduler class for pulling data from mySQL and write to CSV.
 */
package com.jpmorgan.gwmft.batch.schedulers;

import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jpmorgan.gwmft.batch.constants.BatchConstants;
import com.jpmorgan.gwmft.batch.jobs.MySQLToCSVJob;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@EnableBatchProcessing
public class MySQLToCSVScheduler {

	@Autowired
	MySQLToCSVJob mySQLToCSVJob;

	@Autowired
	JobLauncher jobLauncher;

	@Scheduled(cron = "0 * * * * *")
	public void executeMySQLToCSVSchedule() throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException {

		jobLauncher.run(mySQLToCSVJob.mySQLToCSVJob("trueMrktImpct"), new JobParametersBuilder()
				.addLong(BatchConstants.TIME_KEY, System.currentTimeMillis()).toJobParameters());
	}

}