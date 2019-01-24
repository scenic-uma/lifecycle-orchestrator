package org.scenic.orchestrator.core.service;

import org.scenic.orchestrator.core.deployer.DeployerProxy;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Jose on 23/01/19.
 */
@Component
public class AsycnDeploymentOrchestrator implements DeploymentOrchestrator {


    private final DeployerProxy deployerProxy;

    public AsycnDeploymentOrchestrator(DeployerProxy deployerProxy) {
        this.deployerProxy = deployerProxy;
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public void deploy(RunningAppContext runningAppContext) throws InterruptedException {

        System.out.println("Start to deploy application: " + runningAppContext.getApplicationName());
        deployerProxy.deployApp(runningAppContext.getApplicationName(), runningAppContext.getApplicationTopology());

    }


}
