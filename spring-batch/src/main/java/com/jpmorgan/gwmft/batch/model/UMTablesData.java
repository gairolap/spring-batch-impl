/**
 * Model class for UM_TABLES_DATA.
 */
package com.jpmorgan.gwmft.batch.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UMTablesData {

	String rowId;

	String record;

	String recordTyp;

	String tblNm;

}