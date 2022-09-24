package com.example.demo.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.CurrencyExchange;
import com.example.demo.repo.CurrencyExchangeRepository;

@RestController
/* @RequestMapping("/con") */
public class CurrencyController {

	@Autowired
	Environment environment;

	@Autowired
	CurrencyExchangeRepository currencyExchangeRepository;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CurrencyController.class);

	@GetMapping("/currency-exchangestatic/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
		CurrencyExchange currencyExchange = new CurrencyExchange(100l, from, to, BigDecimal.valueOf(79));
		String property = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(property);
		return currencyExchange;

	}

	@GetMapping("/currency-exchangedynamic/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValueDynamic(@PathVariable String from, @PathVariable String to) {
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
		if (currencyExchange == null)
			throw new RuntimeException("Unable to find the conversion for " + from + " to " + to);
		String property = environment.getProperty("local.server.port");
		LOGGER.info("retrive exchange values called with {} to {}",from,to);

		currencyExchange.setEnvironment(property);
		return currencyExchange;

	}

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{qty}")
	public CurrencyExchange conversion(@PathVariable String from, @PathVariable String to, @PathVariable Integer qty) {
		CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
		if (currencyExchange == null)
			throw new RuntimeException("Unable to find the conversion for " + from + " to " + to);
		String property = environment.getProperty("local.server.port");
		currencyExchange.setQty(qty);
		currencyExchange
				.setTotalCalculatedAmount(currencyExchange.getConversionMultiple().multiply(BigDecimal.valueOf(qty)));
		currencyExchange.setEnvironment(property);
		return currencyExchange;

	}

	@GetMapping("/currency-conversiondynamic/from/{from}/to/{to}/quantity/{qty}")
	public CurrencyExchange conversion2(@PathVariable String from, @PathVariable String to, @PathVariable Integer qty) {
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyExchange> currencyExchange = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchangedynamic/from/{from}/to/{to}", CurrencyExchange.class,
				uriVariables);
		CurrencyExchange body = currencyExchange.getBody();
		if (body == null)
			throw new RuntimeException("Unable to find the conversion for " + from + " to " + to);
		String property = environment.getProperty("local.server.port");

		body.setQty(qty);
		body.setTotalCalculatedAmount(body.getConversionMultiple().multiply(BigDecimal.valueOf(qty)));
		body.setEnvironment(property);
		LOGGER.info("retrive exchange values called with {} to {}",from,to);

		return body;

	}
	
	@GetMapping("/sample-app")
	public String sampleApp() {
		return "it's working";
	}
}
