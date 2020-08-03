/**
 * Repository to perform CRUD over MySQL database.
 */
package com.jpmorgan.gwnft.batch.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jpmorgan.gwmft.batch.model.BatchDetails;

@Repository
public interface MySQLRepo extends CrudRepository<BatchDetails, String> {

	@Query(value = "SELECT  JOB_EXECUTION_ID, VERSION, JOB_INSTANCE_ID, CREATE_TIME, START_TIME, END_TIME, STATUS, EXIT_CODE, EXIT_MESSAGE, LAST_UPDATED FROM BATCH_JOB_EXECUTION", nativeQuery = true)
	public List<BatchDetails> findAll(final String tblNm);

}