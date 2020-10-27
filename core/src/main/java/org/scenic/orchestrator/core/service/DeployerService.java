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


    //Crea la app sin iniciar en brooklyn
//    runningAppContext.setAppId(appId);
//    System.out.println("Add to the deployer application: " + runningAppContext.getApplicationName() + " with id " + appId);
 //   runningAppContext.setEntities(deployerProxy.getApplicationEntities(appId));

    public void deploy(String applicationTopology)
            throws InterruptedException {
        RunningAppContext runningAppContext = applicationContextManagerService.postApplicationContext(applicationTopology);
        deploymentOrchestrator.deploy(runningAppContext);
    }




}
