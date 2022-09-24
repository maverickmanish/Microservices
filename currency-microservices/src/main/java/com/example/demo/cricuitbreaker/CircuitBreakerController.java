package com.example.demo.cricuitbreaker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/cb")
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

	@GetMapping("/sample-api")
	 @Retry(name="sample-api",fallbackMethod = "hardCodedresponse")
	// to test it's effect we need to fire up 200 request simultaneously or successively
	// watch curl http:/................. it'll fire one request every 2 sec
	// watch -n 0.1 curl http:/................. it'll fire 10 request every 1 sec
	//@CicuitBreaker(name = "sample-api", fallbackMethod = "hardCodedresponse")
	// Rate Limiter specifies no. of calls per no. of seconds to the below mapping
	// @RateLimiter(name="default")
	public String sampleApi() {
		logger.info("sample api call received ! ");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/somedummy",
				String.class);
		return forEntity.getBody();
	}

	@GetMapping("/sample-app")
	public String sampleApp() {
		return "it's working";
	}

	public String hardCodedresponse(Exception ex) {
		return "fallback-response";
	}
}
