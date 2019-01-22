package org.scenic.orchestrator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

/**
 * Created by Jose on 22/01/19.
 */
@Configuration
public class YamlConfig {

    @Bean
    public Yaml yaml() {
        return new Yaml();
    }


}
