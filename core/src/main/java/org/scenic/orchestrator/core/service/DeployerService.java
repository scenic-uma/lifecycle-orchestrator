package org.scenic.orchestrator.core.service;

import java.util.Map;

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

    public DeployerService(ManagerAnalyzerClient managerAnalyzerClient, Yaml yaml) {
        this.managerAnalyzerClient = managerAnalyzerClient;
        this.yaml = yaml;
    }

    public void deploy(String application) {

        //TODO: instead of use snakeyaml it will be necessary to uses Alien4Cloud transformer to
        //TODO manage TOSCA object domains

        managerAnalyzerClient.deployApplication(application);
        //need to find the topology components
        Plan plan = managerAnalyzerClient.getPlan(getApplicationName(application));

        System.out.println(plan);
    }

    private String getApplicationName(String app){
        final Map<String, Object> obj = yaml.load(app);

        return obj.get("template_name").toString();
    }


}
