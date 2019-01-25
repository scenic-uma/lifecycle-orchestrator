package org.scenic.orchestrator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jose on 22/01/19.
 */
@Configuration
public class RestTemplateConfig {

    @Value("${manager.analyzer.url}")
    private String manaerUrl;

    @Value("${brooklyn.url}")
    private String deployerUrl;

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder().rootUri(manaerUrl).build();
    }

    @Bean
    public RestTemplate brooklynRestTemplate(){
        return new RestTemplateBuilder().rootUri(deployerUrl).build();
    }






}
