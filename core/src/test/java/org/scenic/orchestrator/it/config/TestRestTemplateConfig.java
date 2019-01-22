package org.scenic.orchestrator.it.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jose on 21/01/19.
 */
@Configuration
public class TestRestTemplateConfig {

    @Value("${wiremock.server.port}")
    private int port;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().rootUri("http://127.0.0.1:" + port).build();
    }

}
