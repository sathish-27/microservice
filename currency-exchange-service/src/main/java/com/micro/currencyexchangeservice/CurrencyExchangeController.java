package com.micro.currencyexchangeservice;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {
	@Autowired
	private Environment environment;

	@Autowired
	private ExchangeValueRepositry repositry;
private Logger logger=LoggerFactory.getLogger(this.getClass());
	@GetMapping("currency-exchange/from/{from}/to/{to}")
	public ExchangeValue retriveExchnageValue(@PathVariable String from, @PathVariable String to) {
		ExchangeValue exchangeValue = repositry.findByFromAndTo(from, to);
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		logger.info("{}",exchangeValue);
		return exchangeValue;
	}

}
