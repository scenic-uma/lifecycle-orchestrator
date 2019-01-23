package org.scenic.orchestrator.core.service;

import java.util.Map;

import org.scenic.orchestrator.core.dto.ApplicationStatus;
import org.scenic.orchestrator.core.dto.InitialAppStatusService;
import org.scenic.orchestrator.core.dto.Plan;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

/**
 * Create application in MM and create a application context which contains the application name,
 * the current plan and the application status.
 */
@Service
public class ApplicationContextManagerService {


    private final ManagerAnalyzerClient managerAnalyzerClient;

    private final Yaml yaml;

    private final InitialAppStatusService initialAppStatusService;

    public ApplicationContextManagerService(ManagerAnalyzerClient managerAnalyzerClient, Yaml yaml, InitialAppStatusService initialAppStatusService) {
        this.managerAnalyzerClient = managerAnalyzerClient;
        this.yaml = yaml;
        this.initialAppStatusService = initialAppStatusService;
    }

    public RunningAppContext postApplicationContext(String applicationTopology) {
        final String applicationName = getApplicationName(applicationTopology);
        managerAnalyzerClient.deployApplication(applicationTopology);
        managerAnalyzerClient.putStatus(applicationName, initialAppStatusService.build(applicationTopology));
        final Plan plan = managerAnalyzerClient.getPlan(getApplicationName(applicationTopology));
        final ApplicationStatus status = initialAppStatusService.build(plan.getEntities());

        return new RunningAppContext(applicationName, status, plan);
    }

    private String getApplicationName(String applicationTopology) {
        final Map<String, Object> obj = yaml.load(applicationTopology);
        return obj.get("template_name").toString();
    }
}
