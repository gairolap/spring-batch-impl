/**
 * RowMapper class for UM_TABLES_DATA.
 */
package com.jpmorgan.gwmft.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpmorgan.gwmft.batch.model.UMTablesData;

public class UMTablesMapper implements RowMapper<UMTablesData> {

	@Override
	public UMTablesData mapRow(ResultSet rs, int rowNum) throws SQLException {

		UMTablesData umTablesData = new UMTablesData();
		umTablesData.setRowId(rs.getString("ROW_ID"));
		umTablesData.setRecord(rs.getString("RECORD"));
		umTablesData.setRecordTyp(rs.getString("RECORD_TYP"));
		umTablesData.setTblNm(rs.getString("TBL_NM"));

		return umTablesData;
	}

}