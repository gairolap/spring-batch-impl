/**
 * Model class for UM_TABLES_DATA.
 */
package com.jpmorgan.gwmft.batch.model;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class UMTablesData implements Entity {

	String rowId;

	String record;

	String recordTyp;

	String tblNm;

	@Override
	public String getEntity() {

		return "UMTablesData";
	}

}