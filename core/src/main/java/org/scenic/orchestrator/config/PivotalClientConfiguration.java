package org.scenic.orchestrator.config;

import org.scenic.orchestrator.core.pivotal.PivotalClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Jose on 10/12/19.
 */
@Configuration
@PropertySource("classpath:pivotal.properties")
public class PivotalClientConfiguration {

    @Value("${pivotalUser}")
    private String pivotalUser;

    @Value("${pivotalPass}")
    private String pivotalPass;

    @Bean
    public PivotalClient pivotalClient() {
        return new PivotalClient(pivotalUser, pivotalPass);
    }
}
