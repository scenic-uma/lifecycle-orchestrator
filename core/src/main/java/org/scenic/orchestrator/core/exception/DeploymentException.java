package org.scenic.orchestrator.core.exception;

/**
 * Created by Jose on 15/05/18.
 */
public class DeploymentException extends IllegalStateException {

    private String applicationId;
    private String taskId;

    public DeploymentException(String msg){
        super(msg);
    }

    public DeploymentException(String msg, String applicationId){
        super(msg);
        this.applicationId=applicationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
