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

	@Value("#{${consumerToModelMapping}}")
	Map<String, String> consumerToModelMapping;

	@Value("#{${consumerToCSVMapping}}")
	Map<String, String> consumerToCSVMapping;

	@Value("#{${consumerToCSVColsMapping}}")
	Map<String, String> consumerToCSVColsMapping;

	@Bean
	public JdbcTemplate batchDetailsJdbcTemplate() {

		return new JdbcTemplate(datasource);
	}

	@Bean
	public Step readMySQLWriteToCSV() {

		return umTablesStepBuilderFactory.get("readMySQLWriteToCSV").chunk(chunkSize)
				.reader(jdbcBatchReader.jdbcCursorItemReader(modelsMap.get(consumerToModelMapping.get("TMI")),
						datasource, fetchUMTablesDetailsSQL, new UMTablesMapper()))
				.writer(batchWriter.flatFileItemWriter(modelsMap.get(consumerToModelMapping.get("TMI")),
						consumerToCSVMapping.get("TMI"), consumerToCSVColsMapping.get("TMI")))
				.build();
	}

	@Bean
	public Job exportUMTablesDataJob() {

		return umTablesJobBuilderFactory.get("exportUMTablesDataJob").incrementer(new RunIdIncrementer())
				.flow(readMySQLWriteToCSV()).end().build();
	}

}