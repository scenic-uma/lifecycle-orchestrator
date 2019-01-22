package org.scenic.orchestrator.core.service;

import org.scenic.orchestrator.core.dto.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jose on 22/01/19.
 */
@Service
public class ManagerAnalyzerClient {

    private static final String BASE = "/mm";
    private static final String GET_PLAN_TEMPLATE = BASE + "/%s/plan";

    private final RestTemplate restTemplate;


    @Autowired
    public ManagerAnalyzerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void deployApplication(String application) {
        restTemplate.postForEntity(BASE, application, Void.class);
    }

    public Plan getPlan(String applicationName) {
        return restTemplate.getForEntity(String.format(GET_PLAN_TEMPLATE, applicationName), Plan.class).getBody();
    }


}
