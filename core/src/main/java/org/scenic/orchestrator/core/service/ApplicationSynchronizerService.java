package org.scenic.orchestrator.core.service;

import org.scenic.orchestrator.core.ManagementContext;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.springframework.stereotype.Service;

/**
 * Contains the logic to orchestrate an application deployment.
 */
@Service
public class ApplicationSynchronizerService {

    private final ApplicationContextManagerService applicationContextManagerService;
    private final DeploymentOrchestrator deploymentOrchestrator;
    private final ManagementContext managementContext;

    public ApplicationSynchronizerService(ApplicationContextManagerService applicationContextManagerService,
                                          DeploymentOrchestrator deploymentOrchestrator, ManagementContext  managementContext) {
        this.applicationContextManagerService = applicationContextManagerService;
        this.deploymentOrchestrator=deploymentOrchestrator;
        this.managementContext=managementContext;
    }

    public void syncApplication(String applicationTopology, String appId)
            throws InterruptedException {
        RunningAppContext runningAppContext = applicationContextManagerService.sycnApplication(applicationTopology, appId);
        managementContext.addRunningAppContext(runningAppContext);
    }




}
