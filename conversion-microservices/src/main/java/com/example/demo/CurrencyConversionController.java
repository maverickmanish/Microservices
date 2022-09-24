package com.example.demo;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyExchange2 calculateCurrencyConversion(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyExchange> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchangedynamic/from/{from}/to/{to}", CurrencyExchange.class,
				uriVariables);

		CurrencyExchange currencyExchange = responseEntity.getBody();
		currencyExchange.setQty(quantity);
		System.out.println(currencyExchange);
		return new CurrencyExchange2(currencyExchange.getId(), from, to, quantity,
				currencyExchange.getConversionMultiple(), quantity.multiply(currencyExchange.getConversionMultiple()),
				currencyExchange.getEnvironment() + " " + "rest template");

	}

	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyExchange2 calculateCurrencyConversionFeign(

			@PathVariable String from,

			@PathVariable String to,

			@PathVariable BigDecimal quantity) {

		CurrencyExchange currencyConversion = proxy.retrieveExchangeValue(from, to);

		return new CurrencyExchange2(currencyConversion.getId(), from, to, quantity,
				currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()),
				currencyConversion.getEnvironment() + " " + "feign");

	}

}
