/**
 * Model class for BATCH_JOB_EXECUTION.
 */
package com.jpmorgan.gwmft.batch.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BatchDetails {

	String jobExecutionId;

	String jobKey;

	String jobName;

	String stepName;

	String readCount;

	String writeCount;

	String createTym;

	String startTym;

	String endTym;

	String status;

	String exitCode;

}