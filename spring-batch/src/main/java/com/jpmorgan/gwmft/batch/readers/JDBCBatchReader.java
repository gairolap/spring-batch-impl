/**
 * Generic reader implementation to read from given data-source. 
 */
package com.jpmorgan.gwmft.batch.readers;

import javax.sql.DataSource;

import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class JDBCBatchReader {

	/**
	 * Reads from given data-source.
	 * 
	 * @param {@linkplain T}.
	 * @param {@linkplain DataSource}.
	 * @param {@linkplain String}.
	 * @param {@linkplain T}.
	 * 
	 * @return {@linkplain JdbcCursorItemReader}.
	 */
	@SuppressWarnings("unchecked")
	public <T> JdbcCursorItemReader<T> jdbcCursorItemReader(T t, DataSource datasource, String sql, T rowMapper) {

		JdbcCursorItemReader<T> itemReader = new JdbcCursorItemReader<>();

		itemReader.setDataSource(datasource);
		itemReader.setSql(sql);
		itemReader.setRowMapper((RowMapper<T>) rowMapper);
		return itemReader;
	}

}