package org.scenic.orchestrator.core.service;

import org.scenic.orchestrator.core.deployer.DeployerProxy;
import org.scenic.orchestrator.core.dto.RunningAppContext;
import org.scenic.orchestrator.core.service.plan.PlanExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by Jose on 23/01/19.
 */
@Component
public class AsycnDeploymentOrchestrator implements DeploymentOrchestrator {


    private final DeployerProxy deployerProxy;
    private final PlanExecutor planExecutor;

    public AsycnDeploymentOrchestrator(DeployerProxy deployerProxy, PlanExecutor planExecutor) {
        this.deployerProxy = deployerProxy;
        this.planExecutor=planExecutor;
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public void deploy(RunningAppContext runningAppContext) throws InterruptedException {
        System.out.println("Start to deploy application: " + runningAppContext.getApplicationName());
        String appId = deployerProxy.deployApp(runningAppContext.getApplicationName(), runningAppContext.getApplicationTopology());
        runningAppContext.setAppId(appId);
        System.out.println("Add to the deployer application: " + runningAppContext.getApplicationName() + " with id " + appId);
        runningAppContext.setEntities(deployerProxy.getApplicationEntities(appId));

        planExecutor.executePlan(runningAppContext);
    }





}
