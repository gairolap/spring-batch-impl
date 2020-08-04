/**
 * Controller class for Spring Batch application.
 */
package com.jpmorgan.gwmft.batch.controller;

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

import com.jpmorgan.gwmft.batch.constant.BatchConstants;
import com.jpmorgan.gwmft.batch.mapper.BatchDetailsMapper;
import com.jpmorgan.gwmft.batch.model.BatchDetails;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BatchController {

	@Value("${fetchBatchDetailsSQL}")
	String fetchBatchDetailsSQL;

	@Value("${fetchByCreateDtClause}")
	String fetchByCreateDtClause;

	@Autowired
	JdbcTemplate batchDtlsJdbcTemplate;

	@GetMapping("/batches")
	public ModelAndView batches(@ModelAttribute BatchDetails batchDtls,
			@RequestParam(value = "batchDate", required = false) String batchDate) {

		ModelAndView modelAndView = new ModelAndView();
		List<BatchDetails> batchDetails;

		if (StringUtils.isEmpty(batchDate)) {
			batchDetails = (List<BatchDetails>) batchDtlsJdbcTemplate.query(fetchBatchDetailsSQL,
					new BatchDetailsMapper());
		} else {
			batchDetails = (List<BatchDetails>) batchDtlsJdbcTemplate
					.query(fetchBatchDetailsSQL.concat(BatchConstants.WHITESPACE_KEY).concat(fetchByCreateDtClause)
							.concat(BatchConstants.WHITESPACE_KEY).concat(BatchConstants.DOUBLE_QUOTE_KEY)
							.concat(batchDate).concat(BatchConstants.DOUBLE_QUOTE_KEY), new BatchDetailsMapper());
		}

		modelAndView.setViewName(BatchConstants.BATCH_VIEWNM_KEY);
		modelAndView.addObject(BatchConstants.BATCH_MODEL_KEY, batchDetails);

		return modelAndView;
	}

}