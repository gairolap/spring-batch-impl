/**
 * Interface to fetch entity details.
 */
package com.jpmorgan.gwmft.batch.model;

import org.springframework.stereotype.Component;

@Component
public interface Entity {

	public String getEntity();

}