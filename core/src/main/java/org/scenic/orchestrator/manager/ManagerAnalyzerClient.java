package org.scenic.orchestrator.manager;

import org.scenic.orchestrator.core.dto.ApplicationStatus;
import org.scenic.orchestrator.core.dto.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jose on 22/01/19.
 */
public class ManagerAnalyzerClient {

    protected static final String BASE = "/mm";
    protected static final String APPLICATION_RESOURCE = BASE + "/%s";
    private static final String GET_PARALLEL_PLAN_TEMPLATE = APPLICATION_RESOURCE + "/psteps";

    private final RestTemplate restTemplate;

    @Autowired
    public ManagerAnalyzerClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void deployApplication(String application) {
        restTemplate.postForEntity(BASE, application, Void.class);
    }

    public Plan getPlan(String applicationName) {
        return restTemplate.getForEntity(String.format(getPlanResource(), applicationName), Plan.class).getBody();
    }

    protected String getPlanResource() {
        return GET_PARALLEL_PLAN_TEMPLATE;
    }

    public void putStatus(String applicationName, ApplicationStatus applicationStatus) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<ApplicationStatus> entity = new HttpEntity<>(applicationStatus, headers);

        putStatusWithRetries(applicationName, entity);
    }

    private void putStatusWithRetries(String applicationName, HttpEntity<ApplicationStatus> entity) {
        int i = 0;
        boolean pushed = false;
        while ((i <= 20) && (!pushed)) {
            try {
                i++;
                restTemplate.put(String.format(APPLICATION_RESOURCE, applicationName), entity);
                pushed = true;
            } catch (Exception e) {
                if (i == 20) {
                    throw e;
                }
            }
        }//while
    }


}
