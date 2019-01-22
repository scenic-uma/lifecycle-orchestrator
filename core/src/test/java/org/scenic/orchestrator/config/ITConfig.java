package org.scenic.orchestrator.config;

import org.scenic.orchestrator.Application;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * Created by Jose on 22/01/19.
 */
@Configuration
@ComponentScan(basePackages = "org.scenic.orchestrator",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value =
                {PropertyConfig.class, SwaggerConfig.class, Application.class})
        })
public class ITConfig {
}
