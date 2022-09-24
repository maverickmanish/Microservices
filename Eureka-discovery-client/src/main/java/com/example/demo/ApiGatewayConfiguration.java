package com.example.demo;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route.AsyncBuilder;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder)
	{
		Function<PredicateSpec, AsyncBuilder> routeFunction
		=p-> p.path("/get")
		.filters(f->f.addRequestHeader("MyHeader", "MyURI")
				.addRequestParameter("param", "MyValue"))
		.uri("http://httpbin.org:80");
		
		Function<PredicateSpec, AsyncBuilder> routeCurrencyConversionService
		=p-> p.path("/currency-conversion-feign/**")
		.uri("lb://currency-conversion");
		
		Function<PredicateSpec, AsyncBuilder> routeCurrencyExchangeService
		=p-> p.path("/currency-conversiondynamic/**")
		.uri("lb://currency-exchange-service");
		
		
		
		return builder.routes().route(routeFunction)
				.route(routeCurrencyConversionService)
				.route(routeCurrencyExchangeService)
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath(
								"/currency-conversion-new/(?<segment>.*)", 
								"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))
				.build();
	}
}
