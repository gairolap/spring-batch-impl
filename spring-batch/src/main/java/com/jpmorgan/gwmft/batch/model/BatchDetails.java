/**
 * Model class for BATCH_JOB_EXECUTION.
 */
package com.jpmorgan.gwmft.batch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name = "BATCH_JOB_EXECUTION")
@Data
public class BatchDetails {

	@Id
	@Column(name = "JOB_EXECUTION_ID")
	@JsonProperty("jobExecutionId")
	public String jobExecutionId;
	@Column(name = "VERSION")
	@JsonProperty("version")
	public String version;
	@Column(name = "JOB_INSTANCE_ID")
	@JsonProperty("jobInstanceId")
	public String jobInstanceId;
	@Column(name = "CREATE_TIME")
	@JsonProperty("createTym")
	public String createTym;
	@Column(name = "START_TIME")
	@JsonProperty("startTym")
	public String startTym;
	@Column(name = "END_TIME")
	@JsonProperty("endTym")
	public String endTym;
	@Column(name = "STATUS")
	@JsonProperty("status")
	public String status;
	@Column(name = "EXIT_CODE")
	@JsonProperty("exitCd")
	public String exitCd;
	@Column(name = "EXIT_MESSAGE")
	@JsonProperty("exitMsg")
	public String exitMsg;
	@Column(name = "LAST_UPDATED")
	@JsonProperty("lastUpdDt")
	public String lastUpdDt;

}