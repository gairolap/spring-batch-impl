/**
 * Step definition to pull data from mySQL and write to CSV.
 */
package com.jpmorgan.gwmft.batch.steps;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.jpmorgan.gwmft.batch.mappers.Mapper;
import com.jpmorgan.gwmft.batch.models.Entity;
import com.jpmorgan.gwmft.batch.readers.JDBCBatchReader;
import com.jpmorgan.gwmft.batch.writer.FlatFileWriter;

@Component
public class MySQLToCSVStep {

	final Map<String, Entity> modelsMap;

	final Map<String, Mapper> mappersMap;

	@Autowired
	public MySQLToCSVStep(List<Entity> entities, List<Mapper> mappers) {

		modelsMap = entities.stream().collect(Collectors.toMap(Entity::getEntityKey, Function.identity()));
		mappersMap = mappers.stream().collect(Collectors.toMap(Mapper::getMapperKey, Function.identity()));
	}

	@Value("#{${chunkSizeMapping}}")
	Map<String, Integer> chunkSizeMapping;

	@Value("#{${sqlsMapping}}")
	Map<String, String> sqlsMapping;

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

	@Autowired
	DataSource datasource;

	@Autowired
	JDBCBatchReader jdbcBatchReader;

	@Autowired
	FlatFileWriter flatFileWriter;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	/**
	 * Step to pull data from mySql table and write to CSV.
	 * 
	 * @param {@linkplain String}.
	 * @return {@linkplain Step}.
	 */
	public Step mySQLToCSVStep(String jobKey) {

		return stepBuilderFactory.get("mySQLToCSVStep").chunk(chunkSizeMapping.get(jobKey))
				.reader(jdbcBatchReader.jdbcCursorItemReader(modelsMap.get(jobKey), datasource, sqlsMapping.get(jobKey),
						mappersMap.get(jobKey)))
				.writer(flatFileWriter.flatFileItemWriter(modelsMap.get(jobKey), filePathMapping.get(jobKey),
						fileNameMapping.get(jobKey), colsToWriteMapping.get(jobKey), delimiterMapping.get(jobKey)))
				.build();
	}

}