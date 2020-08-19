/**
 * RowMapper class for UM_TABLES_DATA.
 */
package com.jpmorgan.gwmft.batch.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.jpmorgan.gwmft.batch.models.UMTablesData;

@Component
public class UMTablesMapper implements RowMapper<UMTablesData>, Mapper {

	@Override
	public UMTablesData mapRow(ResultSet rs, int rowNum) throws SQLException {

		UMTablesData umTablesData = new UMTablesData();
		umTablesData.setRowId(rs.getString("ROW_ID"));
		umTablesData.setRecord(rs.getString("RECORD"));
		umTablesData.setRecordTyp(rs.getString("RECORD_TYP"));
		umTablesData.setTblNm(rs.getString("TBL_NM"));

		return umTablesData;
	}

	@Override
	public String getMapperKey() {

		return "trueMrktImpct";
	}

}