package com.jpmorgan.gwmft.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.jpmorgan.gwmft.batch.model.UMTablesData;
import com.jpmorgan.gwmft.mapper.UMTablesMapper;
import com.jpmorgan.gwmft.processor.UMTablesProcessor;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Configuration
@EnableBatchProcessing
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BatchConfiguration {

	@Autowired
	JobBuilderFactory jobBuilderFactory;

	@Autowired
	StepBuilderFactory stepBuilderFactory;

	@Autowired
	DataSource datasource;
	
	public JdbcCursorItemReader<UMTablesData> reader() {

		JdbcCursorItemReader<UMTablesData> umTablesDataReader = new JdbcCursorItemReader<UMTablesData>();

		umTablesDataReader.setDataSource(datasource);
		umTablesDataReader.setSql("SELECT ROW_ID, RECORD, RECORD_TYP, TBL_NM FROM UM_TABLES_DATA");
		umTablesDataReader.setRowMapper(new UMTablesMapper());

		return umTablesDataReader;
	}

	@Bean
	public UMTablesProcessor processor() {

		return new UMTablesProcessor();
	}

	@Bean
	public FlatFileItemWriter<UMTablesData> writer() {
		FlatFileItemWriter<UMTablesData> writer = new FlatFileItemWriter<UMTablesData>();
		writer.setResource(new ClassPathResource("umtablesdata.csv"));
		writer.setLineAggregator(new DelimitedLineAggregator<UMTablesData>() {
			{
				setDelimiter(",");
				setFieldExtractor(new BeanWrapperFieldExtractor<UMTablesData>() {
					{
						setNames(new String[] { "rowId", "record", "recordTyp", "tblNm" });
					}
				});
			}
		});

		return writer;
	}

	@Bean
	public Step step1() {

		return stepBuilderFactory.get("step1").<UMTablesData, UMTablesData>chunk(2).reader(reader())
				.processor(processor()).writer(writer()).build();
	}

	@Bean
	public Job exportUMTablesDataJob() {

		return jobBuilderFactory.get("exportUMTablesDataJob").incrementer(new RunIdIncrementer()).flow(step1()).end()
				.build();
	}

}