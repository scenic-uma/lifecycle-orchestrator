package org.scenic.orchestrator.core.deployer;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.brooklyn.rest.api.ApplicationApi;
import org.apache.brooklyn.rest.client.BrooklynApi;
import org.apache.http.HttpStatus;
import org.scenic.orchestrator.core.deployer.dto.CustomApplicationEntities;
import org.scenic.orchestrator.core.deployer.dto.EffectorTemplate;
import org.scenic.orchestrator.core.exception.DeploymentException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

/**
 * Wraps Brooklyn API.
 */
@Component
public class DeployerProxy {

    private static final String START_EFFECTOR_NAME = "start";

    private final BrooklynApi brooklynApi;
    private final ApplicationApi applicationApi;

    private final CustomBrooklynClient customBrooklynClient;

    public DeployerProxy(BrooklynApi brooklynApi, CustomBrooklynClient customBrooklynClient) {

        this.brooklynApi = brooklynApi;
        this.applicationApi = brooklynApi.getApplicationApi();
        this.customBrooklynClient = customBrooklynClient;
    }

    public String deployApp(String appName, String blueprint) {
        Response a = applicationApi.createFromYaml(blueprint);
        if (a.getStatus() != HttpStatus.SC_CREATED) {
            throw new DeploymentException("Error during the deployment of " + appName);
        }
        return getApplicationId(a);
    }

    private String getApplicationId(Response a) {
        String[] slices = a.getMetadata().get(HttpHeaders.LOCATION).toArray()[0].toString().split("/");
        return slices[slices.length - 1];
    }

    public CustomApplicationEntities getApplicationEntities(String applicationId) {
        return new CustomApplicationEntities(customBrooklynClient.getDescendant(applicationId));
    }

    public void startEffector(String appId, String entityId) {
        EffectorTemplate effectorTemplate = new EffectorTemplate(appId, entityId, START_EFFECTOR_NAME);
        try {
            customBrooklynClient.executeEffector(effectorTemplate);
        } catch (HttpServerErrorException e) {
            System.out.println("Error processing effector START for " + appId + " - " + entityId);
            throw e;
        }
    }
}
