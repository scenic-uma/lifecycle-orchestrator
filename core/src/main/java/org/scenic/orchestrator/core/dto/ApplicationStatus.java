package org.scenic.orchestrator.core.dto;

import java.util.Map;

/**
 * Created by Jose on 23/01/19.
 */
public class ApplicationStatus {

    private Map<String, EntityStatus> current;
    private Map<String, EntityStatus> target;

    public ApplicationStatus(Map<String, EntityStatus> current, Map<String, EntityStatus> target) {
        this.current = current;
        this.target = target;
    }

    public void setCurrentEntityStatus(String entity, EntityStatus currentStatus) {
        current.put(entity, currentStatus);
    }


    public Map<String, EntityStatus> getTarget() {
        return target;
    }

    public void setTarget(Map<String, EntityStatus> target) {
        this.target = target;
    }

    public Map<String, EntityStatus> getCurrent() {
        return current;
    }

    public void setCurrent(Map<String, EntityStatus> current) {
        this.current = current;
    }
}
