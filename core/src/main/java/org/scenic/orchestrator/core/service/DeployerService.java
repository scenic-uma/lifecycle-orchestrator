package org.scenic.orchestrator.core.service;

import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.springframework.stereotype.Service;

/**
 * Contains the logic to orchestrate an application deployment.
 */
@Service
public class DeployerService {

    private final ApplicationContextManagerService applicationContextManagerService;
    private final DeploymentOrchestrator deploymentOrchestrator;

    public DeployerService(ApplicationContextManagerService applicationContextManagerService,
                           DeploymentOrchestrator deploymentOrchestrator) {
        this.applicationContextManagerService = applicationContextManagerService;
        this.deploymentOrchestrator=deploymentOrchestrator;
    }

    public void deploy(String applicationTopology)
            throws InterruptedException {
        RunningAppContext runningAppContext = applicationContextManagerService.postApplicationContext(applicationTopology);
        deploymentOrchestrator.deploy(runningAppContext);
        System.out.println("FINNNN");

    }


}
