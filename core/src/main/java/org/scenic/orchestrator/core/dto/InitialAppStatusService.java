package org.scenic.orchestrator.core.dto;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

/**
 * Retrieves an ApplicationStatus from an list of entity names. (It should be refactored to use two status).
 */
@Service
public class InitialAppStatusService {

    private static final String TOPOLOGY_TEMPLATE = "topology_template";
    private static final String NODE_TEMPLATES = "node_templates";

    private final Yaml yaml;

    public InitialAppStatusService(Yaml yaml) {
        this.yaml = yaml;
    }

    public ApplicationStatus build(String appTopology) {
        return build(getNodeTemplateNames(appTopology));
    }

    public ApplicationStatus build(List<String> entityNames) {
        Map<String, EntityStatus> current = new HashMap<>();
        Map<String, EntityStatus> target = new HashMap<>();
        for (String entityName : entityNames) {
            current.put(entityName, EntityStatus.UNAVAILABLE);
            target.put(entityName, EntityStatus.STARTED);
        }

        return new ApplicationStatus(current, target);
    }

    private List<String> getNodeTemplateNames(String app) {
        final Map<String, Object> obj = yaml.load(app);
        return new LinkedList<>(((Map<String, Object>) ((Map<String, Object>) obj.get(TOPOLOGY_TEMPLATE)).get(NODE_TEMPLATES)).keySet());
    }

}
