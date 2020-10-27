package org.scenic.orchestrator.it;

//import org.apache.commons.

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.time.Instant.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.brooklyn.rest.api.VersionApi;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.scenic.orchestrator.core.dto.ApplicationStatus;
import org.scenic.orchestrator.core.dto.EntityStatus;
import org.scenic.orchestrator.manager.ManagerAnalyzerClient;
import org.scenic.orchestrator.webapp.CustomVersionsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.ImmutableMap;

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

    @Autowired
    private ManagerAnalyzerClient mmClient;

    @Before
    public void setUp() {
        when(brooklynApi.getVersionApi()).thenReturn(versionApi);
        when(versionApi.getVersion()).thenReturn(VERSION);
    }

    @Test
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

    @Test
    public void t() {

        //org.apache.commons.math3.stat.descriptive.moment.
        StandardDeviation math = new StandardDeviation();
        Map<String, EntityStatus> current = ImmutableMap.<String, EntityStatus>builder()
                .put("SoftcareDB", EntityStatus.UNAVAILABLE)
                .put("SoftcareWS", EntityStatus.FAILED)
                //.put("Forum", EntityStatus.STARTED)
                //.put("ForumDB", EntityStatus.FAILED)
                .put("Softcare_dashboard", EntityStatus.FAILED)
                .put("Multimedia", EntityStatus.FAILED)
                .put("MultimediaDB", EntityStatus.STARTED)
                .build();


        Map<String, EntityStatus> target = ImmutableMap.<String, EntityStatus>builder()
                .put("SoftcareDB", EntityStatus.STARTED)
                .put("SoftcareWS", EntityStatus.STARTED)
                //.put("Forum", EntityStatus.STARTED)
                //.put("ForumDB", EntityStatus.STARTED)
                .put("Softcare_dashboard", EntityStatus.STARTED)
                .put("Multimedia", EntityStatus.STARTED)
                .put("MultimediaDB", EntityStatus.STARTED)
                .build();


        String name = "SoftcareApp";
        ApplicationStatus status = new ApplicationStatus(current, target);
        mmClient.putStatus(name, status);
        List<Double> latencies = new ArrayList<>();


        for (int i = 0; i < 20; i++) {
            Instant before = now();
            mmClient.getPlan(name);
            Instant after = now();
            Double latency = (double) (after.getEpochSecond() - before.getEpochSecond());
            latencies.add(latency);
            System.out.println(latency);
        }

        double[] j = latencies.stream()
                .mapToDouble(k -> k)
                .toArray();

        double av = latencies.stream()
                .mapToDouble(k -> k)
                .average().getAsDouble();
        System.out.println("Std. Desv.: " + math.evaluate(j));
        System.out.println("Av: " + av);
    }
}
