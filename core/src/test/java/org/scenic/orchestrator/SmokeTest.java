package org.scenic.orchestrator;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.apache.brooklyn.rest.api.VersionApi;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.scenic.orchestrator.webapp.CustomVersionsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jose on 21/01/19.
 */
public class SmokeTest extends BaseIntegrationTest {

    private static final String VERSION = "FINAL VERSION";

    @Mock
    private VersionApi versionApi;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CustomVersionsController customVersionsController;

    @Before
    public void setUp() {
        when(brooklynApi.getVersionApi()).thenReturn(versionApi);
        when(versionApi.getVersion()).thenReturn(VERSION);
    }

    //@Test
    public void test() {
        assertThat(customVersionsController.getVersion()).isEqualTo(VERSION);
    }

    @Test
    public void testWM() {
        // Stubbing WireMock
        stubFor(get(urlEqualTo("/resource"))
                .willReturn(aResponse().withHeader("Content-Type", "text/plain")
                        .withBody("Hello World!")));

        ResponseEntity<String> value = restTemplate.getForEntity("/resource", String.class);
        assertThat(value.getBody()).isEqualTo("Hello World!");
    }
}
