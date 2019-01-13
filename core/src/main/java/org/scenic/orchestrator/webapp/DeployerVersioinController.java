package org.scenic.orchestrator.webapp;

import org.apache.brooklyn.rest.client.BrooklynApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides information about the version of the current deployer.
 */
@RestController
@RequestMapping("/versions")
public class DeployerVersioinController {

    private final BrooklynApi brooklynApi;

    @Autowired
    public DeployerVersioinController(final BrooklynApi brooklynApi) {
        this.brooklynApi = brooklynApi;
    }

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String version() {
        return "Greetings from Spring Boot!" + brooklynApi.getVersionApi().getVersion();
    }

}
