package org.scenic.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@PropertySource("classpath:application.properties")
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Application {

    /**
     * @Override protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
     * return application.sources(Application.class);
     * }
     */

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
