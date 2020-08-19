/**
 * RowMapper class for Batch Details.
 */
package com.jpmorgan.gwmft.batch.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jpmorgan.gwmft.batch.models.BatchDetails;

@Component
public class BatchDetailsMapper implements RowMapper<BatchDetails> {

	@Override
	public BatchDetails mapRow(ResultSet rs, int rowNum) throws SQLException {

		BatchDetails batchDetails = new BatchDetails();
		batchDetails.setJobExecutionId(rs.getString("JOB_EXECUTION_ID"));
		batchDetails.setJobKey(rs.getString("JOB_KEY"));
		batchDetails.setJobName(rs.getString("JOB_NAME"));
		batchDetails.setStepName(rs.getString("STEP_NAME"));
		batchDetails.setReadCount(rs.getString("READ_COUNT"));
		batchDetails.setWriteCount(rs.getString("WRITE_COUNT"));
		batchDetails.setCreateTym(rs.getString("CREATE_TIME"));
		batchDetails.setStartTym(rs.getString("START_TIME"));
		batchDetails.setEndTym(rs.getString("END_TIME"));
		batchDetails.setStatus(rs.getString("STATUS"));
		batchDetails.setExitCode(rs.getString("EXIT_CODE"));

		return batchDetails;
	}

}