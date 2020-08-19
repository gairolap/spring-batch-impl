/**
 * Job definition to pull data from mySQL and write to CSV.
 */
package com.jpmorgan.gwmft.batch.jobs;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jpmorgan.gwmft.batch.steps.MySQLToCSVStep;

@Component
public class MySQLToCSVJob {

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	MySQLToCSVStep mySQLToCSVStep;

	/**
	 * Job to pull data from mySql table and write to CSV.
	 * 
	 * @param {@linkplain String}.
	 * @return {@linkplain Job}.
	 */
	public Job mySQLToCSVJob(String jobKey) {

		return jobBuilderFactory.get("mySQLToCSVJob").incrementer(new RunIdIncrementer())
				.flow(mySQLToCSVStep.mySQLToCSVStep(jobKey)).end().build();
	}

}