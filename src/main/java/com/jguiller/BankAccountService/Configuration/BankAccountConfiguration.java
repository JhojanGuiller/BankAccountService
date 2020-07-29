package com.jguiller.BankAccountService.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BankAccountConfiguration {
	
	@Bean
	public WebClient.Builder getWebClientBuilder(){

		return WebClient.builder();
		
	}

}
