/**
 * Controller class for Batch application.
 */
package com.jpmorgan.gwmft.batch.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jpmorgan.gwmft.batch.constants.BatchConstants;
import com.jpmorgan.gwmft.batch.mappers.BatchDetailsMapper;
import com.jpmorgan.gwmft.batch.models.BatchDetails;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BatchController {

	@Value("${fetchBatchDetailsSQL}")
	String fetchBatchDetailsSQL;

	@Value("${batchDetailsOrderByClause}")
	String batchDetailsOrderByClause;

	@Autowired
	JdbcTemplate batchDtlsJdbcTemplate;

	@GetMapping
	public ModelAndView batches(@ModelAttribute BatchDetails batchDtls,
			@RequestParam(value = "batchDate", required = false) String batchDate) {

		ModelAndView modelAndView = new ModelAndView();
		List<BatchDetails> batchDetails;

		if (StringUtils.isEmpty(batchDate)) {

			batchDate = new SimpleDateFormat(BatchConstants.BATCH_DTLS_DT_FRMT).format(new Date());
		}

		batchDetails = (List<BatchDetails>) batchDtlsJdbcTemplate.query(
				fetchBatchDetailsSQL.concat(BatchConstants.WHITESPACE_KEY).concat(BatchConstants.DOUBLE_QUOTE_KEY)
				.concat(batchDate).concat(BatchConstants.DOUBLE_QUOTE_KEY).concat(BatchConstants.WHITESPACE_KEY)
				.concat(batchDetailsOrderByClause),
				new BatchDetailsMapper());

		modelAndView.setViewName(BatchConstants.BATCH_VIEWNM_KEY);
		modelAndView.addObject(BatchConstants.BATCH_MODEL_KEY, batchDetails);

		return modelAndView;
	}

}