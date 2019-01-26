package org.scenic.orchestrator.core.deployer;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.brooklyn.rest.api.ApplicationApi;
import org.apache.brooklyn.rest.client.BrooklynApi;
import org.apache.brooklyn.util.collections.MutableMap;
import org.apache.http.HttpStatus;
import org.scenic.orchestrator.core.deployer.dto.CustomApplicationEntities;
import org.scenic.orchestrator.core.deployer.dto.EffectorTemplate;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.scenic.orchestrator.core.exception.DeploymentException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Wraps Brooklyn API.
 */
@Component
public class DeployerProxy {

    private static final String EFFECTOR_ERROR_MESSAGE = "*ERROR: processing effector %s for %s(%s)";


    private static final String START_EFFECTOR_NAME = "start";
    private static final String STOP_EFFECTOR_NAME = "stop";
    private static final String STOP_MACHINE_MODE = "stopMachineMode";
    private static final String STOP_MACHINE_IF_NOT_STOPPED = "IF_NOT_STOPPED";

    private final BrooklynApi brooklynApi;
    private final ApplicationApi applicationApi;

    private final RestBrooklynClient restBrooklynClient;
    private final ObjectMapper objectMapper;

    public DeployerProxy(BrooklynApi brooklynApi,
                         RestBrooklynClient restBrooklynClient,
                         ObjectMapper objectMapper) {

        this.brooklynApi = brooklynApi;
        this.applicationApi = brooklynApi.getApplicationApi();
        this.restBrooklynClient = restBrooklynClient;
        this.objectMapper = objectMapper;
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
        return new CustomApplicationEntities(restBrooklynClient.getDescendant(applicationId));
    }

    public void startEffector(RunningAppContext app, String entityId) {
        EffectorTemplate effectorTemplate = new EffectorTemplate(app, entityId, START_EFFECTOR_NAME);
        executeEffector(effectorTemplate);
    }

    public void stopEffector(RunningAppContext app, String entityId) {
        EffectorTemplate effectorTemplate =
                new EffectorTemplate(app, entityId, STOP_EFFECTOR_NAME, getStopBody());
        executeEffector(effectorTemplate);
    }

    private void executeEffector(EffectorTemplate effectorTemplate) {
        try {
            restBrooklynClient.executeEffector(effectorTemplate);
        } catch (HttpServerErrorException e) {
            System.out.println(String.format(EFFECTOR_ERROR_MESSAGE,
                    effectorTemplate.getEffectorName().toUpperCase(),
                    effectorTemplate.getEntityName(),
                    effectorTemplate.getEntityId()));
            throw e;
        }
    }

    public String getStopBody() {
        try {
            return objectMapper.writeValueAsString(MutableMap.of(STOP_MACHINE_MODE, STOP_MACHINE_IF_NOT_STOPPED));
        } catch (Exception e) {
            throw new RuntimeException("STOP params can not be generated", e);
        }

    }
}
