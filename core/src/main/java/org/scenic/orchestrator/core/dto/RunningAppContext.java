package org.scenic.orchestrator.core.dto;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.scenic.orchestrator.core.deployer.dto.BrooklynEntityStatus;
import org.scenic.orchestrator.core.deployer.dto.CustomApplicationEntities;
import org.scenic.orchestrator.core.deployer.dto.CustomEntity;
import org.scenic.orchestrator.core.pivotal.PivotalClient;

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
    private PivotalClient pivotalClient;


    public RunningAppContext(String applicationName, ApplicationStatus status, Plan plan, String applicationTopology, PivotalClient pivotalClient) {
        this.status = status;
        this.applicationName = applicationName;
        this.plan = plan;
        this.applicationTopology = applicationTopology;
        this.pivotalClient = pivotalClient;
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
            EntityStatus entityStatus = mapEntityStatus(entity);
            status.setCurrentEntityStatus(entity.getName(), entityStatus);
            System.out.println(String.format("%s: %s", entity.getName(), entityStatus));
        }
        System.out.println("[End] -- ");
    }

    boolean hasCloudResources(CustomEntity entity) {
        return entity.hasSshLiveCloudResources() || hasPaasResourcesAssociated(entity);
    }


    private EntityStatus mapEntityStatus(CustomEntity entity) {
        BrooklynEntityStatus status = entity.status();
        boolean hasLiveCloudResources = hasCloudResources(entity);
        switch (status) {
            case RUNNING:
                return EntityStatus.STARTED;
            case STOPPED:
                return hasLiveCloudResources ? EntityStatus.STOPPED : EntityStatus.UNAVAILABLE;
            case ON_FIRE:
                return EntityStatus.FAILED;
            case CREATED:
            default:
                return EntityStatus.UNAVAILABLE;
        }
    }

    private boolean hasPaasResourcesAssociated(CustomEntity entity) {
        System.out.println("checking if pass " + entity.getName());
        if (entity.isInPaas()) {
            System.out.println("is pass ");
            boolean r = applicationExistsInPivotal(entity);
            System.out.println("Application exist?" + r);
            System.out.println("-------------------------");
            return r;
        }
        System.out.println("is NOt pass ");
        System.out.println("-------------------------");
        return false;
    }

    private boolean applicationExistsInPivotal(CustomEntity entity) {
        String webName = webAppDeployedWar(entity);
        System.out.println("checking paas resources with name==> " + webName);
        Boolean result = null;
        int loop = 0;
        while (result == null) {
            try {
                result = pivotalClient.applicationExist(webName);
            } catch (Exception e) {
                loop++;
                if (loop > 11) {
                    throw e;
                }
            }
        }
        return result;
    }

    private String webAppDeployedWar(CustomEntity entity) {
        return toStringList(entity.getSensor("webapp.deployedWars")).get(0);
    }

    private List<String> toStringList(String value) {
        return stream(value.replace("[", "").replace("]", "").split(",")).map(String::trim).collect(toList());
    }

}
