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
public class CustomVersionsController {

    private final BrooklynApi brooklynApi;

    @Autowired
    public CustomVersionsController(final BrooklynApi brooklynApi) {
        this.brooklynApi = brooklynApi;
    }

    //TODO: instead of use snakeyaml it will be necessary to uses Alien4Cloud transformer to
    //TODO manage TOSCA object domain
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String getVersion() {
        return brooklynApi.getVersionApi().getVersion();
    }

}
