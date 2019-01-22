package org.scenic.orchestrator.it.config;

import org.scenic.orchestrator.Application;
import org.scenic.orchestrator.config.PropertyConfig;
import org.scenic.orchestrator.config.SwaggerConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.scenic.orchestrator.config.RestTemplateConfig;


/**
 * Created by Jose on 22/01/19.
 */
@Configuration
@ComponentScan(basePackages = "org.scenic.orchestrator",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value =
                {PropertyConfig.class, SwaggerConfig.class, Application.class, RestTemplateConfig.class})
        })
public class ITConfig {
}
