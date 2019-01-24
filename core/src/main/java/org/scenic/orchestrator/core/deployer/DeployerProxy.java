package org.scenic.orchestrator.core.deployer;

import javax.ws.rs.core.Response;

import org.apache.brooklyn.rest.api.ApplicationApi;
import org.apache.brooklyn.rest.client.BrooklynApi;
import org.apache.http.HttpStatus;
import org.scenic.orchestrator.core.exception.DeploymentException;
import org.springframework.stereotype.Component;

/**
 * Wraps Brooklyn API.
 */
@Component
public class DeployerProxy {

    private final BrooklynApi brooklynApi;
    private final ApplicationApi applicationApi;

    public DeployerProxy(BrooklynApi brooklynApi) {
        this.brooklynApi = brooklynApi;
        this.applicationApi = brooklynApi.getApplicationApi();
    }

    public void deployApp(String appName, String blueprint) {
        Response a = applicationApi.createFromYaml(blueprint);
        if (a.getStatus() != HttpStatus.SC_CREATED) {
            throw new DeploymentException("Error during the deployment of " + appName);
        }

    }

}
