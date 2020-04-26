package com.micro.currencyexchangeservice;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeValueRepositry extends JpaRepository<ExchangeValue, Long>{
	ExchangeValue findByFromAndTo(String from,String to);

}
