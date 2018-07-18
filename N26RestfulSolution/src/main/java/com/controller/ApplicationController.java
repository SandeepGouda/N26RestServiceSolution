package com.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Request.ServiceRequest;
import com.log.Log;
import com.response.ResponseService;
import com.service.ApplicationService;
import com.utility.ApplicationUtility;

/**
 * Rest controller to handle the incoming request for Transaction and Statistics
 * 
 */
@RestController
public class ApplicationController {

	@Autowired
	private ApplicationUtility applicationUtility;

	@Autowired
	private ApplicationService applicationService;

	private static final Log LOGGER = new Log(ApplicationController.class);

	/**
	 * Handles incoming request for transactions
	 * @param request
	 * @return
	 * @throws ExecutionException
	 */
	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public ResponseEntity<Void> transactions(@RequestBody ServiceRequest request) throws ExecutionException {
		LOGGER.info("NEW_TRANSACTION_REQUEST_RECEIVED");
		long diffSeconds = applicationUtility.calTimeDifference(request.getTimestamp());
		if (diffSeconds < 60) {
			applicationService.addTransaction(request);
			return new ResponseEntity<Void>(HttpStatus.CREATED);

		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	/**
	 * Handles incoming request for statistics
	 * @return
	 */
	@RequestMapping(value = "/statistics", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ResponseService> statistics() {
		LOGGER.info("NEW_STATISTICS_REQUEST_RECEIVED");
		ResponseService response = new ResponseService();
		response = applicationService.getStatistics();
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
