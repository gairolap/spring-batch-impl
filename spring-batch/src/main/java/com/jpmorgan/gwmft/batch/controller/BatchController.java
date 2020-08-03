/**
 * Controller class for Spring Batch application.
 */
package com.jpmorgan.gwmft.batch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.jpmorgan.gwmft.batch.model.BatchDetails;
import com.jpmorgan.gwmft.batch.repo.MySQLRepo;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityScan("com.jpmorgan.gwmft.batch.model")
@EnableJpaRepositories(basePackages = { "com.jpmorgan.gwmft.batch.repo" })
public class BatchController {

	@Autowired
	MySQLRepo mySQLRepo;

	@GetMapping("/batches")
	public ModelAndView batches(@ModelAttribute BatchDetails batchDtls,
			@RequestParam(value = "batchDate", required = false) String batchDate) {

		ModelAndView modelAndView = new ModelAndView();
		List<BatchDetails> batchDetails;

		if (StringUtils.isEmpty(batchDate)) {
			batchDetails = (List<BatchDetails>) mySQLRepo.findAll();
		} else {
			batchDetails = (List<BatchDetails>) mySQLRepo.findByDate(batchDate);
		}

		modelAndView.setViewName("batches");
		modelAndView.addObject("batchDetails", batchDetails);

		return modelAndView;
	}

}