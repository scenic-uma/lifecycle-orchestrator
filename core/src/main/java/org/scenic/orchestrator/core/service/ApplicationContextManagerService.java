package org.scenic.orchestrator.core.service;

import java.util.Map;

import org.scenic.orchestrator.core.dto.ApplicationStatus;
import org.scenic.orchestrator.core.dto.InitialAppStatusService;
import org.scenic.orchestrator.core.dto.Plan;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.scenic.orchestrator.core.modifier.TopologyModifierService;
import org.scenic.orchestrator.manager.ManagerAnalyzerClient;
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

    private final TopologyModifierService topologyModifierService;

    public ApplicationContextManagerService(ManagerAnalyzerClient managerAnalyzerClient, Yaml yaml,
                                            InitialAppStatusService initialAppStatusService,
                                            TopologyModifierService topologyModifierService) {
        this.managerAnalyzerClient = managerAnalyzerClient;
        this.yaml = yaml;
        this.initialAppStatusService = initialAppStatusService;
        this.topologyModifierService = topologyModifierService;
    }

    //Crea RunningApplication Context
    public RunningAppContext postApplicationContext(String applicationTopology) {

        final String applicationName = getApplicationName(applicationTopology);

        //manda la app a analyzer
        managerAnalyzerClient.deployApplication(applicationTopology);

        //actualiza el estado (source) unavailable to (target) started
        managerAnalyzerClient.putStatus(applicationName, initialAppStatusService.build(applicationTopology));

        //coge el plan
        final Plan plan = managerAnalyzerClient.getPlan(getApplicationName(applicationTopology));

        //Application status es el estado actual de la app
        final ApplicationStatus status = initialAppStatusService.build(plan.getEntities());

        //Genera un RunningAppContext y lo (guardando la topologia con los midificadores necesarios)
        return new RunningAppContext(applicationName, status, plan, topologyModifierService.apply(applicationTopology));
    }

    private String getApplicationName(String applicationTopology) {
        final Map<String, Object> obj = yaml.load(applicationTopology);
        return obj.get("template_name").toString();
    }
}
