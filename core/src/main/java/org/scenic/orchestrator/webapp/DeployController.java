package org.scenic.orchestrator.webapp;

import org.scenic.orchestrator.core.service.ApplicationSynchronizerService;
import org.scenic.orchestrator.core.service.DeployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints to orchestrate the application deployment.
 */
@RestController
@RequestMapping()
public class DeployController {

    private final DeployerService deployerService;
    private final ApplicationSynchronizerService applicationSynchronizerService;

    @Autowired
    public DeployController(DeployerService deployerService,  ApplicationSynchronizerService applicationSynchronizerService) {
        this.deployerService = deployerService;
        this.applicationSynchronizerService = applicationSynchronizerService;
    }

    @PostMapping("/deploy")
    public void deployApp(@RequestBody String topology) throws InterruptedException {
        deployerService.deploy(topology);
    }

    @PutMapping("/sync/{appId}")
    public void syncApp(@PathVariable String appId, @RequestBody String topology) throws InterruptedException {
        System.out.println("--Sync appId=" + appId);
        applicationSynchronizerService.syncApplication(topology, appId);
    }
}
