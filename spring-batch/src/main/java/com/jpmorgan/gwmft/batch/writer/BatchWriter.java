/**
 * Generic writer implementation for writing to CSV.
 */
package com.jpmorgan.gwmft.batch.writer;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Component;

import com.jpmorgan.gwmft.batch.constant.BatchConstants;

@Component
public class BatchWriter {

	/**
	 * Writes to CSV.
	 * 
	 * @param {@linkplain T}.
	 * @param {@linkplain String}.
	 * @param {@linkplain String}.
	 * @param {@linkplain String}.
	 * @param {@linkplain String}.
	 * 
	 * @return {@linkplain FlatFileItemWriter}.
	 */
	public <T> FlatFileItemWriter<T> flatFileItemWriter(T t, String filePath, String fileToWriteTo, String colsToWrite,
			String delimiter) {

		FlatFileItemWriter<T> umTablesDataWriter = new FlatFileItemWriter<>();
		umTablesDataWriter.setResource(new PathResource(filePath.concat(fileToWriteTo)));
		umTablesDataWriter.setLineAggregator(new DelimitedLineAggregator<T>() {
			{
				setDelimiter(delimiter);
				setFieldExtractor(new BeanWrapperFieldExtractor<T>() {
					{
						String[] colsArray = colsToWrite.split(BatchConstants.COMMA_KEY);
						setNames(colsArray);
					}
				});
			}
		});
		return umTablesDataWriter;
	}

}