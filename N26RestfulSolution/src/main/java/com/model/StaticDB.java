package com.model;

import java.math.BigDecimal;

public class StaticDB {

	private static Double sum=0.0;
	private static Double avg=0.0;
	private static Double max=0.0;
	private static Double min=null;
	private static Double count=0.0;
	public static Double getSum() {
		return sum;
	}
	public static void setSum(Double sum) {
		StaticDB.sum = sum;
	}
	public static Double getAvg() {
		return avg;
	}
	public static void setAvg(Double avg) {
		StaticDB.avg = avg;
	}
	public static Double getMax() {
		return max;
	}
	public static void setMax(Double max) {
		StaticDB.max = max;
	}
	public static Double getMin() {
		return min;
	}
	public static void setMin(Double min) {
		StaticDB.min = min;
	}
	public static Double getCount() {
		return count;
	}
	public static void setCount(Double count) {
		StaticDB.count = count;
	}

	
}
