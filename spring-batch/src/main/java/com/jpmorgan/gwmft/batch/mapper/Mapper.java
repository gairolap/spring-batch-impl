/**
 * Interface to fetch mapper details.
 */
package com.jpmorgan.gwmft.batch.mapper;

import org.springframework.stereotype.Component;

@Component
public interface Mapper {

	public String getMapper();

}