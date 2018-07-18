package com.utility;

import java.time.Instant;

import org.springframework.stereotype.Component;

/**
 * This class used as utility to calculate time difference
 *
 */
@Component
public class ApplicationUtility {
	
	public long calTimeDifference(long request) {
		long now = Instant.now().toEpochMilli();
		long diff = now - request;
		long diffSeconds = diff / 1000;
		return diffSeconds;
	}

}
