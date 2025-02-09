package com.micro.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurreencyConversionController {
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertuCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		Map<String, String> urlVariables = new HashMap<>();
		urlVariables.put("from", from);
		urlVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> response = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				urlVariables);
		CurrencyConversionBean conversionBean = response.getBody();
		logger.info("{}", conversionBean);
		return new CurrencyConversionBean(conversionBean.getId(), from, to, conversionBean.getConversionMultiple(),
				quantity, quantity.multiply(conversionBean.getConversionMultiple()), conversionBean.getPort());
	}

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertuCurrencyfeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		CurrencyConversionBean conversionBean = proxy.retriveExchnageValue(from, to);
		logger.info("{}", conversionBean);
		return new CurrencyConversionBean(conversionBean.getId(), from, to, conversionBean.getConversionMultiple(),
				quantity, quantity.multiply(conversionBean.getConversionMultiple()), conversionBean.getPort());
	}
}
