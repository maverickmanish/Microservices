package com.manish.microservices.run.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
/*
 * gonna pull values from app.properties with below name 
 * : "limits-service" as suffix
 * 
 * #picking values from app.properties
	limits-service.minimum=11
	limits-service.maximum=11111
 */
@ConfigurationProperties("limits-service")
public class Configuration {
	private int minimum;
	private int maximum;

	protected Configuration() {
		// TODO Auto-generated constructor stub
	}

	public Configuration(int minimum, int maximum) {
		super();
		this.minimum = minimum;
		this.maximum = maximum;
	}

	public int getMinimum() {
		return minimum;
	}

	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}

	public int getMaximum() {
		return maximum;
	}

	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}

}
