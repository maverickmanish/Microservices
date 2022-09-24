package com.manish.microservices.run.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manish.microservices.run.configuration.Configuration;
import com.manish.microservices.run.model.Limits;

@RestController
public class LimitController {

	@Autowired
	private Configuration configuration;

	@GetMapping("/limits")
	public Limits retrieveLimits() {
		return new Limits(configuration.getMinimum(), configuration.getMaximum());
	}

}
