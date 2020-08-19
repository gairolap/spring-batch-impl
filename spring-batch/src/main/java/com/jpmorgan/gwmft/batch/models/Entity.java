/**
 * Interface to fetch entity details.
 */
package com.jpmorgan.gwmft.batch.models;

import org.springframework.stereotype.Component;

@Component
public interface Entity {

	public String getEntityKey();

}