package org.scenic.orchestrator.core.dto;

/**
 * Created by Jose on 23/01/19.
 */
public class RunningAppContext {

    private ApplicationStatus status;

    private String applicationName;

    private Plan plan;


    public RunningAppContext(String applicationName, ApplicationStatus status, Plan plan) {
        this.status = status;
        this.applicationName = applicationName;
        this.plan = plan;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
