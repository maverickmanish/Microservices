package com.example.demo;

import java.math.BigDecimal;



public class CurrencyExchange {
	private Long id;
	private String from;
	private String to;
	private BigDecimal qty;
	private BigDecimal conversionMultiple;
	//private BigDecimal totalCalculatedAmount;
	private String environment;

	public CurrencyExchange() {
		
	}
	
	public CurrencyExchange(Long id, String from, String to, BigDecimal qty, BigDecimal conversionMultiple, String environment) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
		this.qty = qty;
		this.environment = environment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}

	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	/*
	 * public BigDecimal getTotalCalculatedAmount() { return totalCalculatedAmount;
	 * }
	 * 
	 * public void setTotalCalculatedAmount(BigDecimal totalCalculatedAmount) {
	 * this.totalCalculatedAmount = totalCalculatedAmount; }
	 */

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	@Override
	public String toString() {
		return "CurrencyExchange [id=" + id + ", from=" + from + ", to=" + to + ", qty=" + qty + ", conversionMultiple="
				+ conversionMultiple + ", environment=" + environment + "]";
	}
	
	
	

}
