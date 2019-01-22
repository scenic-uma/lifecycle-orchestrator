package org.scenic.orchestrator.webapp;

import org.scenic.orchestrator.core.service.DeployerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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

    @Autowired
    public DeployController(DeployerService deployerService) {
        this.deployerService = deployerService;
    }


    @PostMapping("/deploy")
    public void deployApp(@RequestBody String app) {
        deployerService.deploy(app);
    }


}
