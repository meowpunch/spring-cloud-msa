package com.meowailand.bootiful.config;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * test LoadBalancer with RestTemplate
 */
@Configuration
public class HttpClient {

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpRequestFactory.setConnectTimeout(1000);
		httpRequestFactory.setReadTimeout(1000);
		org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(200)
				.setMaxConnPerRoute(20)
				.build();
		httpRequestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
		return restTemplate;
	}
}
