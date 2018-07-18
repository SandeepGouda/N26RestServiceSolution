package com.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.Request.ServiceRequest;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.model.StaticDB;
import com.response.ResponseService;

@Service
public class ApplicationService {
	
	private static ConcurrentHashMap<Long, BigDecimal> mp = new ConcurrentHashMap<Long, BigDecimal>();
	private LoadingCache<Long, BigDecimal> cache = null;
	private CacheLoader<Long, BigDecimal> loader = null;
	
	/**
	 * create loader and cache using google guava for Map
	 */
	@PostConstruct
	public void init() {
		loader = new CacheLoader<Long, BigDecimal>() {
			@Override
			public BigDecimal load(Long key) {
				return mp.get(key);
			}
		};
		
		//Cache expire in 60S can be changed accordingly to verify 
		cache = CacheBuilder.newBuilder().expireAfterWrite(15, TimeUnit.SECONDS).removalListener(new RemovalListener() {
			
			
			/* 
			 * @see com.google.common.cache.RemovalListener#onRemoval(com.google.common.cache.RemovalNotification)
			 * On each removal from cache after 60 Seconds result will refreshed.
			 */
			@Override
			public void onRemoval(RemovalNotification notification) {
				mp.remove(notification.getKey());
				if (!CollectionUtils.isEmpty(mp)) {
					StaticDB.setSum(StaticDB.getSum() - ((BigDecimal) notification.getValue()).doubleValue());
					StaticDB.setCount(StaticDB.getCount() - 1);
					StaticDB.setMax(
							mp.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue().doubleValue());
					StaticDB.setMin(
							mp.entrySet().stream().min(Map.Entry.comparingByValue()).get().getValue().doubleValue());
					StaticDB.setAvg(StaticDB.getSum() / StaticDB.getCount());
				}else{
					StaticDB.setSum((double) 0);
					StaticDB.setCount((double) 0);
					StaticDB.setMax((double) 0);
					StaticDB.setMin(null);
					StaticDB.setAvg((double) 0);
				}
			}
		}).build(loader);
	}
	
	
	/**
	 * Method to add Transactions
	 * @param request
	 */
	public void addTransaction(ServiceRequest request) {
		mp.put(request.getTimestamp(), request.getAmount());
		cache.put(request.getTimestamp(), request.getAmount());
		getSum(request);
		getMax(request);
		getMin(request);
		getCount(request);
		StaticDB.setAvg(StaticDB.getSum() / StaticDB.getCount());
	}

	/**
	 * @param request
	 */
	public void getMin(ServiceRequest request) {
		if (StaticDB.getMin() == null) {
			StaticDB.setMin(request.getAmount().doubleValue());
		} else if (StaticDB.getMin() > request.getAmount().doubleValue()) {
			StaticDB.setMin(request.getAmount().doubleValue());
		}
	}

	/**
	 * @param request
	 */
	public void getMax(ServiceRequest request) {
		if (StaticDB.getMax() < request.getAmount().doubleValue()) {
			StaticDB.setMax(request.getAmount().doubleValue());
		}
	}
	
	
	

	/**
	 * @param request
	 */
	public void getSum(ServiceRequest request) {
		StaticDB.setSum(StaticDB.getSum() + request.getAmount().doubleValue());
	}

	public void getCount(ServiceRequest request) {
		StaticDB.setCount(StaticDB.getCount() + 1);
	}
	public ResponseService getStatistics() {
		ResponseService response = new ResponseService();
		cache.cleanUp();
		response.setSum(StaticDB.getSum());
		response.setAvg(StaticDB.getAvg());
		response.setCount(StaticDB.getCount());
		response.setMax(StaticDB.getMax());
		response.setMin(StaticDB.getMin()==null?(double)0:StaticDB.getMin());
		return response;
	}

}
