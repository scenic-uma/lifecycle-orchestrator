package org.scenic.orchestrator.config;

import org.apache.brooklyn.rest.client.BrooklynApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Wraps Brooklyn API.
 */
@Configuration
public class BrooklynApiConfig {

    @Value("${brooklyn.url}")
    private String brooklynUrl;

    @Bean
    public BrooklynApi brooklynApi() {
        return new BrooklynApi(brooklynUrl);
    }

}
