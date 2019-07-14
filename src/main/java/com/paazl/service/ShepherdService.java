package com.paazl.service;

import com.paazl.configuration.ServerConfigurationProperties;
import com.paazl.model.SheepOrderDto;
import com.paazl.model.SheepStatusesDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;

@Service
public class ShepherdService {
    private final RestTemplate restTemplate;
    private final ServerConfigurationProperties serverProperties;

    public ShepherdService(RestTemplate restTemplate, ServerConfigurationProperties serverProperties) {
        this.restTemplate = restTemplate;
        this.serverProperties = serverProperties;
    }

    public SheepStatusesDto getSheepStatuses() {
        String url = serverProperties.getHost().concat("rest/sheeps/statistics");
        return restTemplate.getForObject(url, SheepStatusesDto.class);
    }

    public BigInteger getCurrentBalance() {
        String url = serverProperties.getHost().concat("rest/shepherdmanager/balance");
        return restTemplate.getForObject(url, BigInteger.class);
    }

    public SheepOrderDto orderSheeps(int numberOfSheeps) {
        String url = serverProperties.getHost().concat("rest/sheeps/order");
        ResponseEntity<SheepOrderDto> response;

        try {
            response = restTemplate.exchange(url, HttpMethod.POST,
                    new HttpEntity<>(numberOfSheeps), SheepOrderDto.class);
        } catch (HttpServerErrorException e) {
            throw new RuntimeException("Order is not successful: " + e.getResponseBodyAsString());
        }

        return response.getBody();
    }
}
