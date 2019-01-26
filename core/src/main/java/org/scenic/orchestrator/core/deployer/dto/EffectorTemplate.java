package org.scenic.orchestrator.core.deployer.dto;

import org.scenic.orchestrator.core.dto.RunningAppContext;

/**
 * Created by Jose on 25/01/19.
 */
public class EffectorTemplate {

    private static final String EFFECTOR_REQUEST = "/v1/applications/%s/entities/%s/effectors/%s?timeout=never";

    private String body;

    private String effectorName;

    private RunningAppContext application;

    private String entityId;

    public EffectorTemplate(RunningAppContext application, String entityId, String effectorName) {
        this.effectorName = effectorName;
        this.application = application;
        this.entityId = entityId;
        this.body = null;
    }

    public EffectorTemplate(RunningAppContext application, String entityId, String effectorName, String body) {
        this.effectorName = effectorName;
        this.application = application;
        this.entityId = entityId;
        this.body = body;
    }

    public String getEffectorName() {
        return effectorName;
    }

    public void setEffectorName(String effectorName) {
        this.effectorName = effectorName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String composeUri() {
        return String.format(EFFECTOR_REQUEST, application.getAppId(), entityId, effectorName);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getEntityName() {
        return application.getEntityById(entityId).getName();
    }

    public RunningAppContext getApplication() {
        return application;
    }

    public void setApplication(RunningAppContext application) {
        this.application = application;
    }
}
