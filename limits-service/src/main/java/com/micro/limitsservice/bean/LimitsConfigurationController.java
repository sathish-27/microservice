package com.micro.limitsservice.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.micro.limitsservice.Configuration;
import com.micro.limitsservice.LimitConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigurationController {

	@Autowired
	private Configuration configuration;

	@GetMapping("/limits")
	public LimitConfiguration retriveLimitsFromConfigurations() {
		return new LimitConfiguration(configuration.getMaximum(), configuration.getMinimum());
	}
	
	@GetMapping("/fault-tolerance-example")
	@HystrixCommand(fallbackMethod = "fallbackretriveConfiguration")
	public LimitConfiguration retriveConfiguration() {
		 throw new RuntimeException("NOT Avalilable");
	}
	
	public LimitConfiguration  fallbackretriveConfiguration() {
		return new LimitConfiguration(99,9);
	}
}
