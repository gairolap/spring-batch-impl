/**
 * Interface to fetch mapper details.
 */
package com.jpmorgan.gwmft.batch.mappers;

import org.springframework.stereotype.Component;

@Component
public interface Mapper {

	public String getMapperKey();

}