package org.scenic.orchestrator.core.service;

import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.springframework.stereotype.Service;

/**
 * Contains the logic to orchestrate an application deployment.
 */
@Service
public class DeployerService {

    private final ApplicationContextManagerService applicationContextManagerService;

    public DeployerService(ApplicationContextManagerService applicationContextManagerService) {
        this.applicationContextManagerService = applicationContextManagerService;
    }

    public void deploy(String applicationTopology) {
        RunningAppContext runningAppContext = applicationContextManagerService.postApplicationContext(applicationTopology);


    }


}
