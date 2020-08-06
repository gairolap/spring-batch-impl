/**
 * Class to hold Batch configuration.
 */
package com.jpmorgan.gwmft.batch.config;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.jpmorgan.gwmft.batch.constant.BatchConstants;
import com.jpmorgan.gwmft.batch.mapper.UMTablesMapper;
import com.jpmorgan.gwmft.batch.model.Entity;
import com.jpmorgan.gwmft.batch.reader.JDBCBatchReader;
import com.jpmorgan.gwmft.batch.writer.BatchWriter;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Configuration
@EnableBatchProcessing
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BatchConfiguration {

	final Map<String, Entity> modelsMap;

	@Autowired
	public BatchConfiguration(List<Entity> repositories) {

		modelsMap = repositories.stream().collect(Collectors.toMap(Entity::getEntity, Function.identity()));
	}

	@Autowired
	JobBuilderFactory umTablesJobBuilderFactory;

	@Autowired
	StepBuilderFactory umTablesStepBuilderFactory;

	@Autowired
	JDBCBatchReader jdbcBatchReader;

	@Autowired
	BatchWriter batchWriter;

	@Autowired
	DataSource datasource;

	@Value("${fetchUMTablesDetailsSQL}")
	String fetchUMTablesDetailsSQL;

	@Value("${chunkSize}")
	int chunkSize;

	@Value("#{${modelMapping}}")
	Map<String, String> modelMapping;

	@Value("#{${fileNameMapping}}")
	Map<String, String> fileNameMapping;

	@Value("#{${filePathMapping}}")
	Map<String, String> filePathMapping;

	@Value("#{${colsToWriteMapping}}")
	Map<String, String> colsToWriteMapping;

	@Value("#{${delimiterMapping}}")
	Map<String, String> delimiterMapping;

	@Bean
	public JdbcTemplate batchDetailsJdbcTemplate() {

		return new JdbcTemplate(datasource);
	}

	@Bean
	public Step readMySQLWriteToCSV() {

		return umTablesStepBuilderFactory.get("readMySQLWriteToCSV").chunk(chunkSize)
				.reader(jdbcBatchReader.jdbcCursorItemReader(
						modelsMap.get(modelMapping.get(BatchConstants.TRUE_MRKT_IMPCT_KEY)), datasource,
						fetchUMTablesDetailsSQL, new UMTablesMapper()))
				.writer(batchWriter.flatFileItemWriter(
						modelsMap.get(modelMapping.get(BatchConstants.TRUE_MRKT_IMPCT_KEY)),
						filePathMapping.get(BatchConstants.TRUE_MRKT_IMPCT_KEY),
						fileNameMapping.get(BatchConstants.TRUE_MRKT_IMPCT_KEY),
						colsToWriteMapping.get(BatchConstants.TRUE_MRKT_IMPCT_KEY),
						delimiterMapping.get(BatchConstants.TRUE_MRKT_IMPCT_KEY)))
				.build();
	}

	@Bean
	public Job exportUMTablesDataJob() {

		return umTablesJobBuilderFactory.get("exportUMTablesDataJob").incrementer(new RunIdIncrementer())
				.flow(readMySQLWriteToCSV()).end().build();
	}

}