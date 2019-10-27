package org.scenic.orchestrator.core.dto;

import org.scenic.orchestrator.core.deployer.dto.BrooklynEntityStatus;
import org.scenic.orchestrator.core.deployer.dto.CustomApplicationEntities;
import org.scenic.orchestrator.core.deployer.dto.CustomEntity;

/**
 * Created by Jose on 23/01/19.
 */
public class RunningAppContext {

    private ApplicationStatus status;

    private String applicationName;

    private Plan plan;

    private String applicationTopology;
    private String appId;
    private CustomApplicationEntities entities;


    public RunningAppContext(String applicationName, ApplicationStatus status, Plan plan, String applicationTopology) {
        this.status = status;
        this.applicationName = applicationName;
        this.plan = plan;
        this.applicationTopology = applicationTopology;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getApplicationTopology() {
        return applicationTopology;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setEntities(CustomApplicationEntities entities) {
        this.entities = entities;
    }

    public CustomEntity getEntityByDisplayName(String name) {
        return this.entities.getEntityByName(name);
    }

    public CustomEntity getEntityById(String id) {
        return this.entities.getEntityById(id);
    }

    public boolean isUp() {
        return entities.areUp();
    }

    public void updateStatus() {
        System.out.println("-- Update the status from brooklyn -- ");
        for (CustomEntity entity : entities.entities()) {
            EntityStatus entityStatus = mapEntityStatus(entity.status());
            status.setCurrentEntityStatus(entity.getName(), entityStatus);
            System.out.println(String.format("%s: %s", entity.getName(), entityStatus));
        }
        System.out.println("[End] -- ");
    }

    private EntityStatus mapEntityStatus(BrooklynEntityStatus status) {
        switch (status) {
            case RUNNING:
                return EntityStatus.STARTED;
            case ON_FIRE:
                return EntityStatus.FAILED;
            case CREATED:
            default:
                return EntityStatus.UNAVAILABLE;
        }
    }
}
