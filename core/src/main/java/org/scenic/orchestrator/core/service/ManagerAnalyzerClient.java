package org.scenic.orchestrator.core.service;

import java.util.Map;

import org.scenic.orchestrator.core.dto.ApplicationStatus;
import org.scenic.orchestrator.core.dto.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Jose on 22/01/19.
 */
@Service
public class ManagerAnalyzerClient {

    private static final String BASE = "/mm";
    private static final String APPLICATION_RESOURCE = BASE + "/%s";
    private static final String GET_PLAN_TEMPLATE = APPLICATION_RESOURCE + "/plan";

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;


    @Autowired
    public ManagerAnalyzerClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public void deployApplication(String application) {
        restTemplate.postForEntity(BASE, application, Void.class);
    }

    public Plan getPlan(String applicationName) {
        return restTemplate.getForEntity(String.format(GET_PLAN_TEMPLATE, applicationName), Plan.class).getBody();
    }

    public void putStatus(String applicationName, ApplicationStatus applicationStatus) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<ApplicationStatus> entity = new HttpEntity<>(applicationStatus,headers);

        restTemplate.put(String.format(APPLICATION_RESOURCE, applicationName), entity);
    }


}
