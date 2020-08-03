/**
 * Processor class for UM_TABLES_DATA.
 */
package com.jpmorgan.gwmft.batch.processor;

import org.springframework.batch.item.ItemProcessor;

import com.jpmorgan.gwmft.batch.model.UMTablesData;

public class UMTablesProcessor implements ItemProcessor<UMTablesData, UMTablesData> {

	@Override
	public UMTablesData process(UMTablesData umTablesData) throws Exception {

		return umTablesData;
	}

}