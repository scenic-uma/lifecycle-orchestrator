package org.scenic.orchestrator.core.service;

import org.scenic.orchestrator.core.dto.RunningAppContext;

/**
 * Created by Jose on 23/01/19.
 */
public interface DeploymentOrchestrator {

    void deploy(RunningAppContext runningAppContext) throws InterruptedException;

}
