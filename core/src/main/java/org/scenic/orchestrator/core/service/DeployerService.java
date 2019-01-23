package org.scenic.orchestrator.core.service;

import java.util.Map;

import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.scenic.orchestrator.core.dto.ApplicationStatus;
import org.scenic.orchestrator.core.dto.InitialAppStatusService;
import org.scenic.orchestrator.core.dto.Plan;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

/**
 * Contains the logic to orchestrate an application deployment.
 */
@Service
public class DeployerService {

    private final ManagerAnalyzerClient managerAnalyzerClient;

    private final Yaml yaml;

    private final InitialAppStatusService initialAppStatusService;

    public DeployerService(ManagerAnalyzerClient managerAnalyzerClient, Yaml yaml, InitialAppStatusService initialAppStatusService) {
        this.managerAnalyzerClient = managerAnalyzerClient;
        this.yaml = yaml;
        this.initialAppStatusService = initialAppStatusService;
    }

    public void deploy(String applicationTopology) {

        //TODO: instead of use snakeyaml it will be necessary to uses Alien4Cloud transformer to
        //TODO manage TOSCA object domains

        String applicationName = getApplicationName(applicationTopology);
        managerAnalyzerClient.deployApplication(applicationTopology);
        managerAnalyzerClient.putStatus(applicationName, initialAppStatusService.build(applicationTopology));
        Plan plan = managerAnalyzerClient.getPlan(getApplicationName(applicationTopology));
        ApplicationStatus status = initialAppStatusService.build(plan.getEntities());

        RunningAppContext runningAppContext= new RunningAppContext(applicationName, status, plan);
        System.out.println(runningAppContext);
    }

    private String getApplicationName(String applicationTopology){
        final Map<String, Object> obj = yaml.load(applicationTopology);
        return obj.get("template_name").toString();
    }




}
