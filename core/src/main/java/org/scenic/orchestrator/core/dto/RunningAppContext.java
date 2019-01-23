package org.scenic.orchestrator.core.dto;

/**
 * Created by Jose on 23/01/19.
 */
public class RunningAppContext {

    private ApplicationStatus status;

    private String applicationName;

    private Plan plan;

    private String applicationTopology;


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
}
