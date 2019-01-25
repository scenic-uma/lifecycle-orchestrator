package org.scenic.orchestrator.core.deployer.dto;

/**
 * Created by Jose on 25/01/19.
 */
public class EffectorTemplate {

    private static final String EFFECTOR_REQUEST = "/v1/applications/%s/entities/%s/effectors/%s?timeout=never";

    private String effectorName;

    private String applicationId;

    private String entityId;

    public EffectorTemplate(String applicationId, String entityId, String effectorName) {
        this.effectorName = effectorName;
        this.applicationId = applicationId;
        this.entityId = entityId;
    }

    public String getEffectorName() {
        return effectorName;
    }

    public void setEffectorName(String effectorName) {
        this.effectorName = effectorName;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String composeUri() {
        return String.format(EFFECTOR_REQUEST, applicationId, entityId, effectorName);
    }
}
