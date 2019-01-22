package org.scenic.orchestrator;

import org.apache.brooklyn.rest.client.BrooklynApi;
import org.junit.runner.RunWith;
import org.scenic.orchestrator.config.ITConfig;
import org.scenic.orchestrator.config.PropertyConfig;
import org.scenic.orchestrator.config.SwaggerConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Jose on 21/01/19.
 */
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application-it.properties")
@SpringBootTest(classes = ITConfig.class)
@AutoConfigureWireMock(port = 0)
public abstract class BaseIntegrationTest {

    @MockBean
    public BrooklynApi brooklynApi;

}
