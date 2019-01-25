package org.scenic.orchestrator.core.deployer;

import org.scenic.orchestrator.core.deployer.dto.CustomEntity;
import org.scenic.orchestrator.core.deployer.dto.EffectorTemplate;
import org.scenic.orchestrator.core.dto.ApplicationStatus;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.common.collect.ImmutableList;

/**
 * Created by Jose on 24/01/19.
 */
@Service
public class CustomBrooklynClient {

    private static final String APP_DESCENDANT = "/v1/applications/%s/entities";

    private final RestTemplate brooklynRestTemplate;

    public CustomBrooklynClient(RestTemplate brooklynRestTemplate) {
        this.brooklynRestTemplate = brooklynRestTemplate;
    }

    public CustomEntity[] getDescendant(String applicationId) {
        return brooklynRestTemplate.getForEntity(String.format(APP_DESCENDANT, applicationId), CustomEntity[].class).getBody();
    }

    public void executeEffector(EffectorTemplate effectorTemplate){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));

        HttpEntity<ApplicationStatus> entity = new HttpEntity<>(headers);

        brooklynRestTemplate.postForEntity(effectorTemplate.composeUri(), entity, Void.class);
    }


}
